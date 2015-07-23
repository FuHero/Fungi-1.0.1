package com.ikwang.fungi.coder;

import java.security.GeneralSecurityException;

import com.ikwang.fungi.IDigitalSigner;

public class RSADigitalSigner implements IDigitalSigner {

	public RSADigitalSigner() {
		 
	}

	 
	public byte[] sign(byte[] input, String privateKey) throws GeneralSecurityException
		 {
		return RSACoder.sign(input, privateKey);
	}
	
 
 
	public String sign(String input, String privateKey)
			throws GeneralSecurityException {
		 
		 return Base64Coder.encrypt(sign(input.getBytes(), privateKey));
	}

	 
	public boolean verify(byte[] sign, byte[] original, String publicKey)  { 
		try {
			return RSACoder.verify(original, publicKey, sign);
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
    public com.ikwang.fungi.IDigitalSigner.KeyPair generateKeyPair(int size)
    {
        
        return RSACoder.generateKeyPair(size);
    }
	 
	public boolean verify(String sign, String original, String publicKey) {
		 
		 return verify(Base64Coder.decrypt(sign), original.getBytes(), publicKey);
	}

}
