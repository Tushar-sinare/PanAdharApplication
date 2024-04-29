package com.netwin.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
@Component
public class PnRequestValidation {

	public boolean checkNetwnValidation(String panNo) {
		final String PAN_PATTERN = "[A-Z]{5}\\d{4}[A-Z]";
		  if (panNo == null) { 
			  return false; 
			  } 
		  Pattern pattern = Pattern.compile(PAN_PATTERN);
		  Matcher matcher =	  pattern.matcher(panNo); 
		  
		  return matcher.matches();
		  

		
	}

	/*
	 * public Result checkNetwnValidation(Map<String, String> pnRequestDecrypt,
	 * Map<String,Object> netwinFieldResults, PnRequest pnRequest){
	 * 
	 * for (Field field : PnRequest.class.getDeclaredFields()) {
	 * 
	 * if (netwinFieldResults.containsKey(field.getName())
	 * &&(((String)netwinFieldResults.get(field.getName())).equals('Y')) &&
	 * pnRequestDecrypt.containsKey(field.getName())) { String fieldName =
	 * field.getName(); String fieldValue = pnRequestDecrypt.get(fieldName); if
	 * (fieldValue == null) { errorApplicationService.storeError(108, "Please " +
	 * fieldName + " is Empty insert the Value", 0, null, null); return new
	 * Result("Please " + fieldName + " is Empty insert the Value"); } }
	 * 
	 * }
	 * 
	 * if (!checkValidation(pnRequest)) { Result1 result =
	 * ntResponse.getNtResponse(2003); String resultPan =
	 * result.getResMap().toString(); return new Result(resultPan);
	 * 
	 * } else { return new Result(pnRequest); }
	 * 
	 * }
	 */
}
