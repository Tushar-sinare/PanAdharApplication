package com.netwin.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.netwin.dto.AharNtwnReqDto;
import com.netwin.entiry.AharNtwnRequest;
import com.netwin.entiry.AharRequest;
import com.netwin.entiry.AharVendorDetails;
import com.netwin.entiry.NetwinCustomerDetails;
import com.netwin.entiry.NetwinProductionDetails;
import com.netwin.logger.LoggerProvider;
import com.netwin.logger.MyLogger;
import com.netwin.mapper.AharNtwnRequestMapper;
import com.netwin.repo.AharNtwnRequestRepo;
import com.netwin.service.AharNtwnRequestService;
import com.netwin.service.AharRequestService;
import com.netwin.service.AharVendorDetailsService;
import com.netwin.service.ErrorApplicationService;
import com.netwin.service.NetwinCustomerDetailsService;
import com.netwin.service.NetwinProductionDetailsService;
import com.netwin.util.EncryptionAndDecryptionData;
import com.netwin.util.QueryUtil;

@Component
public class AharNtwnRequestServiceImpl implements AharNtwnRequestService {
	static final MyLogger logger = LoggerProvider.getLogger(AharNtwnRequestServiceImpl.class);
	private Date date = new Date(System.currentTimeMillis());
	private EncryptionAndDecryptionData encryptionAndDecryptionData;
	private ErrorApplicationService errorApplicationService;
	private AharNtwnRequestRepo aharNtwnRequestRepo;
	private AharNtwnRequestMapper mapper;
	private JdbcTemplate jdbcTemplate;
	private NetwinCustomerDetailsService ntwnCustomerDetailsService;
	private NetwinProductionDetailsService ntwnProductionDetailsService;
	private AharVendorDetailsService aharVendorDetailsService;
	private AharRequestService aharRequestService;
	@Autowired
	public AharNtwnRequestServiceImpl(EncryptionAndDecryptionData encryptionAndDecryptionData,
			AharNtwnRequestRepo aharNtwnRequestRepo, ErrorApplicationService errorApplicationService,
			AharNtwnRequestMapper mapper,JdbcTemplate jdbcTemplate,NetwinCustomerDetailsService ntwnCustomerDetailsService,NetwinProductionDetailsService ntwnProductionDetailsService,AharVendorDetailsService aharVendorDetailsService,AharRequestService aharRequestService) {

		this.encryptionAndDecryptionData = encryptionAndDecryptionData;
		this.errorApplicationService = errorApplicationService;
		this.mapper = mapper;
		this.aharNtwnRequestRepo = aharNtwnRequestRepo;
		this.jdbcTemplate = jdbcTemplate;
		this.ntwnCustomerDetailsService = ntwnCustomerDetailsService;
		this.ntwnProductionDetailsService=ntwnProductionDetailsService;
		this.aharVendorDetailsService = aharVendorDetailsService;
		this.aharRequestService = aharRequestService;
	}

	@Override
	public String callPanRequest(String aharJson, String clientIp) {
		String resultStr = null;
		AharNtwnReqDto aharNtwnReqDto = new AharNtwnReqDto();

		String aharRequestDecryptString = encryptionAndDecryptionData.getPnRequestDecryptData(aharJson);
		aharNtwnReqDto.setCallingIpAdr(clientIp);
		aharNtwnReqDto.setReqDecrypt(aharRequestDecryptString);
		aharNtwnReqDto.setReqEncrypt(aharJson);
		aharNtwnReqDto.setEntryDate(date);
		AharNtwnRequest aharNtwnRequest = mapper.toAharNtwnRequestEntity(aharNtwnReqDto);
		//remove if else
		if (aharNtwnRequest != null) {
			// Save client request Data
			aharNtwnRequest = aharNtwnRequestRepo.save(aharNtwnRequest);
			
			resultStr = getMappingDataBaseThrough(aharRequestDecryptString, aharNtwnRequest);

		} else {
			resultStr = "Error: Unable to map PnNetwinRequest entity from DTO.";
			errorApplicationService.storeError(1003, resultStr);
			logger.error(resultStr);
			
		}

	

		return resultStr;
	}

	private String getMappingDataBaseThrough(String aharRequestDecryptString, AharNtwnRequest aharNtwnRequest){
		// Json String to Map Convert
		Map<String, String> aharRequestJsonMap = jsonStringToMap(aharRequestDecryptString);
		AharRequest aharRequestDetObj = new AharRequest();
		//details object
		//Database field Name Fetch
		Map<String, Object> netwinFieldResults1 = getNetwinFieldResults();
		
		//set field value from jsonStringtoMap
		mapFields(aharRequestDetObj, netwinFieldResults1, aharRequestJsonMap);
		
		// Call services to set related entities Mapping Id
		setRelatedEntities(aharRequestDetObj, aharNtwnRequest);
		
		return callVendorServiceAndGetResult(aharRequestDetObj, aharRequestJsonMap);
	}

	private String callVendorServiceAndGetResult(AharRequest aharRequestObj,Map<String, String> aharRequestJsonMap) {
		//Store Vendor Request Details 
		
		 AharRequest aharRequest1 = aharRequestService.callVendorService(aharRequestObj);
		 return aharRequest1.toString();
	}

	private void setRelatedEntities(AharRequest aharRequestObj, AharNtwnRequest aharNtwnRequest) {
		//Fetch Customer Details and Validate 
			NetwinCustomerDetails ntCustomerDetails = ntwnCustomerDetailsService
					.fetchNetwinCustomerDetails(aharRequestObj.getCustId());
			if (ntCustomerDetails != null) {
				aharRequestObj.setNtwnCustomerDetails(ntCustomerDetails);
			
			}
			
			// Call Netwin Product Details Service and set
			NetwinProductionDetails ntNetwinProductionDetails = ntwnProductionDetailsService
					.fetchNetwinProductionDetails(aharRequestObj.getProdId());
			if (ntCustomerDetails != null) {
				aharRequestObj.setNetwinProductionDetails(ntNetwinProductionDetails);
			}
		
			// Call Vendor Details Service and set
			Optional<AharVendorDetails> aharVendorDetails = aharVendorDetailsService
					.fetchPnVendorDetails(ntCustomerDetails.getNetwVndrs());
			if (aharVendorDetails.isPresent()) {
				aharRequestObj.setAharVndrDetails(aharVendorDetails.get());
			}
			
			aharRequestObj.setAharNtwnRequest(aharNtwnRequest);
			aharRequestObj.setAppDate(date);
		
		
	}
//Mapping through field  database and Json 
	private void mapFields(AharRequest aharRequestObj, Map<String, Object> netwinFieldResults1,
			Map<String, String> aharRequestJsonMap){
		  for (Field field : AharRequest.class.getDeclaredFields()) {
			 
	            if (netwinFieldResults1.containsKey(field.getName()) && aharRequestJsonMap.containsKey(field.getName())) {
	                setFieldValue(aharRequestObj, field, aharRequestJsonMap.get(field.getName()));
	            } else if (!"adhReqDetSrNo".equals(field.getName())) {
	                setFieldValue(aharRequestObj, field, null);
	            }
			 
	        }
		
	}
//SetField Entity Value Method
	private void setFieldValue(AharRequest aharRequestObj, Field field, String value) {
		String capitalizedFieldName = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
        String setterMethodName = "set" + capitalizedFieldName;
        Method setterMethod = null;
		try {
			setterMethod = AharRequest.class.getMethod(setterMethodName, field.getType());
			setterMethod.invoke(aharRequestObj, value);
		} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
	        logger.info(e.getMessage());
	        int errorCode = 5000;
	        if (e instanceof NoSuchMethodException) {
	            errorCode = 5004;
	            
	        } else if (e instanceof IllegalAccessException) {
	            errorCode = 5001;
	        } else if (e instanceof IllegalArgumentException) {
	            errorCode = 5002;
	        } else if (e instanceof InvocationTargetException) {
	            errorCode = 5003;
	        }
	        errorApplicationService.storeError(errorCode, e.getMessage());
	       
	        //use throw exception
	    }
		
	}
//Netwin Database Field Fetch
	private Map<String, Object> getNetwinFieldResults() {
		Map<String, Object> netwinFieldResults1 = new HashMap<>();
		//Without two loop solve this problem
		List<Map<String, Object>> netwinFieldResultsMap = jdbcTemplate.queryForList(QueryUtil.NETWNFIELDQUERY, "A",
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
	
//JsonString to Map Convert Method
	private Map<String, String> jsonStringToMap(String aharRequestDecryptString) {
		Gson gson = new Gson();
		Type type = new com.google.gson.reflect.TypeToken<Map<String, String>>() {
		}.getType();
		return gson.fromJson(aharRequestDecryptString, type);
	}


}
