package com.netwin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.netwin.dto.CustomerVendorDetailsDto;

public interface AharVndrRequestService {



	String callVenderRequest(String vendorRequestJson, CustomerVendorDetailsDto customerVendorDetailsDto,String reqStatus) throws JsonMappingException, JsonProcessingException;

}
