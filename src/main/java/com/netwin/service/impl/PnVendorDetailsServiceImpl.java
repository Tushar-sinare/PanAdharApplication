package com.netwin.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.netwin.entiry.PnVendorDetails;
import com.netwin.repo.PnVendorDetailsRepo;
import com.netwin.service.PnVendorDetailsService;

@Service
public class PnVendorDetailsServiceImpl implements PnVendorDetailsService {

	PnVendorDetailsRepo pnVendorDetailsRepo;

	@Override
	public Optional<PnVendorDetails> fetchPnVendorDetails(int pnVndrSrNo) {
		return pnVendorDetailsRepo.findById(pnVndrSrNo);

	}

}
