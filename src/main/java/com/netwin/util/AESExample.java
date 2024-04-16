package com.netwin.util;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESExample {

    // Encrypt function

	  private AESExample() {
		    throw new IllegalStateException("Utility class");
		  }

	  public static String encrypt(String plainText, String key) throws Exception{
	  
	  SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES"); 
	  Cipher cipher = Cipher.getInstance("AES");
	  cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	  byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
	  return Base64.getEncoder().encodeToString(encryptedBytes); }
	  
	  // Decrypt function
	  
	  public static String decrypt(String encryptedText, String key)throws Exception{
		  SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES"); 
		  Cipher cipher = Cipher.getInstance("AES");
	  cipher.init(Cipher.DECRYPT_MODE, secretKey); 
	  byte[] cipherBytes = Base64.getDecoder().decode(encryptedText);
	  byte[] decryptedBytes =  cipher.doFinal(cipherBytes);
	  return new String(decryptedBytes);
	  }
	  
		
}
