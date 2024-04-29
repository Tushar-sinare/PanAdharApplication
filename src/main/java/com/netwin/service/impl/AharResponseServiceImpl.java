package com.netwin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.netwin.dto.CustomerVendorDetailsDto;
import com.netwin.service.AharResponseService;
import com.netwin.util.ConstantVariable;
import com.netwin.util.QueryUtil;
import com.netwin.util.VendorFieldMapping;

@Service
public class AharResponseServiceImpl implements AharResponseService {
private JdbcTemplate jdbcTemplate;
private VendorFieldMapping vendorFieldMapping;
@Autowired
public AharResponseServiceImpl(JdbcTemplate jdbcTemplate,VendorFieldMapping vendorFieldMapping) {
	this.jdbcTemplate = jdbcTemplate;
	this.vendorFieldMapping=vendorFieldMapping;
}
	@Override
	public String customerResponseMapping(String vndrResponseStr, CustomerVendorDetailsDto customerVendorDetailsDto,String reqStatus) throws JsonMappingException, JsonProcessingException{
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode jsonNode = (ObjectNode)objectMapper.readTree(vndrResponseStr);
		
		Object id = customerVendorDetailsDto.getAhaReqMasSrNo();
		((ObjectNode) jsonNode).put("userUuid", id.toString());
		List<Map<String, Object>> netwinFieldResults2=null;
		if (reqStatus.equals("V")) {
			netwinFieldResults2 = jdbcTemplate.queryForList(QueryUtil.NETWNWITHVNDRRESPQUERY, customerVendorDetailsDto.getVendorId(), "AV");
		}else {
			netwinFieldResults2 = jdbcTemplate.queryForList(QueryUtil.NETWNWITHVNDRRESPQUERY, customerVendorDetailsDto.getVendorId(), "AO");
				
		}
			Map<String, String> validationNetVn = new HashMap<>();
			  for (Map<String,Object> vendorField : netwinFieldResults2) { 
				  String key1 = (String)vendorField.get(ConstantVariable.VNDRRESCOLUMN);
			  if(vendorField.containsKey(ConstantVariable.NTWNRESCOLUMN))
			  { 
			 String value1 =(String) vendorField.get(ConstantVariable.NTWNRESCOLUMN);
			  validationNetVn.put(key1,value1); 
			  }
			  }
			String result= vendorFieldMapping.replaceKeys(jsonNode.toString(),validationNetVn);
		    
		    return result;
			
		
	}

}
