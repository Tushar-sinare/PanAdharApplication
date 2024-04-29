package com.netwin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.netwin.service.ErrorApplicationService;
import com.netwin.service.PnNetwinRequestService;

@RestController
public class PnNetwinRequestController {

	// Add this annotation to inject the service
	private PnNetwinRequestService pnNetwinRequestService;
	private ErrorApplicationService errorApplicationService;

	@Autowired
	public PnNetwinRequestController(PnNetwinRequestService pnNetwinRequestService,
			ErrorApplicationService errorApplicationService) {
		this.pnNetwinRequestService = pnNetwinRequestService;
		this.errorApplicationService = errorApplicationService;
	}

	@PostMapping("/pnrequest")
	public ResponseEntity<String> callPanRequest(@RequestBody String panRequestJson) {
		String retrnStr = "Required request body is missing";
		HttpStatus status = HttpStatus.BAD_GATEWAY;
		try {
			if (panRequestJson != null && !panRequestJson.isEmpty()) {
				String pnRequestStr = pnNetwinRequestService.callPanRequest(panRequestJson);
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
			retrnStr =ex.getMessage();
			status = HttpStatus.BAD_GATEWAY;

		}

		return new ResponseEntity<String>(retrnStr, status);
	}

}
