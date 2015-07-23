// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ParameterValidationException.java

package com.ikwang.fungi.model.exception;


public class ParameterValidationException extends Exception
{

    public ParameterValidationException()
    {
    }

    public ParameterValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ParameterValidationException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ParameterValidationException(String message)
    {
        super(message);
    }

    public ParameterValidationException(Throwable cause)
    {
        super(cause);
    }

    private static final long serialVersionUID = 0x5bb0ebcd971a353eL;
}
