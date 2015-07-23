package com.ikwang.fungi.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ikwang.fungi.Consts;
import com.ikwang.fungi.IIDGenerator;
import com.ikwang.fungi.dao.IIKwangEventDAO;
import com.ikwang.fungi.dao.ITransactionDAO;
import com.ikwang.fungi.model.IKwangEvent;
import com.ikwang.fungi.model.Transaction;
import com.ikwang.fungi.model.UpdateParam;
import com.ikwang.fungi.model.exception.BalanceInsufficientException;
import com.ikwang.fungi.model.exception.InvalidStateException;
import com.ikwang.fungi.model.search.SearchBase;
import com.ikwang.fungi.service.IAccountService;
import com.ikwang.fungi.service.IIKwangEventService;

public class IKwangEventService implements IIKwangEventService {
	private static final Logger logger = LoggerFactory.getLogger(BillService.class);
	@Autowired
	private IAccountService accountService;
	
	@Autowired
	private ITransactionDAO transactionDAO;
	@Autowired
	private IIKwangEventDAO iKwangEventDAO;
	@Autowired
	private IIDGenerator idGenerator;
	public IKwangEventService() {
		 
	}

	@Override
	public int create(IKwangEvent event) {
		event.setId("E"+idGenerator.generate());
		return iKwangEventDAO.create(event);
	}

	@Override
	public IKwangEvent get(String id) {
		
		return iKwangEventDAO.getById(id);
	}

	
	protected int update(UpdateParam param) {
		return iKwangEventDAO.update(param);
	}

	@Override
	public Transaction deposit(IKwangEvent event, double amount, String account,boolean freeze) throws BalanceInsufficientException {
 
		try{
		 Transaction ret= accountService.transferOut(account, event.getId(),Consts.TRANSACTION_TYPE_DEPOSIT, amount, "eventdeposit", event.getId(), freeze);
		 UpdateParam param=new UpdateParam().field("id", event.getId());
		 String field=freeze?"frozen":"deposit";
		 param.field(field, amount);
		 update(param);
		 if(freeze){
			 event.setFrozen(event.getFrozen()+amount); 
		 }else{
			 event.setDeposit(event.getDeposit()+amount);
		 }
		 
		 return ret;
		} catch(BalanceInsufficientException e){
			logger.error("deposit failed",e);
			throw e;
		}
		  
	}

	@Override
	public Transaction reward(IKwangEvent event, double amount, String account,
			String remark) throws InvalidStateException {
	    if(event.getDeposit() - event.getRewarded() < amount){
	    	throw new InvalidStateException("insufficient balance");
	    }
	    Transaction ret= accountService.transferIn(event.getId(), account,Consts.TRANSACTION_TYPE_REWARD, amount, remark, event.getId(), false);
	    UpdateParam param=new UpdateParam().field("id", event.getId());
		 
		param.field("rewarded", amount);
		update(param);
		 event.setRewarded(event.getRewarded()+amount);
		return ret;
	}

	protected void abort(Transaction trans){
		UpdateParam param=UpdateParam.create("status", Consts.TRANSACTION_STATUS_ABORTED)
				.field("id", trans.getId());
		transactionDAO.update(param);
	}
	@Override
	public void unfreeze(IKwangEvent event, double amount)
			throws BalanceInsufficientException {
	    if(event.getFrozen() < amount){
	    	throw new BalanceInsufficientException("insufficient balance");
	    }
	    if(amount <=0 )
	    	amount=event.getFrozen();
	    SearchBase s=new SearchBase().field("referenceId", event.getId()).field("status", Consts.TRANSACTION_STATUS_FROZEN);
		 List<Transaction> transList=transactionDAO.search(s);
		 double left=amount;
		 for (Transaction transaction : transList) {
			 if(transaction.getStatus() == Consts.TRANSACTION_STATUS_FROZEN){
				 if(left >= transaction.getAmount()){
					 accountService.release(transaction,transaction.getAmount());
					 left-=transaction.getAmount();
				 }
				 else{
					 abort(transaction);
					 Transaction tr1=Transaction.create(left, transaction.getType(),transaction.getPaymentMethod(), transaction.getCurrency(), transaction.getPayerId(),
							 transaction.getPayeeId(), transaction.getPayerAccount(), transaction.getPayeeAccount(), 
							 transaction.getRemark(), transaction.getReferenceId(), Consts.TRANSACTION_STATUS_RELEASED);
					 Transaction tr2=Transaction.create(transaction.getAmount()-left, transaction.getType(), transaction.getPaymentMethod(),transaction.getCurrency(), transaction.getPayerId(),
							 transaction.getPayeeId(), transaction.getPayerAccount(), transaction.getPayeeAccount(), 
							 transaction.getRemark(), transaction.getReferenceId(), Consts.TRANSACTION_STATUS_FROZEN);
					 transactionDAO.create(tr1);
					 transactionDAO.create(tr2);
				 }
				 if(left <= 0){
					 break;
				 }
			 }
				//accountService.release(transaction);
		}
		UpdateParam p=UpdateParam.create("id", event.getId()).field("deposit", amount).field("frozen", -amount);;
		update(p);
		event.setFrozen(event.getFrozen()-amount);
		event.setDeposit(event.getDeposit()+amount);
	}

	public void close(IKwangEvent event) {
		double refund=event.getDeposit()-event.getRewarded();
		
		if(refund>0){//refund before closing the event
			 accountService.transferIn(event.getId(), event.getCreator(),Consts.TRANSACTION_TYPE_REFUND, refund, "refund", event.getId(), false);
		}
		 SearchBase s=new SearchBase().field("referenceId", event.getId()).field("status", Consts.TRANSACTION_STATUS_FROZEN);
		 List<Transaction> transList=transactionDAO.search(s);
		 if(transList.isEmpty() == false){//canceling all frozen transaction before closing the event;
			 for (Transaction transaction : transList) {
				accountService.cancel(transaction);
			}
		 }
		UpdateParam p=UpdateParam.create("id", event.getId()).field("status", Consts.EVENT_STATUS_CLOSED).field("closeTime", new Date())
				.field("rewarded", refund).field("frozen", -event.getFrozen());
		update(p);	 
	}

}
