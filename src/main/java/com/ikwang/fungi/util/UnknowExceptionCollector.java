// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UnknowExceptionCollector.java

package com.ikwang.fungi.util;

import com.ikwang.fungi.model.IRequest;
import com.ikwang.fungi.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnknowExceptionCollector
{

    public UnknowExceptionCollector()
    {
    }

    public static void log(Exception e, IRequest request, Response response)
    {
        logger.error(String.format("%s,%s", new Object[] {
            request, response
        }), e);
    }

    private static final Logger logger = LoggerFactory.getLogger(UnknowExceptionCollector.class);

}
