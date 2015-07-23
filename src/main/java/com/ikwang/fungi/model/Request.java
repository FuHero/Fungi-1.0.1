// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Request.java

package com.ikwang.fungi.model;

import com.ikwang.fungi.util.DateUtil;
import com.ikwang.fungi.util.StringUtils;

import java.io.Serializable;
import java.util.*;

// Referenced classes of package com.ikwang.fungi.model:
//            IRequest, AppDef

public class Request
    implements Serializable, IRequest
{

    public Request()
    {
        format = "json";
        params = new HashMap<String, String>();
    }
    
 
    public String getMethod()
    {
        return method;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    public String getAccessToken()
    {
        return accessToken;
    }

    public void setAccessToken(String accessToken)
    {
        this.accessToken = accessToken;
    }

    public String getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(String timestamp)
    {
        this.timestamp = timestamp;
    }

    public String getSign()
    {
        return sign;
    }

    public void setSign(String sign)
    {
        this.sign = sign;
    }

    public String getAppKey()
    {
        return appKey;
    }

    public void setAppKey(String appKey)
    {
        this.appKey = appKey;
    }

    public String getFormat()
    {
        return format;
    }

    public void setFormat(String format)
    {
        if(StringUtils.hasText(format))
            this.format = format;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public Map<String, String> getParams()
    {
        return params;
    }

    public void setParams(Map<String, String> params)
    {
        this.params = params;
    }

    public long getRequestId()
    {
        return requestId;
    }

    public void setRequestId(long requestId)
    {
        this.requestId = requestId;
    }

    public String getValue(String param)
    {
        return params.get(param);
    }

    public String getValue(String param, String defaultValue)
    {
        String ret = params.get(param);
        if(ret == null)
            ret = defaultValue;
        return ret;
    }

    public Boolean hasValue(String param)
    {
        return Boolean.valueOf(params.containsKey(param));
    }

    public double getDouble(String param)
    {
        return getDouble(param, 0.0D);
    }

    public double getDouble(String param, double defaultValue)
    {
        String v;
        v = getValue(param);
        if(!StringUtils.hasText(v))
            return defaultValue;
        try{
        	return Double.valueOf(v).doubleValue();
        }
        catch(Exception e){
        	
        }
        return defaultValue;
    }

    public int getInt(String param)
    {
        return getInt(param, 0);
    }

    public int getInt(String param, int defaultValue)
    {
        String v;
        v = getValue(param);
        if(!StringUtils.hasText(v))
            return defaultValue;
        try{
        	return Integer.valueOf(v).intValue();
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        return defaultValue;
    }

    public long getLong(String param)
    {
        return getLong(param, 0L);
    }

    public long getLong(String param, long defaultValue)
    {
        String v;
        v = getValue(param);
        if(!StringUtils.hasText(v))
            return defaultValue;
        try{
        return Long.valueOf(v).longValue();
        }
        catch(Exception e){
        	//
        }
        return defaultValue;
    }

    public Date getDate(String param)
    {
        return getDate(param, null);
    }

    public Date getDate(String param, Date defaultValue)
    {
        String v = getValue(param);
        if(!StringUtils.hasText(v))
            return defaultValue;
        Date d = DateUtil.parseFromString(v);
        if(d == null)
            d = defaultValue;
        return d;
    }

    public boolean getBoolean(String param)
    {
        return getBoolean(param, false);
    }

    public boolean getBoolean(String param, boolean defaultValue)
    {
        String v = getValue(param);
        if(!StringUtils.hasText(v))
            return defaultValue;
        return "true".equals(v) || "1".equals(v);
    }

    public IRequest putParam(String param, String value)
    {
        params.put(param, value);
        return this;
    }

    public String toString()
    {
        return (new StringBuilder("Request [requestId=")).append(requestId).append(", method=").append(method).append(", accessToken=").append(accessToken).append(", timestamp=").append(timestamp).append(", sign=").append(sign).append(", appKey=").append(appKey).append(", format=").append(format).append(", version=").append(version).append(", params=").append(params).append("]").toString();
    }

    public AppDef getAppDef()
    {
        return appDef;
    }

    public void setAppDef(AppDef appDef)
    {
        this.appDef = appDef;
    }

    private static final long serialVersionUID = 0x519d7a68fbb52cf2L;
    private String method;
    private String accessToken;
    private AppDef appDef;
    private String timestamp;
    private String sign;
    private String appKey;
    private String format;
    private String version;
    private long requestId;
    private Map<String, String> params;
}
