package com.netwin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.netwin.exception.PnNetwinRequestException;

public interface PnNetwinRequestService {

	String callPanRequest(String panRequest) throws PnNetwinRequestException, JsonMappingException, JsonProcessingException;

}
