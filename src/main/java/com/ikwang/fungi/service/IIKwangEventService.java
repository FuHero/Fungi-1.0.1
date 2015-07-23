package com.ikwang.fungi.service;

import org.springframework.transaction.annotation.Transactional;

import com.ikwang.fungi.model.IKwangEvent;
import com.ikwang.fungi.model.Transaction;
import com.ikwang.fungi.model.exception.BalanceInsufficientException;
import com.ikwang.fungi.model.exception.InvalidStateException;

public interface IIKwangEventService {
	public abstract int create(IKwangEvent event);
	public abstract IKwangEvent get(String id);
 
	@Transactional(rollbackFor=Exception.class)
	public abstract Transaction deposit(IKwangEvent event,double amount,String account, boolean freeze) throws BalanceInsufficientException;
	@Transactional(rollbackFor=Exception.class)
	public abstract Transaction reward(IKwangEvent event,double amount,String account,String remark) throws InvalidStateException;
	@Transactional(rollbackFor=Exception.class)
	public abstract void unfreeze(IKwangEvent event,double amount) throws BalanceInsufficientException;
	@Transactional(rollbackFor=Exception.class)
	public abstract void close(IKwangEvent e);
}
