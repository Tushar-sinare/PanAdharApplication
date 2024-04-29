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
import com.netwin.dto.PnNetwinRequestDto;
import com.netwin.entiry.NetwinCustomerDetails;
import com.netwin.entiry.NetwinProductionDetails;
import com.netwin.entiry.PnNetwinRequest;
import com.netwin.entiry.PnResponse;
import com.netwin.repo.PnNetwinRequestRepo;
import com.netwin.repo.PnResponseRepo;
import com.netwin.service.ErrorApplicationService;
import com.netwin.service.NetwinCustomerDetailsService;
import com.netwin.service.NetwinProductionDetailsService;
import com.netwin.service.PnNetwinRequestService;
import com.netwin.service.PnVendorDetailsService;
import com.netwin.service.PnVndrRequestService;
import com.netwin.util.EncryptionAndDecryptionData;
import com.netwin.util.NtResponse;
import com.netwin.util.QueryUtil;
import com.netwin.validation.PnRequestValidation;
import com.netwin.validation.PnVndrValidation;

@Service
public class PnNetwinRequestServiceImpl implements PnNetwinRequestService {

	private PnNetwinRequestRepo pnNetwinRequestRepository;

	private EncryptionAndDecryptionData encryptionAndDecryptionData;
	private Mapper mapper;

	private JdbcTemplate jdbcTemplate;

	private NetwinCustomerDetailsService netwinCustomerDetailsService;

	private NetwinProductionDetailsService netwinProductionDetailsService;

	private PnVndrValidation pnVndrValidation;
	private PnVndrRequestService pnVndrRequestService;
	private NtResponse ntResponse;
	private PnResponseRepo pnResponseRepo;
	private Date date = new Date(System.currentTimeMillis());

	@Autowired
	public PnNetwinRequestServiceImpl(PnNetwinRequestRepo pnNetwinRequestRepository, Mapper mapper,
			JdbcTemplate jdbcTemplate, PnVndrRequestService pnVndrRequestService,
			ErrorApplicationService errorApplicationService, PnRequestValidation pnRequestValidation,
			NetwinCustomerDetailsService netwinCustomerDetailsService, PnVendorDetailsService pnVendorDetailsservice,
			NetwinProductionDetailsService netwinProductionDetailsService,
			EncryptionAndDecryptionData encryptionAndDecryptionData, NtResponse ntResponse,
			PnVndrValidation pnVndrValidation, PnResponseRepo pnResponseRepo) {

		this.pnNetwinRequestRepository = pnNetwinRequestRepository;
		this.mapper = mapper;
		this.jdbcTemplate = jdbcTemplate;
		this.pnVndrRequestService = pnVndrRequestService;
		this.netwinCustomerDetailsService = netwinCustomerDetailsService;
		this.netwinProductionDetailsService = netwinProductionDetailsService;
		this.encryptionAndDecryptionData = encryptionAndDecryptionData;
		this.ntResponse = ntResponse;
		this.pnVndrValidation = pnVndrValidation;
		this.pnResponseRepo = pnResponseRepo;
	}

	@Override
	public String callPanRequest(String panRequestJson) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, JsonMappingException, JsonProcessingException
{
		String resultStr = null;

		PnNetwinRequestDto panRequestDto = new PnNetwinRequestDto();
		// Json String Encrypt
		String pnRequestDecryptString = encryptionAndDecryptionData.getRequestDecryptData(panRequestJson);
		panRequestDto.setReqEncrypt(panRequestJson);
		panRequestDto.setReqDecrypt(pnRequestDecryptString);
		panRequestDto.setEntryDate(date);

		// Mapping Dto to Entity
		PnNetwinRequest pnNetwinRequest = mapper.map(panRequestDto, PnNetwinRequest.class);
		if (pnNetwinRequest != null) {
			// Save client PnrequestMas Data
			pnNetwinRequest = pnNetwinRequestRepository.save(pnNetwinRequest);
			// Mapping Entity to Dto
			panRequestDto = mapper.map(pnNetwinRequest, PnNetwinRequestDto.class);
			System.out.println("panRequestDto " + panRequestDto);
			// Verify Pan No and Netwin required Json Field
			resultStr = validateRequest(panRequestDto);
			ObjectMapper objectMapper = new ObjectMapper();
			// String to convert Json Node required get CustId & ProdId
			JsonNode jsonNode = objectMapper.readTree(panRequestDto.getReqDecrypt());
			String custId = jsonNode.get("custId").asText();
			String prodId = jsonNode.get("prodId").asText();
			Object id = pnNetwinRequest.getPnReMasSrNo();
			((ObjectNode) jsonNode).put("userReqSrNo", id.toString()); // Convert id to String if necessary

			// Fetch Customer Details and check Available or Not
			NetwinCustomerDetails netwinCustomerDetails = netwinCustomerDetailsService
					.fetchNetwinCustomerDetails(custId);
			// Fetch Product Details and check Available or Not
			NetwinProductionDetails netwinProductionDetails = netwinProductionDetailsService.fetchNetwinProductionDetails(prodId);
			if (netwinCustomerDetails == null) {
				resultStr = ntResponse.getNtResponse(423);
			}else if (netwinProductionDetails == null) {
				resultStr = ntResponse.getNtResponse(424);
			}else {
			if (resultStr == null) {
				CustomerVendorDetailsDto customerVendorDetailsDto = new CustomerVendorDetailsDto();

				customerVendorDetailsDto.setPanNo(jsonNode.get("panNo").asText());
				customerVendorDetailsDto.setCustId(custId);
				customerVendorDetailsDto.setProdId(prodId);
				customerVendorDetailsDto.setVendorId(netwinCustomerDetails.getNetwVndrs());
				customerVendorDetailsDto.setPnReqMasSrNo(pnNetwinRequest.getPnReMasSrNo());
				
				// Customer key to Vendor Key Replace Request
				String vendorRequestJson = pnVndrValidation.VendorRequestValidation(jsonNode, customerVendorDetailsDto);
				System.out.println("vendorRequestJson" + vendorRequestJson);
				// Call Vendor Request API
				resultStr = pnVndrRequestService.callVenderRequest(vendorRequestJson, customerVendorDetailsDto);
				CustomerResponseDto customerResponseDto = new CustomerResponseDto();
				customerResponseDto.setPnReqMasSrNo(pnNetwinRequest.getPnReMasSrNo());
				customerResponseDto.setEntDateTM(date);
				customerResponseDto.setReqDecrypt(resultStr);
				customerResponseDto.setReqEncrypt(encryptionAndDecryptionData.getEncryptResponse(resultStr));
				customerResponseDto.setPnReqMasSrNo(customerVendorDetailsDto.getPnReqMasSrNo());
				PnResponse pnResponse = mapper.map(customerResponseDto, PnResponse.class);
				
				pnResponseRepo.save(pnResponse);
				resultStr = customerResponseDto.getReqEncrypt();
			}
			}
		} else {
			resultStr = "Error: Unable to map PnNetwinRequest entity from DTO.";
		}
		// Call to mapping database field Name
		// if decrypt string null then return error show
		return resultStr;// getMappingDataBaseThrough(pnRequestDecryptString, pnNetwinRequest);

	}

	private String validateRequest(PnNetwinRequestDto panRequestDto)
			throws JsonMappingException, JsonProcessingException {
		String result = null;
		// adhar check validation

		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(panRequestDto.getReqDecrypt());
		String panNo = jsonNode.get("panNo").asText();

		final String PAN_PATTERN = "[A-Z]{5}\\d{4}[A-Z]";
		Pattern pattern = Pattern.compile(PAN_PATTERN);
		Matcher matcher = pattern.matcher(panNo);

		boolean flag = matcher.matches();
		if (!flag) {
			result = ntResponse.getNtResponse(2003);
			return result.toString();
		}
		Map<String, String> pnRequestJsonMap = jsonStringToMap(panRequestDto);
		// details object
		// Database field Name Fetch
		Map<String, Object> netwinRequestpara = getNetwinRequestParas();

		for (Map.Entry<String, Object> netwinField : netwinRequestpara.entrySet()) {

			if (!pnRequestJsonMap.containsKey(netwinField.getKey()) && netwinField.getValue().toString().equals("Y")) {

				result = ntResponse.getNtResponse(500);

			}

		}

		return result;
	}

	private Map<String, Object> getNetwinRequestParas() {
		List<Map<String, Object>> netwinFieldResultsMap = null;

		netwinFieldResultsMap = jdbcTemplate.queryForList(QueryUtil.NETWNFIELDQUERY, "P", "V");

		// Process the results using Java Streams
		return netwinFieldResultsMap.stream()
				.collect(Collectors.toMap(vendorField -> (String) vendorField.get("NETWREQKEYNAME"),
						vendorField -> (String) vendorField.get("NETWREQKEYREQ")));
	}

//Mapping method database Field

	private Map<String, String> jsonStringToMap(PnNetwinRequestDto panRequestDto) {
		String aharRequestDecryptString = panRequestDto.getReqDecrypt();
		Gson gson = new Gson();
		Type type = new com.google.gson.reflect.TypeToken<Map<String, String>>() {
		}.getType();
		return gson.fromJson(aharRequestDecryptString, type);
	}

}
