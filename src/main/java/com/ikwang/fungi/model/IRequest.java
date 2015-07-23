// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IRequest.java

package com.ikwang.fungi.model;

import java.util.Date;
import java.util.Map;

// Referenced classes of package com.ikwang.fungi.model:
//            AppDef

public interface IRequest
{

    public abstract String getMethod();

    public abstract String getAccessToken();

    public abstract String getTimestamp();

    public abstract String getSign();

    public abstract String getAppKey();

    public abstract String getFormat();

    public abstract String getVersion();

    public abstract Map<String,String> getParams();

    public abstract long getRequestId();

    public abstract String getValue(String s);

    public abstract String getValue(String s, String s1);

    public abstract Boolean hasValue(String s);

    public abstract double getDouble(String s);

    public abstract double getDouble(String s, double d);

    public abstract int getInt(String s);

    public abstract int getInt(String s, int i);

    public abstract long getLong(String s);

    public abstract long getLong(String s, long l);

    public abstract Date getDate(String s);

    public abstract Date getDate(String s, Date date);

    public abstract boolean getBoolean(String s);

    public abstract boolean getBoolean(String s, boolean flag);

    public abstract IRequest putParam(String s, String s1);

    public abstract AppDef getAppDef();
}
