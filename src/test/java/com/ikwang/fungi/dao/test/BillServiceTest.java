package com.ikwang.fungi.dao.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ikwang.fungi.Consts;
import com.ikwang.fungi.model.Account;
import com.ikwang.fungi.model.Bill;
import com.ikwang.fungi.model.IKwangEvent;
import com.ikwang.fungi.model.exception.AccountNotExistException;
import com.ikwang.fungi.model.exception.BalanceInsufficientException;
import com.ikwang.fungi.model.exception.DepositException;
import com.ikwang.fungi.model.exception.InvalidStateException;
import com.ikwang.fungi.model.exception.ParameterValidationException;
import com.ikwang.fungi.model.exception.PayException;
import com.ikwang.fungi.model.exception.UnsupportedOperationException;
import com.ikwang.fungi.service.IAccountService;
import com.ikwang.fungi.service.IBillService;
import com.ikwang.fungi.service.impl.IKwangEventService;
import com.ikwang.fungi.util.Util;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "xml/*.xml")
public class BillServiceTest {

	@Autowired
	private IBillService billService;
	@Autowired
	private IAccountService accountService;
	@Test
	public void testInstant() throws DataAccessException, DepositException, InvalidStateException, PayException, AccountNotExistException, BalanceInsufficientException {
		String user="4349895@qq.com";
		String userTo="stoneloser@qq.com";
		Account from=accountService.get(user);
		Account to=accountService.get(userTo);
		double b1=from.getBalance();
		double b2=to.getBalance();
		
		Bill b=createBill(from, to, Consts.TRADE_TYPE_INSTANT);
		billService.create(b);
		billService.pay(b);
		 from=accountService.get(user);
		 to=accountService.get(userTo);
		double b3=from.getBalance();
		double b4=to.getBalance();
		Assert.assertTrue(b1==b3+b.getAmount());
		Assert.assertTrue(b2==b4-b.getAmount());
		Assert.assertTrue(b.getStatus() == Consts.BILL_STATUS_COMPLETED);
	}
	@Test
	public void testGuaranted() throws DataAccessException, DepositException, InvalidStateException, PayException, AccountNotExistException, BalanceInsufficientException {
		String user="4349895@qq.com";
		String userTo="stoneloser@qq.com";
		Account from=accountService.get(user);
		Account to=accountService.get(userTo);
		double b1=from.getBalance();
		double b2=to.getBalance();
		
		Bill b=createBill(from, to, Consts.TRADE_TYPE_GUARANTED);
		billService.create(b);
		//b.setVersion(-1);
		billService.pay(b);
		b=billService.get(b.getId());
		
		 from=accountService.get(user);
		 to=accountService.get(userTo);
		double b3=from.getBalance();
		double b4=to.getBalance();
		Assert.assertTrue(b1==b3+b.getAmount());
		Assert.assertTrue(b2==b4);
		Assert.assertTrue(b.getStatus() == Consts.BILL_STATUS_PAID);
		billService.release(b,4000);
		b=billService.get(b.getId());
		 to=accountService.get(userTo);
 
		 b4=to.getBalance();
		 Assert.assertTrue(b2==b4-b.getAmount()+1000);
		 Assert.assertTrue(b.getStatus() == Consts.BILL_STATUS_PAID);
			billService.release(b,1000);
			b=billService.get(b.getId());
			 to=accountService.get(userTo);
	 
			 b4=to.getBalance();
			 Assert.assertTrue(b2==b4-b.getAmount());
			 Assert.assertTrue(b.getStatus() == Consts.BILL_STATUS_COMPLETED);
	}
	@Test
	public void testRefund() throws DataAccessException, DepositException, InvalidStateException, PayException, AccountNotExistException, BalanceInsufficientException, ParameterValidationException, UnsupportedOperationException {
		String user="4349895@qq.com";
		String userTo="stoneloser@qq.com";
		Account from=accountService.get(user);
		Account to=accountService.get(userTo);
		double b1=from.getBalance();
		double b2=to.getBalance();
		
		Bill b=createBill(from, to, Consts.TRADE_TYPE_GUARANTED);
		billService.create(b);
		billService.pay(b);
		 from=accountService.get(user);
		 to=accountService.get(userTo);
		double b3=from.getBalance();
		double b4=to.getBalance();
		Assert.assertTrue(b1==b3+b.getAmount());
		Assert.assertTrue(b2==b4);
		Assert.assertTrue(b.getStatus() == Consts.BILL_STATUS_PAID);
		billService.refund(b, 2000);
		 to=accountService.get(userTo);
		 from=accountService.get(user);
		 b4=to.getBalance();
		 b3=from.getBalance();
		 Assert.assertTrue(b1-b.getAmount()+2000==b3);
		 Assert.assertTrue(b2+b.getAmount()-2000==b4);
		 Assert.assertTrue(b.getStatus() == Consts.BILL_STATUS_REFOUNDED);
	}
	
	
	public void testTransactionalRefund() throws DataAccessException, DepositException, InvalidStateException, PayException, AccountNotExistException, BalanceInsufficientException, ParameterValidationException, UnsupportedOperationException {
		String user="4349895@qq.com";
		String userTo="stoneloser@qq.com";
		Account from=accountService.get(user);
		Account to=accountService.get(userTo);
		double b1=from.getBalance();
		double b2=to.getBalance();
		
		Bill b=createBill(from, to, Consts.TRADE_TYPE_GUARANTED);
		billService.create(b);
		billService.pay(b);
		 from=accountService.get(user);
		 to=accountService.get(userTo);
		double b3=from.getBalance();
		double b4=to.getBalance();
		Assert.assertTrue(b1==b3+b.getAmount());
		Assert.assertTrue(b2==b4);
		Assert.assertTrue(b.getStatus() == Consts.BILL_STATUS_PAID);
		try{
		billService.refund(b, 2000);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		 to=accountService.get(userTo);
		 from=accountService.get(user);
		 b4=to.getBalance();
		 b3=from.getBalance();
		 Assert.assertTrue(b4==b2);
		 Assert.assertTrue(b3==b1-b.getAmount());
		 b=billService.get(b.getId());
		 Assert.assertTrue(b.getStatus() == Consts.BILL_STATUS_PAID);
	}
	
		
	
	private Bill createBill(Account payer,Account payee,int tradeType){
		Bill ret=new Bill();
		ret.setAmount(5000D);
		ret.setPayerId(payer.getId());
		ret.setPayerName(payer.getName());
		ret.setPayeeId(payee.getId());
		ret.setPayeeName(payee.getName());
		ret.setOrderId("");
		ret.setOrderName("afds");
		ret.setOrderThumbnail("afds");
		ret.setOrderUrl("asf");
		ret.setRemark("remark");
		ret.setTradeType(tradeType);
		ret.setExtraInfo("after");
		return ret;
	}
}
