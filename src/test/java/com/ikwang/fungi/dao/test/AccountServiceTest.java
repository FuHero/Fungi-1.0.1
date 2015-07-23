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
import com.ikwang.fungi.model.exception.AccountNotExistException;
import com.ikwang.fungi.model.exception.BalanceInsufficientException;
import com.ikwang.fungi.model.exception.PayException;
import com.ikwang.fungi.service.IAccountService;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "xml/*.xml")
public class AccountServiceTest {

	@Autowired
	private IAccountService accountService;
 
	@Test
	public void test_deposit() throws DataAccessException {
		Account account=accountService.get("4349895@qq.com");
		double balance=account.getBalance();
		try {
			accountService.deposit(account.getId(), 1000, Consts.PAYMENT_METHOD_ALIPAY, "stoneloser@qq.com", "aaa@hzhihui.com","deposit");
		} catch (AccountNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		account=accountService.get("4349895@qq.com");
		Assert.assertTrue(account.getBalance()==balance+1000);
	}
	@Test
	public void test_pay() throws DataAccessException, AccountNotExistException, PayException, BalanceInsufficientException {
		String from="4349895@qq.com";
		String to="stoneloser@qq.com";
		Account account=accountService.get(from);
		Account account1=accountService.get(to);
		double balance=account.getBalance();
		double balance1=account1.getBalance();
		double amount=501.25;
		accountService.transfer(from, to,Consts.TRANSACTION_TYPE_NORMAL, amount, "bill", "12312312321",false);
		account=accountService.get(from);
		account1=accountService.get(to);
		Assert.assertTrue(account.getBalance()==balance-amount);
		Assert.assertTrue(account1.getBalance()==balance1+amount);
		
	}
 
}
