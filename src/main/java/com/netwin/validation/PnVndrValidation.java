package com.netwin.validation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.netwin.entiry.PnRequest;
import com.netwin.entiry.PnVendorDetails;
import com.netwin.entiry.Result;
import com.netwin.service.ErrorApplicationService;
import com.netwin.util.ConstantVariable;
import com.netwin.util.QueryUtil;

@Component
public class PnVndrValidation {
	private static final Logger logger = LoggerFactory.getLogger(PnVndrValidation.class);
	

	private final JdbcTemplate jdbcTemplate;

private final ErrorApplicationService errorApplicationService;
@Autowired
public PnVndrValidation(JdbcTemplate jdbcTemplate,ErrorApplicationService errorApplicationService) {

	this.jdbcTemplate=jdbcTemplate;
	this.errorApplicationService=errorApplicationService;
}
public Result<PnRequest> checkMappingVendor(PnRequest pnReq, Map<String, String> pnRequestDecrypt) {
    int pnVendorId = getPnVendorId(pnReq);
    if (pnVendorId == 0) {
        errorApplicationService.storeError(401, "Please Required Vendor Details ");
        return new Result<>("Please Required Vendor Details ");
    }

    Map<String, String> validationNetVn = getValidationNetVn(pnVendorId);
    Map<String, String> validationNetVn1 = getValidationNetVn1(pnVendorId);
    Map<String, String> vendorValue = getVendorValue(validationNetVn, pnRequestDecrypt);

    return validateVendorValues(validationNetVn1, vendorValue);
}

private int getPnVendorId(PnRequest pnReq) {
    PnVendorDetails pnVendorDetails = pnReq.getPnVendorDetails();
    if (pnVendorDetails == null) {
        errorApplicationService.storeError(401, "Vendor details not available required Product Id");
        return 0;
    } else {
        return pnVendorDetails.getPnVnDrSrNo();
    }
}

private Map<String, String> getValidationNetVn(int pnVendorId) {
    List<Map<String, Object>> netwinFieldResults2 = jdbcTemplate.queryForList(QueryUtil.NETWNWITHVNDRFIELDQUERY, pnVendorId, "P");
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

private Map<String, String> getValidationNetVn1(int pnVendorId) {
    List<Map<String, Object>> pnVendorResults = jdbcTemplate.queryForList(QueryUtil.PNVNDRFIELD, pnVendorId, "P", "Y");
    Map<String, String> validationNetVn1 = new HashMap<>();
    for (Map<String, Object> vendorField : pnVendorResults) {
        String key1 = (String) vendorField.get(ConstantVariable.NTWNFIELDCOLUMN);
        if (vendorField.containsKey(ConstantVariable.VNDRFIELDCOLUMN)) {
            String value1 = (String) vendorField.get(ConstantVariable.VNDRFIELDCOLUMN);
            validationNetVn1.put(key1, value1);
        }
    }
    return validationNetVn1;
}

private Map<String, String> getVendorValue(Map<String, String> validationNetVn, Map<String, String> pnRequestDecrypt) {
    Map<String, String> vendorValue = new HashMap<>();
    for (Map.Entry<String, String> row : validationNetVn.entrySet()) {
        for (Map.Entry<String, String> val : pnRequestDecrypt.entrySet()) {
            String rowKey = row.getKey();
            String valKey = val.getKey();
            if (rowKey.equals(valKey)) {
                vendorValue.put(row.getValue(), val.getValue());
            }
        }
    }
    return vendorValue;
}

private Result<PnRequest> validateVendorValues(Map<String, String> validationNetVn1, Map<String, String> vendorValue) {
    for (Map.Entry<String, String> val : validationNetVn1.entrySet()) {
        if (!vendorValue.containsKey(val.getValue())) {
            logger.error(String.format("%s%s%s", ConstantVariable.RETURNSTR, val.getValue(), ConstantVariable.RETURNSTR1));
            errorApplicationService.storeError(401, ConstantVariable.RETURNSTR + val.getValue() + ConstantVariable.RETURNSTR1);
            return new Result<>(ConstantVariable.RETURNSTR + val.getValue() + ConstantVariable.RETURNSTR1);
        }
    }
    return new Result<>(vendorValue);
}

}
