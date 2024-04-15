package com.netwin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netwin.entiry.PnVendorDetails;
import com.netwin.repo.PnVendorDetailsRepo;
import com.netwin.service.PnVendorDetailsService;

@Service
public class PnVendorDetailsServiceImpl implements PnVendorDetailsService{
	@Autowired
	PnVendorDetailsRepo pnVendorDetailsRepo;

	@Override
	public PnVendorDetails fetchPnVendorDetails(int pnVndrSrNo) {
		PnVendorDetails pnVendorDetails = pnVendorDetailsRepo.findById(pnVndrSrNo).get();
		return pnVendorDetails;
	}

}
