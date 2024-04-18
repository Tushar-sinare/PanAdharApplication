package com.netwin.service.impl;

import org.springframework.stereotype.Service;

import com.netwin.entiry.AharRequest;
import com.netwin.repo.AharRequestRepo;
import com.netwin.service.AharRequestService;

@Service
public class AharRequestServiceImpl implements AharRequestService {
private AharRequestRepo aharRequestRepo;

public AharRequestServiceImpl(AharRequestRepo aharRequestRepo) {
	this.aharRequestRepo = aharRequestRepo;
}
	@Override
	public AharRequest callVendorService(AharRequest aharRequestObj) {
	
		return aharRequestRepo.save(aharRequestObj);
	}

}
