package com.netwin.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.netwin.exception.FieldMappingException;
import com.netwin.exception.FieldValueSetterNotFoundException;
import com.netwin.exception.PnNetwinRequestException;
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
import com.netwin.util.EncryptionAndDecryptionData;
import com.netwin.util.QueryUtil;
import com.netwin.validation.PnRequestValidation;

@Service
public class PnNetwinRequestServiceImpl implements PnNetwinRequestService {

	private PnNetwinRequestRepo pnNetwinRequestRepository;


private EncryptionAndDecryptionData encryptionAndDecryptionData;

	private PnNetwinRequestMapper mapper;

	private JdbcTemplate jdbcTemplate;

	private PnRequestValidation pnRequestValidation;

	private NetwinCustomerDetailsService netwinCustomerDetailsService;

	private PnVendorDetailsService pnVendorDetailsservice;

	private NetwinProductionDetailsService netwinProductionDetailsService;

	private PnRequestService pnRequestService;

	private PnVndrRequestService pnVndrRequestService;

	private ErrorApplicationService errorApplicationService;
	private Date date = new Date(System.currentTimeMillis());

	@Autowired
	public PnNetwinRequestServiceImpl( PnNetwinRequestRepo pnNetwinRequestRepository,
			PnNetwinRequestMapper mapper, JdbcTemplate jdbcTemplate,
			PnVndrRequestService pnVndrRequestService, ErrorApplicationService errorApplicationService,
			PnRequestValidation pnRequestValidation, NetwinCustomerDetailsService netwinCustomerDetailsService,
			PnVendorDetailsService pnVendorDetailsservice,
			NetwinProductionDetailsService netwinProductionDetailsService, PnRequestService pnRequestService,EncryptionAndDecryptionData encryptionAndDecryptionData) {
		
		this.pnNetwinRequestRepository = pnNetwinRequestRepository;
		this.mapper = mapper;
		this.jdbcTemplate = jdbcTemplate;
		this.pnVndrRequestService = pnVndrRequestService;
		this.errorApplicationService = errorApplicationService;
		this.pnRequestValidation = pnRequestValidation;
		this.netwinCustomerDetailsService = netwinCustomerDetailsService;
		this.pnVendorDetailsservice = pnVendorDetailsservice;
		this.netwinProductionDetailsService = netwinProductionDetailsService;
		this.pnRequestService = pnRequestService;
this.encryptionAndDecryptionData=encryptionAndDecryptionData;
	}

	@Override
	public String callPanRequest(String panRequestJson, String clientIp) throws PnNetwinRequestException {
	    try {
	        PnNetwinRequestDto panRequestDto = new PnNetwinRequestDto();
	        String pnRequestDecryptString = encryptionAndDecryptionData.getPnRequestDecryptData(panRequestJson);
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
	        // Call to mapping database field Name
	        // if decrypt string null then return error show
	        return getMappingDataBaseThrough(pnRequestDecryptString, pnNetwinRequest);
	    } catch (Exception e) {
	        // Wrap and throw the caught exception as PnNetwinRequestException
	        throw new PnNetwinRequestException("Error while processing PAN request", e);
	    }
	}

	/**public String callPanRequest(String panRequestJson, String clientIp) throws PnNetwinRequestException {
	    try {
	        //1.Decrypt request string
	        String pnRequestDecryptString = pnNetwinDecrypt.getPnRequestDecryptData(panRequestJson);
	        PnNetwinRequestDto panRequestDto = new PnNetwinRequestDto();
	        panRequestDto.setReqEncrypt(panRequestJson);
	        panRequestDto.setReqDecrypt(pnRequestDecryptString);
	        panRequestDto.setEntryDate(date);
	        panRequestDto.setCallingIpAdr(clientIp);
	        //2. Mapping Dto to Entity
	        PnNetwinRequest pnNetwinRequest = mapper.toPnNetwinRequestEntity(panRequestDto);
	       
	        // 3.Save client request Data
	         pnNetwinRequest = pnNetwinRequestRepository.save(pnNetwinRequest);
	         
	         setRelatedEntities
	        
			PnRequest pnRequestObj = new PnRequest(); 
			Map<String, Object> netwinFieldResults1 = getNetwinFieldResults();
			Map<String, String> pnRequestDecrypt = jsonStringToMap(pnRequestDecryptString);

			mapFields(pnRequestObj, netwinFieldResults1, pnRequestDecrypt);
			// Call services to set related entities
			setRelatedEntities(pnRequestObj, pnNetwinRequest1);
	       // 
	        return callVendorServiceAndGetResult(pnRequestObj, netwinFieldResults1, pnRequestDecrypt);
	        
	    } catch (Exception e) {
	        // Wrap and throw the caught exception as PnNetwinRequestException
	        throw new PnNetwinRequestException("Error while processing PAN request", e);
	    }
	}**/

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
			return e.getMessage();
		}
		
	}

	private Map<String, Object> getNetwinFieldResults() {
		Map<String, Object> netwinFieldResults1 = new HashMap<>();
		List<Map<String, Object>> netwinFieldResultsMap = jdbcTemplate.queryForList(QueryUtil.NETWNFIELDQUERY, "P",
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
	        Map<String, String> pnRequestDecrypt) throws FieldMappingException {
	    try {
	        for (Field field : PnRequest.class.getDeclaredFields()) {
	            if (netwinFieldResults1.containsKey(field.getName()) && pnRequestDecrypt.containsKey(field.getName())) {
	                setFieldValue(pnRequest, field, pnRequestDecrypt.get(field.getName()));
	            } else if (!"pnReqDetSrNo".equals(field.getName())) {
	                setFieldValue(pnRequest, field, null);
	            }
	        }
	    } catch (FieldValueSetterNotFoundException e) {
	        // If there is an issue setting the field value, wrap and throw a dedicated exception
	        throw new FieldMappingException("Error while mapping fields in PnRequest", e);
	    }
	}


	private void setFieldValue(PnRequest pnRequest, Field field, String value) throws FieldValueSetterNotFoundException {
	    try {
	        String capitalizedFieldName = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
	        String setterMethodName = "set" + capitalizedFieldName;
	        Method setterMethod = PnRequest.class.getMethod(setterMethodName, field.getType());
	        setterMethod.invoke(pnRequest, value);
	    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
	        // If the setter method is not found or cannot be accessed, throw a dedicated exception
	        throw new FieldValueSetterNotFoundException("Setter method not found or cannot be accessed for field: " + field.getName(), e);
	    }
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
					.fetchPnVendorDetails(ntCustomerDetails.getNetwVndrs());
			if (pnVendorDetails.isPresent()) {
				pnRequest.setPnVendorDetails(pnVendorDetails.get());
			}
			pnRequest.setPnNetwinRequest(pnNetwinRequest1);
			pnRequest.setAppDate(date);
		} catch (Exception ex) {
			throw new ResourceNotFoundException("Data Not Found", "", 204l, HttpStatus.BAD_REQUEST);
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
			} 
			}
				try {
//Check netwin field and Value Validation
					Result result = pnRequestValidation.checkNetwnValidation(pnRequestDecrypt,
							netwinFieldResults1, pnRequest1);
					if (result.isValid()) {
						pnRequest = (PnRequest) result.getData();
//Call Vendor Service Requst 
						Result pnVndrRequest = pnVndrRequestService.fetchPnVndrRequest(pnRequest,
								pnRequestDecrypt);

						return pnVndrRequest.getErrorMessage();
					} else {

						// Handle the error message
						return result.getErrorMessage();
					}

				} catch (Exception e) {
					errorApplicationService.storeError(504, e.getMessage());
					return e.getMessage();
				}
			

		
	}

	private Map<String, String> jsonStringToMap(String pnRequestDecrypt) {
		Gson gson = new Gson();
		Type type = new com.google.gson.reflect.TypeToken<Map<String, String>>() {
		}.getType();
		return gson.fromJson(pnRequestDecrypt, type);
	}

}
