package com.netwin.util;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESExample {

    // Encrypt function

	
	  public static String encrypt(String plainText, String key) throws Exception {
	  
	  SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES"); Cipher
	  cipher = Cipher.getInstance("AES"); cipher.init(Cipher.ENCRYPT_MODE,
	  secretKey); byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
	  return Base64.getEncoder().encodeToString(encryptedBytes); }
	  
	  // Decrypt function
	  
	  public static String decrypt(String encryptedText, String key) throws Exception {
		  SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(),
	  "AES"); Cipher cipher = Cipher.getInstance("AES");
	  cipher.init(Cipher.DECRYPT_MODE, secretKey); 
	  byte[] cipherBytes = Base64.getDecoder().decode(encryptedText);
	  byte[] decryptedBytes =  cipher.doFinal(cipherBytes); return new String(decryptedBytes);
	  }
	  
		/*
		 * public static byte[] encrypt(String data, byte[] key, byte[] iv) throws
		 * Exception { Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		 * SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES"); IvParameterSpec
		 * ivParameterSpec = new IvParameterSpec(iv); cipher.init(Cipher.ENCRYPT_MODE,
		 * secretKeySpec, ivParameterSpec); return cipher.doFinal(data.getBytes()); }
		 * public static String decrypt(String encryptedData, byte[] key, byte[] iv)
		 * throws Exception { Cipher cipher =
		 * Cipher.getInstance("AES/CBC/PKCS5Padding"); SecretKeySpec secretKeySpec = new
		 * SecretKeySpec(key, "AES"); IvParameterSpec ivParameterSpec = new
		 * IvParameterSpec(iv); cipher.init(Cipher.DECRYPT_MODE, secretKeySpec,
		 * ivParameterSpec); byte[] decodedBytes =
		 * Base64.getDecoder().decode(encryptedData); byte[] decryptedBytes =
		 * cipher.doFinal(decodedBytes); return new String(decryptedBytes); }
		 * 
		 */
	 
	
	 // Encryption function
		/*
		 * public static String encrypt(String text, String key) throws Exception {
		 * Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); SecretKeySpec
		 * secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
		 * cipher.init(Cipher.ENCRYPT_MODE, secretKey); byte[] encryptedBytes =
		 * cipher.doFinal(text.getBytes(StandardCharsets.UTF_8)); return
		 * Base64.getEncoder().encodeToString(encryptedBytes); }
		 * 
		 * // Decryption function public static String decrypt(String ciphertext, String
		 * key) throws Exception { Cipher cipher =
		 * Cipher.getInstance("AES/ECB/PKCS5Padding"); SecretKeySpec secretKey = new
		 * SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
		 * cipher.init(Cipher.DECRYPT_MODE, secretKey); byte[] decodedBytes =
		 * Base64.getDecoder().decode(ciphertext); byte[] decryptedBytes =
		 * cipher.doFinal(decodedBytes); return new String(decryptedBytes,
		 * StandardCharsets.UTF_8); }
		 */
}
