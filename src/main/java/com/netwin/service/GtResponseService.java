package com.netwin.service;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.netwin.dto.CustomerVendorDetailsDto;


public interface GtResponseService {
	
	String customerResponseMapping(String vndrResponseStr, CustomerVendorDetailsDto customerVendorDetailsDto) throws JsonProcessingException;

}
