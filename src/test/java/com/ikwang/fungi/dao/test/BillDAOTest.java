package com.ikwang.fungi.dao.test;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ikwang.fungi.IIDGenerator;
import com.ikwang.fungi.dao.IBaseDAO;
import com.ikwang.fungi.model.Bill;
import com.ikwang.fungi.model.search.BillSearch;
import com.ikwang.fungi.model.search.SearchBase;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "test-context.xml")
public class BillDAOTest {

	@Autowired
	private IBaseDAO billDAO;

	@Autowired
	private IIDGenerator idGenerator;
	@Test
	public void test_create() throws DataAccessException {
		createBill();
	}
	protected String createBill() throws DataAccessException{
		Bill b=new Bill();
		b.setId(idGenerator.generate());
		b.setAmount(1.0);
		b.setPayerId("4349895@qq.com");
		b.setPayeeId("4349895@qq.com");
		int count=billDAO.create(b);
		System.out.print("bill id:"+b.getId());
		Assert.assertTrue(count>0);
		return b.getId();
	}
	@Test
	public void test_bunch_create(){
		for(int i=0;i<10;i++){
			new Thread(method).run();
		}
	}
	private Runnable method=new Runnable() {
		
		@Override
		public void run() {
			for(int i=0;i<100000;i++){
				Bill b=new Bill();
				b.setId(idGenerator.generate());
				b.setAmount(1.0);
				b.setPayerId("4349895@qq.com");
				b.setPayeeId("4349895@qq.com");
				int count=billDAO.create(b);
			}
			// System.out.println(g.getString());
		}
	};

	@Test
	public void test_search() {
		try{
		String bid=createBill();
		SearchBase bs=BillSearch.create().id(bid)
				;
		List<Bill> list=billDAO.search(bs);
		Assert.assertTrue(list.size()==1);
		Assert.assertTrue(list.get(0).getId() .equals(bid));
		 bs=BillSearch.create().payeeId("4349895@qq.com").createTime(null, new Date()).limit(3, 100).orderBy("creationTime", true)
					.orderBy("payerId", false)
					;
		List<Bill> list2=billDAO.search(bs);
		Assert.assertTrue(list2.size()>1);
		}
		catch(DataAccessException e){
			e.printStackTrace();
		}
	}
}
