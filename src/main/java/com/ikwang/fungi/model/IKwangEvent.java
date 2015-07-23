// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IKwangEvent.java

package com.ikwang.fungi.model;

import java.io.Serializable;
import java.util.Date;

public class IKwangEvent  implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7277105698253472161L;
	public IKwangEvent()
    {
        status = 1;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public double getDeposit()
    {
        return deposit;
    }

    public void setDeposit(double deposit)
    {
        this.deposit = deposit;
    }

    public double getFrozen()
    {
        return frozen;
    }

    public void setFrozen(double frozen)
    {
        this.frozen = frozen;
    }

    public double getRewarded()
    {
        return rewarded;
    }

    public void setRewarded(double rewarded)
    {
        this.rewarded = rewarded;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public double getBudget()
    {
        return budget;
    }

    public void setBudget(double budget)
    {
        this.budget = budget;
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

    public String getCreator()
    {
        return creator;
    }

    public void setCreator(String creator)
    {
        this.creator = creator;
    }

    private String id;
    private String name;
    private String remark;
    private String type;
    private double budget;
    private double deposit;
    private double frozen;
    private double rewarded;
    private int status;
    private Date creationTime;
    private Date closeTime;
    private String creator;
}
