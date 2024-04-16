package com.netwin.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.netwin.dto.PnNetwinRequestDto;
import com.netwin.entiry.NetwinCustomerDetails;
import com.netwin.entiry.NetwinProductionDetails;
import com.netwin.entiry.PnNetwinRequest;
import com.netwin.entiry.PnRequest;
import com.netwin.entiry.PnVendorDetails;
import com.netwin.entiry.Result;
import com.netwin.exception.ResourceNotFoundException;
import com.netwin.mapper.PnNetwinRequestMapper;
import com.netwin.repo.PnNetwinRequestRepo;
import com.netwin.service.ErrorApplicationService;
import com.netwin.service.NetwinCustomerDetailsService;
import com.netwin.service.NetwinProductionDetailsService;
import com.netwin.service.PnNetwinRequestService;
import com.netwin.service.PnRequestService;
import com.netwin.service.PnVendorDetailsService;
import com.netwin.service.PnVndrRequestService;
import com.netwin.util.PnNetwinDecrypt;
import com.netwin.util.QueryUtil;
import com.netwin.validation.PnRequestValidation;

@Service
public class PnNetwinRequestServiceImpl implements PnNetwinRequestService {

	private PnNetwinRequestRepo pnNetwinRequestRepository;

	private PnNetwinDecrypt pnNetwinDecrypt;

	private PnNetwinRequestMapper mapper;

	private QueryUtil queryUtil;

	private JdbcTemplate jdbcTemplate;

	private PnRequestValidation pnRequestValidation;

	private NetwinCustomerDetailsService netwinCustomerDetailsService;

	private PnVendorDetailsService pnVendorDetailsservice;

	private NetwinProductionDetailsService netwinProductionDetailsService;

	private PnRequestService pnRequestService;

	private PnVndrRequestService pnVndrRequestService;

	private ErrorApplicationService errorApplicationService;
	private Date date = new Date(System.currentTimeMillis());
	

	@Override
	public String callPanRequest(String panRequestJson, String clientIp) {
		PnNetwinRequestDto panRequestDto = new PnNetwinRequestDto();
		// Call to Decrypt Data

		String pnRequestDecryptString = pnNetwinDecrypt.getPnRequestDecryptData(panRequestJson);

		panRequestDto.setReqEncrypt(panRequestJson);
		panRequestDto.setReqDecrypt(pnRequestDecryptString);
		panRequestDto.setEntryDate(date);
		panRequestDto.setCallingIpAdr(clientIp);
		// Mapping Dto to Entity
		PnNetwinRequest pnNetwinRequest = mapper.toPnNetwinRequestEntity(panRequestDto);
		if (pnNetwinRequest != null) {
			// Save client request Data
			pnNetwinRequest = pnNetwinRequestRepository.save(pnNetwinRequest);
		} else {
			errorApplicationService.storeError(1003, "Error: Unable to map PnNetwinRequest entity from DTO.");
			return "Error: Unable to map PnNetwinRequest entity from DTO.";
		}
//Call to mapping database field Name
		// if decrypt string null then return error show

		return getMappingDataBaseThrough(pnRequestDecryptString, pnNetwinRequest);
		 

	}

//Mapping method database Field
	public String getMappingDataBaseThrough(String pnRequestDecryptString, PnNetwinRequest pnNetwinRequest1) {
		PnRequest pnRequestObj = new PnRequest();
		Map<String, Object> netwinFieldResults1 = getNetwinFieldResults();
		Map<String, String> pnRequestDecrypt = jsonStringToMap(pnRequestDecryptString);

		try {
			mapFields(pnRequestObj, netwinFieldResults1, pnRequestDecrypt);
			// Call services to set related entities
			setRelatedEntities(pnRequestObj, pnNetwinRequest1);

			return callVendorServiceAndGetResult(pnRequestObj, netwinFieldResults1, pnRequestDecrypt);
		} catch (Exception e) {
			errorApplicationService.storeError(504, e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	private Map<String, Object> getNetwinFieldResults() {
		Map<String, Object> netwinFieldResults1 = new HashMap<>();
		List<Map<String, Object>> netwinFieldResultsMap = jdbcTemplate.queryForList(queryUtil.NETWINFIELDQUERY, "P",
				"V");
		for (Map<String, Object> vendorField : netwinFieldResultsMap) {
			String key = (String) vendorField.get("NETWREQKEYNAME");
			for (Map.Entry<String, Object> vendorEntry : vendorField.entrySet()) {
				if (vendorEntry.getKey().startsWith("NETWREQKEYREQ")) {
					netwinFieldResults1.put(key, vendorEntry.getValue());
				}
			}
		}
		return netwinFieldResults1;
	}

	private void mapFields(PnRequest pnRequest, Map<String, Object> netwinFieldResults1,
			Map<String, String> pnRequestDecrypt) throws Exception {
		for (Field field : PnRequest.class.getDeclaredFields()) {
			if (netwinFieldResults1.containsKey(field.getName()) && pnRequestDecrypt.containsKey(field.getName())) {
				setFieldValue(pnRequest, field, pnRequestDecrypt.get(field.getName()));
			} else if (!"pnReqDetSrNo".equals(field.getName())) {
				setFieldValue(pnRequest, field, null);
			}
		}
	}

	private void setFieldValue(PnRequest pnRequest, Field field, String value) throws SecurityException,Exception {
		String capitalizedFieldName = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
		String setterMethodName = "set" + capitalizedFieldName;
		Method setterMethod = PnRequest.class.getMethod(setterMethodName, field.getType());
		setterMethod.invoke(pnRequest, value);
	}

	private void setRelatedEntities(PnRequest pnRequest, PnNetwinRequest pnNetwinRequest1) {
		// Call Netwin Customer Details Service and set
		try {
			NetwinCustomerDetails ntCustomerDetails = netwinCustomerDetailsService
					.fetchNetwinCustomerDetails(pnRequest.getCustId());
			if (ntCustomerDetails != null) {
				pnRequest.setNetwinCustomerDetails(ntCustomerDetails);
			}
			// Call Netwin Product Details Service and set
			NetwinProductionDetails ntNetwinProductionDetails = netwinProductionDetailsService
					.fetchNetwinProductionDetails(pnRequest.getProdId());
			if (ntCustomerDetails != null) {
				pnRequest.setNetwinProductionDetails(ntNetwinProductionDetails);
			}
			// Call Vendor Details Service and set
			Optional<PnVendorDetails> pnVendorDetails = pnVendorDetailsservice
					.fetchPnVendorDetails(ntNetwinProductionDetails.getNetwVndrs());
			if (pnVendorDetails.isPresent()) {
				pnRequest.setPnVendorDetails(pnVendorDetails.get());
			}
			pnRequest.setPnNetwinRequest(pnNetwinRequest1);
			pnRequest.setAppDate(date);
		} catch (Exception ex) {
			throw new ResourceNotFoundException("Data Not Found", "",204l, HttpStatus.BAD_REQUEST);
		}

	}

	private String callVendorServiceAndGetResult(PnRequest pnRequest, Map<String, Object> netwinFieldResults1,
			Map<String, String> pnRequestDecrypt) {
		// Call Vendor Service Request
		// Validate netwin fields and values
		// Handle errors or return result
		PnRequest pnRequest1 = pnRequestService.callVendorService(pnRequest);
		for (Map.Entry<String, Object> netwinField : netwinFieldResults1.entrySet()) {
			if (!pnRequestDecrypt.containsKey(netwinField.getKey()) && (netwinField.getValue()).equals('Y')) {
				errorApplicationService.storeError(1007, "Please Required " + netwinField);
				return "Please Required " + netwinField;
			} else {
				try {
//Check netwin field and Value Validation
					Result<PnRequest> result = pnRequestValidation.checkNetwnValidation(pnRequestDecrypt,
							netwinFieldResults1, pnRequest1);
					if (result.isValid()) {
						pnRequest = result.getData();
//Call Vendor Service Requst 
						Result<String> pnVndrRequest = pnVndrRequestService.fetchPnVndrRequest(pnRequest,
								pnRequestDecrypt);

						return pnVndrRequest.getErrorMessage();
					} else {
					
						// Handle the error message
						return  result.getErrorMessage();
					}

				} catch (Exception e) {
					errorApplicationService.storeError(504, e.getMessage());
					e.printStackTrace();
				}
			}

		}
		return null;
	}

	private Map<String, String> jsonStringToMap(String pnRequestDecrypt) {
		Gson gson = new Gson();
		Type type = new com.google.gson.reflect.TypeToken<Map<String, String>>() {
		}.getType();
		return gson.fromJson(pnRequestDecrypt, type);
	}

}
