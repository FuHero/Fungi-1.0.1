package com.ikwang.fungi;

import java.security.GeneralSecurityException;

public interface IDigitalSigner {
	
	/**
	 * signs the input byte array using the given privateKey
	 * @param input the byte array to be signed
	 * @Param privateKey the private key
	 * @return the signature in the form of byte array
	 *   
	 * */
	public byte[] sign(byte[] input,String privateKey) throws GeneralSecurityException;
	/**
	 * signs the input String using the given privateKey
	 * @param input the base64 String to be signed
	 * @Param privateKey the private key
	 * @return the signature in the form of base64 string
	 *   
	 * */
	public String sign(String input,String privateKey) throws GeneralSecurityException;
	/**
	 * verify the signature of the input byte array using the given public key
	 * @param sign the signature
	 * @param original the original byte array
	 * @Param publicKey the public key
	 * @return true if the signature passed in is valid,false otherwise
	 *   
	 * */
	public boolean verify(byte[] sign,byte[] original,String publicKey);
	/**
	 * verify the signature of the input base64 string using the given public key
	 * @param sign the signature in the form of base64 string
	 * @param original the original input in the form of base64 string
	 * @Param publicKey the public key
	 * @return true if the signature passed in is valid,false otherwise
	 *   
	 * */
	public boolean verify(String sign,String original,String publicKey);
	
	/**
	 * generates an instance of KeyPair
	 * @Param size the size
	 * @return an instance of KeyPair
	 * */
	//public KeyPair generateKeyPair(int size);
	
	public class KeyPair{
		private String privateKey;
		private String publicKey;
		
		public KeyPair(String privateKey,String publicKey){
			this.privateKey = privateKey;
			this.publicKey = publicKey;
			
		}
		
		public String getPrivateKey() {
			return privateKey;
		}
 
		public String getPublicKey() {
			return publicKey;
		}
 
		
	}
}
