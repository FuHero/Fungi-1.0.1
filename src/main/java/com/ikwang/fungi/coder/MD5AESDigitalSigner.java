// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MD5AESDigitalSigner.java

package com.ikwang.fungi.coder;

import com.ikwang.fungi.IDigitalSigner;

import java.security.GeneralSecurityException;

// Referenced classes of package com.ikwang.fungi.coder:
//            HashCoder, Base64Coder, AESCoder

public class MD5AESDigitalSigner
    implements IDigitalSigner
{

    public MD5AESDigitalSigner()
    {
    }

    public byte[] sign(byte input[], String privateKey)
        throws GeneralSecurityException
    {
        byte hash[] = HashCoder.md5(input);
        return AESCoder.encrypt(hash, Base64Coder.decrypt(privateKey));
    }

    public String sign(String input, String privateKey)
        throws GeneralSecurityException
    {
        return Base64Coder.encrypt(sign(input.getBytes(), privateKey));
    }

    public com.ikwang.fungi.IDigitalSigner.KeyPair generateKeyPair(int size)
    {
        String key = Base64Coder.encrypt(AESCoder.generateSecretKey(size));
        return new com.ikwang.fungi.IDigitalSigner.KeyPair(key, key);
    }

    public boolean verify(byte sign[], byte original[], String publicKey)
    {
        byte hash[];
		try {
			hash = AESCoder.decrypt(sign, Base64Coder.decrypt(publicKey));
	        return HashCoder.md5Verify(original, hash);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


        return false;
    }

    public boolean verify(String sign, String original, String publicKey)
    {
        return verify(Base64Coder.decrypt(sign), original.getBytes(), publicKey);
    }
}
