package com.ikwang.fungi.coder;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import com.ikwang.fungi.IDigitalSigner;

public class MD5DigitalSigner implements IDigitalSigner {

	public MD5DigitalSigner() {
		// TODO Auto-generated constructor stub
	}

 
	public byte[] sign(byte[] input, String privateKey)
			throws GeneralSecurityException {
		
		return sign(new String(input)+privateKey);
	}

	protected byte[] sign(String input) throws GeneralSecurityException{
		try {
			return HashCoder.md5(input.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {//impossible to reach here
 
			e.printStackTrace();
		}
		return new byte[0];
		
	}
 
	public String sign(String input, String privateKey)
			throws GeneralSecurityException {
		
		return Base64Coder.encrypt(sign(input+privateKey));
	}

 
	public boolean verify(byte[] sign, byte[] original, String publicKey) {

		try {
			return verify(sign,new String(original,"UTF-8")+publicKey);
		} catch (UnsupportedEncodingException e) {//impossible to reach here
			 
			e.printStackTrace();
		}
		return false;
	}

	protected boolean verify(byte[] sign,String original){
		try {
			return HashCoder.md5Verify(original.getBytes("UTF-8"), sign);
		} catch (UnsupportedEncodingException e) {//impossible to reach here
			 
			e.printStackTrace();
		}
		return false;
	}
 
	public boolean verify(String sign, String original, String publicKey) {
		 
		return verify(Base64Coder.decrypt(sign), original+publicKey);
	}

}
