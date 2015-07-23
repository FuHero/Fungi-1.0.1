package com.ikwang.fungi.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.ikwang.fungi.Consts;
import com.ikwang.fungi.IExceptionInterpreter;
import com.ikwang.fungi.model.IKwangEvent;
import com.ikwang.fungi.model.IRequest;
import com.ikwang.fungi.model.Response;
import com.ikwang.fungi.model.exception.BalanceInsufficientException;
import com.ikwang.fungi.model.exception.InvalidStateException;
import com.ikwang.fungi.service.IBillService;
import com.ikwang.fungi.service.IIKwangEventService;
import com.ikwang.fungi.util.Util;

public class IKwangEventHandler extends HandlerBase {
	private static final Logger logger = LoggerFactory.getLogger(IKwangEventHandler.class);
	public static final String API_EVENT_METHOD_CREATE="api.event.create";
	public static final String API_EVENT_METHOD_DEPOSIT="api.event.deposit";
	public static final String API_EVENT_METHOD_CLOSE="api.event.close";
	public static final String API_EVENT_METHOD_REWARD="api.event.reward";
	public static final String API_EVENT_METHOD_RELEASE="api.event.release";

	
	@Autowired
	private IBillService billService;
	
	@Autowired
	private IIKwangEventService iKwangEventService;
	@Autowired
	private IExceptionInterpreter exceptionInterpreter;
	@Override
	public Response handle(IRequest request, Response response) {
		switch(request.getMethod()){
		case Consts.API_EVENT_METHOD_SEARCH:
			doSearch(request,response);
			break;
		case Consts.API_EVENT_METHOD_CREATE:
			doCreate(request,response);
			break;
		case Consts.API_EVENT_METHOD_DEPOSIT:
			doPay(request,response);
			break;
		case Consts.API_EVENT_METHOD_CLOSE:
			doCancel(request,response);
			break;
		case Consts.API_EVENT_METHOD_RELEASE:
			doRelease(request,response);
			break;
		case Consts.API_EVENT_METHOD_REWARD:
			doReward(request,response);
			break;
		default:
			response.setStatus(Response.STATUS_FAILED_METHODNOTSUPPORTED);
			response.setMessage("Method not supported");
		} 
		
		return response;
	}
	private void doReward(IRequest request, Response response) {
		IKwangEvent ev=getEvent(request, response);
		if(ev == null)
			return;
		double amount=request.getDouble("amount");
		String account=request.getValue("account");
		String remark=request.getValue("remark");
		if(amount<=0 || StringUtils.isEmpty(account)){
			 response.setStatus(Response.STATUS_FAILED_PARAMETERCHECK);
			 response.setMessage("amount and account must be provided");
			return;
		}
		 try {
			iKwangEventService.reward(ev, amount, account, remark);
		} catch (InvalidStateException e) {
			response.setStatus(Response.STATUS_FAILED_SERVER);
			exceptionInterpreter.interpret(e, request, response);
			logger.error("refunding bill failed",e);
			e.printStackTrace();
		}
	}
	private void doCancel(IRequest request, Response response) {
		IKwangEvent e=getEvent(request, response);
		if(e == null)
			return;
		 iKwangEventService.close(e);
		
	}
	private IKwangEvent getEvent(IRequest request,Response response){
		 String id=request.getValue("id");
		 IKwangEvent e=iKwangEventService.get(id);
		 if(e == null){
			 response.setStatus(Response.STATUS_FAILED_PARAMETERCHECK);
			 response.setMessage("event not exists");
			
		 }
		 return e;
	}
	private void doRelease(IRequest request, Response response) {
		IKwangEvent e=getEvent(request, response);
		if(e == null)
			return;
		double amount=request.getDouble("amount",-1);
		try {
			iKwangEventService.unfreeze(e, amount);
		} catch (BalanceInsufficientException e1) {
			response.setStatus(Response.STATUS_FAILED_SERVER);
			exceptionInterpreter.interpret(e1, request, response);
			logger.error("releasing frozen funds failed",e);
			e1.printStackTrace();
		}
	}
	private void doPay(IRequest request, Response response) {
		IKwangEvent e=getEvent(request, response);
		if(e == null)
			return;
		double amount=request.getDouble("amount");
		String account=request.getValue("account");
		boolean freeze=request.getBoolean("freeze");
		if(amount<=0 || StringUtils.isEmpty(account)){
			 response.setStatus(Response.STATUS_FAILED_PARAMETERCHECK);
			 response.setMessage("amount and account must be provided");
			return;
		}
		 try {
			 
			iKwangEventService.deposit(e, amount, account, freeze);
		} catch (BalanceInsufficientException e1) {
			response.setStatus(Response.STATUS_FAILED_SERVER);
			exceptionInterpreter.interpret(e1, request, response);
			logger.error("depositing event failed",e1);
			e1.printStackTrace();
		}
	}
	private void doCreate(IRequest request, Response response) {
 
		try {
			IKwangEvent event=Util.createIKwangEventFromRequest(request);
			
			iKwangEventService.create(event);
			response.setStatus(Response.STATUS_OK);
			response.setValue(event);
		} catch (Exception e) {
			response.setStatus(Response.STATUS_FAILED_SERVER);
			exceptionInterpreter.interpret(e, request, response);
			logger.error("creating ikwang event from request failed",e);
			e.printStackTrace();
		}
		
	}
	private void doSearch(IRequest request, Response response) {
		try{
			String id=request.getValue("id");
			if(StringUtils.isEmpty(id)){
				response.setStatus(Response.STATUS_FAILED_PARAMETERCHECK);
				response.setMessage("parameter id is missing");
				return;
			}
			IKwangEvent event=iKwangEventService.get(id);
			if(event == null){
				response.setStatus(Response.STATUS_FAILED_PARAMETERCHECK);
				response.setMessage("event not exist");
				return;
				
			}
			response.setValue(event);
		}
		catch(Exception e){
			
			response.setStatus(Response.STATUS_FAILED_SERVER);
			exceptionInterpreter.interpret(e, request, response);
		}
		
	}

}
