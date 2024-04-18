package com.netwin.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.netwin.entiry.AharVendorDetails;
import com.netwin.entiry.PnVendorDetails;
import com.netwin.repo.AharVendorDetailsRepo;
import com.netwin.service.AharVendorDetailsService;
@Service
public class AharVendorDetailsServiceImpl implements AharVendorDetailsService{
private AharVendorDetailsRepo aharVendorDetailsRepo;
public AharVendorDetailsServiceImpl(AharVendorDetailsRepo aharVendorDetailsRepo) {
	this.aharVendorDetailsRepo = aharVendorDetailsRepo;
}

	@Override
	public Optional<AharVendorDetails> fetchPnVendorDetails(int netwVndrs) {
		
		return aharVendorDetailsRepo.findById(netwVndrs);
	}

}
