package com.netwin.service;

import java.util.Optional;

import org.springframework.context.annotation.Lazy;

import com.netwin.entiry.AharVendorDetails;
@Lazy
public interface AharVendorDetailsService {

	Optional<AharVendorDetails> fetchPnVendorDetails(int netwVndrs);

}
