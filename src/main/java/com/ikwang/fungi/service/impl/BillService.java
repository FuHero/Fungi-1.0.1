package com.ikwang.fungi.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.ikwang.fungi.Consts;
import com.ikwang.fungi.IIDGenerator;
import com.ikwang.fungi.dao.IBillDAO;
import com.ikwang.fungi.dao.ITransactionDAO;
import com.ikwang.fungi.event.PayEvent;
import com.ikwang.fungi.model.Account;
import com.ikwang.fungi.model.Bill;
import com.ikwang.fungi.model.Transaction;
import com.ikwang.fungi.model.UpdateParam;
import com.ikwang.fungi.model.exception.AccountNotExistException;
import com.ikwang.fungi.model.exception.BalanceInsufficientException;
import com.ikwang.fungi.model.exception.InvalidStateException;
import com.ikwang.fungi.model.exception.ParameterValidationException;
import com.ikwang.fungi.model.exception.UnsupportedOperationException;
import com.ikwang.fungi.model.search.BillSearch;
import com.ikwang.fungi.model.search.SearchBase;
import com.ikwang.fungi.model.search.TransactionSearch;
import com.ikwang.fungi.service.IAccountService;
import com.ikwang.fungi.service.IBillService;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

public class BillService implements IBillService {
	protected static final Logger logger = LoggerFactory.getLogger(BillService.class);
	@Autowired
	private IIDGenerator idGenerator;
	@Autowired
	private IBillDAO billDAO;
	
	@Autowired
	private IAccountService accountService;
	
	@Autowired
	private ITransactionDAO transactionDAO;
	@SuppressWarnings("unused")
	private PayEventProducer eventProducer;
	public BillService(){
		
		//for future use;
		//initDisruptor();
	}
	@SuppressWarnings({ "unchecked", "unused" })
	private void initDisruptor(){
		 // Executor that will be used to construct new threads for consumers
        Executor executor = Executors.newCachedThreadPool();

        // The factory for the event
        PayEventFactory factory = new PayEventFactory();

        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 1024;

        // Construct the Disruptor
        Disruptor<PayEvent> disruptor = new Disruptor<>(factory, bufferSize, executor);

        // Connect the handler
        disruptor.handleEventsWith(new PayEventHandler());

        // Start the Disruptor, starts all threads running
        disruptor.start();

        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<PayEvent> ringBuffer = disruptor.getRingBuffer();

        eventProducer = new PayEventProducer(ringBuffer);
	}
	/* (non-Javadoc)
	 * @see com.ikwang.fungi.service.impl.IBillService#saveBill(com.ikwang.fungi.model.Bill)
	 */
	@Override
	public Bill create(Bill bill) throws DataAccessException, AccountNotExistException{
		Account from=accountService.get(bill.getPayerId());
		Account to =accountService.get(bill.getPayeeId());
		if(from == null || to == null){
			throw new AccountNotExistException("account not exist");
			//return null;
		}
		bill.setPayeeName(to.getName());
		bill.setPayerName(from.getName());
		bill.setId(idGenerator.generate());
		billDAO.create(bill);
		return bill;
	}
	/* (non-Javadoc)
	 * @see com.ikwang.fungi.service.impl.IBillService#getBill(java.lang.String)
	 */
	@Override                                                                                                         
	public Bill get(String id){
		//TODO cache may be needed in future
		return billDAO.getById(id);
	}
	/* (non-Javadoc)
	 * @see com.ikwang.fungi.service.impl.IBillService#searchBill(com.ikwang.fungi.model.search.BillSearch)
	 */
	@Override
	public List<Bill> search(BillSearch bs){
		return billDAO.search(bs);
	}
	public Account getAccount(String id){
		return accountService.get(id);
	}

	/* (non-Javadoc)
	 * @see com.ikwang.fungi.service.impl.IBillService#pay(com.ikwang.fungi.model.Bill, int, double, java.lang.String, java.lang.String)
	 */
	@Override
	public void pay(Bill bill) throws  BalanceInsufficientException, InvalidStateException {
		//eventProducer.onData(bill, paymentType, useAccountBalance, amount, payerAccount, payeeAccount);
		checkStatus(bill);
		boolean guranteed=bill.getTradeType() == Consts.TRADE_TYPE_GUARANTED;
		
		//TODO bill status check
		Transaction trans=accountService.transfer(bill.getPayerId(), bill.getPayeeId(),Consts.TRANSACTION_TYPE_NORMAL, bill.getAmount()-bill.getPaid(), "bill", bill.getId(),
				guranteed);
		int status=Consts.BILL_STATUS_COMPLETED;
		if(guranteed){
			status=Consts.BILL_STATUS_PAID;
		}
		
		Date d=new Date();
		UpdateParam param=new UpdateParam().field("id", bill.getId())
				.field("paid", trans.getAmount())
				.field("paymentTime", d)
				.field("status", status)
				.field("version",bill.getVersion());
		if(!guranteed){
			param.field("closeTime", d)
			.field("released", trans.getAmount());
			bill.setCloseTime(d);
			bill.setReleased(trans.getAmount());
		}
		if(billDAO.update(param)==0){
			throw new InvalidStateException("the bill information has changed,please refresh first.");
		}
		bill.setPaid(bill.getPaid()+trans.getAmount());
		bill.setPaymentTime(d);
		bill.setStatus(status);
	}
	
	
	protected boolean checkStatus(Bill bill) throws InvalidStateException{
		if(bill.getStatus() == Consts.BILL_STATUS_CANCELLED || bill.getStatus() == Consts.BILL_STATUS_COMPLETED){
			throw new InvalidStateException("the bill is closed");
		}
		return bill.getTradeType() == Consts.TRADE_TYPE_GUARANTED;
	}
	
	@Override
	public void refund(Bill bill, double amount) throws ParameterValidationException, BalanceInsufficientException, UnsupportedOperationException, InvalidStateException {
		if(checkStatus(bill) == false){
			throw new UnsupportedOperationException("only guaranted trade supports a refund operation");
		}
		double available=bill.getAvailable();
		if(amount <= 0){
			amount=available;
		}
		 if(available<amount){
			 throw new BalanceInsufficientException("insufficient balance");
		 }
		 //get all transactions in this bill
		 SearchBase s=new SearchBase().field("referenceId", bill.getId()).field("status",new int[]{Consts.TRANSACTION_STATUS_FROZEN});
		 List<Transaction> transList=transactionDAO.search(s);
		 
		 boolean cancel=bill.getAvailable() == amount;
		 for (Transaction transaction : transList) {
			if(cancel)//cancel all transactions
				accountService.cancel(transaction);
			else//release all transactions first and create a refund payment
				accountService.release(transaction,0);
		}
		 if(cancel == false){//create a refund payment
			 accountService.transfer(bill.getPayeeId(), bill.getPayerId(),Consts.TRANSACTION_TYPE_REFUND, amount, "refund", bill.getId(), false);
		 }
		 Date d=new Date();
			UpdateParam param=new UpdateParam().field("id", bill.getId())
					.field("refunded", amount).field("released", available-amount)
					.field("closeTime", d)
					.field("status", Consts.BILL_STATUS_REFOUNDED)
					.field("version",bill.getVersion());
			
			if(billDAO.update(param)==0){
				throw new InvalidStateException("the bill information has changed,please refresh first.");
			}
			bill.setRefunded(bill.getRefunded()+amount);
			bill.setReleased(bill.getReleased()+available-amount);
			bill.setCloseTime(d);
			bill.setStatus(Consts.BILL_STATUS_REFOUNDED);
	}
	
	
	@Override
	public void release(Bill bill,double amount) throws InvalidStateException, BalanceInsufficientException {
		if(checkStatus(bill) == false){
			 throw new InvalidStateException("invalid bill state");}
		 if(amount <= 0){
			 amount=bill.getAvailable();
		 }
		 if(bill.getAvailable()<amount){
			 throw new BalanceInsufficientException("invalid amount for this bill");
		 }
		 TransactionSearch s=(TransactionSearch) new TransactionSearch().field("referenceId", bill.getId());
		 s.status(Consts.TRANSACTION_STATUS_FROZEN);
		 List<Transaction> transList=transactionDAO.search(s);
		 double left=amount;
		 for (Transaction transaction : transList) {
			 double release=0;
			 if(left > transaction.getAmount()){
				 left-=transaction.getAmount();
			 }
			 else{
				 release=left;
				 left=0;
			 }
			accountService.release(transaction,release);
			if(left<=0)
				break;
		}
		 
		 Date d=new Date();
		 bill.setReleased(bill.getReleased()+amount);
		 
		 UpdateParam param=new UpdateParam().field("id", bill.getId()).field("released", amount)
				 .field("version",bill.getVersion());
					//
		 	if(bill.getPaid()<=bill.getReleased()+bill.getRefunded()){
		 		param.field("status", Consts.BILL_STATUS_COMPLETED)
		 		.field("closeTime", d);
				bill.setCloseTime(d);
				bill.setStatus(Consts.BILL_STATUS_COMPLETED);
		 	}
			if(billDAO.update(param)==0){
				throw new InvalidStateException("the bill information has changed,please refresh first.");
			}

	}

	@Override
	public void cancel(Bill bill) throws InvalidStateException {
		 checkStatus(bill);
		 if(bill.getPaid() > 0){//can't cancel an already paid bill,should go through refund instead
			 throw new InvalidStateException("can't cancel an already paid bill");
		 }
		 Date d=new Date();
			UpdateParam param=new UpdateParam().field("id", bill.getId())
					.field("closeTime", d) 
					.field("status", Consts.BILL_STATUS_CANCELLED)
					.field("version",bill.getVersion());
			
			if(billDAO.update(param)==0){
				throw new InvalidStateException("the bill information has changed,please refresh first.");
			}
			bill.setCloseTime(d);
			bill.setStatus(Consts.BILL_STATUS_CANCELLED);
	}

	private static class PayEventFactory implements EventFactory<PayEvent>
	{
	    public PayEvent newInstance()
	    {
	        return new PayEvent();
	    }
	}
	private static class PayEventProducer
	{
	    private final RingBuffer<PayEvent> ringBuffer;

	    public PayEventProducer(RingBuffer<PayEvent> ringBuffer)
	    {
	        this.ringBuffer = ringBuffer;
	    }

	    @SuppressWarnings("unused")
		public void onData(Bill bill,int paymentType,boolean useAccountBalance,double amount,String payerAccount,String payeeAccount)
	    {
	        long sequence = ringBuffer.next();  // Grab the next sequence
	        try
	        {
	        	PayEvent event = ringBuffer.get(sequence); // Get the entry in the Disruptor
	                                                        // for the sequence
	            event.fill(bill, paymentType, useAccountBalance, amount, payerAccount, payeeAccount);
	        }
	        finally
	        {
	            ringBuffer.publish(sequence);
	        }
	    }
	}
	private  class PayEventHandler implements EventHandler<PayEvent>
	{
	    public void onEvent(PayEvent event, long sequence, boolean endOfBatch)
	    {
	        // Bill bill=get(event.getBill().getId());
	         //TODO bill validation
	         if(event.getAmount()>0){//payment
	        	 
	         }
	    }
	}

}
