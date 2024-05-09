package com.netwin.service;

import java.util.Optional;

import org.springframework.context.annotation.Lazy;

import com.netwin.entity.AharVndrDetails;
@Lazy
public interface AharVendorDetailsService {

	Optional<AharVndrDetails> fetchPnVendorDetails(int netwVndrs);

}
