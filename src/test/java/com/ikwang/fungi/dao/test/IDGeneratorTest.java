package com.ikwang.fungi.dao.test;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import com.ikwang.fungi.dao.IBillDAO;
import com.ikwang.fungi.model.Bill;
import com.ikwang.fungi.model.search.BillSearch;
import com.ikwang.fungi.model.search.SearchBase;
import com.ikwang.fungi.util.IDGenerator;

 
public class IDGeneratorTest {

	protected String mapField(String input){
		int idx=input.indexOf("_");
		if(idx<0)
			return input;
		StringBuilder sb=new StringBuilder();
		String[] arr=input.split("_");
		for(int i=0;i<arr.length;i++){
			String s=arr[i];
			if(i>0){
				sb.append(Character.toUpperCase(s.charAt(0))).append(s.substring(1));
			}
			else{sb.append(s);}
		}
		return sb.toString();
	}
	@Test
	public void test_data(){
		System.out.println(System.getProperty("os.name"));
		System.out.println(System.getProperty("os.version"));
		System.out.println(System.getProperty("os.arch"));
		System.out.println(System.getProperty("java.version"));
		test_tree_map();
	}
	protected void test_tree_map(){
		Map<String,String> map=new HashMap<String,String>();
		map.put("z", "z");
		map.put("b", "b");
		map.put("ab", "ab");
		map.put("ac", "ac");
		map.put("c", "c");
		map.put("af", "af");
		map.put("az", "za");
		TreeMap<String,String> sorted=new TreeMap<String,String>(map);
		for (Entry<String, String> entry : sorted.entrySet()) {
			System.out.println(entry.toString());
		}
		System.out.println(StringUtils.collectionToDelimitedString(sorted.entrySet(), "&"));
		//junit.framework.Assert.assertTrue();
		
	}
   
	@Test
	public void test_map_field(){
		Assert.assertTrue("aBully".equals(mapField("a_bully")));
		Assert.assertTrue("anAnnoyingBully".equals(mapField("an_annoying_bully")));
		Assert.assertTrue("aTrulyAnnoyingBully".equals(mapField("a_truly_annoying_bully")));
	}
	IDGenerator g=new IDGenerator();
	 
	public void test_create() {
		for(int i=0;i<10;i++){
			new Thread(method).run();
		}
	}
	private Runnable method=new Runnable() {
		
		@Override
		public void run() {
			for(int i=0;i<100000;i++)
			 System.out.println(g.generate());
		}
	};

}
