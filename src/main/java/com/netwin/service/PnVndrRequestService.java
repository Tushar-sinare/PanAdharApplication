package com.netwin.service;




import com.fasterxml.jackson.core.JsonProcessingException;
import com.netwin.dto.CustomerVendorDetailsDto;

public interface PnVndrRequestService {



	String callVenderRequest(String vendorRequestJson, CustomerVendorDetailsDto customerVendorDetailsDto) throws JsonProcessingException;


}
