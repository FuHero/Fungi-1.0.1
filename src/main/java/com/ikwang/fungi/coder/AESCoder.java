// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AESCoder.java

package com.ikwang.fungi.coder;

import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class AESCoder
{

    public AESCoder()
    {
    }

    public static byte[] generateSecretKey(int size)
    {
        KeyGenerator kg = null;
        try
        {
            kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            return new byte[0];
        }
        kg.init(size);
        SecretKey secretKey = kg.generateKey();
        return secretKey.getEncoded();
    }

    private static Key toKey(byte key[])
    {
        return new SecretKeySpec(key, KEY_ALGORITHM);
    }

    public static byte[] encrypt(byte data[], Key key)
        throws GeneralSecurityException
    {
        return encrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
    }

    public static byte[] encrypt(byte data[], byte key[])
        throws GeneralSecurityException
    {
        return encrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
    }

    public static byte[] encrypt(byte data[], byte key[], String cipherAlgorithm)
        throws GeneralSecurityException
    {
        Key k = toKey(key);
        return encrypt(data, k, cipherAlgorithm);
    }

    public static byte[] encrypt(byte data[], Key key, String cipherAlgorithm)
        throws GeneralSecurityException
    {
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        cipher.init(1, key);
        return cipher.doFinal(data);
    }

    public static byte[] decrypt(byte data[], byte key[])
        throws Exception
    {
        return decrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
    }

    public static byte[] decrypt(byte data[], Key key)
        throws Exception
    {
        return decrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
    }

    public static byte[] decrypt(byte data[], byte key[], String cipherAlgorithm)
        throws Exception
    {
        Key k = toKey(key);
        return decrypt(data, k, cipherAlgorithm);
    }

    public static byte[] decrypt(byte data[], Key key, String cipherAlgorithm)
        throws Exception
    {
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        cipher.init(2, key);
        return cipher.doFinal(data);
    }

    protected static String showByteArray(byte data[])
    {
        if(data == null)
            return null;
        StringBuilder sb = new StringBuilder("{");
        byte abyte0[];
        int j = (abyte0 = data).length;
        for(int i = 0; i < j; i++)
        {
            byte b = abyte0[i];
            sb.append(b).append(",");
        }

        sb.deleteCharAt(sb.length() - 1);
        sb.append("}");
        return sb.toString();
    }

    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
}
