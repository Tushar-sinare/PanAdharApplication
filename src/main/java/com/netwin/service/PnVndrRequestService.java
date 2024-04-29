package com.netwin.service;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.netwin.dto.CustomerVendorDetailsDto;
import com.netwin.entiry.Result;

public interface PnVndrRequestService {

	//Result fetchPnVndrRequest(PnRequest pnRequest2,Map<String, String> pnRequestDecrypt);

	String callVenderRequest(String vendorRequestJson, CustomerVendorDetailsDto customerVendorDetailsDto) throws JsonMappingException, JsonProcessingException;


}
