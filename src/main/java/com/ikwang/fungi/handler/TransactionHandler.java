package com.ikwang.fungi.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ikwang.fungi.Consts;
import com.ikwang.fungi.IExceptionInterpreter;
import com.ikwang.fungi.dao.ITransactionDAO;
import com.ikwang.fungi.model.IRequest;
import com.ikwang.fungi.model.Response;
import com.ikwang.fungi.model.Transaction;
import com.ikwang.fungi.model.search.TransactionSearch;

public class TransactionHandler extends HandlerBase {
	private static final Logger logger = LoggerFactory.getLogger(TransactionHandler.class);


	
	@Autowired
	private ITransactionDAO transactionDAO;
	@Autowired
	private IExceptionInterpreter exceptionInterpreter;
	@Override
	public Response handle(IRequest request, Response response) {
		switch(request.getMethod()){
		case Consts.API_TRANSACTION_METHOD_SEARCH:
			doSearch(request,response);
			break;
		default:
			response.setStatus(Response.STATUS_FAILED_PARAMETERCHECK);
			response.setMessage("Method not supported");
		} 
		
		return response;
	}

	private void doSearch(IRequest request, Response response) {
		try{
		TransactionSearch ts=TransactionSearch.create(request);
		List<Transaction> list=transactionDAO.search(ts);
		response.setStatus(Response.STATUS_OK);
		response.setValue(list);
		}
		catch(Exception e){
			logger.error("searching for transaction failed",e);
			response.setStatus(Response.STATUS_FAILED_SERVER);
			exceptionInterpreter.interpret(e, request, response);
		}
		
	}

}
