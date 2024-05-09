package com.netwin.service.impl;

import java.lang.reflect.Type;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.netwin.dto.CustomerResponseDto;
import com.netwin.dto.CustomerVendorDetailsDto;
import com.netwin.dto.GtNtwnRequestDto;
import com.netwin.dto.PnNetwinRequestDto;
import com.netwin.entity.GtNtwnRequest;
import com.netwin.entity.GtResponse;
import com.netwin.entity.NetwinCustomerDetails;
import com.netwin.entity.NetwinProductionDetails;
import com.netwin.entity.PnResponse;
import com.netwin.repo.GSTNtwnReqRepo;
import com.netwin.repo.GtResponseRepo;
import com.netwin.service.GSTNtwnRequestService;
import com.netwin.service.GtVndrRequestService;
import com.netwin.service.NetwinCustomerDetailsService;
import com.netwin.service.NetwinProductionDetailsService;
import com.netwin.util.EncryptionAndDecryptionData;
import com.netwin.util.GtNtResponse;
import com.netwin.util.QueryUtil;
import com.netwin.validation.GtVndrValidation;
@Service
public class GtNtwnRequestServiceImpl implements GSTNtwnRequestService {
private EncryptionAndDecryptionData encryptionAndDecryptionData;
private Mapper mapper;
private GSTNtwnReqRepo gstNtwnReqRepo;
private GtNtResponse gtNtResponse;
private NetwinCustomerDetailsService netwinCustomerDetailsService;
private NetwinProductionDetailsService netwinProductionDetailsService;
private GtVndrValidation gtVndrValidation;
private GtVndrRequestService gtVndrRequestService;
private GtResponseRepo gtResponseRepo;
private JdbcTemplate jdbcTemplate;
@Autowired
public GtNtwnRequestServiceImpl(EncryptionAndDecryptionData encryptionAndDecryptionData,GSTNtwnReqRepo gstNtwnReqRepo,Mapper mapper,GtNtResponse gtNtResponse,NetwinCustomerDetailsService netwinCustomerDetailsService,NetwinProductionDetailsService netwinProductionDetailsService,GtVndrValidation gtVndrValidation,GtVndrRequestService gtVndrRequestService,GtResponseRepo gtResponseRepo,JdbcTemplate jdbcTemplate) {
	this.encryptionAndDecryptionData = encryptionAndDecryptionData;
	this.mapper = mapper;
	this.gtNtResponse = gtNtResponse;
	this.netwinCustomerDetailsService=netwinCustomerDetailsService;
	this.netwinProductionDetailsService = netwinProductionDetailsService;
	this.gtVndrValidation = gtVndrValidation;
	this.gtVndrRequestService=gtVndrRequestService;
	this.gstNtwnReqRepo = gstNtwnReqRepo;
this.gtResponseRepo =gtResponseRepo;
this.jdbcTemplate = jdbcTemplate;
}
	@Override
	public String callGSTRequest(String gstRequestJson) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException,JsonProcessingException {
		 Date date = new Date(System.currentTimeMillis());
			String userUuid= null;
			String resultStr = null;
			CustomerVendorDetailsDto customerVendorDetailsDto = new CustomerVendorDetailsDto();
			GtNtwnRequestDto gtNtwnRequestDto = new GtNtwnRequestDto();
			// Json String Encrypt
			String pnRequestDecryptString = encryptionAndDecryptionData.getRequestDecryptData(gstRequestJson);
			gtNtwnRequestDto.setReqEncrypt(gstRequestJson);
			gtNtwnRequestDto.setReqDecrypt(pnRequestDecryptString);
			gtNtwnRequestDto.setEntryDate(date);

			
			// Mapping Dto to Entity
			GtNtwnRequest gtNtwnRequest = mapper.map(gtNtwnRequestDto, GtNtwnRequest.class);
			
			if (gtNtwnRequest != null) {
				
				// Save client PnrequestMas Data
				gtNtwnRequest = gstNtwnReqRepo.save(gtNtwnRequest);
				// Mapping Entity to Dto
				gtNtwnRequestDto = mapper.map(gtNtwnRequest, GtNtwnRequestDto.class);
				// Verify Pan No and Netwin required Json Field
				resultStr = validateRequest(gtNtwnRequestDto);
				
				if (resultStr == null) {
					
				ObjectMapper objectMapper = new ObjectMapper();
				// String to convert Json Node required get CustId & ProdId
				JsonNode jsonNode = objectMapper.readTree(gtNtwnRequestDto.getReqDecrypt());
				JsonNode custIdNode = jsonNode.get("custId");
				
				JsonNode prodIdNode = jsonNode.get("prodId");

			
				if (custIdNode == null) {
					resultStr =  gtNtResponse.getNtResponse(502,userUuid);
				}
				if (prodIdNode == null) {
					resultStr =  gtNtResponse.getNtResponse(503,userUuid);
				}
				Object id = gtNtwnRequest.getGtReqMasSrNo();
				userUuid=id.toString();
				((ObjectNode) jsonNode).put("userReqSrNo", id.toString()); // Convert id to String if necessary
				// Fetch Customer Details and check Available or Not
		
				NetwinCustomerDetails netwinCustomerDetails = netwinCustomerDetailsService.fetchNetwinCustomerDetails(custIdNode.asText());
				
				// Fetch Product Details and check Available or Not
				NetwinProductionDetails netwinProductionDetails = netwinProductionDetailsService.fetchNetwinProductionDetails(prodIdNode.asText());
				if (netwinCustomerDetails == null) {
					resultStr = gtNtResponse.getNtResponse(423,userUuid);
				}else if (netwinProductionDetails == null) {
					resultStr = gtNtResponse.getNtResponse(424,userUuid);
				}else {
				if (resultStr == null) {
					

					customerVendorDetailsDto.setUserGstNo(jsonNode.get("userGstNo").asText());
					customerVendorDetailsDto.setCustId(custIdNode.asText());
					customerVendorDetailsDto.setProdId(prodIdNode.asText());
					customerVendorDetailsDto.setVendorId(netwinCustomerDetails.getNetwVndrs());
					customerVendorDetailsDto.setGtReqMasSrNo(gtNtwnRequest.getGtReqMasSrNo());
					
					// Customer key to Vendor Key Replace Request
					String vendorRequestJson = gtVndrValidation.VendorRequestValidation(jsonNode, customerVendorDetailsDto);
					
					// Call Vendor Request API
					resultStr = gtVndrRequestService.callVenderRequest(vendorRequestJson, customerVendorDetailsDto);
					
				}
				}
				}
			} else {
				resultStr = gtNtResponse.getNtResponse(421,userUuid);
			}
			
			CustomerResponseDto customerResponseDto = new CustomerResponseDto();
			customerResponseDto.setGtReqMasSrNo(gtNtwnRequest.getGtReqMasSrNo());
			customerResponseDto.setEntDateTM(date);
			customerResponseDto.setReqDecrypt(resultStr);
			customerResponseDto.setReqEncrypt(encryptionAndDecryptionData.getEncryptResponse(resultStr));
			GtResponse gtResponse = mapper.map(customerResponseDto, GtResponse.class);
			 
			gtResponseRepo.save(gtResponse);
			
			resultStr = customerResponseDto.getReqEncrypt();
			
			// Call to mapping database field Name
			// if decrypt string null then return error show
			return resultStr;// getMappingDataBaseThrough(pnRequestDecryptString, pnNetwinRequest);
		
	}
	private String validateRequest(GtNtwnRequestDto gtNtwnRequestDto) throws JsonProcessingException{
		String result = null;
		// adhar check validation

		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(gtNtwnRequestDto.getReqDecrypt());
		String gstNo = jsonNode.get("userGstNo").asText();
		Object id  = gtNtwnRequestDto.getGtReqMasSrNo();



		boolean flag = isValidGST(gstNo);
		
		if (!flag) {
			
			result = gtNtResponse.getNtResponse(2009,id.toString());
			return result.toString();
		}
		
	
		Map<String, String> gtRequestJsonMap = jsonStringToMap(gtNtwnRequestDto);
		// details object
		// Database field Name Fetch
		Map<String, Object> netwinRequestpara = getNetwinRequestParas();

		for (Map.Entry<String, Object> netwinField : netwinRequestpara.entrySet()) {

			if (!gtRequestJsonMap.containsKey(netwinField.getKey()) && netwinField.getValue().toString().equals("Y")) {

				result = gtNtResponse.getNtResponse(500,id.toString());

			}

		}

		return result;
	}
	private Map<String, Object> getNetwinRequestParas() {
		List<Map<String, Object>> netwinFieldResultsMap = null;

		netwinFieldResultsMap = jdbcTemplate.queryForList(QueryUtil.NETWNFIELDQUERY, "G", "V");

		// Process the results using Java Streams
		return netwinFieldResultsMap.stream()
				.collect(Collectors.toMap(vendorField -> (String) vendorField.get("NETWREQKEYNAME"),
						vendorField -> (String) vendorField.get("NETWREQKEYREQ")));
	}
	private Map<String, String> jsonStringToMap(GtNtwnRequestDto gtNtwnRequestDto) {
		String aharRequestDecryptString = gtNtwnRequestDto.getReqDecrypt();
		Gson gson = new Gson();
		Type type = new com.google.gson.reflect.TypeToken<Map<String, String>>() {
		}.getType();
		return gson.fromJson(aharRequestDecryptString, type);
	}
	public static boolean isValidGST(String gstNumber) {
		final String GST_REGEX = "^\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$";
		final Pattern pattern = Pattern.compile(GST_REGEX);
	    Matcher matcher = pattern.matcher(gstNumber);
	    return matcher.matches();
	}
	
}