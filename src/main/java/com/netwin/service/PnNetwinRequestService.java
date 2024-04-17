package com.netwin.service;

import com.netwin.exception.PnNetwinRequestException;

public interface PnNetwinRequestService {

	String callPanRequest(String panRequest, String clientIp) throws PnNetwinRequestException;

}
