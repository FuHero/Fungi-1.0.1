package com.ikwang.fungi.event;

import com.ikwang.fungi.model.Bill;

public class PayEvent {
	private Bill bill;
	private int paymentType;
	private boolean useAccountBalance;
	private double amount;
	private String payerAccount;
	private String payeeAccount;
	public PayEvent(Bill bill, int paymentType, boolean useAccountBalance,
			double amount, String payerAccount, String payeeAccount) {
		super();
		this.bill = bill;
		this.paymentType = paymentType;
		this.useAccountBalance = useAccountBalance;
		this.amount = amount;
		this.payerAccount = payerAccount;
		this.payeeAccount = payeeAccount;
	}
	public PayEvent(){}
	public PayEvent fill(Bill bill, int paymentType, boolean useAccountBalance,
			double amount, String payerAccount, String payeeAccount){
		this.bill = bill;
		this.paymentType = paymentType;
		this.useAccountBalance = useAccountBalance;
		this.amount = amount;
		this.payerAccount = payerAccount;
		this.payeeAccount = payeeAccount;
		return this;
	}
	public Bill getBill() {
		return bill;
	}
	public void setBill(Bill bill) {
		this.bill = bill;
	}
	public int getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(int paymentType) {
		this.paymentType = paymentType;
	}
	public boolean isUseAccountBalance() {
		return useAccountBalance;
	}
	public void setUseAccountBalance(boolean useAccountBalance) {
		this.useAccountBalance = useAccountBalance;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getPayerAccount() {
		return payerAccount;
	}
	public void setPayerAccount(String payerAccount) {
		this.payerAccount = payerAccount;
	}
	public String getPayeeAccount() {
		return payeeAccount;
	}
	public void setPayeeAccount(String payeeAccount) {
		this.payeeAccount = payeeAccount;
	}
}
