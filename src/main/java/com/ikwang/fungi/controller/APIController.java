package com.ikwang.fungi.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.ikwang.fungi.Consts;
import com.ikwang.fungi.IExceptionInterpreter;
import com.ikwang.fungi.IRequestHandler;
import com.ikwang.fungi.IRequestParser;
import com.ikwang.fungi.IRequestValidator;
import com.ikwang.fungi.IResponseSerializer;
import com.ikwang.fungi.model.Account;
import com.ikwang.fungi.model.Bill;
import com.ikwang.fungi.model.IRequest;
import com.ikwang.fungi.model.Request;
import com.ikwang.fungi.model.Response;
import com.ikwang.fungi.service.IAccountService;
import com.ikwang.fungi.service.IBillService;
import com.ikwang.fungi.util.IDGenerator;
import com.ikwang.fungi.util.UnknowExceptionCollector;

@Controller
public class APIController {
	private static final Logger logger = LoggerFactory.getLogger(APIController.class);

	@Autowired(required = false)
	@Qualifier("preHandler")
	private IRequestHandler preHandler;

	@Autowired(required = false)
	@Qualifier("postHandler")
	private IRequestHandler postHandler;
	@Autowired
	private IRequestHandler apiHandler;
	@Autowired
	private IRequestParser requestParser;
	@Autowired
	private IRequestValidator requestValidator;
	@Autowired
	private IResponseSerializer responseSerializer;
	@Autowired
	private IBillService billService;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IExceptionInterpreter exceptionInterpreter;

	@RequestMapping(value = "/api", produces = { "application/json;charset=UTF-8" })
	public @ResponseBody String call(WebRequest web, HttpServletResponse hsr) {
		IRequest request = null;
		try {
			String ret = "";

			request = requestParser.parse(web);

			Response response = processRequest(request);
			ret = responseSerializer.serialize(response, request, request.getFormat());
			if (logger.isInfoEnabled()) {
				logger.info("[REQUEST]process finished, request:" + request
						+ ",response:" + ret);
			}
			return ret;
		} catch (Exception e) {// it really shouldn't run into this codes;
			logger.error("[REQUEST]processing request failed,request:"
					+ request, e);
			return e.getMessage();
		}
	}

	protected Response processRequest(IRequest request) {
		Response response = new Response(Response.STATUS_FAILED_PARAMETERCHECK);
		try {
			response = requestValidator.validate(request);// validates the
															// request
			response = preProcess(request, response);// prehandle the
														// request,handle the
														// request even if it's
														// not a valid request
			if (response.ok()) {// handle the request,only if it's valid,
				response = apiHandler.handle(request, response);
			}
			response = postProcess(request, response);// posthandle the request
			return response;
		} catch (Exception e) {
			//encountered some unknown exception
			Response ret = new Response(Response.STATUS_FAILED_SERVER);
			//exception interpreter should be able to interpret the exception to  specified known status code
			//if the status remains as STATUS_FAILED_SERVER,it means that the exception is an unknown exception,and
			//should be covered in the next update.
			exceptionInterpreter.interpret(e, request, ret);
			//log the unknown exception for further analyzing
			if(ret.getStatus() == Response.STATUS_FAILED_SERVER){
				//logger.error("[REQUEST]processing request failed", e);
				UnknowExceptionCollector.log(e, request, ret);
			}
			return ret;
		}
		// return null;

	}

	private Response preProcess(IRequest request, Response response) {
		if (preHandler != null) {
			return preHandler.handle(request, response);
		}
		return response;
	}

	private Response postProcess(IRequest request, Response response) throws Exception {
		
		if (postHandler != null) {
			response= postHandler.handle(request, response);
		}
		return response;
	}

	@RequestMapping(value = "/createbill")
	public String createBill(WebRequest web, Model model) {
		Request request =(Request) requestParser.parse(web);
		request.setMethod(Consts.API_BILL_METHOD_CREATE);
		Response response = processRequest(request);

		if (response.ok() == false) {
			model.addAttribute("message", response.getMessage());
			return "error";
		}
		try {
			Bill bill = (Bill) response.getValue();
			Account account = accountService.get(bill.getPayerId());
			model.addAttribute("account", account);
			model.addAttribute("bill", bill);
			return "pay";
		} catch (Exception e) {
			logger.warn("createbill:failed", e);
			e.printStackTrace();
			model.addAttribute("message",
					exceptionInterpreter.interpret(e, request, response));
			return "error";
		}

	}
	private Bill createBill(Account payer,Account payee,int tradeType){
		Bill ret=new Bill();
		ret.setId(new IDGenerator().generate());
		ret.setAmount(126D);
		ret.setPayerId(payer.getId());
		ret.setPayerName(payer.getName());
		ret.setPayeeId(payee.getId());
		ret.setPayeeName(payee.getName());
		ret.setOrderId("");
		ret.setOrderName("清凉一夏细纱连衣裙");
		ret.setOrderThumbnail("afds");
		ret.setOrderUrl("asf");
		ret.setRemark("remark");
		ret.setTradeType(tradeType);
		ret.setExtraInfo("after");
		return ret;
	}
	@RequestMapping(value = "/paybill")
	public String payBill(WebRequest web, Model model) {
		IRequest request = requestParser.parse(web);
		Response response =Response.ok(null);// requestValidator.validate(request);

		if (response.ok() == false) {
			model.addAttribute("message", response.getMessage());
			return "error";
		}
		try {
			Account a=new Account();
			a.setBalance(0);
			
			Bill bill = createBill(a, a, 1); //billService.get(request.getValue("bill_id"));

			Account account = a;//accountService.get(bill.getPayerId());
			model.addAttribute("account", account);
			model.addAttribute("bill", bill);
			return "pay";
		} catch (DataAccessException de) {
			logger.warn("createbill:paying bill failed", de);
			de.printStackTrace();
			model.addAttribute("message",
					exceptionInterpreter.interpret(de, request, response));
			return "error";
		}
	}

}
