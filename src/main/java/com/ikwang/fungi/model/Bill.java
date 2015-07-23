// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Bill.java

package com.ikwang.fungi.model;

import java.io.Serializable;
import java.util.Date;

public class Bill  implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4913046328850752932L;
	public Bill()
    {
        creationTime = new Date();
        status = 1;
        paid = 0.0D;
        version = 0;
        source = "ikwang";
    }

    public String toString()
    {
        return (new StringBuilder("Bill [id=")).append(id).append(", payerName=").append(payerName).append(", payerId=").append(payerId).append(", payeeName=").append(payeeName).append(", payeeId=").append(payeeId).append(", orderName=").append(orderName).append(", orderThumbnail=").append(orderThumbnail).append(", orderUrl=").append(orderUrl).append(", orderId=").append(orderId).append(", creationTime=").append(creationTime).append(", paymentTime=").append(paymentTime).append(", remark=").append(remark).append(", amount=").append(amount).append(", status=").append(status).append(", tradeType=").append(tradeType).append(", refunded=").append(getRefunded()).append(", extraInfo=").append(extraInfo).append(", freightInfo=").append(freightInfo).append(", paid=").append(paid).append(", version=").append(version).append(", source=").append(source).append("]").toString();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getPayerName()
    {
        return payerName;
    }

    public void setPayerName(String payerName)
    {
        this.payerName = payerName;
    }

    public String getPayerId()
    {
        return payerId;
    }

    public void setPayerId(String payerId)
    {
        this.payerId = payerId;
    }

    public String getPayeeName()
    {
        return payeeName;
    }

    public void setPayeeName(String payeeName)
    {
        this.payeeName = payeeName;
    }

    public String getPayeeId()
    {
        return payeeId;
    }

    public void setPayeeId(String payeeId)
    {
        this.payeeId = payeeId;
    }

    public String getOrderName()
    {
        return orderName;
    }

    public void setOrderName(String orderName)
    {
        this.orderName = orderName;
    }

    public String getOrderThumbnail()
    {
        return orderThumbnail;
    }

    public void setOrderThumbnail(String orderThumbnail)
    {
        this.orderThumbnail = orderThumbnail;
    }

    public String getOrderUrl()
    {
        return orderUrl;
    }

    public void setOrderUrl(String orderUrl)
    {
        this.orderUrl = orderUrl;
    }

    public Date getCreationTime()
    {
        return creationTime;
    }

    public void setCreationTime(Date creationTime)
    {
        this.creationTime = creationTime;
    }

    public Date getPaymentTime()
    {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime)
    {
        this.paymentTime = paymentTime;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public Double getAmount()
    {
        return amount;
    }

    public void setAmount(Double amount)
    {
        this.amount = amount;
    }

    public int getTradeType()
    {
        return tradeType;
    }

    public void setTradeType(int tradeType)
    {
        this.tradeType = tradeType;
    }

    public String getExtraInfo()
    {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo)
    {
        this.extraInfo = extraInfo;
    }

    public String getFreightInfo()
    {
        return freightInfo;
    }

    public void setFreightInfo(String freightInfo)
    {
        this.freightInfo = freightInfo;
    }

    public double getPaid()
    {
        return paid;
    }

    public void setPaid(double paid)
    {
        this.paid = paid;
    }

    public String getOrderId()
    {
        return orderId;
    }

    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public int getVersion()
    {
        return version;
    }

    public void setVersion(int version)
    {
        this.version = version;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public double getRefunded()
    {
        return refunded;
    }

    public void setRefunded(double refunded)
    {
        this.refunded = refunded;
    }

    public Date getCloseTime()
    {
        return closeTime;
    }

    public void setCloseTime(Date closeTime)
    {
        this.closeTime = closeTime;
    }

    public double getReleased()
    {
        return released;
    }

    public void setReleased(double released)
    {
        this.released = released;
    }

    public double getAvailable()
    {
        return getPaid() - getRefunded() - getReleased();
    }

    private String id;
    private String payerName;
    private String payerId;
    private String payeeName;
    private String payeeId;
    private String orderName;
    private String orderThumbnail;
    private String orderUrl;
    private String orderId;
    private Date creationTime;
    private Date paymentTime;
    private Date closeTime;
    private String remark;
    private Double amount;
    private int status;
    private int tradeType;
    private double refunded;
    private double released;
    private String extraInfo;
    private String freightInfo;
    private double paid;
    private int version;
    private String source;
    private String notifyUrl;
}
