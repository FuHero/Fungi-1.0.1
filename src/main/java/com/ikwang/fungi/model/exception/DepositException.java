// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DepositException.java

package com.ikwang.fungi.model.exception;


public class DepositException extends Exception
{

    public DepositException()
    {
    }

    public DepositException(String message)
    {
        super(message);
    }

    public DepositException(Throwable cause)
    {
        super(cause);
    }

    public DepositException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public DepositException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    private static final long serialVersionUID = 0x99ea3849a1445617L;
}
