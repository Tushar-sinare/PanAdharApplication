package com.netwin.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.netwin.service.PnNetwinRequestService;

import com.netwin.util.PnNetwinDecrypt;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class PnNetwinRequestController {
	
	private PnNetwinDecrypt pnNetwinDecrypt;

	private PnNetwinRequestService pnNetwinRequestService;

	@PostMapping("/pnrequest")
	public ResponseEntity<String> callPanRequest(@RequestBody String panRequestJson, HttpServletRequest request)
			throws Exception {
		long startTime = System.currentTimeMillis();
		String clientIp = request.getRemoteAddr();

		try {
			if (panRequestJson != null && !panRequestJson.isEmpty()) {
				String pnRequestStr = pnNetwinRequestService.callPanRequest(panRequestJson, clientIp);
				long endTime = System.currentTimeMillis();

				long responseTime = endTime - startTime;
				System.err.println(responseTime);
				return new ResponseEntity<String>(pnRequestStr, HttpStatus.ACCEPTED);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new ResponseEntity<String>("Json is Empty", HttpStatus.BAD_GATEWAY);

	}
}
