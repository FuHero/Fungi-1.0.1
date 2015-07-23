package com.ikwang.fungi.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ikwang.fungi.Consts;
import com.ikwang.fungi.IExceptionInterpreter;
import com.ikwang.fungi.model.Bill;
import com.ikwang.fungi.model.IRequest;
import com.ikwang.fungi.model.Response;
import com.ikwang.fungi.model.exception.BalanceInsufficientException;
import com.ikwang.fungi.model.exception.InvalidStateException;
import com.ikwang.fungi.model.exception.ParameterValidationException;
import com.ikwang.fungi.model.exception.UnsupportedOperationException;
import com.ikwang.fungi.model.search.BillSearch;
import com.ikwang.fungi.service.IBillService;
import com.ikwang.fungi.util.Util;

public class BillHandler extends HandlerBase {
	private static final Logger logger = LoggerFactory.getLogger(BillHandler.class);


	
	@Autowired
	private IBillService billService;
	@Autowired
	private IExceptionInterpreter exceptionInterpreter;
	@Override
	public Response handle(IRequest request, Response response) {
		switch(request.getMethod()){
		case Consts.API_BILL_METHOD_SEARCH:
			doSearch(request,response);
			break;
		case Consts.API_BILL_METHOD_CREATE:
			doCreate(request,response);
			break;
		case Consts.API_BILL_METHOD_PAY:
			doPay(request,response);
			break; 
		case Consts.API_BILL_METHOD_CANCEL:
			doCancel(request,response);
			break;
		case Consts.API_BILL_METHOD_RELEASE:
			doRelease(request,response);
			break;
		case Consts.API_BILL_METHOD_REFUND:
			doRefund(request,response);
			break;
		default:
			response.setStatus(Response.STATUS_FAILED_METHODNOTSUPPORTED);
			response.setMessage("Method not supported");
		} 
		
		return response;
	}
	private void doRefund(IRequest request, Response response) {
		Bill b=getBill(request, response);
		if(b==null)
			return;
		int version=request.getInt("version",-1);
		if(version >= 0){
			b.setVersion(version);
		}
		double amount=request.getDouble("amount",0);
		if(amount <= 0)
			amount = b.getAvailable();
		 try {
			billService.refund(b, amount);
		} catch (  BalanceInsufficientException | ParameterValidationException | UnsupportedOperationException | InvalidStateException e) {
			response.setStatus(Response.STATUS_FAILED_SERVER);
			exceptionInterpreter.interpret(e, request, response);
			logger.error("refunding bill failed",e);
			e.printStackTrace();
		}
	}
	private void doCancel(IRequest request, Response response) {
		Bill b=getBill(request, response);
		if(b==null)
			return;
		int version=request.getInt("version",-1);
		if(version >= 0){
		b.setVersion(version);}
		 try {
			billService.cancel(b);
		} catch (InvalidStateException e) {
			response.setStatus(Response.STATUS_FAILED_SERVER);
			exceptionInterpreter.interpret(e, request, response);
			logger.error("cancelling bill failed",e);
			e.printStackTrace();
		}
		
	}
	private Bill getBill(IRequest request,Response response){
		 String bill_id=request.getValue(Consts.SEARCH_FIELD_BILL_ID);
		 Bill b=billService.get(bill_id);
		 if(b == null){
			 response.setStatus(Response.STATUS_FAILED_PARAMETERCHECK);
			 response.setMessage("bill not exists");
			
		 }
		 return b;
	}
	private void doRelease(IRequest request, Response response) {
		Bill b=getBill(request, response);
		if(b==null)
			return;
		int version=request.getInt("version",-1);
		if(version >= 0){
		b.setVersion(version);}
		 try {
			billService.release(b,request.getDouble("amount", 0));
		} catch (InvalidStateException | BalanceInsufficientException e) {
			response.setStatus(Response.STATUS_FAILED_SERVER);
			exceptionInterpreter.interpret(e, request, response);
			logger.error("releasing bill failed",e);
			e.printStackTrace();
		}
	}
	private void doPay(IRequest request, Response response)  {
		Bill b=getBill(request, response);
		if(b==null)
			return;
		int version=request.getInt("version",-1);
		if(version >= 0){
		b.setVersion(version);}
		try {
			billService.pay(b);
		} catch (BalanceInsufficientException | InvalidStateException e) {
			response.setStatus(Response.STATUS_FAILED_SERVER);
			exceptionInterpreter.interpret(e, request, response);
			logger.error("paying bill failed",e);
			e.printStackTrace();
		}
	}
	private void doCreate(IRequest request, Response response) {
		Bill bill;
		try {
			bill = Util.createBillFromRequest(request);
			bill=billService.create(bill);
			response.setStatus(Response.STATUS_OK);
			response.setValue(bill);
		} catch (Exception e) {
			response.setStatus(Response.STATUS_FAILED_SERVER);
			exceptionInterpreter.interpret(e, request, response);
			logger.error("creating bill from request failed",e);
			e.printStackTrace();
		}
		
	}
	private void doSearch(IRequest request, Response response) {
		try{
		BillSearch bs=BillSearch.create(request);
		List<Bill> list=billService.search(bs);
		response.setStatus(Response.STATUS_OK);
		response.setValue(list);
		}
		catch(Exception e){
			
			response.setStatus(Response.STATUS_FAILED_SERVER);
			exceptionInterpreter.interpret(e, request, response);
		}
		
	}

}
