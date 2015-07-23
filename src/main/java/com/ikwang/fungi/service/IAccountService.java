package com.ikwang.fungi.service;

import org.springframework.transaction.annotation.Transactional;

import com.ikwang.fungi.model.Account;
import com.ikwang.fungi.model.Transaction;
import com.ikwang.fungi.model.exception.AccountNotExistException;
import com.ikwang.fungi.model.exception.BalanceInsufficientException;

public interface IAccountService {
	@Transactional(rollbackFor=Exception.class)
	void deposit(String id,double amount,int paymentMethod,String payerAccount,String payeeAccount,String remark) throws AccountNotExistException;
	@Transactional(rollbackFor=Exception.class)
	void withdraw(String id,double amount,String payeeAccount,String remark)throws AccountNotExistException,BalanceInsufficientException;
	void create(Account account);
	
	Account get(String id);
	@Transactional(rollbackFor=Exception.class)
	/**
	 * transfer a certain amount of money from one account to another
	 * @param from  
	 * @throws BalanceInsufficientException 
	 * */
	Transaction transfer(String from,String to,int transactionType,double amount,String remark,String reference,boolean freeze) throws  BalanceInsufficientException;
	@Transactional(rollbackFor=Exception.class)
	/**
	 * transfer a certain amount of money out from an account
	 * */
	Transaction transferOut(String from,String to,int transactionType,double amount,String remark,String reference,boolean freeze) throws BalanceInsufficientException;
	@Transactional(rollbackFor=Exception.class)
	/**
	 * transfer a certain amount of money in to an account
	 * */
	Transaction transferIn(String from,String to,int transactionType,double amount,String remark,String reference,boolean freeze);
	@Transactional(rollbackFor=Exception.class)
	void release(Transaction transaction,double amount) throws BalanceInsufficientException;
	
	@Transactional(rollbackFor=Exception.class)
	void cancel(Transaction transaction);
}
