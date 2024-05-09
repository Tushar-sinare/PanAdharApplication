package com.netwin.service.impl;

import java.lang.reflect.Type;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.dozer.Mapper;
import org.json.JSONObject;
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
import com.google.gson.Gson;
import com.netwin.dto.CustomerVendorDetailsDto;
import com.netwin.dto.GtVndrRequestDto;
import com.netwin.dto.GtVndrResponseDto;
import com.netwin.entity.GtVendorDetails;
import com.netwin.entity.GtVndrRequest;
import com.netwin.entity.GtVndrResponse;
import com.netwin.repo.GtVndrDetailsRepo;
import com.netwin.repo.GtVndrRequestRepo;
import com.netwin.repo.GtVndrResponseRepo;
import com.netwin.service.GtResponseService;
import com.netwin.service.GtVndrRequestService;
import com.netwin.util.EncryptionAndDecryptionData;
import com.netwin.util.GtNtResponse;
import com.netwin.util.QueryUtil;
@Service
public class GtVndrRequestServiceImpl implements GtVndrRequestService{
private GtVndrDetailsRepo gtVndrDetailsRepo;
	private GtNtResponse gtNtResponse;
	private EncryptionAndDecryptionData encryptionAndDecryptionData;
	private Mapper mapper;
	private GtVndrRequestRepo gtVndrRequestRepo;
	private JdbcTemplate jdbcTemplate;
	private GtVndrResponseRepo gtVndrResponseRepo;
	private GtResponseService gtResponseService;
	@Autowired
	public GtVndrRequestServiceImpl(GtVndrDetailsRepo gtVndrDetailsRepo,GtNtResponse gtNtResponse,EncryptionAndDecryptionData encryptionAndDecryptionData,Mapper mapper,GtVndrRequestRepo gtVndrRequestRepo,JdbcTemplate jdbcTemplate,GtVndrResponseRepo gtVndrResponseRepo,GtResponseService gtResponseService) {
		this.gtVndrDetailsRepo = gtVndrDetailsRepo;
		this.gtNtResponse = gtNtResponse;
		this.encryptionAndDecryptionData = encryptionAndDecryptionData;
		this.mapper = mapper;
		this.gtVndrRequestRepo = gtVndrRequestRepo;
		this.jdbcTemplate = jdbcTemplate;
		this.gtVndrResponseRepo = gtVndrResponseRepo;
		this.gtResponseService = gtResponseService;
	}
	@Override
	public String callVenderRequest(String vendorRequestJson, CustomerVendorDetailsDto customerVendorDetailsDto) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, JsonProcessingException {
		String apiUrl = null;
		
		Object id = customerVendorDetailsDto.getGtReqMasSrNo();
		System.out.println(customerVendorDetailsDto.getVendorId());
		GtVendorDetails gtVendorDetails = gtVndrDetailsRepo.findByGtVnDrSrNo(customerVendorDetailsDto.getVendorId());
		
		
		if (gtVendorDetails == null) {
			return gtNtResponse.getNtResponse(425,id.toString());
		}
		apiUrl = gtVendorDetails.getGtVrfyURL();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBasicAuth(gtVendorDetails.getGtVnDrApiUser(), gtVendorDetails.getGtVndrApiPsw());
		String requestBody = vendorRequestJson;
		
		requestBody = requestBody.substring(1, requestBody.length() - 1);
		// Split the string by comma and equal sign
		String[] keyValuePairs = requestBody.split(", ");
		GtVndrRequestDto gtVndrRequestDto = new GtVndrRequestDto();
		// Create a JSONObject and add key-value pairs
		JSONObject jsonObject = new JSONObject();
		for (String pair : keyValuePairs) {
			String[] entry = pair.split("=");
			jsonObject.put(entry[0], entry[1]);
		}
		
		gtVndrRequestDto.setReqDecrypt(jsonObject.toString());
		gtVndrRequestDto.setReqEncrypt(encryptionAndDecryptionData.getEncryptResponse(jsonObject.toString()));
		gtVndrRequestDto.setEntryDate(new Date(System.currentTimeMillis()));
		gtVndrRequestDto.setGtReqMasSrNo(customerVendorDetailsDto.getGtReqMasSrNo());
		
		HttpEntity<String> requestEntity = new HttpEntity<>(jsonObject.toString(), headers);
		GtVndrRequest gtVndrRequest = mapper.map(gtVndrRequestDto, GtVndrRequest.class);
		
		gtVndrRequestRepo.save(gtVndrRequest);
	
		Map<String, String> aharRequestJsonMap = jsonStringToMap(gtVndrRequestDto);

		// details object
		// Database field Name Fetch.
		Map<String, Object> netwinRequestpara = getNetwinRequestParas();
		
		for (Map.Entry<String, Object> netwinField : netwinRequestpara.entrySet()) {
			if (!aharRequestJsonMap.containsKey(netwinField.getKey())
					&& (netwinField.getValue().toString()).equals("Y")) {
				return gtNtResponse.getNtResponse(500,id.toString());

			}
		}
		String vndrResponseStr = callPanVerifyApi(apiUrl, HttpMethod.POST, requestEntity, customerVendorDetailsDto);
		
		return gtResponseService.customerResponseMapping(vndrResponseStr, customerVendorDetailsDto);
	}
	
	private String callPanVerifyApi(String apiUrl, HttpMethod post, HttpEntity<String> requestEntity,
			CustomerVendorDetailsDto customerVendorDetailsDto) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
RestTemplate restTemplate = new RestTemplate();
		
		
		// Make the HTTP request using RestTemplate
		ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, post, requestEntity,String.class);
		String response = responseEntity.getBody();

		GtVndrResponseDto gtVndrResponseDto = new GtVndrResponseDto();
		gtVndrResponseDto.setUserGstNo(customerVendorDetailsDto.getUserGstNo());
		gtVndrResponseDto.setEntryDate(new Date(System.currentTimeMillis()));
		gtVndrResponseDto.setReqDecrypt(response);
		gtVndrResponseDto.setReqEncrypt(encryptionAndDecryptionData.getEncryptResponse(response));
		gtVndrResponseDto.setGtReqMasSrNo(customerVendorDetailsDto.getGtReqMasSrNo());
		GtVndrResponse gtVndrResponse = mapper.map(gtVndrResponseDto,GtVndrResponse.class);
		gtVndrResponseRepo.save(gtVndrResponse);
	return response;
	
	}
	private Map<String, Object> getNetwinRequestParas() {
		List<Map<String, Object>> netwinFieldResultsMap = null;

		netwinFieldResultsMap = jdbcTemplate.queryForList(QueryUtil.NETWNFIELDQUERY11, "G");
		
	// Process the results using Java Streams
	return netwinFieldResultsMap.stream()
			.collect(Collectors.toMap(vendorField -> (String) vendorField.get("VNDRREQKEYNAME"),
					vendorField -> (String) vendorField.get("VNDRREQKEYREQ")));

	}
	private Map<String, String> jsonStringToMap(GtVndrRequestDto gtVndrRequestDto) {
		String gtRequestDecryptString = gtVndrRequestDto.getReqDecrypt();
		Gson gson = new Gson();
		Type type = new com.google.gson.reflect.TypeToken<Map<String, String>>() {
		}.getType();
		return gson.fromJson(gtRequestDecryptString, type);
	}

}
