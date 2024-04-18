package com.netwin.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netwin.entiry.PnRequest;
import com.netwin.repo.PnRequestRepo;
import com.netwin.service.PnRequestService;

@Service
public class PnRequestServiceImpl implements PnRequestService {

	private PnRequestRepo pnRequestRepo;
@Autowired
public PnRequestServiceImpl(PnRequestRepo pnRequestRepo) {
	this.pnRequestRepo=pnRequestRepo;
}
	@Override
	public PnRequest callVendorService(PnRequest pnRequest) {

	 
		return pnRequestRepo.save(pnRequest);

	}

}
