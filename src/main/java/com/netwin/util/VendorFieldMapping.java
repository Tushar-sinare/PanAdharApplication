package com.netwin.util;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;


@Component
public class VendorFieldMapping {
	public String replaceKeys(String jsonString, Map<String, String> replacementMap) {
		Map<String, String> vendorMap = new HashMap<>();
		// Parse the JSON string
		JSONObject jsonObject = new JSONObject(jsonString);

		// Iterate over the keys of the JSON object
		for (String key : jsonObject.keySet()) {
			if (replacementMap.containsKey(key) && (!replacementMap.get(key).isEmpty())) {
				if (jsonObject.isNull(key)) {
					vendorMap.put(replacementMap.get(key), "null");
				} else {

					vendorMap.put(replacementMap.get(key), jsonObject.getString(key));
				}

			}
		}

		// Convert the modified JSON object back to a string
		return vendorMap.toString();
	}

	public String replaceKeys1(String jsonString, Map<String, String> replacementMap) {
	
		JSONObject jsonObject = new JSONObject(jsonString);
		Map<String, Object> vendorMap = new HashMap<>();
		Map<String, Object> resultVo = new HashMap<>();
		
		for (String key : jsonObject.keySet()) {
			
			// System.out.println(key);
			if (key.equals("resultVO")) {
				JSONObject value = (JSONObject) jsonObject.get(key);

				for (String key1 : value.keySet()) {
					if (replacementMap.containsKey(key1) && !replacementMap.get(key1).isEmpty()) {

						if (value.isNull(key1)) {
							resultVo.put(replacementMap.get(key1), "null");
						}else {
						
							String newKey = replacementMap.get(key1);

							resultVo.put(newKey, value.get(key1));
						}
					}
				}
			}else if(key.equals("userUuid")) {
				
				resultVo.put(replacementMap.get(key), jsonObject.get(key));
			}
			if (replacementMap.containsKey(key) && !replacementMap.get(key).isEmpty()) {

				if (jsonObject.isNull(key)) {
					
					vendorMap.put(replacementMap.get(key), "null");
				}else if(key.equals("userUuid")|| key.equals("success")) {
					
				} else {
					String newKey = replacementMap.get(key);

					vendorMap.put(newKey, jsonObject.get(key));
				}
			}
		}
		vendorMap.put("resultVO", resultVo.toString());
		return vendorMap.toString();
	}
	public String replaceKeys11(String jsonString, Map<String, String> replacementMap) {
		JSONObject jsonObject = new JSONObject(jsonString);
		Map<String, Object> vendorMap = new HashMap<>();
		Map<String, Object> resultVo = new HashMap<>();
		
		for (String key : jsonObject.keySet()) {
		
			// System.out.println(key);
			if (key.equals("resultVO")) {
				JSONObject value = (JSONObject) jsonObject.get(key);

				for (String key1 : value.keySet()) {
					if (replacementMap.containsKey(key1) && !replacementMap.get(key1).isEmpty()) {

						if (value.isNull(key1)) {
							resultVo.put(replacementMap.get(key1), "null");
						}else {
						
							String newKey = replacementMap.get(key1);

							resultVo.put(newKey, value.get(key1));
						}
					}
				}

			}else if(key.equals("gadtlResp")) {
				String str = (String) jsonObject.get(key);
				JSONObject value = new JSONObject(str);
				for (String key1 : value.keySet()) {
					if (replacementMap.containsKey(key1) && !replacementMap.get(key1).isEmpty()) {
						resultVo.put(replacementMap.get(key1), value.get(key1));
					}
				}
				
			}else if(key.equals("userUuid")) {
				
				resultVo.put(replacementMap.get(key), jsonObject.get(key));
			}
			if (replacementMap.containsKey(key) && !replacementMap.get(key).isEmpty()) {

				if (jsonObject.isNull(key)) {
					
					vendorMap.put(replacementMap.get(key), "null");
				}else if(key.equals("userUuid")|| key.equals("success")) {
					
				} else {
					String newKey = replacementMap.get(key);

					vendorMap.put(newKey, jsonObject.get(key));
				}
			}
		}
		vendorMap.put("resultVO", resultVo.toString());
		return vendorMap.toString();
	}
	public String replaceKeys( Map<String, String> replacementMap,String jsonString, String reqStatus) {
			Map<String, String> vendorMap = new HashMap<>();
			Map<String, Object> resultVo = new HashMap<>();
			// Parse the JSON string
			JSONObject jsonObject = new JSONObject(jsonString);

	//{request=request, status_code=status_code, valid_aadhaar=valid_aadhaar, message_code=message_code, success=success, response=responsess, otp_sent=otp_sent, userUuid=userReqSrNo, if_number=if_number, message=message, client_id=client_id
			// Iterate over the keys of the JSON object
			for (String key : jsonObject.keySet()) {
				if (replacementMap.containsKey(key) && (!replacementMap.get(key).isEmpty())) {
					if (key.equals("userUuid")||key.equals("success")) {

						resultVo.put(replacementMap.get(key), jsonObject.get(key));
					} else if (jsonObject.isNull(key)) {

						vendorMap.put(replacementMap.get(key), "null");
					} else {

						vendorMap.put(replacementMap.get(key), jsonObject.getString(key));
					}

				}else {
					
				}
			}
			if(resultVo.containsKey("success")) {
				
				if(resultVo.get("success").equals("true")) {
					if(reqStatus.equals("V")) {
					resultVo.put("msgDescr", jsonObject.get("message"));
					}else {
						resultVo.put("msgDescr", jsonObject.get("status"));
					}
					resultVo.put("msgCode", "200");
				}
			}
			vendorMap.put("ResultVo", resultVo.toString());
			// Convert the modified JSON object back to a string
			System.out.println("---- vendorMap    "+vendorMap);
			return vendorMap.toString();
		
	}

}
