package com.ikwang.fungi.parser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.WebRequest;

import com.ikwang.fungi.IRequestParser;
import com.ikwang.fungi.dao.IAppDefDAO;
import com.ikwang.fungi.model.AppDef;
import com.ikwang.fungi.model.IRequest;
import com.ikwang.fungi.model.Request;
import com.ikwang.fungi.util.Util;

public class DefaultRequestParser implements IRequestParser {

	@Autowired
	private IAppDefDAO appDefDAO;
	private AtomicLong seq=new AtomicLong();
	@Override
	public IRequest parse(WebRequest request) {
	    Map<String,String> map=new HashMap<String,String>();
	    Iterator<String> pnames=request.getParameterNames();
	    while(pnames.hasNext()){
	    	String name=pnames.next();
	    	map.put(name,request.getParameter(name));
	    }
		Request ret=Util.createRequestFromMap(map);
		ret.setAppDef(getAppDef(ret.getAppKey()));
		ret.setRequestId(seq.incrementAndGet());
//	    ret.setMethod(request.getParameter(METHOD_KEY));
//	    ret.setAccessToken(request.getParameter(ACCESSTOKEN_KEY));
//	    ret.setAppKey(request.getParameter(APPKEY_KEY));
//	    ret.setVersion(request.getParameter(VERSION_KEY));
//	    ret.setTimestamp(request.getParameter(TIMESTAMP_KEY));
//	    ret.setSign(request.getParameter(SIGN_KEY));
//	    ret.setFormat(request.getParameter(FORMAT_KEY));
//
//	    ret.setParams(map);
		return ret;
	}
	//a simple cache for appdef
	private Map<String,AppDef> simpleCache=new HashMap<String,AppDef>();

	protected AppDef getAppDef(String key){
		if(StringUtils.isEmpty(key))
			return null;
		if(simpleCache.containsKey(key))
			return simpleCache.get(key);
		AppDef ret=appDefDAO.getById(key);
		if(ret != null){
			simpleCache.put(key, ret);
		}
		return ret;
	}
}
