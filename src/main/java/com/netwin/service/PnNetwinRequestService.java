package com.netwin.service;


import com.fasterxml.jackson.core.JsonProcessingException;


public interface PnNetwinRequestService {

	String callPanRequest(String panRequest) throws JsonProcessingException;

}
