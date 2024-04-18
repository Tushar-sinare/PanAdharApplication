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
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class AharNtwnRequestController {
	private ErrorApplicationService errorApplicationService;
	private AharNtwnRequestService aharNtwnRequestService;

	@Lazy
	@Autowired
	public AharNtwnRequestController(AharNtwnRequestService aharNtwnRequestService,
			ErrorApplicationService errorApplicationService) {
		this.aharNtwnRequestService = aharNtwnRequestService;
		this.errorApplicationService = errorApplicationService;
	}

	@PostMapping("/aharRequest")
	public ResponseEntity<String> callAharRequest(@RequestBody(required = false) String aharJson,
			HttpServletRequest request) {
		String clientIp = request.getLocalAddr();
		String retrnStr = "Required request body is missing";
		HttpStatus status = HttpStatus.BAD_GATEWAY;
		try {
			if (aharJson != null && !aharJson.isEmpty()) {
				String pnRequestStr = aharNtwnRequestService.callPanRequest(aharJson, clientIp);
				retrnStr = pnRequestStr;
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception ex) {
			errorApplicationService.storeError(502, ex.getMessage());
			retrnStr = ex.getMessage();
			status = HttpStatus.BAD_GATEWAY;

		}
		aharNtwnRequestService = null;

		return new ResponseEntity<String>(retrnStr, status);
	}

}
