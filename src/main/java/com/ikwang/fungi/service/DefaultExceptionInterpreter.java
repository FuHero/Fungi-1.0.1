package com.ikwang.fungi.service;

import com.ikwang.fungi.IExceptionInterpreter;
import com.ikwang.fungi.model.IRequest;
import com.ikwang.fungi.model.Response;
import com.ikwang.fungi.model.exception.AccountNotExistException;
import com.ikwang.fungi.model.exception.BalanceInsufficientException;
import com.ikwang.fungi.model.exception.InvalidStateException;
import com.ikwang.fungi.model.exception.ParameterValidationException;
import com.ikwang.fungi.model.exception.UnsupportedOperationException;

 

public class DefaultExceptionInterpreter implements IExceptionInterpreter {

	@Override
	public String interpret(Exception exception,IRequest request,Response response) {
		String msg=process(exception,request,response,exception.getMessage());
		response.setMessage(msg);
		return msg;
	}
	
	private String process(Exception exception,IRequest request,Response response,String msg){
		if(exception.getMessage().contains("Duplicate entry") && request.getMethod().equals("api.account.create")){
			response.setStatus(Response.STATUS_FAILED_ACCOUNT_ALREADY_EXIST);
			msg="duplicate id";
		}
		else if(exception   instanceof ParameterValidationException) {
			response.setStatus(Response.STATUS_FAILED_PARAMETERCHECK);
			
		}
		else if(exception   instanceof AccountNotExistException) {
			response.setStatus(Response.STATUS_FAILED_ACCOUNT_NOT_EXIST);
			
		}
		else if(exception   instanceof BalanceInsufficientException) {
			response.setStatus(Response.STATUS_FAILED_INSUFFICIENT_BALANCE);
			
		}
		else if(exception   instanceof InvalidStateException) {
			response.setStatus(Response.STATUS_FAILED_INVALID_STATE);
			
		}
		else if(exception instanceof UnsupportedOperationException){
			response.setStatus(Response.STATUS_FAILED_OPERATIONUNSUPPORTED);
		}
		return msg;
	}

}
