// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DateUtil.java

package com.ikwang.fungi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil
{

    public DateUtil()
    {
    }

    public static Date parseFromString(String input)
    {
    	
        try {
			return sdf.parse(input);
		} catch (ParseException e) {
			 
			e.printStackTrace();
		}

        return null;
    }

    public static String toString(Date date)
    {
        return sdf.format(date);
    }

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

}
