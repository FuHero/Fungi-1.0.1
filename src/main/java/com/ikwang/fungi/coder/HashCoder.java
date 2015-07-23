// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HashCoder.java

package com.ikwang.fungi.coder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// Referenced classes of package com.ikwang.fungi.coder:
//            Base64Coder

public class HashCoder
{

    public HashCoder()
    {
    }

    public static byte[] md5(byte data[])
        throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(data);
        return md.digest();
    }

    public static String md5Base64(byte data[])
        throws NoSuchAlgorithmException
    {
        byte bits[] = md5(data);
        return Base64Coder.encrypt(bits);
    }

    public static String sha1Base64(byte data[])
        throws NoSuchAlgorithmException
    {
        byte bits[] = sha1(data);
        return Base64Coder.encrypt(bits);
    }

    public static byte[] sha1(byte data[])
        throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        md.update(data);
        return md.digest();
    }

    public static boolean md5Verify(byte data[], byte hash[])
    {
        byte sign[] = null;
        try
        {
            sign = md5(data);
        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            return false;
        }
        return MessageDigest.isEqual(sign, hash);
    }

    public static boolean sha1Verify(byte data[], byte hash[])
    {
        byte sign[] = null;
        try
        {
            sign = sha1(data);
        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            return false;
        }
        return MessageDigest.isEqual(sign, hash);
    }
}
