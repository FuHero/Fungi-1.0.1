// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SearchBase.java

package com.ikwang.fungi.model.search;

import com.ikwang.fungi.model.IRequest;

import java.util.*;

public class SearchBase extends HashMap<String, Object>
{

    public SearchBase()
    {
    }

    public SearchBase field(String name, Object value)
    {
        put(name, value);
        return this;
    }

    public SearchBase limit(int start, int limit)
    {
        field("START", Integer.valueOf(start));
        return field("LIMIT", Integer.valueOf(limit));
    }

    public SearchBase orderBy(String field, boolean desc)
    {
        @SuppressWarnings("unchecked")
		List<String> list = (List<String>)get("ORDERBY");
        if(list == null)
        {
            list = new ArrayList<String>();
            field("ORDERBY", list);
        }
        list.add((new StringBuilder(String.valueOf(field))).append(" ").append(desc ? "desc" : "asc").toString());
        return this;
    }

    protected String mapRequestField(String input)
    {
        int idx = input.indexOf("_");
        if(idx < 0)
            return input;
        StringBuilder sb = new StringBuilder();
        String arr[] = input.split("_");
        for(int i = 0; i < arr.length; i++)
        {
            String s = arr[i];
            if(i > 0)
                sb.append(Character.toUpperCase(s.charAt(0))).append(s.substring(1));
            else
                sb.append(s);
        }

        return sb.toString();
    }

    public void readFromRequest(IRequest request)
    {
        if(request.hasValue("page_start").booleanValue())
        {
            int s = request.getInt("page_start");
            int l = request.getInt("page_limit");
            if(l <= 0)
                l = 10;
            limit(s, l);
        }
        if(request.hasValue("order_by").booleanValue())
            orderBy(request.getValue("order_by"), request.getBoolean("order_desc"));
    }

    private static final long serialVersionUID = 0xdb13b5bee65475bbL;
}
