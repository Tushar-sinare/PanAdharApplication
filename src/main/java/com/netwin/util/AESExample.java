package com.netwin.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


import com.netwin.logger.LoggerProvider;
import com.netwin.logger.MyLogger;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class AESExample {
static final MyLogger logger = LoggerProvider.getLogger(AESExample.class);
    private static final int CBC_IV_LENGTH = 16;

    private AESExample() {
        throw new IllegalStateException("Utility class");
    }

 
    private static byte[] generateRandomIV() {
        byte[] iv = new byte[CBC_IV_LENGTH];
        new SecureRandom().nextBytes(iv);
        return iv;
    }
    
    public static String encrypt(String plainText, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
        String result = null;

            byte[] iv = generateRandomIV();
            SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
            byte[] combined = new byte[iv.length + encryptedBytes.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(encryptedBytes, 0, combined, iv.length, encryptedBytes.length);
            result = Base64.getEncoder().encodeToString(combined);
       
        logger.info(result);
        return result;
    }

    public static String decrypt(String encryptedText, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
        String result = null;

            byte[] combined = Base64.getDecoder().decode(encryptedText);
            byte[] iv = new byte[CBC_IV_LENGTH];
            byte[] encryptedBytes = new byte[combined.length - CBC_IV_LENGTH];
            System.arraycopy(combined, 0, iv, 0, CBC_IV_LENGTH);
            System.arraycopy(combined, CBC_IV_LENGTH, encryptedBytes, 0, encryptedBytes.length);
            SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            result = new String(decryptedBytes);
        
        return result;
    }

}
