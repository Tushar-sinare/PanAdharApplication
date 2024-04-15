package com.netwin.validation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.netwin.entiry.PnRequest;
import com.netwin.entiry.Result;
import com.netwin.entiry.Result1;
import com.netwin.service.ErrorApplicationService;
import com.netwin.util.NtResponse;

@Component
public class PnRequestValidation {
	@Autowired
	private NtResponse ntResponse;
	@Autowired
	ErrorApplicationService errorApplicationService;
	private static final String PAN_PATTERN = "[A-Z]{5}[0-9]{4}[A-Z]{1}";

	public boolean checkValidation(PnRequest pnRequest) {

		return isValidPanNumber(pnRequest.getPanNo());

	}

	private boolean isValidPanNumber(String panNo) {
		if (panNo == null || panNo.isEmpty()) {
			return false;
		}
		Pattern pattern = Pattern.compile(PAN_PATTERN);
		Matcher matcher = pattern.matcher(panNo);
		return matcher.matches();
	}

	public Result<PnRequest> checkNetwnValidation(Map<String, String> pnRequestDecrypt,
			Map<String, Object> netwinFieldResults, PnRequest pnRequest) throws NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		for (Field field : PnRequest.class.getDeclaredFields()) {

			if (netwinFieldResults.containsKey(field.getName())
					&& (((String) netwinFieldResults.get(field.getName())).equals('Y'))
					&& pnRequestDecrypt.containsKey(field.getName())) {
				String fieldName = field.getName();
				String fieldValue = pnRequestDecrypt.get(fieldName);
				if (fieldValue == null && fieldValue.isEmpty() && fieldValue.isBlank()) {
					errorApplicationService.storeError(108, "Please " + fieldName + " is Empty insert the Value");
					return new Result<PnRequest>("Please " + fieldName + " is Empty insert the Value");
				}
			}

		}

		if (!checkValidation(pnRequest)) {
			Result1<Map<String, Object>> result = ntResponse.getNtResponse(2003);
			String resultPan = result.getResMap().toString();
			return new Result<>(resultPan);

		} else {
			return new Result<PnRequest>(pnRequest);
		}

	}
}
