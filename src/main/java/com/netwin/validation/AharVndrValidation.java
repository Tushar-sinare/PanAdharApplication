package com.netwin.validation;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netwin.dto.CustomerVendorDetailsDto;
import com.netwin.logger.LoggerProvider;
import com.netwin.logger.MyLogger;
import com.netwin.service.impl.AharNtwnRequestServiceImpl;
import com.netwin.util.ConstantVariable;
import com.netwin.util.NtAharResponse;
import com.netwin.util.QueryUtil;
import com.netwin.util.VendorFieldMapping;
@Component
public class AharVndrValidation {
	private VendorFieldMapping vendorFieldMapping;
	String result =null;
	static final MyLogger logger = LoggerProvider.getLogger(AharNtwnRequestServiceImpl.class);
	private JdbcTemplate jdbcTemplate;
	private NtAharResponse ntAharResponse;
	@Autowired
	public AharVndrValidation(JdbcTemplate jdbcTemplate,VendorFieldMapping vendorFieldMapping,NtAharResponse ntAharResponse) {
		this.jdbcTemplate=jdbcTemplate;
		this.vendorFieldMapping = vendorFieldMapping;
		this.ntAharResponse = ntAharResponse;
	}
	public String VendorRequestValidation(JsonNode jsonNode, CustomerVendorDetailsDto customerVendorDetailsDto,String reqStatus) throws JsonMappingException, JsonProcessingException {

	
	String str1 = jsonNode.toString();
	
		Map<String,String> vndrRequestMap = vendorRequestMap(customerVendorDetailsDto,reqStatus);
	
		
	result= vendorFieldMapping.replaceKeys(str1,vndrRequestMap);
	//String validKeyRequest = validRequiredKeyVndr(vndrJson,netwinCustomerDetails);
		
		
		return result;
	}

	
	/*
	 * private String validRequiredKeyVndr(String vndrJson,NetwinCustomerDetails
	 * netwinCustomerDetails) {
	 * 
	 * List<Map<String, Object>> netwinFieldResults2 =
	 * jdbcTemplate.queryForList(QueryUtil.NETWNWITHVNDRFIELDQUERY,
	 * netwinCustomerDetails.getNetwVndrs(), "A");
	 * System.out.println(netwinFieldResults2);
	 * if(netwinFieldResults2.get("VNDRREQKEYREQ")=="Y" &&
	 * vendorField.containsKey(ConstantVariable.VNDRFIELDCOLUMN)) {
	 * 
	 * }
	 * 
	 * return null; }
	 */
	
	private Map<String, String> vendorRequestMap(CustomerVendorDetailsDto customerVendorDetailsDto,String reqStatus) {
		List<Map<String, Object>> netwinFieldResults2 =null;
		if(reqStatus=="V") {
		netwinFieldResults2 = jdbcTemplate.queryForList(QueryUtil.NETWNWITHVNDRFIELDQUERY, customerVendorDetailsDto.getVendorId(), "A","V");
		}else {
			netwinFieldResults2 = jdbcTemplate.queryForList(QueryUtil.NETWNWITHVNDRFIELDQUERY, customerVendorDetailsDto.getVendorId(), "A","O");
	
		}
		
		Map<String, String> validationNetVn = new HashMap<>();
	    for (Map<String, Object> vendorField : netwinFieldResults2) {
	        String key1 = (String) vendorField.get(ConstantVariable.NTWNFIELDCOLUMN);
	        if (vendorField.containsKey(ConstantVariable.VNDRFIELDCOLUMN)) {
	            String value1 = (String) vendorField.get(ConstantVariable.VNDRFIELDCOLUMN);
	            validationNetVn.put(key1, value1);
	        }
	        
	    }
	    return validationNetVn;
		
	}

}
