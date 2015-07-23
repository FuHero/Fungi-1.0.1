// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AccountNotExistException.java

package com.ikwang.fungi.model.exception;


public class AccountNotExistException extends Exception
{

    public AccountNotExistException()
    {
        super("Account not exist");
    }

    public AccountNotExistException(String arg0)
    {
        super(arg0);
    }

    public AccountNotExistException(Throwable arg0)
    {
        super(arg0);
    }

    public AccountNotExistException(String arg0, Throwable arg1)
    {
        super(arg0, arg1);
    }

    public AccountNotExistException(String arg0, Throwable arg1, boolean arg2, boolean arg3)
    {
        super(arg0, arg1, arg2, arg3);
    }

    private static final long serialVersionUID = 0x603b3edab372d382L;
}
