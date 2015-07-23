package com.ikwang.fungi.dao.test;

import com.ikwang.fungi.IIDGenerator;
import com.ikwang.fungi.dao.IAccountDAO;
import com.ikwang.fungi.model.Account;
import com.ikwang.fungi.model.UpdateParam;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "test-context.xml")
public class AccountDAOTest {

	@Autowired
	private IAccountDAO accountDAO;

	@Autowired
	private IIDGenerator idGenerator;
	@Test
	public void test_create() throws DataAccessException {
		createAccount();
	}
	protected void createAccount() throws DataAccessException{
		Account account=new Account();
		account.setId("stoneloser@qq.com");
		account.setName("魔法卡");
		account.setRemark("what?");
		int count=accountDAO.create(account);
		 
		Assert.assertTrue(count>0);
		 
	}
 
	@Test
	public void test_deposit(){
		int count=accountDAO.update(new UpdateParam().field("id", "4349895@qq.com").field("deposit", 10000));
		System.out.println(count+" rows affected.");
	}

	@Test
	public void test_search() {
		Account account=accountDAO.getById("4349895@qq.com");
		Assert.assertNotNull(account);
	}
}
