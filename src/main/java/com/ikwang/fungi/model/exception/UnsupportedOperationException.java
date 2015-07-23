// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UnsupportedOperationException.java

package com.ikwang.fungi.model.exception;


public class UnsupportedOperationException extends Exception
{

    public UnsupportedOperationException()
    {
    }

    public UnsupportedOperationException(String message)
    {
        super(message);
    }

    public UnsupportedOperationException(Throwable cause)
    {
        super(cause);
    }

    public UnsupportedOperationException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public UnsupportedOperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    private static final long serialVersionUID = 0x803687e86169d3b3L;
}
