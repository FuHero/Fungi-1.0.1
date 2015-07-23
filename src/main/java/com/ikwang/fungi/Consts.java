package com.ikwang.fungi;

public class Consts {
	
	public static final String BILL_SOURCE_DEFAULT="ikwang";
	
 
	/**
	 * the default currency this system use;
	 * */
	public static final String CURRENCY_DEFAULT="CNY";
	
	//defines trade type
	/**
	 * instance trade,
	 * */
	public static final int TRADE_TYPE_INSTANT=1;
	public static final int TRADE_TYPE_GUARANTED=2;
	public static final int TRADE_TYPE_REFUND=3;
	public static final int TRADE_TYPE_LIQUIDATION=4;
	
	//defines bill status
	public static final int BILL_STATUS_CREATED=1;
	public static final int BILL_STATUS_FROZEN=2;
	public static final int BILL_STATUS_PARTIALPAID=3;
	public static final int BILL_STATUS_PAID=4;
	public static final int BILL_STATUS_SHIPPED=5;
	public static final int BILL_STATUS_REFOUNDING=6;
	public static final int BILL_STATUS_REFOUNDED=7;
	public static final int BILL_STATUS_CANCELLED=8;
	public static final int BILL_STATUS_COMPLETED=9;
	
	//defines account types
	
	public static final int ACCOUNT_TYPE_PERSONAL=1;
	public static final int ACCOUNT_TYPE_BUSINESS=2;
	
	//defines account status
	public static final int ACCOUNT_STATUS_NORMAL=1;
	public static final int ACCOUNT_STATUS_FROZEN=9;
	
	//defines payment methods
	public static final int PAYMENT_METHOD_AIGUANG=1;
	public static final int PAYMENT_METHOD_ALIPAY=2;
	public static final int PAYMENT_METHOD_WEPAY=3;
	public static final int PAYMENT_METHOD_UNIONPAY=4;

	//defines transaction types
	public static final int TRANSACTION_TYPE_DEPOSIT=1;
	public static final int TRANSACTION_TYPE_NORMAL=2;
	public static final int TRANSACTION_TYPE_WITHDRAW=3;
	public static final int TRANSACTION_TYPE_REFUND=4;
	public static final int TRANSACTION_TYPE_REWARD=5;
	
	//defines transaction status
	public static final int TRANSACTION_STATUS_FROZEN=1;
	public static final int TRANSACTION_STATUS_COMPLETED=2;
	public static final int TRANSACTION_STATUS_CANCELLED=3; 
	public static final int TRANSACTION_STATUS_RELEASED=4;
	public static final int TRANSACTION_STATUS_ABORTED=5;
	public static final int TRANSACTION_STATUS_FAILED=6;
	
	
	//defines method names
	public static final String API_BILL_METHOD_SEARCH="api.bill.search";
	public static final String API_BILL_METHOD_CREATE="api.bill.create";
	public static final String API_BILL_METHOD_PAY="api.bill.pay";
	public static final String API_BILL_METHOD_CANCEL = "api.bill.cancel";
	public static final String API_BILL_METHOD_RELEASE = "api.bill.release";
	public static final String API_BILL_METHOD_REFUND = "api.bill.refund";
 
	public static final String API_BILL_METHOD_PREPAY = "api.bill.prepay";
	 
	
	public static final String API_ACCOUNT_METHOD_CREATE="api.account.create";
	public static final String API_ACCOUNT_METHOD_ADDWITHDRAWACCOUNT="api.account.addwithdrawaccount";
	public static final String API_ACCOUNT_METHOD_WITHDRAW="api.account.withdraw";
	public static final String API_ACCOUNT_METHOD_SEARCH="api.account.search";
	public static final String API_ACCOUNT_METHOD_DEPOSIT="api.account.deposit";
	public static final String API_ACCOUNT_METHOD_TRANSFER="api.account.transfer";
	public static final String API_EVENT_METHOD_CREATE="api.event.create";
	public static final String API_EVENT_METHOD_SEARCH="api.event.search";
	public static final String API_EVENT_METHOD_DEPOSIT="api.event.deposit";
	public static final String API_EVENT_METHOD_CLOSE="api.event.close";
	public static final String API_EVENT_METHOD_REWARD="api.event.reward";
	public static final String API_EVENT_METHOD_RELEASE="api.event.release";
	public static final String API_TRANSACTION_METHOD_SEARCH="api.transaction.search";
	
	public static final String SEARCH_FIELD_PAGE_START="page_start";
	public static final String SEARCH_FIELD_PAGE_LIMIT="page_limit";
	public static final String SEARCH_FIELD_ORDER_BY="order_by";
	public static final String SEARCH_FIELD_ORDER_DESC="order_desc";

	public static final String SEARCH_FIELD_CREATION_TIME_START="creation_time_start";
	public static final String SEARCH_FIELD_CREATION_TIME_END="creation_time_end";
	
	public static final String SEARCH_FIELD_PAYER_ID="payer_id";
	public static final String SEARCH_FIELD_PAYEE_ID="payee_id";
	public static final String SEARCH_FIELD_PAYER_ACCOUNT="payer_account";
	public static final String SEARCH_FIELD_PAYEE_ACCOUNT="payee_account";
	public static final String SEARCH_FIELD_ORDER_ID="order_id";
	public static final String SEARCH_FIELD_BILL_ID="bill_id";
	public static final String SEARCH_FIELD_REFERENCE_ID="reference_id";
	public static final String SEARCH_FIELD_TYPE="type";
	public static final String SEARCH_FIELD_STATUS="status";
	
	public static final String REQUEST_SIGN_TYPE="sign_type";

	public static final int EVENT_STATUS_OPEN = 1;
	public static final int EVENT_STATUS_INPROGRESS = 2;
	public static final int EVENT_STATUS_CLOSED = 3;
	public static final int EVENT_STATUS_COMPLETED = 3;


}
