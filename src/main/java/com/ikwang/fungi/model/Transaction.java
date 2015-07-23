// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Transaction.java

package com.ikwang.fungi.model;

import java.io.Serializable;
import java.util.Date;

import com.ikwang.fungi.Consts;

public class Transaction  implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1671125196091415107L;
	public Transaction()
    {
        creationTime = new Date();
    }

    public static Transaction create(double amount, int type, int payment, String currency, String payerId, String payeeId, String payerAccount, 
            String payeeAccount, String remark, String referenceId, int status)
    {
        Transaction ret = new Transaction();
        ret.setAmount(amount);
        ret.setCurrency(currency);
        ret.setType(type);
        ret.setReferenceId(referenceId);
        ret.setPayerId(payerId);
        ret.setPayeeId(payeeId);
        ret.setPayerAccount(payerAccount);
        ret.setPayeeAccount(payeeAccount);
        ret.setRemark(remark);
        ret.setStatus(status);
        ret.setPaymentMethod(payment);
        ret.setCreationTime(new Date());
        if(status ==  Consts.TRANSACTION_STATUS_COMPLETED){
        	ret.setCloseTime(new Date());
        }
        return ret;
    }

    public String getPayerId()
    {
        return payerId;
    }

    public void setPayerId(String payerId)
    {
        this.payerId = payerId;
    }

    public String getPayeeId()
    {
        return payeeId;
    }

    public void setPayeeId(String payeeId)
    {
        this.payeeId = payeeId;
    }

    public String getPayerAccount()
    {
        return payerAccount;
    }

    public void setPayerAccount(String payerAccount)
    {
        this.payerAccount = payerAccount;
    }

    public String getPayeeAccount()
    {
        return payeeAccount;
    }

    public void setPayeeAccount(String payeeAccount)
    {
        this.payeeAccount = payeeAccount;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public double getAmount()
    {
        return amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    public String getCurrency()
    {
        return currency;
    }

    public void setCurrency(String currency)
    {
        this.currency = currency;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public String getReferenceId()
    {
        return referenceId;
    }

    public void setReferenceId(String referenceId)
    {
        this.referenceId = referenceId;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public int getPaymentMethod()
    {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod)
    {
        this.paymentMethod = paymentMethod;
    }

    public Date getCreationTime()
    {
        return creationTime;
    }

    public void setCreationTime(Date creationTime)
    {
        this.creationTime = creationTime;
    }

    public Date getCloseTime()
    {
        return closeTime;
    }

    public void setCloseTime(Date closeTime)
    {
        this.closeTime = closeTime;
    }

    private long id;
    private double amount;
    private String currency;
    private int type;
    private String referenceId;
    private String payerId;
    private String payeeId;
    private String payerAccount;
    private String payeeAccount;
    private String remark;
    private int paymentMethod;
    private Date creationTime;
    private Date closeTime;
    private int status;
}
