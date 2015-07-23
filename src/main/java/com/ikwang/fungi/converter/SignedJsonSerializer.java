package com.ikwang.fungi.converter;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.ikwang.fungi.IResponseSerializer;
import com.ikwang.fungi.coder.MD5AESDigitalSigner;
import com.ikwang.fungi.coder.MD5DigitalSigner;
import com.ikwang.fungi.coder.RSADigitalSigner;
import com.ikwang.fungi.model.IRequest;
import com.ikwang.fungi.model.Response;
import com.ikwang.fungi.util.JsonUtil;

public class SignedJsonSerializer implements IResponseSerializer {

	private static final String JSON="signed_json";
	MD5AESDigitalSigner aesSigner=new MD5AESDigitalSigner();
	RSADigitalSigner rsaSigner=new RSADigitalSigner();
	MD5DigitalSigner md5Signer=new MD5DigitalSigner();
	@Override
	public String serialize(Response response, IRequest request,String format) {
		if(JSON.equals(format) == false)
			return null;
		 try {
			String ret= JsonUtil.toJson(response);
			String sign=sign(ret,request);
			return ret+"&fungi_sign="+sign;
		} catch (IOException e) {
			
			e.printStackTrace();
			return e.getLocalizedMessage();
		}
		 
	}

	private String sign(String ret, IRequest request) {			try {
		switch(request.getValue("sign_type","rsa")){
		case "rsa":

			return rsaSigner.sign(ret, request.getAppDef().getPrivateKey());
			
		case "aes":
			return aesSigner.sign(ret, request.getAppDef().getCustomPublicKey());
		case "md5":
			return md5Signer.sign(ret, request.getAppDef().getPublicKey());
		
		}}
	catch (GeneralSecurityException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return "";
	}

}
