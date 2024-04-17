package com.netwin.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import com.netwin.exception.AESOperationException;

import java.security.SecureRandom;
import java.util.Base64;

public class AESExample {

    private static final int CBC_IV_LENGTH = 16;

    private AESExample() {
        throw new IllegalStateException("Utility class");
    }

    // Encrypt function
    public static String encrypt(String plainText, String key) throws AESOperationException {
        try {
            byte[] iv = generateRandomIV();
            SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));

            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());

            byte[] combined = new byte[iv.length + encryptedBytes.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(encryptedBytes, 0, combined, iv.length, encryptedBytes.length);

            return Base64.getEncoder().encodeToString(combined);
        } catch (Exception e) {
            throw new AESOperationException("Encryption failed: " + e.getMessage());
        }
    }

    // Decrypt function
    public static String decrypt(String encryptedText, String key) throws AESOperationException {
        try {
            byte[] combined = Base64.getDecoder().decode(encryptedText);
            byte[] iv = new byte[CBC_IV_LENGTH];
            byte[] encryptedBytes = new byte[combined.length - CBC_IV_LENGTH];

            System.arraycopy(combined, 0, iv, 0, CBC_IV_LENGTH);
            System.arraycopy(combined, CBC_IV_LENGTH, encryptedBytes, 0, encryptedBytes.length);

            SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));

            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new AESOperationException("Decryption failed: " + e.getMessage());
        }
    }

    private static byte[] generateRandomIV() {
        byte[] iv = new byte[CBC_IV_LENGTH];
        new SecureRandom().nextBytes(iv);
        return iv;
    }
}
