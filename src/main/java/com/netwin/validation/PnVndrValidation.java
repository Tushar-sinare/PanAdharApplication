package com.netwin.validation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.netwin.entiry.PnNetwinRequest;
import com.netwin.entiry.PnRequest;
import com.netwin.entiry.PnVendorDetails;
import com.netwin.entiry.Result;
import com.netwin.service.ErrorApplicationService;
import com.netwin.service.impl.PnVndrRequestServiceImpl;
import com.netwin.util.QueryUtil;

@Component
public class PnVndrValidation {
	private static final Logger logger = LoggerFactory.getLogger(PnVndrValidation.class);
	@Autowired
	private QueryUtil queryUtil;
	@Autowired
	private JdbcTemplate jdbcTemplate;
@Autowired
ErrorApplicationService errorApplicationService;
	public Result<PnRequest> checkMappingVendor(PnNetwinRequest pnRequestId, PnRequest pnReq,
			Map<String, String> pnRequestDecrypt) {
		Map<String, String> validationNetVn = new HashMap<>();
		Map<String, String> validationNetVn1 = new HashMap<>();
		Map<String, String> vendorValue = new HashMap<>();
		int pnVendorId =0;
		PnVendorDetails pnVendorDetails = pnReq.getPnVendorDetails();
		if(pnVendorDetails == null) {
			errorApplicationService.storeError(401, "Vendor details not available required Product Id");
		return new Result<>("Vendor details not available required Product Id");
		}else {
		pnVendorId = pnVendorDetails.getPnVnDrSrNo();
		}
		List<Map<String, Object>> pnVendorResults=null;
		List<Map<String, Object>> netwinFieldResults2 =null;
		Map<String,Map<String, String>> validationNetVn11 = new HashMap<>();
	if(pnVendorId!=0) {
		pnVendorResults = jdbcTemplate.queryForList(queryUtil.PNVNDRPARAMETER, pnVendorId,
				"P", "Y");
		netwinFieldResults2 = jdbcTemplate.queryForList(queryUtil.NETWINFIELDQUERY2,pnVendorId, "P");
	}else {
	
		errorApplicationService.storeError(401, "Please Required Vendor Details ");
		return new Result<> ("Please Required Vendor Details ");
	}

		for (Map<String, Object> vendorField : netwinFieldResults2) {
			for (Map.Entry<String, Object> vendorEntry : vendorField.entrySet()) {
				String key1 = (String) vendorField.get("NETWREQKEYNAME");
				 
				if (vendorEntry.getKey().contains("VNDRREQKEYNAME")) {
					String value1 = (String) vendorEntry.getValue();

					validationNetVn.put(key1, value1);
					validationNetVn11.put(key1, validationNetVn);
				}
			}
		}
		
		System.out.println("netwinFieldResults2 -----"+netwinFieldResults2);
		System.out.println("validationNetVn -----"+validationNetVn+"required -- "+validationNetVn11);
		for (Map<String, Object> vendorField : pnVendorResults) {
			for (Map.Entry<String, Object> vendorEntry : vendorField.entrySet()) {
				String key1 = (String) vendorField.get("NETWREQKEYNAME");
				if (vendorEntry.getKey().contains("VNDRREQKEYNAME")) {
					String value1 = (String) vendorEntry.getValue();

					validationNetVn1.put(key1, value1);

				}
			}
		}
		
		for (Map.Entry<String, String> row : validationNetVn.entrySet()) {
			for (Map.Entry<String, String> val : pnRequestDecrypt.entrySet()) {
				String rowKey = row.getKey();
				String valKey = val.getKey();

				if (rowKey.equals(valKey)) {
				
					vendorValue.put(row.getValue(), val.getValue());
				}

			}
		}
	
		for (Map.Entry<String, String> val : validationNetVn1.entrySet()) {
			if(!vendorValue.containsKey(val.getValue())) {
				 logger.error("Please Required "+val.getValue() +" this field");
				errorApplicationService.storeError(401,"Please Required "+val.getValue() +" this field");
				return new Result<> ("Please Required "+val.getValue() +" this field");
		}
		}
		return new Result<>(vendorValue);
	

	}
}
