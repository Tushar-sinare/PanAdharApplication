package com.netwin.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netwin.service.ErrorApplicationService;

@Component
public class EncryptionData {
	@Autowired
	ErrorApplicationService errorApplicationService;

	/*
	 * public Map<String, String> getEncryptedData(Map<String, Object> dataMap,
	 * Map<String, String> resultVOMap) { Map<String, String> mainResultVOMap = new
	 * HashMap<>(); Map<String, String> resultVOMap1 = new HashMap<>();
	 * 
	 * Map<String, String> resultMap = new HashMap<>();
	 * 
	 * // Process entries from responseMap for (Map.Entry<String, Object> returnData
	 * : dataMap.entrySet()) { Object value = returnData.getValue(); if (value !=
	 * null) { try {
	 * resultMap.put(AESExample.encrypt(returnData.getKey(),ConstantVariable.
	 * SECRETKEY), AESExample.encrypt(value.toString(),
	 * ConstantVariable.SECRETKEY)); } catch (Exception e) {
	 * errorApplicationService.storeError(401, e.getMessage()); } } }
	 * 
	 * // Process entries from resultVOMap for (Entry<String, String> returnData1 :
	 * resultVOMap.entrySet()) { Object value = returnData1.getValue(); if (value !=
	 * null) { try {
	 * resultMap.put(AESExample.encrypt(returnData1.getKey(),ConstantVariable.
	 * SECRETKEY), AESExample.encrypt(value.toString(),
	 * ConstantVariable.SECRETKEY));
	 * resultVOMap1.put(AESExample.encrypt(returnData1.getKey(),ConstantVariable.
	 * SECRETKEY), AESExample.encrypt(value.toString(),
	 * ConstantVariable.SECRETKEY)); } catch (Exception e) {
	 * errorApplicationService.storeError(401, e.getMessage()); } } }
	 * 
	 * // Print encrypted data for (Map.Entry<String, String> returnData1 :
	 * resultMap.entrySet()) { if (!returnData1.getKey().contains("resultVO")) {
	 * mainResultVOMap.put(returnData1.getKey(), returnData1.getValue()); } else {
	 * mainResultVOMap.put(returnData1.getKey(), resultVOMap1.toString()); } }
	 * return mainResultVOMap; }
	 */

	public String getEncryptResponse(String response) {
		String strEncrypt = null;
		try {
			strEncrypt = AESExample.encrypt(response, ConstantVariable.SECRETKEY);
		} catch (Exception ex) {
			System.out.println(ex);
		}

		return strEncrypt;
	}
}
