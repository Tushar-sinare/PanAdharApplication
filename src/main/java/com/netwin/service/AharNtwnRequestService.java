package com.netwin.service;

import org.springframework.context.annotation.Lazy;

import com.netwin.exception.AESOperationException;
@Lazy
public interface AharNtwnRequestService {

	String callPanRequest(String aharJson, String clientIp) throws AESOperationException;

}
