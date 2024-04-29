package com.netwin.service.impl;

import java.lang.reflect.Type;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.netwin.dto.AharVndrRequestDto;
import com.netwin.dto.AharVndrResponseDto;
import com.netwin.dto.CustomerVendorDetailsDto;
import com.netwin.entiry.AharVndrDetails;
import com.netwin.entiry.AharVndrRequest;
import com.netwin.entiry.AharVndrResponse;
import com.netwin.repo.AharVndrDetailsRepo;
import com.netwin.repo.AharVndrRequestRepo;
import com.netwin.repo.AharVndrResponseRepo;
import com.netwin.service.AharResponseService;
import com.netwin.service.AharVndrRequestService;
import com.netwin.util.EncryptionAndDecryptionData;
import com.netwin.util.NtAharResponse;
import com.netwin.util.QueryUtil;

@Service
public class AharVndrRequestServiceImpl implements AharVndrRequestService {
	private NtAharResponse ntAharResponse;
	private AharVndrDetailsRepo aharVndrDetailsRepo;
	private AharVndrRequestRepo aharVndrRequestRepo;
	private EncryptionAndDecryptionData encryptionAndDecryptionData;
	private Mapper mapper;
	private AharVndrResponseRepo aharVndrResponseRepo;
	private AharResponseService aharResponseService;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public AharVndrRequestServiceImpl(NtAharResponse ntAharResponse, AharVndrDetailsRepo aharVndrDetailsRepo,
			EncryptionAndDecryptionData encryptionAndDecryptionData, AharVndrRequestRepo aharVndrRequestRepo,
			Mapper mapper, AharVndrResponseRepo aharVndrResponseRepo, AharResponseService aharResponseService,
			JdbcTemplate jdbcTemplate) {
		this.ntAharResponse = ntAharResponse;
		this.aharVndrDetailsRepo = aharVndrDetailsRepo;
		this.aharVndrRequestRepo = aharVndrRequestRepo;
		this.encryptionAndDecryptionData = encryptionAndDecryptionData;
		this.mapper = mapper;
		this.aharVndrResponseRepo = aharVndrResponseRepo;
		this.aharResponseService = aharResponseService;
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public String callVenderRequest(String vendorRequestJson, CustomerVendorDetailsDto customerVendorDetailsDto,
			String reqStatus) throws JsonMappingException, JsonProcessingException {
		String apiUrl = null;
		AharVndrDetails aharVndrDetails = aharVndrDetailsRepo
				.findByAharVnDrSrNo(customerVendorDetailsDto.getVendorId());
		if (aharVndrDetails == null) {
			return ntAharResponse.getNtResponse(425);
		}
		if (reqStatus == "V") {
			apiUrl = aharVndrDetails.getAharVrfyOtpURL();
		} else {
			apiUrl = aharVndrDetails.getAharVrfyURL();
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBasicAuth(aharVndrDetails.getAharVdrApiUser(), aharVndrDetails.getAharVdrApiPsw());
		String requestBody = vendorRequestJson;
		requestBody = requestBody.substring(1, requestBody.length() - 1);
		// Split the string by comma and equal sign
		String[] keyValuePairs = requestBody.split(", ");

		// Create a JSONObject and add key-value pairs
		JSONObject jsonObject = new JSONObject();
		for (String pair : keyValuePairs) {
			String[] entry = pair.split("=");
			jsonObject.put(entry[0], entry[1]);
		}
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(jsonObject.toString());

		
		Object id = customerVendorDetailsDto.getAhaReqMasSrNo();
		((ObjectNode) jsonNode).put("userReqSrNo", id.toString());

		AharVndrRequestDto aharVndrRequestDto = new AharVndrRequestDto();
		aharVndrRequestDto.setReqDecrypt(jsonObject.toString());
		aharVndrRequestDto.setReqEncrypt(encryptionAndDecryptionData.getEncryptResponse(jsonObject.toString()));
		aharVndrRequestDto.setEntryDate(new Date(System.currentTimeMillis()));
		aharVndrRequestDto.setAharReqMasSrNo(customerVendorDetailsDto.getAhaReqMasSrNo());
		aharVndrRequestDto.setReqFor(reqStatus);
		HttpEntity<String> requestEntity = new HttpEntity<>(jsonObject.toString(), headers);
		AharVndrRequest aharVndrRequest = mapper.map(aharVndrRequestDto, AharVndrRequest.class);
		aharVndrRequestRepo.save(aharVndrRequest);
		// Ruff Loop
		String vndrResponseStr = null;
		Map<String, String> aharRequestJsonMap = jsonStringToMap(aharVndrRequestDto);
		// details object
		// Database field Name Fetch.
		Map<String, Object> netwinRequestpara = getNetwinRequestParas(reqStatus);

		for (Map.Entry<String, Object> netwinField : netwinRequestpara.entrySet()) {
			if (!aharRequestJsonMap.containsKey(netwinField.getKey())
					&& (netwinField.getValue().toString()).equals("Y")) {
System.out.println(aharRequestJsonMap);

System.out.println(netwinRequestpara);
				return ntAharResponse.getNtResponse(500);

			}

		}

		vndrResponseStr = callPanVerifyApi(apiUrl, HttpMethod.POST, requestEntity, customerVendorDetailsDto, reqStatus);

		return aharResponseService.customerResponseMapping(vndrResponseStr, customerVendorDetailsDto, reqStatus);
	}

	private String callPanVerifyApi(String aharVrfyURL, HttpMethod post, HttpEntity<String> requestEntity,
			CustomerVendorDetailsDto customerVendorDetailsDto, String reqStatus) {
		RestTemplate restTemplate = new RestTemplate();

		// Make the HTTP request using RestTemplate
		ResponseEntity<String> responseEntity = restTemplate.exchange(aharVrfyURL, post, requestEntity,String.class);
		String response = responseEntity.getBody();
		// Dummy Data
		/*
		 * String response = null; if (reqStatus.equals("V")) { response =
		 * "{\"client_id\":\"aadhaar_v2_QkErwTgBmONpjUoxBhZf\",\"otp_sent\":\"true\",\"if_number\":\"true\",\"valid_aadhaar\":\"true\",\"status_code\":\"200\",\"message_code\":\"success\",\"message\":\"OTP Sent.\",\"success\":\"true\",\"request\":null,\"response\":\"200\"}"
		 * ; } else { response =
		 * "{\"clientId\":\"aadhaar_v2_BqMyezwHRgvfWqhWOOcc\",\"fullName\":\"Suyash Jayant Jadhavar\",\"aadhaarNumber\":\"680518862840\",\"dob\":\"2001-09-04\",\"gender\":\"M\",\"country\":\"India\",\"dist\":\"Nashik\",\"state\":\"Maharashtra\",\"postOffice\":\"Nashik Road\",\"loc\":\"nashik road\",\"villageTownCity\":\"Nashik\",\"subdist\":\"Nashik\",\"house\":\"902, swaroop ,shree gajanan park\",\"landmark\":\"sinnar phata\",\"faceStatus\":\"false\",\"faceScore\":\"-1\",\"zip\":\"422101\",\"profileImage\":\"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\",\"hasImage\":\"true\",\"emailHash\":\"\",\"mobileHash\":\"16eb692c11439ab7c06afa042bb8e11b27b84d38912c954879725c93d061511e\",\"rawXml\":\"https://aadhaar-kyc-docs.s3.amazonaws.com/suresell/aadhaar_xml/284020240424194313276/284020240424194313276-2024-04-24-141313434839.xml?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAY5K3QRM5FYWPQJEB%2F20240424%2Fap-south-1%2Fs3%2Faws4_request&X-Amz-Date=20240424T141313Z&X-Amz-Expires=432000&X-Amz-SignedHeaders=host&X-Amz-Signature=11246b8cddacc42bf0eb69e3d206577fe65dde3f0f9e82dd61fc55a0f8a7c7db\",\"zipData\":\"https://aadhaar-kyc-docs.s3.amazonaws.com/suresell/aadhaar_xml/284020240424194313276/284020240424194313276-2024-04-24-141313356436.zip?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAY5K3QRM5FYWPQJEB%2F20240424%2Fap-south-1%2Fs3%2Faws4_request&X-Amz-Date=20240424T141313Z&X-Amz-Expires=432000&X-Amz-SignedHeaders=host&X-Amz-Signature=681911c591464f3f18182f9c7f8b2044b8028870128e0c85cf24809ed34500a1\",\"careOf\":null,\"shareCode\":\"1512\",\"mobileVerified\":\"false\",\"referenceId\":\"284020240424194313276\",\"aadhaarPdf\":null,\"status\":\"success_aadhaar\",\"uniquenessId\":\"80bbbcbcd12b012654eb5f9b87aa134c2b40f2c77a892cb263923c45e6885637\",\"street\":\"kharjul mala\",\"statusCode\":\"200\",\"success\":\"true\",\"message\":null,\"messageCode\":\"success\"}"
		 * ;
		 * 
		 * }
		 */
		
		AharVndrResponseDto aharVndrResponseDto = new AharVndrResponseDto();
		aharVndrResponseDto.setAdharNo(customerVendorDetailsDto.getAdharNo());
		aharVndrResponseDto.setEntryDate(new Date(System.currentTimeMillis()));
		aharVndrResponseDto.setReqDecrypt(response);
		aharVndrResponseDto.setReqEncrypt(encryptionAndDecryptionData.getEncryptResponse(response));
		aharVndrResponseDto.setAharReqMasSrNo(customerVendorDetailsDto.getAhaReqMasSrNo());
		aharVndrResponseDto.setReqFor(response);
		aharVndrResponseDto.setReqFor(reqStatus);
		AharVndrResponse aharVndrResponse = mapper.map(aharVndrResponseDto, AharVndrResponse.class);
		aharVndrResponseRepo.save(aharVndrResponse);

		return response;

	}

	// Netwin Database Field Fetch
	private Map<String, Object> getNetwinRequestParas(String reqStatus) {
		// Execute the query and retrieve results
		List<Map<String, Object>> netwinFieldResultsMap = null;

		if (reqStatus == "V") {
			netwinFieldResultsMap = jdbcTemplate.queryForList(QueryUtil.NETWNFIELDQUERY1, "A", "O");
		} else {
			netwinFieldResultsMap = jdbcTemplate.queryForList(QueryUtil.NETWNFIELDQUERY1, "A", "V");
		}
		// Process the results using Java Streams
		return netwinFieldResultsMap.stream()
				.collect(Collectors.toMap(vendorField -> (String) vendorField.get("VNDRREQKEYNAME"),
						vendorField -> (String) vendorField.get("VNDRREQKEYREQ")));
	}
	
//JsonString to Map Convert Method
	private Map<String, String> jsonStringToMap(AharVndrRequestDto aharVndrRequestDto) {
		String aharRequestDecryptString = aharVndrRequestDto.getReqDecrypt();
		Gson gson = new Gson();
		Type type = new com.google.gson.reflect.TypeToken<Map<String, String>>() {
		}.getType();
		return gson.fromJson(aharRequestDecryptString, type);
	}

}
