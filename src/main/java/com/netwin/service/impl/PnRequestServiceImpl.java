package com.netwin.service.impl;



import org.springframework.stereotype.Service;

import com.netwin.entiry.PnRequest;
import com.netwin.repo.PnRequestRepo;
import com.netwin.service.PnRequestService;

@Service
public class PnRequestServiceImpl implements PnRequestService {

	private PnRequestRepo pnRequestRepo;

	@Override
	public PnRequest callVendorService(PnRequest pnRequest) {

	 
		return pnRequestRepo.save(pnRequest);

	}

}
