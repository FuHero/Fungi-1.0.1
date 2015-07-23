// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BillSearch.java

package com.ikwang.fungi.model.search;

import com.ikwang.fungi.model.IRequest;
import com.ikwang.fungi.util.StringUtils;

import java.util.*;


// Referenced classes of package com.ikwang.fungi.model.search:
//            SearchBase

public class BillSearch extends SearchBase
{

    public BillSearch()
    {
    }

    public static BillSearch create()
    {
        BillSearch ret = new BillSearch();
        return ret;
    }

    public static BillSearch create(IRequest request)
    {
        BillSearch ret = create();
        ret.readFromRequest(request);
        return ret;
    }

    public BillSearch id(String id)
    {
        if(StringUtils.hasText(id))
            field("id", id);
        return this;
    }

    public BillSearch payerId(String payerId)
    {
        if(StringUtils.hasText(payerId))
            field("payerId", payerId);
        return this;
    }

    public BillSearch payeeId(String payeeId)
    {
        if(StringUtils.hasText(payeeId))
            field("payeeId", payeeId);
        return this;
    }

    public BillSearch orderId(String orderId)
    {
        if(StringUtils.hasText(orderId))
            field("orderId", orderId);
        return this;
    }

    public BillSearch status(String status)
    {
        if(StringUtils.hasText(status))
        {
            String statusArr[] = status.split(",");
            List<Integer> statusList = new ArrayList<Integer>();
            String as[];
            int j = (as = statusArr).length;
            for(int i = 0; i < j; i++)
            {
                String s = as[i];
                try
                {
                    statusList.add(Integer.valueOf(s));
                }
                catch(NumberFormatException e)
                {
                    e.printStackTrace();
                }
            }

            field("status", statusList);
        }
        return this;
    }

    public BillSearch createTime(Date start, Date end)
    {
        if(start != null)
            field("creationTimeStart", start);
        if(end != null)
            field("creationEnd", end);
        return this;
    }

    public void readFromRequest(IRequest request)
    {
        super.readFromRequest(request);
        createTime(request.getDate("creation_time_start"), request.getDate("creation_time_end"));
        payerId(request.getValue("payer_id"));
        id(request.getValue("bill_id"));
        payeeId(request.getValue("payee_id"));
        orderId(request.getValue("order_id"));
        status(request.getValue("status"));
    }

    private static final long serialVersionUID = 0x8c207bf7088f8375L;
}
