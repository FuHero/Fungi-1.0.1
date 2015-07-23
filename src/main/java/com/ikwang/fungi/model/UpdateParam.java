// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UpdateParam.java

package com.ikwang.fungi.model;

import java.util.HashMap;

public class UpdateParam extends HashMap<String, Object>
{

    public UpdateParam()
    {
    }

    public static UpdateParam create(String name, Object value)
    {
        return (new UpdateParam()).field(name, value);
    }

    public UpdateParam field(String name, Object value)
    {
        put(name, value);
        return this;
    }

    private static final long serialVersionUID = 0xea585c46c5e0baa7L;
}
