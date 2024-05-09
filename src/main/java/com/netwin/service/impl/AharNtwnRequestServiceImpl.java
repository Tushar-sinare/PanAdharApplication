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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.netwin.dto.AharNtwnReqDto;
import com.netwin.dto.CustomerResponseDto;
import com.netwin.dto.CustomerVendorDetailsDto;
import com.netwin.entity.AharNtwnRequest;
import com.netwin.entity.AharResponse;
import com.netwin.entity.NetwinCustomerDetails;
import com.netwin.entity.NetwinProductionDetails;
import com.netwin.logger.LoggerProvider;
import com.netwin.logger.MyLogger;
import com.netwin.repo.AharNtwnRequestRepo;
import com.netwin.repo.AharResponseRepo;
import com.netwin.service.AharNtwnRequestService;
import com.netwin.service.AharVndrRequestService;
import com.netwin.service.NetwinCustomerDetailsService;
import com.netwin.service.NetwinProductionDetailsService;
import com.netwin.util.ConstantVariable;
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
	public String callAharRequest(String aharJson, String reqStatus)
			throws JsonProcessingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		String resultStr = null;
		String userUuid = null;
		String vendorRequestJson = null;
		AharNtwnReqDto aharNtwnReqDto = new AharNtwnReqDto();
		// Convert Json String Encryption to Decryption
		String aharRequestDecryptString = encryptionAndDecryptionData.getRequestDecryptData(aharJson);
		ObjectMapper objectMapper = new ObjectMapper();
		aharNtwnReqDto.setReqDecrypt(aharRequestDecryptString);
		aharNtwnReqDto.setReqEncrypt(aharJson);
		aharNtwnReqDto.setEntryDate(date);
		aharNtwnReqDto.setReqFor(reqStatus);
		
		JsonNode jsonNode1 = objectMapper.readTree(aharNtwnReqDto.getReqDecrypt());
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
			Object useerUuid = aharNtwnReqDto.getAhaReMasSrNo();
			userUuid = useerUuid.toString();

			JsonNode jsonNode = objectMapper.readTree(aharNtwnReqDto.getReqDecrypt());
			JsonNode custIdNode = jsonNode.get("custId");
			JsonNode prodIdNode = jsonNode.get("prodId");

			if (custIdNode == null) {
				resultStr = ntAharResponse.getNtResponse(502, userUuid);
			}
			if (prodIdNode == null) {
				resultStr = ntAharResponse.getNtResponse(503, userUuid);
			}

			CustomerVendorDetailsDto customerVendorDetailsDto = new CustomerVendorDetailsDto();
			if (resultStr == null) {

				Object id = aharNtwnRequest.getAhaReMasSrNo();
				((ObjectNode) jsonNode).put("userReqSrNo", id.toString());
				// Customer Details Fetch
				NetwinCustomerDetails netwinCustomerDetails = netwinCustomerDetailsService
						.fetchNetwinCustomerDetails(custIdNode.asText());
				// Product Details Fetch
				NetwinProductionDetails netwinProductionDetails = netwinProductionDetailsService
						.fetchNetwinProductionDetails(prodIdNode.asText());
				if (netwinCustomerDetails == null) {
					resultStr = ntAharResponse.getNtResponse(423, userUuid);
				} else if (netwinProductionDetails == null) {
					resultStr = ntAharResponse.getNtResponse(424, userUuid);
				} else {
					if (reqStatus.equals("V")) {
						customerVendorDetailsDto.setAdharNo(jsonNode.get(ConstantVariable.ADHARNO).asText());
					}
					customerVendorDetailsDto.setCustId(custIdNode.asText());
					customerVendorDetailsDto.setProdId(prodIdNode.asText());
					customerVendorDetailsDto.setVendorId(netwinCustomerDetails.getNetwVndrs());
					customerVendorDetailsDto.setAhaReqMasSrNo(aharNtwnRequest.getAhaReMasSrNo());
					// Vendor Validation and Replace Key netwn to Vendor field
					vendorRequestJson = aharVndrValidation.VendorRequestValidation(jsonNode, customerVendorDetailsDto,
							reqStatus);
					// Call Vendor Request API
					if (reqStatus.equals("O")) {
						String userReqSrNo = jsonNode1.get("userReqSrNo").asText();
						String client_id1 = jsonNode1.get("clientId").asText();
						String requestOTP = jdbcTemplate.queryForObject(QueryUtil.VERIFYOTP,
								new Object[] { userReqSrNo }, String.class);
						if (requestOTP == null) {
							resultStr = ntAharResponse.getNtResponse(504, userUuid);
						} else {
							requestOTP = requestOTP.substring(1, requestOTP.length() - 1); // Split the string by comma
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
								resultStr = ntAharResponse.getNtResponse(500, userUuid);
							}
						}
					}
				}
				if (resultStr == null) {

					resultStr = aharVndrRequestService.callVenderRequest(vendorRequestJson, customerVendorDetailsDto,
							reqStatus);

				}
			}
			// call Vendor request

		}
		CustomerResponseDto customerResponseDto = new CustomerResponseDto();
		customerResponseDto.setAhaReqMasSrNo(aharNtwnRequest.getAhaReMasSrNo());
		customerResponseDto.setEntDateTM(date);
		customerResponseDto.setReqDecrypt(resultStr);
		customerResponseDto.setReqEncrypt(encryptionAndDecryptionData.getEncryptResponse(resultStr));

		customerResponseDto.setReqFor(reqStatus);
		AharResponse aharResponse = mapper.map(customerResponseDto, AharResponse.class);

		aharResponseRepo.save(aharResponse);
		resultStr = customerResponseDto.getReqEncrypt();
		return resultStr;
	}

	private String validateRequest(AharNtwnReqDto aharNtwnReqDto, String reqStatus) throws JsonProcessingException {
		String result = null;
		Object userUuid = aharNtwnReqDto.getAhaReMasSrNo();
		String userUuids = userUuid.toString();
//adhar check validation 
		if (reqStatus.equals("V")) {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(aharNtwnReqDto.getReqDecrypt());

			JsonNode adharNo = jsonNode.get(ConstantVariable.ADHARNO);

			if (adharNo == null) {
				return ntAharResponse.getNtResponse(501, userUuids);
			}
			String aharNo = jsonNode.get(ConstantVariable.ADHARNO).asText();

			boolean flag = aharRequestValidation.isValidAadhar(aharNo);
			if (!flag) {
				result = ntAharResponse.getNtResponse(422, userUuids);
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

				result = ntAharResponse.getNtResponse(500, userUuids);

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
