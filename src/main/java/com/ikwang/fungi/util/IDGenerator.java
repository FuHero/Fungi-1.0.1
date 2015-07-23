package com.ikwang.fungi.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import com.ikwang.fungi.IIDGenerator;

public class IDGenerator implements IIDGenerator {

	private AtomicInteger atomic=new AtomicInteger(1);
	private static final int MAX=999;
	private SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmssSSS");
	public String generate() { 
		String date=format.format(new Date());
		
		int num=0;
		while((num=atomic.getAndIncrement())>=MAX){
			atomic.set(1);
		}
		String numStr=String.format("%03d", num);
		
		
		return date+numStr;
	}
 
}
