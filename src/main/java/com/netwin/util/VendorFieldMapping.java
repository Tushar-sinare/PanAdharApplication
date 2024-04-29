package com.netwin.util;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;

@Component
public class VendorFieldMapping {
	public String replaceKeys(String jsonString, Map<String, String> replacementMap) {
		Map<String, String> vendorMap = new HashMap<>();
		Map<String, String> resultVo = new HashMap<>();
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
						} else {

							String newKey = replacementMap.get(key1);

							resultVo.put(newKey, value.get(key1));
						}
					}
				}

			}
			if (replacementMap.containsKey(key) && !replacementMap.get(key).isEmpty()) {

				if (jsonObject.isNull(key)) {
					System.out.println("key " + key);
					vendorMap.put(replacementMap.get(key), "null");
				} else {
					String newKey = replacementMap.get(key);

					vendorMap.put(newKey, jsonObject.get(key));
				}
			}
		}
		vendorMap.put("resultVO", resultVo.toString());

		return vendorMap.toString();
	}

}
