package com.netwin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.netwin.dto.CustomerVendorDetailsDto;
import com.netwin.service.PnResponseService;
import com.netwin.util.ConstantVariable;
import com.netwin.util.QueryUtil;
import com.netwin.util.VendorFieldMapping;
@Service
public class PnResponseServiceImpl implements PnResponseService {


	private JdbcTemplate jdbcTemplate;
private VendorFieldMapping vendorFieldMapping; 
	@Autowired
	public PnResponseServiceImpl(JdbcTemplate jdbcTemplate,VendorFieldMapping vendorFieldMapping) {
	
		this.jdbcTemplate =jdbcTemplate;
this.vendorFieldMapping = vendorFieldMapping;
	}
	
	@Override
	public String customerResponseMapping(String vndrResponseStr, CustomerVendorDetailsDto customerVendorDetailsDto) throws JsonMappingException, JsonProcessingException{
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(vndrResponseStr);
		
		Object id1 = customerVendorDetailsDto.getPnReqMasSrNo();
		((ObjectNode) jsonNode).put("userUuid", id1.toString());
		List<Map<String, Object>> netwinFieldResults2=null;
	
			netwinFieldResults2 = jdbcTemplate.queryForList(QueryUtil.NETWNWITHVNDRRESPQUERY, customerVendorDetailsDto.getVendorId(), "P");
		
			Map<String, String> validationNetVn = new HashMap<>();
			  for (Map<String,Object> vendorField : netwinFieldResults2) { 
				  String key1 = (String)vendorField.get(ConstantVariable.VNDRRESCOLUMN);
			  if(vendorField.containsKey(ConstantVariable.NTWNRESCOLUMN))
			  { 
			 String value1 =(String) vendorField.get(ConstantVariable.NTWNRESCOLUMN);
			  validationNetVn.put(key1,value1); 
			  }
			  }
			String result= vendorFieldMapping.replaceKeys1(jsonNode.toString(),validationNetVn);
		    
		    return result;
			
	}
		

}
