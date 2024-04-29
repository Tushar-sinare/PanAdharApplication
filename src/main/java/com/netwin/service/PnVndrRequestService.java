package com.netwin.service;




import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.netwin.dto.CustomerVendorDetailsDto;

public interface PnVndrRequestService {

	//Result fetchPnVndrRequest(PnRequest pnRequest2,Map<String, String> pnRequestDecrypt);

	String callVenderRequest(String vendorRequestJson, CustomerVendorDetailsDto customerVendorDetailsDto) throws JsonMappingException, JsonProcessingException;


}
