package com.netwin.validation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.netwin.dto.CustomerVendorDetailsDto;
import com.netwin.util.ConstantVariable;
import com.netwin.util.QueryUtil;
import com.netwin.util.VendorFieldMapping;

@Component
public class GtVndrValidation {
	private VendorFieldMapping vendorFieldMapping;
private JdbcTemplate jdbcTemplate;

@Autowired
public GtVndrValidation(JdbcTemplate jdbcTemplate,VendorFieldMapping vendorFieldMapping) {

	this.vendorFieldMapping=vendorFieldMapping;
	this.jdbcTemplate=jdbcTemplate;
}

public String VendorRequestValidation(JsonNode jsonNode, CustomerVendorDetailsDto customerVendorDetailsDto) {
	String str1 = jsonNode.toString();
	String result =null;
	Map<String,String> vndrRequestMap = vendorRequestMap(customerVendorDetailsDto);
	
result= vendorFieldMapping.replaceKeys(str1,vndrRequestMap);
//String validKeyRequest = validRequiredKeyVndr(vndrJson,netwinCustomerDetails);
	
	
	return result;
}

private Map<String, String> vendorRequestMap(CustomerVendorDetailsDto customerVendorDetailsDto) {
	List<Map<String, Object>> netwinFieldResults2 =null;

	netwinFieldResults2 = jdbcTemplate.queryForList(QueryUtil.NETWNWITHVNDRFIELDQUERY, customerVendorDetailsDto.getVendorId(), "G","V");

	
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
