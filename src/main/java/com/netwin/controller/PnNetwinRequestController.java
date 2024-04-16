package com.netwin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.netwin.service.PnNetwinRequestService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class PnNetwinRequestController {

	// Add this annotation to inject the service
	private final PnNetwinRequestService pnNetwinRequestService;

	@Autowired
	public PnNetwinRequestController(PnNetwinRequestService pnNetwinRequestService) {
		this.pnNetwinRequestService = pnNetwinRequestService;
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
			ex.printStackTrace();
		}

		return new ResponseEntity<String>("Json is Empty", HttpStatus.BAD_GATEWAY);
	}
}
