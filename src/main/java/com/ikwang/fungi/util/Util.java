// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Util.java

package com.ikwang.fungi.util;

import com.ikwang.fungi.model.*;
import com.ikwang.fungi.model.exception.ParameterValidationException;
import java.util.Date;
import java.util.Map;


public class Util
{

    public Util()
    {
    }

    public static Bill createBillFromRequest(IRequest request)
        throws ParameterValidationException
    {
        Bill ret = new Bill();
        ret.setAmount(Double.valueOf(request.getDouble("amount")));
        ret.setPayerId(request.getValue("payer_id"));
        ret.setPayerName(request.getValue("payer_name"));
        ret.setPayeeId(request.getValue("payee_id"));
        ret.setPayeeName(request.getValue("payee_name"));
        ret.setOrderId(request.getValue("order_id"));
        ret.setOrderName(request.getValue("order_name"));
        ret.setOrderThumbnail(request.getValue("order_thumbnail"));
        ret.setOrderUrl(request.getValue("order_url"));
        ret.setRemark(request.getValue("remark"));
        ret.setTradeType(request.getInt("trade_type", 1));
        ret.setExtraInfo(request.getValue("extra_info"));
        ret.setSource(request.getValue("source", "ikwang"));
        if(ret.getAmount().doubleValue() < 0.0D || StringUtils.isEmpty(ret.getPayerId()) || StringUtils.isEmpty(ret.getPayeeId()))
            throw new ParameterValidationException("field payer_id,payee_id,amount can not be empty");
        else
            return ret;
    }

    public static Request createRequestFromMap(Map<String, String> map)
    {
        Request ret = new Request();
        ret.setMethod((String)map.get(METHOD_KEY));
        ret.setAccessToken((String)map.get(ACCESSTOKEN_KEY));
        ret.setAppKey((String)map.get(APPKEY_KEY));
        ret.setVersion((String)map.get(VERSION_KEY));
        ret.setTimestamp((String)map.get(TIMESTAMP_KEY));
        ret.setSign((String)map.get(SIGN_KEY));
        ret.setFormat((String)map.get(FORMAT_KEY));
        ret.setParams(map);
        return ret;
    }

    public static IKwangEvent createIKwangEventFromRequest(IRequest request)
        throws ParameterValidationException
    {
        IKwangEvent ret = new IKwangEvent();
        ret.setBudget(request.getDouble("budget"));
        ret.setName(request.getValue("name"));
        ret.setRemark(request.getValue("remark"));
        ret.setType(request.getValue("type"));
        ret.setType(request.getValue("creator"));
        ret.setCreationTime(new Date());
        if(ret.getBudget() <= 0.0D)
            throw new ParameterValidationException("invalid value for budget");
        if(StringUtils.isEmpty(ret.getName()))
            throw new ParameterValidationException("parameter name can not be empty");
        else
            return ret;
    }

    private static final String METHOD_KEY = "method";
    private static final String ACCESSTOKEN_KEY = "access_token";
    private static final String APPKEY_KEY = "app_key";
    private static final String VERSION_KEY = "v";
    private static final String TIMESTAMP_KEY = "timestamp";
    private static final String SIGN_KEY = "sign";
    private static final String FORMAT_KEY = "format";
}
