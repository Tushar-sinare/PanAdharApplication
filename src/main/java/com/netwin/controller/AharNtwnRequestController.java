package com.netwin.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.netwin.service.AharNtwnRequestService;
import com.netwin.service.ErrorApplicationService;
import com.netwin.util.EncryptionAndDecryptionData;

@RestController
public class AharNtwnRequestController {
	private ErrorApplicationService errorApplicationService;
	private AharNtwnRequestService aharNtwnRequestService;
private EncryptionAndDecryptionData encryptionAndDecryptionData;
	@Lazy
	@Autowired
	public AharNtwnRequestController(AharNtwnRequestService aharNtwnRequestService,
			ErrorApplicationService errorApplicationService,EncryptionAndDecryptionData encryptionAndDecryptionData) {
		this.aharNtwnRequestService = aharNtwnRequestService;
		this.errorApplicationService = errorApplicationService;
		this.encryptionAndDecryptionData = encryptionAndDecryptionData;
	}
//HttpServletRequest Check mobile to need to change
	
	@PostMapping("/aharRequestVer")
	public ResponseEntity<String> callAharRequestVer(@RequestBody(required = false) String aharJson
			) {
		String reqStatus ="V";
		String retrnStr = "Required request body is missing";
		HttpStatus status = HttpStatus.BAD_GATEWAY;
		try {
			if (aharJson != null && !aharJson.isEmpty()) {
				String pnRequestStr = aharNtwnRequestService.callAharRequest(aharJson,reqStatus);
				retrnStr = pnRequestStr;
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception ex) {
			StackTraceElement[] stackTrace = ex.getStackTrace();
			  String className = null;
			    String methodName = null;
			    int lineNumber =0;
			if (stackTrace.length > 0) {
			    StackTraceElement stackTraceElement = stackTrace[0];
			   className = stackTraceElement.getClassName();
			    methodName = stackTraceElement.getMethodName();
			    lineNumber = stackTraceElement.getLineNumber();
			}

			errorApplicationService.storeError(502,ex.getMessage(), lineNumber, className, methodName);
			retrnStr =encryptionAndDecryptionData.getEncryptResponse(ex.getMessage());
			status = HttpStatus.BAD_GATEWAY;

		}
		aharNtwnRequestService = null;

		return new ResponseEntity<String>(retrnStr, status);
	}
	@PostMapping("/aharRequestOtp")
	public ResponseEntity<String> callAharRequest(@RequestBody(required = false) String aharJson) {
	String reqStatus ="O";
		String retrnStr = "Required request body is missing";
		HttpStatus status = HttpStatus.BAD_GATEWAY;
		try {
			if (aharJson != null && !aharJson.isEmpty()) {
				String pnRequestStr = aharNtwnRequestService.callAharRequest(aharJson,reqStatus);
				retrnStr = pnRequestStr;
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception ex) {
			StackTraceElement[] stackTrace = ex.getStackTrace();
			  String className = null;
			    String methodName = null;
			    int lineNumber =0;
			if (stackTrace.length > 0) {
			    StackTraceElement stackTraceElement = stackTrace[0];
			   className = stackTraceElement.getClassName();
			    methodName = stackTraceElement.getMethodName();
			    lineNumber = stackTraceElement.getLineNumber();
			}

			errorApplicationService.storeError(502,ex.getMessage(), lineNumber, className, methodName);
			retrnStr = ex.getMessage();
			
			status = HttpStatus.BAD_GATEWAY;

		}
		aharNtwnRequestService = null;

		return new ResponseEntity<String>(retrnStr, status);
	}
}
