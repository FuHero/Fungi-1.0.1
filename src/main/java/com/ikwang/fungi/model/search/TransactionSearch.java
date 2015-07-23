// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TransactionSearch.java

package com.ikwang.fungi.model.search;

import com.ikwang.fungi.model.IRequest;
import com.ikwang.fungi.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


// Referenced classes of package com.ikwang.fungi.model.search:
//            SearchBase

public class TransactionSearch extends SearchBase
{

    public TransactionSearch()
    {
    }

    public static TransactionSearch create()
    {
        TransactionSearch ret = new TransactionSearch();
        return ret;
    }

    public static TransactionSearch create(IRequest request)
    {
        TransactionSearch ret = create();
        ret.readFromRequest(request);
        return ret;
    }

    public TransactionSearch payerId(String payerId)
    {
        if(StringUtils.hasText(payerId))
            field("payerId", payerId);
        return this;
    }

    public TransactionSearch payeeId(String payeeId)
    {
        if(StringUtils.hasText(payeeId))
            field("payeeId", payeeId);
        return this;
    }

    public TransactionSearch payerAccount(String payerAccount)
    {
        if(StringUtils.hasText(payerAccount))
            field("payerAccount", payerAccount);
        return this;
    }

    public TransactionSearch payeeAccount(String payeeAccount)
    {
        if(StringUtils.hasText(payeeAccount))
            field("payeeAccount", payeeAccount);
        return this;
    }

    public TransactionSearch referenceId(String referenceId)
    {
        if(StringUtils.hasText(referenceId))
            field("referenceId", referenceId);
        return this;
    }

    public TransactionSearch type(int type)
    {
        if(type > 0)
            field("type", Integer.valueOf(type));
        return this;
    }

    @SuppressWarnings("unchecked")
	public TransactionSearch status(int status)
    {
        if(!containsKey("status"))
            field("status", new ArrayList<Integer>());
        List<Integer> list = (List<Integer>)get("status");
        list.add(Integer.valueOf(status));
        return this;
    }

    public TransactionSearch status(String status)
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

    public void readFromRequest(IRequest request)
    {
        super.readFromRequest(request);
        payerId(request.getValue("payer_id"));
        payeeId(request.getValue("payee_id"));
        payerAccount(request.getValue("payer_account"));
        payeeAccount(request.getValue("payee_account"));
        referenceId(request.getValue("reference_id"));
        type(request.getInt("type", -1));
        status(request.getValue("status"));
    }

    private static final long serialVersionUID = 0x8c207bf7088f8375L;
}
