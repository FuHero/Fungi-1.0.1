package com.ikwang.fungi.dao.test;

import com.ikwang.fungi.IDigitalSigner.KeyPair;
import com.ikwang.fungi.IRequestValidator;
import com.ikwang.fungi.coder.MD5AESDigitalSigner;
import com.ikwang.fungi.coder.MD5DigitalSigner;
import com.ikwang.fungi.coder.RSADigitalSigner;
import com.ikwang.fungi.model.Response;
import com.ikwang.fungi.util.Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "test-context.xml")
public class RequestValidatorTest {

	private static String PRIVATEKEY="zVzS110gTj/oSnDGZanoLQ==";
	private static String PUBLICKEY="MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJgfXsIMKRECr/ReauByNwhYVeCIjKZ9ePa8WQ7t7gXo9nfgyY8sQk3ttY5QMd9AbFt2Q4rXPr40Ya4ZaTjfaqUCAwEAAQ==";
 
	@Autowired
	private IRequestValidator signatureValidator;
	MD5AESDigitalSigner signer=new MD5AESDigitalSigner();
	RSADigitalSigner rsaSigner=new RSADigitalSigner();
	MD5DigitalSigner md5Signer=new MD5DigitalSigner();
	@Test
	public void testMD5AESDigitalSigner() throws GeneralSecurityException{
	
		KeyPair pair=signer.generateKeyPair(128);
		System.out.println("private key:"+pair.getPrivateKey()+",public key:"+pair.getPublicKey());
		String plainText="this is a very plain text to test digital signer";
		String t="";
		Date d;
		for(int i=0;i<10;i++){
			t+=plainText;
			d=new Date();
		String sign=signer.sign(t, pair.getPrivateKey());
		long consume=new Date().getTime()-d.getTime();
		System.out.println("plain text:"+plainText+",sign:"+sign+",length:"+t.length()+",consuming:"+consume);
		d=new Date();
		boolean result=signer.verify(sign, t, pair.getPublicKey());
		System.out.println("result:"+result+",consuming:"+(new Date().getTime()-d.getTime()));
		}
	}
	@Test
	public void testRSADigitalSigner() throws GeneralSecurityException{
	
		KeyPair pair=rsaSigner.generateKeyPair(1024);
		System.out.println("private key:"+pair.getPrivateKey()+",public key:"+pair.getPublicKey());
		String plainText="this is a very plain text to test digital signer";
		String t="";
		Date d;
		for(int i=0;i<10;i++){
			t+=plainText;
			d=new Date();
		String sign=rsaSigner.sign(t, pair.getPrivateKey());
		long consume=new Date().getTime()-d.getTime();
		System.out.println("plain text:"+plainText+",sign:"+sign+",length:"+t.length()+",consuming:"+consume);
		d=new Date();
		boolean result=rsaSigner.verify(sign, t, pair.getPublicKey());
		System.out.println("result:"+result+",consuming:"+(new Date().getTime()-d.getTime()));
		}
	}
	@Test
	public void testMD5DigitalSigner() throws GeneralSecurityException{
	
		KeyPair pair=new KeyPair("some random text", "some random text");
		System.out.println("private key:"+pair.getPrivateKey()+",public key:"+pair.getPublicKey());
		String plainText="this is a very plain text to test digital signer";
		String t="";
		Date d;
		for(int i=0;i<10;i++){
			t+=plainText;
			d=new Date();
		String sign=md5Signer.sign(t, pair.getPrivateKey());
		long consume=new Date().getTime()-d.getTime();
		System.out.println("plain text:"+plainText+",sign:"+sign+",length:"+t.length()+",consuming:"+consume);
		d=new Date();
		boolean result=md5Signer.verify(sign, t, pair.getPublicKey());
		System.out.println("result:"+result+",consuming:"+(new Date().getTime()-d.getTime()));
		}
	}
	@Test
	public void testSignatureValidator() throws Exception{
		Map<String,String> map=new TreeMap<String,String>();
		map.put("method", "test");
		map.put("v", "1.0");
		map.put("timestamp", "2001-12-12 19:19:19");
		map.put("format", "json");
		map.put("access_token", "none");
		map.put("af", "af");
		map.put("app_key", "ikwang");
		String ori=StringUtils.collectionToDelimitedString(map.entrySet(), "&");
		String sign=signer.sign(ori, PRIVATEKEY);
		map.put("sign", sign);
		map.put("sign_type", "aes");
		Response ret=signatureValidator.validate(Util.createRequestFromMap(map));
		Assert.isTrue(ret.ok());
	}

}
