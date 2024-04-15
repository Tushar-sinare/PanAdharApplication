package com.netwin.service.impl;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netwin.entiry.PnRequest;
import com.netwin.entiry.PnResponse;
import com.netwin.entiry.PnVndrResponse;
import com.netwin.repo.PnRequestRepo;
import com.netwin.service.PnResponseService;
import com.netwin.util.QueryUtil;
@Service
public class PnResponseServiceImpl implements PnResponseService {
	@Autowired
	PnRequestRepo pnResponseRepo;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private QueryUtil queryUtil;
	@Override
	public  Map<String,Object> fetchNetwinResponse(PnVndrResponse pnVndrResponse, PnRequest pnRequest2) throws JsonMappingException, JsonProcessingException {
		PnResponse pnResponse = new PnResponse();
		Date date = new Date(System.currentTimeMillis());
		pnResponse.setAppDate(date);
		pnResponse.setCustId(pnRequest2.getCustId());
		pnResponse.setPanNo(pnRequest2.getPanNo());
        pnResponse.setPnVndrResponse(pnVndrResponse);
		
		
		Map<String,Object> pnResNtw = fetchResMapping(pnVndrResponse,pnResponse,pnRequest2);
		
		
		
		return pnResNtw;
	}
	public Map<String,Object> fetchResMapping(PnVndrResponse pnVndrResponse, PnResponse pnResponse, PnRequest pnRequest2) throws JsonMappingException, JsonProcessingException {
		int vendorId = pnRequest2.getPnVendorDetails().getPnVnDrSrNo();
		Map<String, String> validationNetVn = new HashMap<>();
		Map<String, Object> vendorValue = new HashMap<>();
		Map<String,Object> pnResValue = new HashMap<>();
		
		List<Map<String, Object>> netwinFieldResults = jdbcTemplate.queryForList(queryUtil.VNDRRESFIELDQUERY, vendorId,
				"P", "Y");
		/*
		 * List<String> netwinResFieldResults =
		 * jdbcTemplate.queryForList(queryUtil.VENDORRESFIELDQUERY, String.class, "P",
		 * "Y", "V"); List<String> netwinResFieldResults1 =
		 * jdbcTemplate.queryForList(queryUtil.NETWINRESFIELDQUERY11, String.class, "P",
		 * "V");
		 */
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
		Map<String, String> resultVOMap = (Map<String, String>) dataMap.get("resultVO");
		for (Map.Entry<String, String> vendorField : validationNetVn.entrySet()) {
			if(dataMap.containsKey(vendorField.getValue())) {
				vendorValue.put(vendorField.getKey(), dataMap.get(vendorField.getValue()));
			}else if(resultVOMap.containsKey(vendorField.getValue())) {
				//vendorValue.put(vendorField.getKey(), (String) dataMap.get(vendorField.getValue()));
				pnResValue.put(vendorField.getKey(),dataMap.get(vendorField.getValue()));
			}
				if(dataMap.containsKey("resultVO")) {
					vendorValue.put("resultVO", pnResValue);
				}

				
			}
		return vendorValue;

				
			}
		

}
