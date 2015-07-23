package com.ikwang.fungi.handler;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alipay.sign.RSA;
import com.ikwang.fungi.Consts;
import com.ikwang.fungi.model.Account;
import com.ikwang.fungi.model.Bill;
import com.ikwang.fungi.model.IRequest;
import com.ikwang.fungi.model.Request;
import com.ikwang.fungi.model.Response;
import com.ikwang.fungi.service.IAccountService;
import com.ikwang.fungi.service.IBillService;
import com.ikwang.fungi.util.StringUtils;
import com.ikwang.fungi.util.Util;

public class PayHandler extends HandlerBase {

	
	private String alipayPartner;
	private String alipayId;
	private String alipayPrivateKey;
	private String alipayPublicKey;
	private String alipayNotifyUrl;
	public String getAlipayPartner() {
		return alipayPartner;
	}

	public void setAlipayPartner(String alipayPartner) {
		this.alipayPartner = alipayPartner;
	}

	public String getAlipayId() {
		return alipayId;
	}

	public void setAlipayId(String alipayId) {
		this.alipayId = alipayId;
	}

	public String getAlipayPrivateKey() {
		return alipayPrivateKey;
	}

	public void setAlipayPrivateKey(String alipayPrivateKey) {
		this.alipayPrivateKey = alipayPrivateKey;
	}

	public PayHandler() {
		 
	}
	@Autowired
	private IBillService billService;

	@Autowired
	private IAccountService accountService;
	@Override
	public Response handle(IRequest request, Response response) {
		 if(Consts.API_BILL_METHOD_PREPAY.equals(request.getMethod()) == false){
				response.setStatus(Response.STATUS_FAILED_METHODNOTSUPPORTED);
				response.setMessage("Method not supported");
				return response;
		 }
		return doPrepay(request,response);
	}

	private Response doPrepay(IRequest request, Response response) {
		String billId=request.getValue(Consts.SEARCH_FIELD_BILL_ID);
		int version=request.getInt("version",-1);
		if(StringUtils.isEmpty(billId) || version < 0){
			response.setStatus(Response.STATUS_FAILED_PARAMETERCHECK);
			response.setMessage("parameter bill_id or version is missing");
			return response;
		}
		Bill bill=billService.get(billId);
		if(bill == null){
			response.setStatus(Response.STATUS_FAILED_PARAMETERCHECK);
			response.setMessage("bill doesn't exist");
			return response;
		}
		if(bill.getVersion() != version){
			response.setStatus(Response.STATUS_FAILED_INVALID_STATE);
			response.setMessage("bill status has changed,please refresh first");
			return response;
		}
		Map<String,String> value=new HashMap<String, String>();
		Account account=accountService.get(bill.getPayerId());
		int paymentmethod=request.getInt("payment_method", Consts.PAYMENT_METHOD_AIGUANG);
		boolean payByAccountBalance=request.getBoolean("payByAccount",true);
		double payamount=bill.getAmount()-bill.getPaid();
		if(payByAccountBalance){
			payamount-=account.getBalance();
			if(payamount<=0){
				Request payRequest=Util.createRequestFromMap(request.getParams());
				payRequest.setMethod(Consts.API_BILL_METHOD_PAY);
				response= parent.handle(payRequest, response);
				if(response.ok()){
					value.put("status", "200");
					response.setValue(value);
				}
				return response;
			}
		}
		switch(paymentmethod){
		case Consts.PAYMENT_METHOD_ALIPAY:
			doAlipayPreparation(bill,account,request,response,payamount,value);
			break;
		case Consts.PAYMENT_METHOD_WEPAY:
		case Consts.PAYMENT_METHOD_UNIONPAY:
		default:
			response.setStatus(Response.STATUS_FAILED_PAYMENTMETHODNOTSUPPORT);
			response.setMessage("payment method not support yet");
			break;
		}
		return response;
	}

	@SuppressWarnings("deprecation")
	private void doAlipayPreparation(Bill bill, Account account,
			IRequest request, Response response,double amount,Map<String,String> value) {
		StringBuilder sb = new StringBuilder();
		sb.append("partner=\"");
		sb.append(getAlipayPartner());
		sb.append("\"&out_trade_no=\"");
		sb.append(bill.getId());
		sb.append("\"&subject=\"");
		sb.append(bill.getOrderName());
		sb.append("\"&body=\"");
		sb.append(bill.getRemark());
		sb.append("\"&total_fee=\"");
		sb.append(amount);
		sb.append("\"&notify_url=\"");

		// 网址需要做URL编码
		sb.append(URLEncoder.encode(getAlipayNotifyUrl()));
		sb.append("\"&service=\"mobile.securitypay.pay");
		sb.append("\"&_input_charset=\"UTF-8");
		sb.append("\"&return_url=\"");
		sb.append(URLEncoder.encode("http://m.alipay.com"));
		sb.append("\"&payment_type=\"1");
		sb.append("\"&seller_id=\"");
		sb.append(getAlipayId());
		sb.append("\"&it_b_pay=\"1m");
		sb.append("\"");
		String info=sb.toString();
		String sign=RSA.sign(info, getAlipayPrivateKey(), "UTF-8");
		sign = URLEncoder.encode(sign);
		info += "&sign=\"" + sign + "\"&sign_type=\"RSA\"";
		value.put("status", "302");
		value.put("info", info);
		value.put("publicKey",getAlipayPublicKey() );
		response.setValue(value);
	}

	public String getAlipayNotifyUrl() {
		return alipayNotifyUrl;
	}

	public void setAlipayNotifyUrl(String alipayNotifyUrl) {
		this.alipayNotifyUrl = alipayNotifyUrl;
	}

	public String getAlipayPublicKey() {
		return alipayPublicKey;
	}

	public void setAlipayPublicKey(String alipayPublicKey) {
		this.alipayPublicKey = alipayPublicKey;
	}

}
