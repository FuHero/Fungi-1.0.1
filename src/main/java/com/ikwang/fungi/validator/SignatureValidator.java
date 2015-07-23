package com.ikwang.fungi.validator;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.util.StringUtils;

import com.ikwang.fungi.IRequestValidator;
import com.ikwang.fungi.coder.MD5AESDigitalSigner;
import com.ikwang.fungi.coder.MD5DigitalSigner;
import com.ikwang.fungi.coder.RSADigitalSigner;
import com.ikwang.fungi.model.AppDef;
import com.ikwang.fungi.model.IRequest;
import com.ikwang.fungi.model.Response;

public class SignatureValidator implements IRequestValidator {

	public static final String SIGN_TYPE_RSA="rsa";
	public static final String SIGN_TYPE_MD5="md5";
	public static final String SIGN_TYPE_AES="aes";
	MD5AESDigitalSigner aesSigner=new MD5AESDigitalSigner();
	RSADigitalSigner rsaSigner=new RSADigitalSigner();
	MD5DigitalSigner md5Signer=new MD5DigitalSigner();
	public SignatureValidator() {
		
	}
	
	@Override
	public Response validate(IRequest request) {
 
		AppDef app=request.getAppDef();
		if(app == null){
			return Response.fail(Response.STATUS_FAILED_PARAMETERCHECK, "a proper appKey is required");
		}
		
		Map<String,String> param=new TreeMap<String,String>(request.getParams());
		String sign=param.remove("sign");
		if(StringUtils.hasText(sign) == false){
			return Response.fail(Response.STATUS_FAILED_PARAMETERCHECK, "parameter sign is required");
		}
		String paramString=StringUtils.collectionToDelimitedString(param.entrySet(), "&");
		try {
			if(verify(sign,paramString, app,request.getValue("sign_type")) == false){
				return Response.fail(Response.STATUS_FAILED_SIGNATURECHECK, "verifying signature failed");
			}
		} catch (Exception e) {		
			e.printStackTrace();
			return Response.fail(Response.STATUS_FAILED_SIGNATURECHECK, "verifying signature failed");
		}
		return Response.ok(null);
	}
	
	protected boolean verify(String sign,String original,AppDef app,String signType){
		if(SIGN_TYPE_MD5.equals(signType)){
			return md5Signer.verify(sign, original, app.getPublicKey());
		}
		else if(SIGN_TYPE_AES.equals(signType)){
			return aesSigner.verify(sign, original, app.getCustomPublicKey());
		}
		else{
			return rsaSigner.verify(sign, original, app.getCustomPublicKey());
		}
	}


}
