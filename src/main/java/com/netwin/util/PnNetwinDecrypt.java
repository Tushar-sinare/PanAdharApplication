package com.netwin.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.netwin.service.ErrorApplicationService;

public class PnNetwinDecrypt {
	@Autowired
	private ErrorApplicationService errorApplicationService;

	public String getPnRequestDecryptData(String pnrequestJson) throws Exception {
		String decryptedKey = null;
		// try {
		// Your secret key
		// Replace this with your actual secret key
		decryptedKey = AESExample.decrypt(pnrequestJson, ConstantVariable.SECRETKEY);

		/*
		 * } catch (Exception e) { errorApplicationService.storeError(401,
		 * e.getMessage()); }
		 */

		return decryptedKey;
	}

	/*
	 * public Map<String, String> getPnRequestDecryptData(Map<String, String>
	 * panRequest) { Map<String, String> jsonRequest1 = new HashMap<String,
	 * String>(); for (Map.Entry<String, String> jsonRequest :
	 * panRequest.entrySet()) { String key1 = jsonRequest.getKey(); String value =
	 * jsonRequest.getValue(); try { // Your secret key // Replace this with your
	 * actual secret key String decryptedKey = AESExample.decrypt(key1,
	 * ConstantVariable.SECRETKEY); String decryptedValue =
	 * AESExample.decrypt(value, ConstantVariable.SECRETKEY);
	 * jsonRequest1.put(decryptedKey, decryptedValue);
	 * 
	 * } catch (Exception e) { errorApplicationService.storeError(401,
	 * e.getMessage()); }
	 * 
	 * } return jsonRequest1; }
	 */

	public Map<String, String> getPnRequestEncryptData(Map<String, String> vendorValue) {
		Map<String, String> jsonRequest1 = new HashMap<String, String>();
		for (Map.Entry<String, String> jsonRequest : vendorValue.entrySet()) {
			String key1 = jsonRequest.getKey();
			String value = jsonRequest.getValue();
			try {
				// Your secret key
				// Replace this with your actual secret key
				String decryptedKey = AESExample.encrypt(key1, ConstantVariable.SECRETKEY);
				String decryptedValue = AESExample.encrypt(value, ConstantVariable.SECRETKEY);
				jsonRequest1.put(decryptedKey, decryptedValue);

			} catch (Exception e) {
				errorApplicationService.storeError(401, e.getMessage());
			}

		}
		return jsonRequest1;
	}

}
