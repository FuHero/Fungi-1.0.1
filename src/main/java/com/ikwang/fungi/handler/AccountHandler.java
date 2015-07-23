package com.ikwang.fungi.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.ikwang.fungi.Consts;
import com.ikwang.fungi.IExceptionInterpreter;
import com.ikwang.fungi.model.Account;
import com.ikwang.fungi.model.Bill;
import com.ikwang.fungi.model.IRequest;
import com.ikwang.fungi.model.Response;
import com.ikwang.fungi.model.exception.AccountNotExistException;
import com.ikwang.fungi.model.exception.BalanceInsufficientException;
import com.ikwang.fungi.service.IAccountService;
import com.ikwang.fungi.service.IBillService;

public class AccountHandler extends HandlerBase {
	private static final Logger logger = LoggerFactory.getLogger(AccountHandler.class);

 
	
	@Autowired
	private IBillService billService;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IExceptionInterpreter exceptionInterpreter;
	@Override
	public Response handle(IRequest request, Response response) {
		switch(request.getMethod()){
		case Consts.API_ACCOUNT_METHOD_SEARCH:
			doSearch(request,response);
			break;
		case Consts.API_ACCOUNT_METHOD_CREATE:
			doCreate(request,response);
			break;
		case Consts.API_ACCOUNT_METHOD_DEPOSIT:
			doDeposit(request,response);
			break;
		case Consts.API_ACCOUNT_METHOD_TRANSFER:
			doTransfer(request,response);
			break;
		case Consts.API_ACCOUNT_METHOD_WITHDRAW:
			doWithdraw(request,response);
			break;
		case Consts.API_ACCOUNT_METHOD_ADDWITHDRAWACCOUNT:
			doAddWithdrawAccount(request,response);
			break;
		default:
			response.setStatus(Response.STATUS_FAILED_PARAMETERCHECK);
			response.setMessage("Method not supported");
		} 
		
		return response;
	}
	private void doTransfer(IRequest request, Response response) {
		String from=request.getValue("account_from");
		String to=request.getValue("account_to");
		double amount=request.getDouble("amount",0);
		String remark=request.getValue("remark");
		Account fromAccount=getAccount(from, response);
		Account toAccount=getAccount(to, response);
		if(fromAccount == null || toAccount == null)
			return;
		if(fromAccount.getBalance() < amount)
		{
			response.setStatus(Response.STATUS_FAILED_INSUFFICIENT_BALANCE);
			response.setMessage("insufficient balance");
			return;
		}
		Bill b=createTransferBill(fromAccount,toAccount, amount, remark);
		
		try {
			billService.create(b);
			billService.pay(b);
		} catch (Exception e) {
			response.setStatus(Response.STATUS_FAILED_SERVER);
			exceptionInterpreter.interpret(e, request, response);
			logger.error("transfer failed",e);
			e.printStackTrace();
		}
		
	}
	private Bill createTransferBill(Account from,Account to,double amount,String remark){
		Bill ret=new Bill();
		ret.setAmount(amount);
		ret.setPayerId(from.getId());
		ret.setPayerName(from.getName());
		ret.setPayeeId(to.getId());
		ret.setPayeeName(to.getName());
		ret.setOrderName("transfer");
		ret.setRemark(remark);
		ret.setTradeType(Consts.TRADE_TYPE_INSTANT);
		 
		return ret;
	}
	private Account getAccount(String account, Response response) {
	
		if(account == null){
			response.setStatus(Response.STATUS_FAILED_PARAMETERCHECK);
			response.setMessage("account can not be empty");
			return null;
		}
		Account ret=accountService.get(account);
		if(ret == null){
			response.setStatus(Response.STATUS_FAILED_ACCOUNT_NOT_EXIST);
			response.setMessage(account+" not exist");
			 
		}
		return ret;
	}
	private void doWithdraw(IRequest request, Response response) {
		String account=request.getValue("account");
		Account acc=getAccount(account, response);
		if(acc == null)
			return;
		double amount=request.getDouble("amount");
		String bank=request.getValue("bank");
		String card=request.getValue("card");
		String name=request.getValue("name");
		String remark=request.getValue("remark");
		if(amount <=0 || StringUtils.isEmpty(bank)||StringUtils.isEmpty(card) || StringUtils.isEmpty(name))
		{
			response.setStatus(Response.STATUS_FAILED_PARAMETERCHECK);
			response.setMessage("field amount,bank,card,name can not be empty");
			return;
		}
		if(acc.getName().equals(name) == false){
			response.setStatus(Response.STATUS_FAILED_ACCOUNT_NAME_NOT_MATCH);
			response.setMessage("account name not match");
			return;
		}
		try {
			accountService.withdraw(account, amount, card, remark+","+bank);
		} catch (AccountNotExistException | BalanceInsufficientException e) {
			response.setStatus(Response.STATUS_FAILED_SERVER);
			exceptionInterpreter.interpret(e, request, response);
			e.printStackTrace();
		}
		
	}
 
	private void doAddWithdrawAccount(IRequest request, Response response) {
		//TODO 
	}
	private void doDeposit(IRequest request, Response response) {
		String account=request.getValue("account");
		Account acc=getAccount(account, response);
		if(acc == null)
			return;
		double amount=request.getDouble("amount");
		if(amount <= 0){
			response.setStatus(Response.STATUS_FAILED_PARAMETERCHECK);
			response.setMessage("invalid value for amount");
			return;
		}
		int paymentMethod=request.getInt("payment_method");
		String payerAccount=request.getValue("payer_account");
		String payeeAccount=request.getValue("payee_account");
		String remark=request.getValue("remark","deposit");
		
		try {
			accountService.deposit(account, amount, paymentMethod, payerAccount, payeeAccount,remark);
			response.setValue(getAccount(account,response));
		} catch (AccountNotExistException e) {
			 
			e.printStackTrace();
			response.setStatus(Response.STATUS_FAILED_SERVER);
			exceptionInterpreter.interpret(e, request, response);
		}

	}
	private void doCreate(IRequest request, Response response) {
		Account acc=new Account();
		String id=request.getValue("id");
		String name=request.getValue("name");
		String cell=request.getValue("cell");
		String email=request.getValue("email");
		if(StringUtils.isEmpty(id) || StringUtils.isEmpty(name) || StringUtils.isEmpty(cell) || StringUtils.isEmpty(email)){
			response.setStatus(Response.STATUS_FAILED_PARAMETERCHECK);
			response.setMessage("fields id,name,cell,email can not be empty");
			return;
		}
		String remark=request.getValue("remark");
		int type=request.getInt("type",Consts.ACCOUNT_TYPE_PERSONAL);
		acc.setId(id);
		acc.setName(name);
		acc.setRemark(remark);
		acc.setType(type);
		acc.setCell(cell);
		acc.setEmail(email);
		accountService.create(acc);
		response.setValue(acc);
		
	}
	private void doSearch(IRequest request, Response response) {
		try{
		String id=request.getValue("account");
		if(StringUtils.isEmpty(id)){
			response.setStatus(Response.STATUS_FAILED_PARAMETERCHECK);
			response.setMessage("parameter account is missing");
			return;
		}
		Account acc=accountService.get(id);
		if(acc == null){
			response.setStatus(Response.STATUS_FAILED_ACCOUNT_NOT_EXIST);
			response.setMessage("account not exist");
			return;
		}
		response.setValue(acc);
		}
		catch(Exception e){
			
			response.setStatus(Response.STATUS_FAILED_SERVER);
			exceptionInterpreter.interpret(e, request, response);
		}
		
	}

}
