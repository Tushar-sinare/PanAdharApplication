package com.netwin.controller;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netwin.service.ErrorApplicationService;
import com.netwin.service.GSTNtwnRequestService;
import com.netwin.service.PnNetwinRequestService;
import com.netwin.util.EncryptionAndDecryptionData;
import com.netwin.util.GtNtResponse;
import com.netwin.util.NtResponse;

@RestController
@RequestMapping("api/v2")
public class GtNtwnRequestController {
	private GSTNtwnRequestService gstNtwnRequestService;
	private ErrorApplicationService errorApplicationService;
	private GtNtResponse gtResponse;
	private EncryptionAndDecryptionData encryptionAndDecryptionData;
	 @Autowired
	 public GtNtwnRequestController(GSTNtwnRequestService gstNtwnRequestService,ErrorApplicationService errorApplicationService,GtNtResponse gtResponse,EncryptionAndDecryptionData encryptionAndDecryptionData) {
		 this.gstNtwnRequestService=gstNtwnRequestService;
		 this.errorApplicationService =errorApplicationService;
		 this.gtResponse= gtResponse;
		 this.encryptionAndDecryptionData = encryptionAndDecryptionData;
	 }
	@PostMapping("/gstrequest")
	public ResponseEntity<String> callPanRequest(@RequestBody String gstRequestJson)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		String retrnStr = "Required request body is missing";
		HttpStatus status = HttpStatus.BAD_GATEWAY;
		try {
			if (gstRequestJson != null && !gstRequestJson.isEmpty()) {
				String pnRequestStr = gstNtwnRequestService.callGSTRequest(gstRequestJson);
				retrnStr = pnRequestStr;
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception ex) {
			StackTraceElement[] stackTrace = ex.getStackTrace();
			String className = null;
			String methodName = null;
			int lineNumber = 0;
			if (stackTrace.length > 0) {
				StackTraceElement stackTraceElement = stackTrace[0];
				className = stackTraceElement.getClassName();
				methodName = stackTraceElement.getMethodName();
				lineNumber = stackTraceElement.getLineNumber();
			}
			errorApplicationService.storeError(502, ex.getMessage(), lineNumber, className, methodName);

			retrnStr = encryptionAndDecryptionData.getEncryptResponse(gtResponse.getNtResponse(500, ex.getMessage()));
			status = HttpStatus.BAD_GATEWAY;

		}

		return new ResponseEntity<String>(retrnStr, status);
	}

}
