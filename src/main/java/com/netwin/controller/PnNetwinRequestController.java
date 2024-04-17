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
	private final PnNetwinRequestService pnNetwinRequestService;
private final ErrorApplicationService errorApplicationService;
	@Autowired
	public PnNetwinRequestController(PnNetwinRequestService pnNetwinRequestService,ErrorApplicationService errorApplicationService) {
		this.pnNetwinRequestService = pnNetwinRequestService;
		this.errorApplicationService = errorApplicationService;
	}

	@PostMapping("/pnrequest")
	public ResponseEntity<String> callPanRequest(@RequestBody String panRequestJson, HttpServletRequest request) {
		String clientIp = request.getRemoteAddr();
		try {
			if (panRequestJson != null && !panRequestJson.isEmpty()) {
				String pnRequestStr = pnNetwinRequestService.callPanRequest(panRequestJson, clientIp);
				return new ResponseEntity<String>(pnRequestStr, HttpStatus.ACCEPTED);
			}
		} catch (Exception ex) {
			 errorApplicationService.storeError(502,ex.getMessage());
			 return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_GATEWAY);
			 
		}

		return new ResponseEntity<String>("Json is Empty", HttpStatus.BAD_REQUEST);
	}
}
