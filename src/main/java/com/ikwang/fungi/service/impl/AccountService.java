package com.ikwang.fungi.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ikwang.fungi.Consts;
import com.ikwang.fungi.dao.IAccountDAO;
import com.ikwang.fungi.dao.ITransactionDAO;
import com.ikwang.fungi.model.Account;
import com.ikwang.fungi.model.Transaction;
import com.ikwang.fungi.model.UpdateParam;
import com.ikwang.fungi.model.exception.AccountNotExistException;
import com.ikwang.fungi.model.exception.BalanceInsufficientException;
import com.ikwang.fungi.service.IAccountService;

public class AccountService implements IAccountService {

	
	protected static final Logger logger = LoggerFactory.getLogger(AccountService.class);
	
	@Autowired
	private IAccountDAO accountDAO;
	@Autowired
	private ITransactionDAO transactionDAO;
	@Override
	public void deposit(String account, double amount, int paymentMethod,
			String payerAccount, String payeeAccount,String remark) throws AccountNotExistException {
		 Account a=get(account);
		 if(a == null){
			 throw new AccountNotExistException();
		 }
		 Transaction trans=Transaction.create(amount, Consts.TRANSACTION_TYPE_DEPOSIT,paymentMethod, Consts.CURRENCY_DEFAULT, "", account, payerAccount, 
				 payeeAccount, remark, account, Consts.TRANSACTION_STATUS_COMPLETED);
		  transactionDAO.create(trans);
		 UpdateParam param=new UpdateParam().field("id", account).field("deposit", amount);
		 accountDAO.update(param);
		 logAccountChanges(account, amount, true, String.format("deposit:%s,%s,%s", paymentMethod,payerAccount,payeeAccount));
	}

	@Override
	public Account get(String id) {
		 
		return accountDAO.getById(id);
	}
	protected void logAccountChanges(String account,double amount,boolean in,String remark){
		logger.debug(String.format("%s#%s%s#%s", account,in?"+":"-",amount,remark));
	}

	@Override
	public Transaction transfer(String from, String to,int transactionType, double amount,String remark,String reference,boolean freeze) throws BalanceInsufficientException {
		//TODO Parallel checking
		Account payer=get(from);
		//Account payee=get(to);
		if(payer.getBalance() < amount){
			throw new BalanceInsufficientException("balance of account is not sufficient");
		}
		Transaction ret=Transaction.create(amount,transactionType, Consts.PAYMENT_METHOD_AIGUANG,  Consts.CURRENCY_DEFAULT,
				from, to, from, to, remark, reference, freeze?Consts.TRANSACTION_STATUS_FROZEN:Consts.TRANSACTION_STATUS_COMPLETED);
 
		
		transactionDAO.create(ret);

		 UpdateParam param=new UpdateParam().field("id", from).field("deposit",-amount);
		 accountDAO.update(param);

		 if(!freeze){
			 UpdateParam param1=new UpdateParam().field("id", to).field("deposit",amount);
			 accountDAO.update(param1);

		 }
		 String rm=remark+","+reference+","+transactionType;
		 logAccountChanges(from, amount, false, rm);
		 if(!freeze){			 logAccountChanges(to, amount, true, rm);}
		return ret;
	} 
	@Override
	public Transaction transferOut(String from,String to,int transactionType,double amount, String remark,
			String reference, boolean freeze) throws BalanceInsufficientException {
		 
		Account payer=get(from);
		if(payer.getBalance() < amount){
			throw new BalanceInsufficientException("balance of account is not sufficient");
		}
		Transaction ret=Transaction.create(amount,transactionType, Consts.PAYMENT_METHOD_AIGUANG,  Consts.CURRENCY_DEFAULT,
				from, to, from, to, remark, reference, freeze?Consts.TRANSACTION_STATUS_FROZEN:Consts.TRANSACTION_STATUS_COMPLETED);
 
		
		transactionDAO.create(ret);

		 UpdateParam param=new UpdateParam().field("id", from).field("deposit",-amount);
		 accountDAO.update(param);
		 String rm=remark+","+reference+","+transactionType;
		 logAccountChanges(from, amount, false, rm);
		return ret;
	}

	@Override
	public Transaction transferIn(String from,String to,int transactionType, double amount, String remark,
			String reference, boolean freeze) {
		Transaction ret=Transaction.create(amount,transactionType, Consts.PAYMENT_METHOD_AIGUANG,  Consts.CURRENCY_DEFAULT,
				from, to, from, to, remark, reference, freeze?Consts.TRANSACTION_STATUS_FROZEN:Consts.TRANSACTION_STATUS_COMPLETED);
		transactionDAO.create(ret);
		 if(!freeze){
			 UpdateParam param1=new UpdateParam().field("id", to).field("deposit",amount);

			 accountDAO.update(param1);
			 String rm=remark+","+reference+","+transactionType;

			 logAccountChanges(to, amount, true, rm);
		 }
		return ret;
	}
	protected void abort(Transaction trans){
		UpdateParam param=UpdateParam.create("status", Consts.TRANSACTION_STATUS_ABORTED)
				.field("closeTime", new Date())
				.field("id", trans.getId());
		transactionDAO.update(param);
	}
	@Override
	public void release(Transaction transaction,double amount) throws BalanceInsufficientException {
		if(transaction.getStatus() != Consts.TRANSACTION_STATUS_FROZEN){
			return;
		}
		if(amount <= 0)
			amount=transaction.getAmount();
		if(transaction.getAmount()<amount){
			throw new BalanceInsufficientException("insufficient balance in this transaction");
		}

		 
		 if(transaction.getAmount() == amount){//release the whole frozen transaction
			 
			 UpdateParam param= UpdateParam.create("id", transaction.getId())
					 .field("status", Consts.TRANSACTION_STATUS_RELEASED);
			 transactionDAO.update(param);
		 }
		 else{//release partial transaction,split the transaction into two transactions
			 abort(transaction);//abort the transaction
			 Transaction tr1=Transaction.create(amount, transaction.getType(),transaction.getPaymentMethod(), transaction.getCurrency(), transaction.getPayerId(),
					 transaction.getPayeeId(), transaction.getPayerAccount(), transaction.getPayeeAccount(), 
					 transaction.getRemark(), transaction.getReferenceId(), Consts.TRANSACTION_STATUS_RELEASED);
			 tr1.setCloseTime(new Date());
			 Transaction tr2=Transaction.create(transaction.getAmount()-amount, transaction.getType(), transaction.getPaymentMethod(),transaction.getCurrency(), transaction.getPayerId(),
					 transaction.getPayeeId(), transaction.getPayerAccount(), transaction.getPayeeAccount(), 
					 transaction.getRemark(), transaction.getReferenceId(), Consts.TRANSACTION_STATUS_FROZEN);
			 transactionDAO.create(tr1);
			 transactionDAO.create(tr2);
		 }
		 UpdateParam param1=new UpdateParam().field("id", transaction.getPayeeId()).field("deposit",amount);

		 accountDAO.update(param1);
		 logAccountChanges(transaction.getPayeeId(), amount, true, "release,transaction:"+transaction.getId());
	}

	@Override
	public void cancel(Transaction transaction) {
		if(transaction.getStatus() != Consts.TRANSACTION_STATUS_FROZEN){
			return;
		}
		 UpdateParam param1=new UpdateParam().field("id", transaction.getPayerId()).field("deposit",transaction.getAmount());

		 accountDAO.update(param1);
		 UpdateParam param= UpdateParam.create("id", transaction.getId())
				 .field("closeTime", new Date())
				 .field("status", Consts.TRANSACTION_STATUS_CANCELLED);
		 transactionDAO.update(param);
		 logAccountChanges( transaction.getPayerId(),  transaction.getAmount(), true, "cancel,transaction:"+transaction.getId());
	}

	@Override
	public void create(Account account) {
		 accountDAO.create(account);
	}

	@Override
	public void withdraw(String id, double amount, String payeeAccount,
			String remark) throws AccountNotExistException,
			BalanceInsufficientException {
		 Account a=get(id);
		 if(a == null){
			 throw new AccountNotExistException();
		 }
		 Transaction trans=Transaction.create(amount, Consts.TRANSACTION_TYPE_WITHDRAW,Consts.PAYMENT_METHOD_UNIONPAY, Consts.CURRENCY_DEFAULT, id,"", id, 
				 payeeAccount, "withdraw:"+remark, id, Consts.TRANSACTION_STATUS_COMPLETED);
		  transactionDAO.create(trans);
		 UpdateParam param=new UpdateParam().field("id", id).field("deposit", -amount);
		 accountDAO.update(param);
		 logAccountChanges( id, amount, false, "withdraw,"+payeeAccount+","+remark);

	}



}
