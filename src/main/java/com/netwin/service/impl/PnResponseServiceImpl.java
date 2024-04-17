package com.netwin.service.impl;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netwin.entiry.PnRequest;
import com.netwin.entiry.PnResponse;
import com.netwin.entiry.PnVndrResponse;
import com.netwin.service.PnResponseService;
import com.netwin.util.ConstantVariable;
import com.netwin.util.QueryUtil;
@Service
public class PnResponseServiceImpl implements PnResponseService {


	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public PnResponseServiceImpl(JdbcTemplate jdbcTemplate) {
	
		this.jdbcTemplate =jdbcTemplate;

	}
	@Override
	public  Map<String,Object> fetchNetwinResponse(PnVndrResponse pnVndrResponse, PnRequest pnRequest2) throws JsonProcessingException {
		PnResponse pnResponse = new PnResponse();
		Date date = new Date(System.currentTimeMillis());
		pnResponse.setAppDate(date);
		pnResponse.setCustId(pnRequest2.getCustId());
		pnResponse.setPanNo(pnRequest2.getPanNo());
        pnResponse.setPnVndrResponse(pnVndrResponse);
		
		
        return fetchResMapping(pnVndrResponse,pnRequest2);
	
	}
	public Map<String,Object> fetchResMapping(PnVndrResponse pnVndrResponse, PnRequest pnRequest2) throws JsonProcessingException {
		int vendorId = pnRequest2.getPnVendorDetails().getPnVnDrSrNo();
		
		Map<String, String> validationNetVn = new HashMap<>();
		Map<String, Object> vendorValue = new HashMap<>();
		Map<String,Object> pnResValue = new HashMap<>();
		
		List<Map<String, Object>> netwinFieldResults = jdbcTemplate.queryForList(QueryUtil.vndrRespFieldQuery, vendorId,
				"P", "Y");
	
		for (Map<String, Object> vendorField : netwinFieldResults) {
			for (Map.Entry<String, Object> vendorEntry : vendorField.entrySet()) {
				String key1 = (String) vendorField.get("NETWRESKEYNAME");
				if (vendorEntry.getKey().contains("VNDRRESKEYNAME")) {
					String value1 = (String) vendorEntry.getValue();

					validationNetVn.put(key1, value1);

				}
			}
		}
		String response = pnVndrResponse.getReqDecrypt();
	
		
				ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> dataMap = mapper.readValue(response, Map.class);
		Map<String, String> resultVOMap = (Map<String, String>) dataMap.get(ConstantVariable.resultVo);
		
		
		for (Map.Entry<String, String> vendorField : validationNetVn.entrySet()) {
			if(dataMap.containsKey(vendorField.getValue())) {
				vendorValue.put(vendorField.getKey(), dataMap.get(vendorField.getValue()));
			}else if(resultVOMap.containsKey(vendorField.getValue())) {
					pnResValue.put(vendorField.getKey(),dataMap.get(vendorField.getValue()));
			}
				if(dataMap.containsKey(ConstantVariable.resultVo)) {
					vendorValue.put(ConstantVariable.resultVo, pnResValue);
				}

				
			}
		
		return vendorValue;

				
			}
		

}
