// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RSACoder.java

package com.ikwang.fungi.coder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
 

import com.ikwang.fungi.IDigitalSigner.KeyPair;

public abstract class RSACoder
{

    public RSACoder()
    {
    }

    public static byte[] sign(byte data[], String privateKey) throws GeneralSecurityException
        
    {
        byte keyBytes[] = decryptBASE64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        java.security.PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(priKey);
        signature.update(data);
        return signature.sign();
    }

    public static boolean verify(byte data[], String publicKey, String sign)
        throws Exception
    {

        return verify(data,publicKey,decryptBASE64(sign));
    }
    public static boolean verify(byte data[], String publicKey, byte[] sign) throws GeneralSecurityException
             
        {
            byte keyBytes[] = decryptBASE64(publicKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            java.security.PublicKey pubKey = keyFactory.generatePublic(keySpec);
            Signature signature = Signature.getInstance("MD5withRSA");
            signature.initVerify(pubKey);
            signature.update(data);
            return signature.verify(sign);
        }
    public static KeyPair generateKeyPair(int size){
    	
    	KeyPairGenerator keyPairGen=null;
		try {
			keyPairGen = KeyPairGenerator     
			            .getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {//not necessary
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     
	        keyPairGen.initialize(size);     
	  
	        java.security.KeyPair keyPair = keyPairGen.generateKeyPair();     
	  
	        
	        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();     
	  
	        
	        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate(); 
	        return new KeyPair(encryptBASE64(privateKey.getEncoded()), encryptBASE64(publicKey.getEncoded()));
 
    	
    }
    private static byte[] decryptByPrivateKey(byte data[], String key)
        throws Exception
    {
        byte keyBytes[] = decryptBASE64(key);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        java.security.Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(2, privateKey);
        return cipher.doFinal(data);
    }

    private static byte[] encryptByPublicKey(byte data[], String key)
        throws Exception
    {
        byte keyBytes[] = decryptBASE64(key);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        java.security.Key publicKey = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(1, publicKey);
        return cipher.doFinal(data);
    }

    public static byte[] encrypt(byte input[], String key, int maxlength)
        throws Exception
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int count = input.length;
        ByteBuffer bf = ByteBuffer.wrap(input);
        for(int i = 0; i < count; i += maxlength)
        {
            int l = Math.min(maxlength, count - i);
            byte ba[] = new byte[l];
            bf.get(ba);
            byte encrypted[] = encryptByPublicKey(ba, key);
            bos.write(intToByteArray(encrypted.length));
            bos.write(encrypted);
        }

        return bos.toByteArray();
    }

    public static byte[] decrypt(byte input[], String key)
        throws IOException, Exception
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte ba[];
        for(ByteBuffer buffer = ByteBuffer.wrap(input); buffer.position() < input.length; bos.write(decryptByPrivateKey(ba, key)))
        {
            byte lar[] = new byte[4];
            buffer.get(lar);
            ba = new byte[byteArrayToInt(lar)];
            buffer.get(ba);
        }

        return bos.toByteArray();
    }

    private static final byte[] intToByteArray(int value)
    {
        return (new byte[] {
            (byte)(value >>> 24), (byte)(value >>> 16), (byte)(value >>> 8), (byte)value
        });
    }

    private static final int byteArrayToInt(byte value[])
    {
        if(value.length != 4)
            return 0;
        int ret = 0;
        for(int i = 0; i < value.length; i++)
            ret += (value[i] & 0xff) << 8 * (3 - i);

        return ret;
    }

    public static byte[] decryptBASE64(String key)
    {
        return Base64Coder.decrypt(key);
    }

    public static String encryptBASE64(byte sign[])
    {
        return Base64Coder.encrypt(sign);
    }

    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
}
