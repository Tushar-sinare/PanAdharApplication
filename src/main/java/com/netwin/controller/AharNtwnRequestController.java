package com.netwin.controller;


import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netwin.service.AharNtwnRequestService;
import com.netwin.service.ErrorApplicationService;
import com.netwin.util.EncryptionAndDecryptionData;
import com.netwin.util.NtAharResponse;

@RestController
@RequestMapping("api/v2")
public class AharNtwnRequestController {
	private ErrorApplicationService errorApplicationService;
	private AharNtwnRequestService aharNtwnRequestService;
	private NtAharResponse ntAharResponse;
private EncryptionAndDecryptionData encryptionAndDecryptionData;
	@Lazy
	@Autowired
	public AharNtwnRequestController(AharNtwnRequestService aharNtwnRequestService,
			ErrorApplicationService errorApplicationService,EncryptionAndDecryptionData encryptionAndDecryptionData,NtAharResponse ntAharResponse) {
		this.aharNtwnRequestService = aharNtwnRequestService;
		this.errorApplicationService = errorApplicationService;
		this.encryptionAndDecryptionData = encryptionAndDecryptionData;
		this.ntAharResponse = ntAharResponse;
	}
//HttpServletRequest Check mobile to need to change
	
	@PostMapping("/aharRequestVer")
	public ResponseEntity<String> callAharRequestVer(@RequestBody(required = false) String aharJson
			) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
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
			retrnStr = encryptionAndDecryptionData.getEncryptResponse(ntAharResponse.getNtResponses(502,ex.getMessage()));
			status = HttpStatus.BAD_GATEWAY;
		

		}
		

		return new ResponseEntity<String>(retrnStr, status);
	}
	@PostMapping("/aharRequestOtp")
	public ResponseEntity<String> callAharRequest(@RequestBody(required = false) String aharJson) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
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
			retrnStr = encryptionAndDecryptionData.getEncryptResponse(ntAharResponse.getNtResponses(502,ex.getMessage()));
			status = HttpStatus.BAD_GATEWAY;

		}
		

		return new ResponseEntity<String>(retrnStr, status);
	}
}
