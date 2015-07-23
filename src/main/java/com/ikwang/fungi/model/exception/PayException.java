// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PayException.java

package com.ikwang.fungi.model.exception;


public class PayException extends Exception
{

    public PayException()
    {
    }

    public PayException(String message)
    {
        super(message);
    }

    public PayException(Throwable cause)
    {
        super(cause);
    }

    public PayException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public PayException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    private static final long serialVersionUID = 0x41218262f8974c0cL;
}
