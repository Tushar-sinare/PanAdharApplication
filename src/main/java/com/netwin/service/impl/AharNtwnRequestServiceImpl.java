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
	public String callAharRequest(String aharJson, String reqStatus) throws JsonProcessingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
	    String resultStr = null;
	    AharNtwnReqDto aharNtwnReqDto = initializeAharNtwnReqDto(aharJson, reqStatus);

	    if (aharNtwnReqDto != null) {
	        resultStr = handleAharNtwnRequest(aharNtwnReqDto, reqStatus);
	    } else {
	        resultStr = ntAharResponse.getNtResponse(421);
	    }
	    return resultStr;
	}

	private AharNtwnReqDto initializeAharNtwnReqDto(String aharJson, String reqStatus) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		AharNtwnReqDto aharNtwnReqDto1 = new AharNtwnReqDto();
	    String aharRequestDecryptString = encryptionAndDecryptionData.getRequestDecryptData(aharJson);
	    aharNtwnReqDto1.setReqDecrypt(aharRequestDecryptString);
	    aharNtwnReqDto1.setReqEncrypt(aharJson);
	    aharNtwnReqDto1.setEntryDate(date);
	    aharNtwnReqDto1.setReqFor(reqStatus);
	    return aharNtwnReqDto1;
	}

	private String handleAharNtwnRequest(AharNtwnReqDto aharNtwnReqDto, String reqStatus) throws JsonProcessingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
	    String resultStr = null;
	    String vendorRequestJson = null;

	    AharNtwnRequest aharNtwnRequest = mapper.map(aharNtwnReqDto, AharNtwnRequest.class);
	    aharNtwnRequest = aharNtwnRequestRepo.save(aharNtwnRequest);
	    aharNtwnReqDto = mapper.map(aharNtwnRequest, AharNtwnReqDto.class);

	    resultStr = validateRequest(aharNtwnReqDto, reqStatus);

	    if (resultStr == null) {
	        ObjectMapper objectMapper = new ObjectMapper();
	        JsonNode jsonNode = objectMapper.readTree(aharNtwnReqDto.getReqDecrypt());

	        JsonNode custIdNode = jsonNode.get("custId");
	        JsonNode prodIdNode = jsonNode.get("prodId");

	        if (custIdNode == null) {
	            resultStr = ntAharResponse.getNtResponse(502);
	        } else if (prodIdNode == null) {
	            resultStr = ntAharResponse.getNtResponse(503);
	        } else {
	            CustomerVendorDetailsDto customerVendorDetailsDto = new CustomerVendorDetailsDto();
	            String custId = jsonNode.get("custId").asText();
	            String prodId = jsonNode.get("prodId").asText();
	            Object id = aharNtwnRequest.getAhaReMasSrNo();
	            ((ObjectNode) jsonNode).put("userReqSrNo", id.toString());
	            
	            NetwinCustomerDetails netwinCustomerDetails = netwinCustomerDetailsService.fetchNetwinCustomerDetails(custId);
	            NetwinProductionDetails netwinProductionDetails = netwinProductionDetailsService.fetchNetwinProductionDetails(prodId);
	            
	            if (netwinCustomerDetails == null) {
	                resultStr = ntAharResponse.getNtResponse(423);
	            } else if (netwinProductionDetails == null) {
	                resultStr = ntAharResponse.getNtResponse(424);
	            } else {
	                vendorRequestJson = handleVendorRequest(jsonNode, customerVendorDetailsDto, reqStatus);
	                resultStr = aharVndrRequestService.callVenderRequest(vendorRequestJson, customerVendorDetailsDto, reqStatus);
	            }
	        }
	        if (resultStr == null) {
	            resultStr = handleCustomerResponse(aharNtwnRequest, resultStr, reqStatus);
	        }
	    }
	    return resultStr;
	}

	private String handleVendorRequest(JsonNode jsonNode, CustomerVendorDetailsDto customerVendorDetailsDto, String reqStatus) throws JsonProcessingException {
	    String vendorRequestJson = aharVndrValidation.VendorRequestValidation(jsonNode, customerVendorDetailsDto, reqStatus);
	    if (reqStatus.equals("O")) {
	        String userReqSrNo = jsonNode.get("userReqSrNo").asText();
	        String client_id1 = jsonNode.get("clientId").asText();
	        String requestOTP = jdbcTemplate.queryForObject(QueryUtil.VERIFYOTP,new Object[] { userReqSrNo }, String.class);
	        if (requestOTP == null) {
	            return ntAharResponse.getNtResponse(504);
	        } else {
	            // handle OTP verification logic
	        }
	    }
	    return vendorRequestJson;
	}

	private String handleCustomerResponse(AharNtwnRequest aharNtwnRequest, String resultStr, String reqStatus) {
	    CustomerResponseDto customerResponseDto = new CustomerResponseDto();
	    customerResponseDto.setAhaReqMasSrNo(aharNtwnRequest.getAhaReMasSrNo());
	    customerResponseDto.setEntDateTM(date);
	    customerResponseDto.setReqDecrypt(resultStr);
	    customerResponseDto.setReqEncrypt(encryptionAndDecryptionData.getEncryptResponse(resultStr));
	    customerResponseDto.setReqFor(reqStatus);
	    
	    AharResponse aharResponse = mapper.map(customerResponseDto, AharResponse.class);
	    aharResponseRepo.save(aharResponse);
	    return customerResponseDto.getReqEncrypt();
	}

	private String validateRequest(AharNtwnReqDto aharNtwnReqDto, String reqStatus) throws JsonProcessingException{
		String result = null;
//adhar check validation 
		if (reqStatus.equals("V")) {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(aharNtwnReqDto.getReqDecrypt());

			JsonNode adharNo = jsonNode.get(ConstantVariable.ADHARNO);

			if (adharNo == null) {
				return ntAharResponse.getNtResponse(501);
			}
			String aharNo = jsonNode.get(ConstantVariable.ADHARNO).asText();

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
