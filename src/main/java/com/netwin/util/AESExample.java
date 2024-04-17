package com.netwin.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class AESExample {

    private static final int IV_LENGTH = 16;

    private AESExample() {
        throw new IllegalStateException("Utility class");
    }

    // Encrypt function
    public static String encrypt(String plainText, String key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] iv = generateRandomIV();
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        byte[] combined = new byte[IV_LENGTH + encryptedBytes.length];
        System.arraycopy(iv, 0, combined, 0, IV_LENGTH);
        System.arraycopy(encryptedBytes, 0, combined, IV_LENGTH, encryptedBytes.length);
        return Base64.getEncoder().encodeToString(combined);
    }

    // Decrypt function
    public static String decrypt(String encryptedText, String key) throws Exception {
        byte[] combined = Base64.getDecoder().decode(encryptedText);
        byte[] iv = new byte[IV_LENGTH];
        byte[] encryptedBytes = new byte[combined.length - IV_LENGTH];
        System.arraycopy(combined, 0, iv, 0, IV_LENGTH);
        System.arraycopy(combined, IV_LENGTH, encryptedBytes, 0, encryptedBytes.length);

        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }

    private static byte[] generateRandomIV() {
        byte[] iv = new byte[IV_LENGTH];
        new SecureRandom().nextBytes(iv);
        return iv;
    }
}
