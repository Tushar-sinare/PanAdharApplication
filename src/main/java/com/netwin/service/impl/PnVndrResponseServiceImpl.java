package com.netwin.service.impl;



import java.sql.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netwin.entiry.PnRequest;
import com.netwin.entiry.PnResponse;
import com.netwin.entiry.PnVendorDetails;
import com.netwin.entiry.PnVndrRequest;
import com.netwin.entiry.PnVndrResponse;
import com.netwin.entiry.Result1;
import com.netwin.repo.PnResponseRepo;
import com.netwin.repo.PnVndrResponseRepo;
import com.netwin.service.ErrorApplicationService;
import com.netwin.service.PnResponseService;
import com.netwin.service.PnVndrResponseService;
import com.netwin.util.ConstantVariable;
import com.netwin.util.EncryptionData;
import com.netwin.util.NtResponse;
@Service
public class PnVndrResponseServiceImpl implements PnVndrResponseService {

private final PnVndrResponseRepo pnVndrResponseRepo;

private EncryptionData encryptionData;

private PnResponseService pnResponseService;

private final PnResponseRepo pnResponseRepo;

private NtResponse ntResponse;
@Autowired
public PnVndrResponseServiceImpl(PnVndrResponseRepo pnVndrResponseRepo,PnResponseRepo pnResponseRepo) {

	this.pnVndrResponseRepo = pnVndrResponseRepo;
	this.pnResponseRepo = pnResponseRepo;
	
	
}
private ErrorApplicationService errorApplicationService;
private Date date = new Date(System.currentTimeMillis());
	@Override
	public Result1<PnResponse> fetchPanApiResponse(PnVndrRequest pnVndrRequest2, PnRequest pnRequest2) {
		
			//fetch vendor details 
		PnVendorDetails pnVendorDetails = pnRequest2.getPnVendorDetails();
		if(pnVendorDetails==null) {
		Result1<Map<String,Object>> result  = ntResponse.getNtResponse(2004);
			return new Result1<>(result.toString());
		}
		try {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBasicAuth(pnVendorDetails.getPnVnDrApiUser(), pnVendorDetails.getPnVndrApiPsw());
		String requestBody = pnVndrRequest2.getReqDecrypt();
		//Vendor Encryption data
		 StringBuilder jsonStringBuilder = new StringBuilder();
	        jsonStringBuilder.append("{");

	        // Assuming requestBody contains key-value pairs separated by commas
	        String[] keyValuePairs = requestBody.split(",");
	        for (String pair : keyValuePairs) {
	            String[] keyValue = pair.split("=");
	            if (keyValue.length == 2) {
	                String key = keyValue[0].trim();
	                String value = keyValue[1].trim();
	                jsonStringBuilder.append("\"").append(key).append("\":\"").append(value).append("\",");
	            }
	        }

	        // Remove the trailing comma if any
	        if (jsonStringBuilder.charAt(jsonStringBuilder.length() - 1) == ',') {
	            jsonStringBuilder.deleteCharAt(jsonStringBuilder.length() - 1);
	        }

	        jsonStringBuilder.append("}");

	        // Get the final JSON string
	        String jsonString = jsonStringBuilder.toString();
	
		HttpEntity<String> requestEntity = new HttpEntity<>(jsonString, headers);
	
		return callPanVerifyApi(pnVendorDetails.getPnVrfyURL(), HttpMethod.POST, requestEntity, pnRequest2);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	private Result1<PnResponse> callPanVerifyApi(String pnVrfyURL, HttpMethod post, HttpEntity<String> requestEntity,
			PnRequest pnRequest2) {
PnResponse pnResponse = new PnResponse();
			
			RestTemplate restTemplate = new RestTemplate();
			
	
				// Make the HTTP request using RestTemplate
				ResponseEntity<String> responseEntity = restTemplate.exchange(pnVrfyURL, post, requestEntity,
						String.class);
				String response = responseEntity.getBody();
			
				ObjectMapper mapper = new ObjectMapper();
				
				try {
					Map<String, Object> dataMap = mapper.readValue(response, Map.class);
					Map<String, String> resultVOMap = (Map<String, String>) dataMap.get(ConstantVariable.RESULTVO);
					String responseJson = encryptionData.getEncryptResponse(response);
					PnVndrResponse pnVndrResponse = new PnVndrResponse();
					pnVndrResponse.setReqDecrypt(response);
					pnVndrResponse.setReqEncrypt(responseJson);
					
					pnVndrResponse.setEntryDate(pnRequest2.getAppDate());
					pnVndrResponse.setPanNo(pnRequest2.getPanNo());
					//Store vender Response
					PnVndrResponse pnVndrResponse1= pnVndrResponseRepo.save(pnVndrResponse);
					//call to send netwin response from vendor
					Map<String,Object>pnNetRes=	pnResponseService.fetchNetwinResponse(pnVndrResponse1,pnRequest2);
					pnResponse.setPnVndrResponse(pnVndrResponse1);
					pnResponse.setAppDate(date);
					pnResponse.setCustId(pnRequest2.getCustId());
					pnResponse.setPanNo(pnRequest2.getPanNo());
					//store response
					pnResponseRepo.save(pnResponse);
					return new Result1<PnResponse>(pnNetRes);
				} catch (Exception e) {
					errorApplicationService.storeError(203, e.getMessage());
				} 
			
			
		return null;
			
		}


}

