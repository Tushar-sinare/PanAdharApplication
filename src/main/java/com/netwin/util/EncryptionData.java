package com.netwin.util;

import org.springframework.stereotype.Component;

import com.netwin.exception.EncryptionDataException;

@Component
public class EncryptionData {

    public String getEncryptResponse(String response) throws EncryptionDataException {
        String strEncrypt = null;
        try {
            strEncrypt = AESExample.encrypt(response, ConstantVariable.SECRETEKEY);
        } catch (Exception e) {
            // Wrap the caught exception into a custom exception and throw it
            throw new EncryptionDataException("Error while encrypting response", e);
        }
        return strEncrypt;
    }
}

