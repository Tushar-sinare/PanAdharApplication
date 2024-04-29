package com.netwin.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.netwin.entiry.AharVndrDetails;
import com.netwin.repo.AharVndrDetailsRepo;
import com.netwin.service.AharVendorDetailsService;
@Service
public class AharVendorDetailsServiceImpl implements AharVendorDetailsService{
private AharVndrDetailsRepo aharVendorDetailsRepo;
public AharVendorDetailsServiceImpl(AharVndrDetailsRepo aharVendorDetailsRepo) {
	this.aharVendorDetailsRepo = aharVendorDetailsRepo;
}

	@Override
	public Optional<AharVndrDetails> fetchPnVendorDetails(int netwVndrs) {
		
		return aharVendorDetailsRepo.findById(netwVndrs);
	}

}
