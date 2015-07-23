// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   InvalidStateException.java

package com.ikwang.fungi.model.exception;


public class InvalidStateException extends Exception
{

    public InvalidStateException()
    {
    }

    public InvalidStateException(String arg0)
    {
        super(arg0);
    }

    public InvalidStateException(Throwable arg0)
    {
        super(arg0);
    }

    public InvalidStateException(String arg0, Throwable arg1)
    {
        super(arg0, arg1);
    }

    public InvalidStateException(String arg0, Throwable arg1, boolean arg2, boolean arg3)
    {
        super(arg0, arg1, arg2, arg3);
    }

    private static final long serialVersionUID = 0xfd93f6a963ad7016L;
}
