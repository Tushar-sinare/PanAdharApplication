package com.netwin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.netwin.service.ErrorApplicationService;
import com.netwin.service.PnNetwinRequestService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class PnNetwinRequestController {

	// Add this annotation to inject the service
	private PnNetwinRequestService pnNetwinRequestService;
private ErrorApplicationService errorApplicationService;
	@Autowired
	public PnNetwinRequestController(PnNetwinRequestService pnNetwinRequestService,ErrorApplicationService errorApplicationService) {
		this.pnNetwinRequestService = pnNetwinRequestService;
		this.errorApplicationService = errorApplicationService;
	}

	@PostMapping("/pnrequest")
	public ResponseEntity<String> callPanRequest(@RequestBody String panRequestJson, HttpServletRequest request) {
		String clientIp = request.getRemoteAddr();
		String retrnStr = "Json is Empty";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		try {
			if (panRequestJson != null && !panRequestJson.isEmpty()) {
				String pnRequestStr = pnNetwinRequestService.callPanRequest(panRequestJson, clientIp);
				retrnStr = pnRequestStr;
				status = HttpStatus.BAD_REQUEST;
			}
		} catch (Exception ex) {
			 errorApplicationService.storeError(502,ex.getMessage());
			 retrnStr = ex.getMessage();
				status = HttpStatus.BAD_REQUEST;
			 
		}

		return new ResponseEntity<String>(retrnStr, status);
	}
	
	
}
