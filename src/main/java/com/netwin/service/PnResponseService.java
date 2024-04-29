package com.netwin.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.netwin.dto.CustomerVendorDetailsDto;

public interface PnResponseService {

	//Map<String, Object> fetchNetwinResponse(PnVndrResponse pnResponse, PnRequest pnRequest2) throws  JsonProcessingException;

	String customerResponseMapping(String vndrResponseStr, CustomerVendorDetailsDto customerVendorDetailsDto) throws JsonMappingException, JsonProcessingException;

}
