package com.netwin.service;

import org.springframework.context.annotation.Lazy;

import com.netwin.entiry.AharRequest;
@Lazy
public interface AharRequestService {

	AharRequest callVendorService(AharRequest aharRequestObj);

}
