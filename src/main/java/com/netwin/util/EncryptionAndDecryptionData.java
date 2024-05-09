package com.netwin.util;


import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netwin.service.ErrorApplicationService;

@Component
public class EncryptionAndDecryptionData {
	
	 private ErrorApplicationService errorApplicationService;

	    @Autowired
	    public EncryptionAndDecryptionData(ErrorApplicationService errorApplicationService) {
	        this.errorApplicationService = errorApplicationService;
	    }

    public String getEncryptResponse(String response) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException  {
        String strEncrypt = null;

            strEncrypt = AESExample.encrypt(response, ConstantVariable.SECRETEKEY);
       
        return strEncrypt;
    }
 public String getRequestDecryptData(String pnrequestJson) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException  {
    	
        String decryptedKey = null;
    
            // Your secret key
            // Replace this with your actual secret key
            decryptedKey = AESExample.decrypt(pnrequestJson, ConstantVariable.SECRETEKEY);
       
        
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
                errorApplicationService.storeError(401, e.getMessage(), 0, null, null);
            }
        }
        return jsonRequest1;
    }
    
    
    
    

}


