// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BalanceInsufficientException.java

package com.ikwang.fungi.model.exception;


public class BalanceInsufficientException extends Exception
{

    public BalanceInsufficientException()
    {
    }

    public BalanceInsufficientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public BalanceInsufficientException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public BalanceInsufficientException(String message)
    {
        super(message);
    }

    public BalanceInsufficientException(Throwable cause)
    {
        super(cause);
    }

    private static final long serialVersionUID = 0xbac18180a2a27259L;
}
