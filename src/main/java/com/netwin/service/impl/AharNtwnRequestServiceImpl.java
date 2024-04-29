package com.netwin.service.impl;

import java.lang.reflect.Type;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.dozer.Mapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.netwin.dto.AharNtwnReqDto;
import com.netwin.dto.CustomerResponseDto;
import com.netwin.dto.CustomerVendorDetailsDto;
import com.netwin.entiry.AharNtwnRequest;
import com.netwin.entiry.AharResponse;
import com.netwin.entiry.NetwinCustomerDetails;
import com.netwin.entiry.NetwinProductionDetails;
import com.netwin.logger.LoggerProvider;
import com.netwin.logger.MyLogger;
import com.netwin.repo.AharNtwnRequestRepo;
import com.netwin.repo.AharResponseRepo;
import com.netwin.service.AharNtwnRequestService;
import com.netwin.service.AharVndrRequestService;
import com.netwin.service.NetwinCustomerDetailsService;
import com.netwin.service.NetwinProductionDetailsService;
import com.netwin.util.EncryptionAndDecryptionData;
import com.netwin.util.NtAharResponse;
import com.netwin.util.QueryUtil;
import com.netwin.validation.AharRequestValidation;
import com.netwin.validation.AharVndrValidation;

@Component
public class AharNtwnRequestServiceImpl implements AharNtwnRequestService {
	static final MyLogger logger = LoggerProvider.getLogger(AharNtwnRequestServiceImpl.class);
	private Date date = new Date(System.currentTimeMillis());
	private EncryptionAndDecryptionData encryptionAndDecryptionData;
	private AharNtwnRequestRepo aharNtwnRequestRepo;
	private Mapper mapper;
	private JdbcTemplate jdbcTemplate;
	private AharVndrRequestService aharVndrRequestService;
	private AharVndrValidation aharVndrValidation;
	private AharRequestValidation aharRequestValidation;
	private NetwinCustomerDetailsService netwinCustomerDetailsService;
	private NetwinProductionDetailsService netwinProductionDetailsService;
	private NtAharResponse ntAharResponse;
	private AharResponseRepo aharResponseRepo;

	@Autowired
	public AharNtwnRequestServiceImpl(EncryptionAndDecryptionData encryptionAndDecryptionData,
			AharNtwnRequestRepo aharNtwnRequestRepo, Mapper mapper, JdbcTemplate jdbcTemplate,
			AharVndrRequestService aharVndrRequestService, NtAharResponse ntAharResponse,
			NetwinProductionDetailsService netwinProductionDetailsService, AharRequestValidation aharRequestValidation,
			AharVndrValidation aharVndrValidation, NetwinCustomerDetailsService netwinCustomerDetailsService,
			AharResponseRepo aharResponseRepo) {

		this.encryptionAndDecryptionData = encryptionAndDecryptionData;
		this.mapper = mapper;
		this.aharNtwnRequestRepo = aharNtwnRequestRepo;
		this.jdbcTemplate = jdbcTemplate;
		this.aharVndrRequestService = aharVndrRequestService;
		this.aharVndrValidation = aharVndrValidation;
		this.aharRequestValidation = aharRequestValidation;
		this.netwinCustomerDetailsService = netwinCustomerDetailsService;
		this.netwinProductionDetailsService = netwinProductionDetailsService;
		this.ntAharResponse = ntAharResponse;
		this.aharResponseRepo = aharResponseRepo;
	}

	@Override
	public String callAharRequest(String aharJson, String reqStatus) throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException, JsonMappingException, JsonProcessingException {
		String resultStr = null;
		AharNtwnReqDto aharNtwnReqDto = new AharNtwnReqDto();
		// Convert Json String Encryption to Decryption
		String aharRequestDecryptString = encryptionAndDecryptionData.getRequestDecryptData(aharJson);

		aharNtwnReqDto.setReqDecrypt(aharRequestDecryptString);
		aharNtwnReqDto.setReqEncrypt(aharJson);
		aharNtwnReqDto.setEntryDate(date);
		aharNtwnReqDto.setReqFor(reqStatus);
		// Mapping DTO To Entity
		AharNtwnRequest aharNtwnRequest = mapper.map(aharNtwnReqDto, AharNtwnRequest.class);
		// remove if else
		if (aharNtwnRequest != null) {
			// Save client request Data
			aharNtwnRequest = aharNtwnRequestRepo.save(aharNtwnRequest);
			// Mapping Entity To DTO
			aharNtwnReqDto = mapper.map(aharNtwnRequest, AharNtwnReqDto.class);
//Adhar No And Ntwin Request Required Field Validation

			resultStr = validateRequest(aharNtwnReqDto, reqStatus);

			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(aharNtwnReqDto.getReqDecrypt());
			JsonNode custIdNode = jsonNode.get("custId");

			JsonNode prodIdNode = jsonNode.get("prodId");

			if (custIdNode == null) {
				resultStr = ntAharResponse.getNtResponse(502);
			}
			if (prodIdNode == null) {
				resultStr = ntAharResponse.getNtResponse(503);
			}
			CustomerVendorDetailsDto customerVendorDetailsDto = new CustomerVendorDetailsDto();
			if (resultStr == null) {
				String custId = jsonNode.get("custId").asText();
				String prodId = jsonNode.get("prodId").asText();
				// Customer Details Fetch
				NetwinCustomerDetails netwinCustomerDetails = netwinCustomerDetailsService
						.fetchNetwinCustomerDetails(custId);
				// Product Details Fetch
				NetwinProductionDetails netwinProductionDetails = netwinProductionDetailsService
						.fetchNetwinProductionDetails(prodId);
				if (netwinCustomerDetails == null) {
					resultStr = ntAharResponse.getNtResponse(423);
				}
				if (netwinProductionDetails == null) {
					resultStr = ntAharResponse.getNtResponse(424);
				}
				if (reqStatus.equals("V")) {
					customerVendorDetailsDto.setAdharNo(jsonNode.get("adharNo").asText());
				}
				customerVendorDetailsDto.setCustId(custId);
				customerVendorDetailsDto.setProdId(prodId);
				customerVendorDetailsDto.setVendorId(netwinCustomerDetails.getNetwVndrs());
				customerVendorDetailsDto.setAhaReqMasSrNo(aharNtwnRequest.getAhaReMasSrNo());

				// Vendor Validation and Replace Key netwn to Vendor field
				String vendorRequestJson = aharVndrValidation.VendorRequestValidation(jsonNode,
						customerVendorDetailsDto, reqStatus);
				// Call Vendor Request API

				if (reqStatus.equals("O")) {

					String userReqSrNo = jsonNode.get("userReqSrNo").asText();
					String client_id1 = jsonNode.get("clientId").asText();

					String requestOTP = jdbcTemplate.queryForObject(QueryUtil.VERIFYOTP, new Object[] { userReqSrNo },
							String.class);

					requestOTP = requestOTP.substring(1, requestOTP.length() - 1); // Split the string by comma and
																					// equal sign
					String[] keyValuePairs = requestOTP.split(", ");

					// Create a JSONObject and add key-value pairs
					JSONObject jsonObject = new JSONObject();
					for (String pair : keyValuePairs) {
						String[] entry = pair.split("=");
						jsonObject.put(entry[0], entry[1]);
					}

					JsonNode jsonNode2 = objectMapper.readTree(jsonObject.toString());
					String client_id = jsonNode2.get("client_id").asText();

					if (!client_id.equals(client_id1)) {
						resultStr = ntAharResponse.getNtResponse(500);
					}
				}
				if (resultStr == null) {
					resultStr = aharVndrRequestService.callVenderRequest(vendorRequestJson, customerVendorDetailsDto,
							reqStatus);
				}

			}
			// call Vendor request
			CustomerResponseDto customerResponseDto = new CustomerResponseDto();
			customerResponseDto.setAhaReqMasSrNo(aharNtwnRequest.getAhaReMasSrNo());
			customerResponseDto.setEntDateTM(date);
			customerResponseDto.setReqDecrypt(resultStr);
			customerResponseDto.setReqEncrypt(encryptionAndDecryptionData.getEncryptResponse(resultStr));

			customerResponseDto.setReqFor(reqStatus);
			AharResponse aharResponse = mapper.map(customerResponseDto, AharResponse.class);

			aharResponseRepo.save(aharResponse);
			resultStr = customerResponseDto.getReqEncrypt();

		} else {
			resultStr = "Error: Unable to map AharNtwnRequest entity from DTO.";
		}
		return resultStr;
	}

	private String validateRequest(AharNtwnReqDto aharNtwnReqDto, String reqStatus)
			throws JsonMappingException, JsonProcessingException {
		String result = null;
//adhar check validation 
		if (reqStatus.equals("V")) {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(aharNtwnReqDto.getReqDecrypt());

			JsonNode adharNo = jsonNode.get("adharNo");

			if (adharNo == null) {
				return ntAharResponse.getNtResponse(501);
			}
			String aharNo = jsonNode.get("adharNo").asText();

			boolean flag = aharRequestValidation.isValidAadhar(aharNo);
			if (!flag) {
				result = ntAharResponse.getNtResponse(422);
				return result;
			}
		}
		Map<String, String> aharRequestJsonMap = jsonStringToMap(aharNtwnReqDto);
		// details object
		// Database field Name Fetch.
		Map<String, Object> netwinRequestpara = getNetwinRequestParas(reqStatus);

		for (Map.Entry<String, Object> netwinField : netwinRequestpara.entrySet()) {
			if (!aharRequestJsonMap.containsKey(netwinField.getKey())
					&& (netwinField.getValue().toString()).equals("Y")) {

				result = ntAharResponse.getNtResponse(500);

			}

		}

		return result;
	}

//Netwin Database Field Fetch
	private Map<String, Object> getNetwinRequestParas(String reqStatus) {
		// Execute the query and retrieve results
		List<Map<String, Object>> netwinFieldResultsMap = null;
		if (reqStatus.equals("V")) {
			netwinFieldResultsMap = jdbcTemplate.queryForList(QueryUtil.NETWNFIELDQUERY, "A", "V");
		} else {
			netwinFieldResultsMap = jdbcTemplate.queryForList(QueryUtil.NETWNFIELDQUERY, "A", "O");
		}
		// Process the results using Java Streams
		return netwinFieldResultsMap.stream()
				.collect(Collectors.toMap(vendorField -> (String) vendorField.get("NETWREQKEYNAME"),
						vendorField -> (String) vendorField.get("NETWREQKEYREQ")));
	}

//JsonString to Map Convert Method
	private Map<String, String> jsonStringToMap(AharNtwnReqDto aharNtwnReqDto) {
		String aharRequestDecryptString = aharNtwnReqDto.getReqDecrypt();
		Gson gson = new Gson();
		Type type = new com.google.gson.reflect.TypeToken<Map<String, String>>() {
		}.getType();
		return gson.fromJson(aharRequestDecryptString, type);
	}

}
