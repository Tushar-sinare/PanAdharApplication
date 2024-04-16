package com.netwin.service;

import java.util.Optional;

import com.netwin.entiry.PnVendorDetails;

public interface PnVendorDetailsService {

	Optional<PnVendorDetails> fetchPnVendorDetails(int pnVndrSrNo);

}
