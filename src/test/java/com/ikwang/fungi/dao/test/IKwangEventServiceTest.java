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
import com.ikwang.fungi.model.IKwangEvent;
import com.ikwang.fungi.model.exception.AccountNotExistException;
import com.ikwang.fungi.model.exception.BalanceInsufficientException;
import com.ikwang.fungi.model.exception.DepositException;
import com.ikwang.fungi.model.exception.InvalidStateException;
import com.ikwang.fungi.model.exception.PayException;
import com.ikwang.fungi.service.IAccountService;
import com.ikwang.fungi.service.impl.IKwangEventService;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "test-context.xml")
public class IKwangEventServiceTest {

	@Autowired
	private IKwangEventService iKwangEventService;
	@Autowired
	private IAccountService accountService;
	@Test
	public void test() throws DataAccessException, DepositException, InvalidStateException, BalanceInsufficientException {
		String user="4349895@qq.com";
		IKwangEvent e=new IKwangEvent();
		e.setBudget(10000);
		e.setName("haha");
		e.setRemark("nothing");
		e.setType("a");
		Account a=accountService.get(user);
		double balance=a.getBalance();
		iKwangEventService.create(e);
		iKwangEventService.deposit(e, 5000, user, false);
		iKwangEventService.deposit(e, 5000, user, true);
		iKwangEventService.unfreeze(e, 50);
		iKwangEventService.unfreeze(e,4000);
		iKwangEventService.unfreeze(e,950);
		iKwangEventService.reward(e, 8000, user, "haha");
		iKwangEventService.reward(e, 2000, user, "haha");
		a=accountService.get(user);
		double balance1=a.getBalance();
		e=iKwangEventService.get(e.getId());
		Assert.assertTrue(balance==balance1);
		Assert.assertTrue(e.getDeposit() == e.getRewarded() && e.getFrozen() == 0);
	}

 
}
