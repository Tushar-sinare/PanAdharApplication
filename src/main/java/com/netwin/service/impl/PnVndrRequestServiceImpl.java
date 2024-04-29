package com.netwin.service.impl;

import java.lang.reflect.Type;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.dozer.Mapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;
import com.netwin.dto.AharVndrRequestDto;
import com.netwin.dto.CustomerVendorDetailsDto;
import com.netwin.dto.PnVndrRequestDto;
import com.netwin.dto.PnVndrResponseDto;
import com.netwin.entiry.PnVendorDetails;
import com.netwin.entiry.PnVndrRequest;
import com.netwin.entiry.PnVndrResponse;
import com.netwin.repo.PnVendorDetailsRepo;
import com.netwin.repo.PnVndrRequestRepo;
import com.netwin.repo.PnVndrResponseRepo;
import com.netwin.service.ErrorApplicationService;
import com.netwin.service.PnResponseService;
import com.netwin.service.PnVndrRequestService;
import com.netwin.util.EncryptionAndDecryptionData;
import com.netwin.util.NtResponse;
import com.netwin.util.QueryUtil;

@Service
public class PnVndrRequestServiceImpl implements PnVndrRequestService {

	private PnResponseService pnResponseService;
	private PnVndrResponseRepo pnVndrResponseRepo;
	private EncryptionAndDecryptionData encryptionAndDecryptionData;
	private PnVndrRequestRepo pnVndrRequestRepo;
	private NtResponse ntResponse;
	private ErrorApplicationService errorApplicationService;
	private PnVendorDetailsRepo pnVendorDetailsRepo;
	private Mapper mapper;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public PnVndrRequestServiceImpl(PnResponseService pnResponseService, PnVndrResponseRepo pnVndrResponseRepo,
			EncryptionAndDecryptionData encryptionAndDecryptionData, PnVndrRequestRepo pnVndrRequestRepo,
			ErrorApplicationService errorApplicationService, PnVendorDetailsRepo pnVendorDetailsRepo,
			NtResponse ntResponse,Mapper mapper,JdbcTemplate jdbcTemplate) {
		this.pnResponseService = pnResponseService;
		this.pnVndrResponseRepo = pnVndrResponseRepo;
		this.encryptionAndDecryptionData = encryptionAndDecryptionData;
		this.pnVndrRequestRepo = pnVndrRequestRepo;
		this.errorApplicationService = errorApplicationService;
		this.pnVendorDetailsRepo = pnVendorDetailsRepo;
		this.ntResponse = ntResponse;
		this.mapper = mapper;
		this.jdbcTemplate = jdbcTemplate;
	}

	private static final Logger logger = LoggerFactory.getLogger(PnVndrRequestServiceImpl.class);

	@Override
	public String callVenderRequest(String vendorRequestJson, CustomerVendorDetailsDto customerVendorDetailsDto) throws JsonMappingException, JsonProcessingException {
		String apiUrl = null;
		PnVendorDetails pnVendorDetails = pnVendorDetailsRepo.findByPnVnDrSrNo(customerVendorDetailsDto.getVendorId());
		if (pnVendorDetails == null) {
			return ntResponse.getNtResponse(425);
		}
		apiUrl = pnVendorDetails.getPnVrfyURL();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBasicAuth(pnVendorDetails.getPnVnDrApiUser(), pnVendorDetails.getPnVndrApiPsw());
		String requestBody = vendorRequestJson;
		
		requestBody = requestBody.substring(1, requestBody.length() - 1);
		// Split the string by comma and equal sign
		String[] keyValuePairs = requestBody.split(", ");
		PnVndrRequestDto pnVndrRequestDto = new PnVndrRequestDto();
		// Create a JSONObject and add key-value pairs
		JSONObject jsonObject = new JSONObject();
		for (String pair : keyValuePairs) {
			String[] entry = pair.split("=");
			jsonObject.put(entry[0], entry[1]);
		}
	
	
		pnVndrRequestDto.setReqDecrypt(jsonObject.toString());
		pnVndrRequestDto.setReqEncrypt(encryptionAndDecryptionData.getEncryptResponse(jsonObject.toString()));
		pnVndrRequestDto.setEntryDate(new Date(System.currentTimeMillis()));
		pnVndrRequestDto.setPnReqMasSrNo(customerVendorDetailsDto.getPnReqMasSrNo());
		
		HttpEntity<String> requestEntity = new HttpEntity<>(jsonObject.toString(), headers);
		PnVndrRequest pnVndrRequest = mapper.map(pnVndrRequestDto, PnVndrRequest.class);
		
		pnVndrRequestRepo.save(pnVndrRequest);
		Map<String, String> aharRequestJsonMap = jsonStringToMap(pnVndrRequestDto);
		// details object
		// Database field Name Fetch.
		Map<String, Object> netwinRequestpara = getNetwinRequestParas();

		for (Map.Entry<String, Object> netwinField : netwinRequestpara.entrySet()) {
			if (!aharRequestJsonMap.containsKey(netwinField.getKey())
					&& (netwinField.getValue().toString()).equals("Y")) {
				return ntResponse.getNtResponse(500);

			}
		}
		String vndrResponseStr = callPanVerifyApi(apiUrl, HttpMethod.POST, requestEntity, customerVendorDetailsDto);
		return pnResponseService.customerResponseMapping(vndrResponseStr, customerVendorDetailsDto);
	}
	

	private String callPanVerifyApi(String apiUrl, HttpMethod post, HttpEntity<String> requestEntity,
			CustomerVendorDetailsDto customerVendorDetailsDto) {
		RestTemplate restTemplate = new RestTemplate();
		
		
		// Make the HTTP request using RestTemplate
		//ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, post, requestEntity,String.class);
		String response = "{\"panNo\":\"EVMPS3728A\",\"name\":\"TUSHAR VITTHAL SINARE\",\"fullNameSplit\":[\"TUSHAR\",\"VITTHAL\",\"SINARE\"],\"category\":\"person\",\"maskedAadhaar\":\"XXXXXXXX8735\",\"address\":\"178/1 Padali Kanhoor 414103 AHMED NAGAR MAHARASHTRA INDIA\",\"email\":\"TUSHARSINARE0202@GMAIL.COM\",\"phoneNumber\":\"7057192939\",\"gender\":\"M\",\"dob\":\"1995-02-02\",\"aadhaarLinked\":\"true\",\"dobVerified\":\"false\",\"resultVO\":{\"msgCode\":\"200\",\"msgDescr\":\"PAN Details fetched successfully\",\"isError\":false,\"success\":\"TRUE\",\"procRefUuid\":null,\"error\":false},\"statusCode\":\"200\",\"success\":\"true\",\"message\":null,\"messageCode\":\"success\"}";//responseEntity.getBody();
		PnVndrResponseDto pnVndrResponseDto = new PnVndrResponseDto();
		pnVndrResponseDto.setPanNo(customerVendorDetailsDto.getPanNo());
		pnVndrResponseDto.setEntryDate(new Date(System.currentTimeMillis()));
		pnVndrResponseDto.setReqDecrypt(response);
		pnVndrResponseDto.setReqEncrypt(encryptionAndDecryptionData.getEncryptResponse(response));
		pnVndrResponseDto.setPnReqMasSrNo(customerVendorDetailsDto.getPnReqMasSrNo());
		PnVndrResponse pnVndrResponse = mapper.map(pnVndrResponseDto,PnVndrResponse.class);
		pnVndrResponseRepo.save(pnVndrResponse);
	
return response;
	
	}
	private Map<String, Object> getNetwinRequestParas() {
		// Execute the query and retrieve results
		List<Map<String, Object>> netwinFieldResultsMap = null;

			netwinFieldResultsMap = jdbcTemplate.queryForList(QueryUtil.NETWNFIELDQUERY11, "P");
		
		// Process the results using Java Streams
		return netwinFieldResultsMap.stream()
				.collect(Collectors.toMap(vendorField -> (String) vendorField.get("VNDRREQKEYNAME"),
						vendorField -> (String) vendorField.get("VNDRREQKEYREQ")));
	}
	
//JsonString to Map Convert Method
	private Map<String, String> jsonStringToMap(PnVndrRequestDto pnVndrRequestDto) {
		String aharRequestDecryptString = pnVndrRequestDto.getReqDecrypt();
		Gson gson = new Gson();
		Type type = new com.google.gson.reflect.TypeToken<Map<String, String>>() {
		}.getType();
		return gson.fromJson(aharRequestDecryptString, type);
	}

}
