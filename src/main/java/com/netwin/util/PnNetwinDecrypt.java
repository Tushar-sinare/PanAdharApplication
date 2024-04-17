package com.netwin.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netwin.exception.PnNetwinDecryptException;
import com.netwin.service.ErrorApplicationService;

@Component
public class PnNetwinDecrypt {
    
    private final ErrorApplicationService errorApplicationService;

    @Autowired
    public PnNetwinDecrypt(ErrorApplicationService errorApplicationService) {
        this.errorApplicationService = errorApplicationService;
    }

    public String getPnRequestDecryptData(String pnrequestJson) throws PnNetwinDecryptException {
    	
        String decryptedKey = null;
        try {
            // Your secret key
            // Replace this with your actual secret key
            decryptedKey = AESExample.decrypt(pnrequestJson, ConstantVariable.SECRETEKEY);
        } catch (Exception e) {
            // Wrap the caught exception into a custom exception and throw it
            throw new PnNetwinDecryptException("Error while decrypting PN request", e);
        }
        return decryptedKey;
    }

    public Map<String, String> getPnRequestEncryptData(Map<String, String> vendorValue) {
        Map<String, String> jsonRequest1 = new HashMap<>();
        for (Map.Entry<String, String> jsonRequest : vendorValue.entrySet()) {
            String key1 = jsonRequest.getKey();
            String value = jsonRequest.getValue();
            try {
                // Replace this with your actual secret key
                String decryptedKey = AESExample.encrypt(key1, ConstantVariable.SECRETEKEY);
                String decryptedValue = AESExample.encrypt(value, ConstantVariable.SECRETEKEY);
                jsonRequest1.put(decryptedKey, decryptedValue);
            } catch (Exception e) {
                // Log the error and continue processing other entries
                errorApplicationService.storeError(401, e.getMessage());
            }
        }
        return jsonRequest1;
    }
}

