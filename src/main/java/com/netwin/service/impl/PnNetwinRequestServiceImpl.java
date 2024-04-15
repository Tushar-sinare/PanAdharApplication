package com.netwin.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.netwin.mapper.PnNetwinRequestMapper;
import com.netwin.repo.PnNetwinRequestRepo;
import com.netwin.service.ErrorApplicationService;
import com.netwin.service.NetwinCustomerDetailsService;
import com.netwin.service.NetwinProductionDetailsService;
import com.netwin.service.PnNetwinRequestService;
import com.netwin.service.PnRequestService;
import com.netwin.service.PnVendorDetailsService;
import com.netwin.service.PnVndrRequestService;
import com.netwin.util.NtResponse;
import com.netwin.util.PnNetwinDecrypt;
import com.netwin.util.QueryUtil;
import com.netwin.validation.PnRequestValidation;

@Service
public class PnNetwinRequestServiceImpl implements PnNetwinRequestService {

	@Autowired
	private PnNetwinRequestRepo pnNetwinRequestRepository;

	@Autowired
	private PnNetwinDecrypt pnNetwinDecrypt;

	@Autowired
	private PnNetwinRequestMapper mapper;
	@Autowired
	private QueryUtil queryUtil;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private PnRequestValidation pnRequestValidation;

	@Autowired
	private NetwinCustomerDetailsService netwinCustomerDetailsService;
	@Autowired
	private PnVendorDetailsService pnVendorDetailsservice;
	@Autowired
	private NetwinProductionDetailsService netwinProductionDetailsService;
	@Autowired
	private PnRequestService pnRequestService;
	@Autowired
	private PnVndrRequestService pnVndrRequestService;
	@Autowired
	private NtResponse ntResponse;
	@Autowired
	private ErrorApplicationService errorApplicationService;
	private Date date = new Date(System.currentTimeMillis());
	PnRequest pnRequest = null;

	@Override
	public String callPanRequest(String panRequestJson, String clientIp) throws Exception {
		PnNetwinRequestDto panRequestDto = new PnNetwinRequestDto();
		// Call to Decrypt Data
		String pnRequestDecryptString = pnNetwinDecrypt.getPnRequestDecryptData(panRequestJson);
		panRequestDto.setReqEncrypt(panRequestJson.toString());
		panRequestDto.setReqDecrypt(pnRequestDecryptString.toString());
		panRequestDto.setEntryDate(date);
		panRequestDto.setCallingIpAdr(clientIp);
		// Mapping Dto to Entity
		PnNetwinRequest pnNetwinRequest = mapper.toPnNetwinRequestEntity(panRequestDto);
		if (pnNetwinRequest != null) {
			// Save client request Data
			PnNetwinRequest pnNetwinRequest1 = pnNetwinRequestRepository.save(pnNetwinRequest);

//Call to mapping database field Name
			// if decrypt string null then return error show

			String pnRespons = getMappingDataBaseThrough(pnRequestDecryptString, pnNetwinRequest1);
			return pnRespons;

		} else {
			errorApplicationService.storeError(1003, "Error: Unable to map PnNetwinRequest entity from DTO.");
			return "Error: Unable to map PnNetwinRequest entity from DTO.";
		}
	}

//Mapping method database Field
	public String getMappingDataBaseThrough(String pnRequestDecryptString, PnNetwinRequest pnNetwinRequest1) {
		PnRequest pnRequest = new PnRequest();
		Date date = new Date(System.currentTimeMillis());
		Map<String, Object> netwinFieldResults1 = new HashMap<>();
		List<Map<String, Object>> netwinFieldResultsMap = jdbcTemplate.queryForList(queryUtil.NETWINFIELDQUERY, "P",
				"V");
		for (Map<String, Object> vendorField : netwinFieldResultsMap) {
			for (Map.Entry<String, Object> vendorEntry : vendorField.entrySet()) {
				String key1 = (String) vendorField.get("NETWREQKEYNAME");
				if (vendorEntry.getKey().contains("NETWREQKEYREQ")) {
					String value1 = (String) vendorEntry.getValue();

					netwinFieldResults1.put(key1, value1);

				}
			}
		}
		Map<String, String> pnRequestDecrypt = jsonStringToMap(pnRequestDecryptString);
		try {

			for (Field field : PnRequest.class.getDeclaredFields()) {

				if (netwinFieldResults1.containsKey(field.getName()) && pnRequestDecrypt.containsKey(field.getName())) {

					String capitalizedFieldName = field.getName().substring(0, 1).toUpperCase()
							+ field.getName().substring(1);

					String setterMethodName = "set" + capitalizedFieldName;

					Method setterMethod = PnRequest.class.getMethod(setterMethodName, field.getType());

					String value = pnRequestDecrypt.get(field.getName());

					setterMethod.invoke(pnRequest, value);

				} else {
					String capitalizedFieldName = field.getName().substring(0, 1).toUpperCase()
							+ field.getName().substring(1);
					if (!("pnReqDetSrNo".equals(field.getName()))) {
						String setterMethodName = "set" + capitalizedFieldName;

						Method setterMethod = PnRequest.class.getMethod(setterMethodName, field.getType());

						String value = null;

						setterMethod.invoke(pnRequest, value);
					}
				}
			}
		} catch (Exception e) {
			errorApplicationService.storeError(504, e.getMessage());
			e.printStackTrace();
		}
		// call Netwin Customer Details Service
		NetwinCustomerDetails ntCustomerDetails = netwinCustomerDetailsService
				.fetchNetwinCustomerDetails(pnRequest.getCustId());
		try {
			pnRequest.setNetwinCustomerDetails(ntCustomerDetails);
//Call Netwin Product Details Service
			NetwinProductionDetails ntNetwinProductionDetails = netwinProductionDetailsService
					.fetchNetwinProductionDetails(pnRequest.getProdId());

			pnRequest.setNetwinProductionDetails(ntNetwinProductionDetails);
//Call Vendor Details Service
			PnVendorDetails pnVendorDetails = pnVendorDetailsservice
					.fetchPnVendorDetails(ntNetwinProductionDetails.getNetwVndrs());

			pnRequest.setPnVendorDetails(pnVendorDetails);

			pnRequest.setPnNetwinRequest(pnNetwinRequest1);
			pnRequest.setAppDate(date);

		} catch (NullPointerException ex) {
			String result = ntResponse.getNtResponse(2002).getResMap().toString();
			errorApplicationService.storeError(2002, result);
			return result;
		}
		// Call Vendor Service
		PnRequest pnRequest1 = pnRequestService.callVendorService(pnRequest);
		for (Map.Entry<String, Object> netwinField : netwinFieldResults1.entrySet()) {
			if (!pnRequestDecrypt.containsKey(netwinField.getKey()) && ((String) netwinField.getValue()).equals('Y')) {
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
						String errorMessage = result.getErrorMessage();
						// Handle the error message
						return errorMessage;
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
