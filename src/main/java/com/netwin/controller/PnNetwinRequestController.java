package com.netwin.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.netwin.exception.ResourceNotFoundException;
import com.netwin.service.PnNetwinRequestService;


import jakarta.servlet.http.HttpServletRequest;

@RestController
public class PnNetwinRequestController {
	

	private PnNetwinRequestService pnNetwinRequestService;

	@PostMapping("/pnrequest")
	public ResponseEntity<String> callPanRequest(@RequestBody(required = false) String panRequestJson, HttpServletRequest request)
			throws Exception {

		String clientIp = request.getRemoteAddr();

		try {
			
				String pnRequestStr = pnNetwinRequestService.callPanRequest(panRequestJson, clientIp);
			return new ResponseEntity<String>(pnRequestStr, HttpStatus.ACCEPTED);
			
		} catch (Exception ex) {
			throw new ResourceNotFoundException("Json is Empty","",HttpStatus.BAD_GATEWAY);
		}

		//return new ResponseEntity<String>("Json is Empty", HttpStatus.BAD_GATEWAY);

	}
}
