package com.ikwang.fungi.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.ikwang.fungi.model.Bill;
import com.ikwang.fungi.model.exception.AccountNotExistException;
import com.ikwang.fungi.model.exception.BalanceInsufficientException;
import com.ikwang.fungi.model.exception.InvalidStateException;
import com.ikwang.fungi.model.exception.ParameterValidationException;
import com.ikwang.fungi.model.exception.UnsupportedOperationException;
import com.ikwang.fungi.model.search.BillSearch;

public interface IBillService {

	public abstract Bill create(Bill bill) throws DataAccessException, AccountNotExistException;

	public abstract Bill get(String id);

	public abstract List<Bill> search(BillSearch bs);
	@Transactional(rollbackFor=Exception.class)
	public abstract void pay(Bill bill) throws  BalanceInsufficientException, InvalidStateException;

	@Transactional(rollbackFor=Exception.class)
	public abstract void refund(Bill bill,double amount) throws  BalanceInsufficientException, ParameterValidationException, UnsupportedOperationException, InvalidStateException;
	@Transactional(rollbackFor=Exception.class)
	public abstract void release(Bill bill,double amount) throws InvalidStateException,BalanceInsufficientException;
	@Transactional(rollbackFor=Exception.class)
	public abstract void cancel(Bill bill) throws InvalidStateException;

}