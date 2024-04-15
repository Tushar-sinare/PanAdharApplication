package com.netwin.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netwin.entiry.PnRequest;
import com.netwin.repo.PnRequestRepo;
import com.netwin.service.PnRequestService;

@Service
public class PnRequestServiceImpl implements PnRequestService {
	@Autowired
	private PnRequestRepo pnRequestRepo;

	@Override
	public PnRequest callVendorService(PnRequest pnRequest) {

		PnRequest pnRequest2 = pnRequestRepo.save(pnRequest);
		return pnRequest2;

	}

}
