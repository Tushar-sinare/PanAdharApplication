package com.netwin.service;


import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.netwin.dto.CustomerVendorDetailsDto;

public interface PnVndrRequestService {

	//Result fetchPnVndrRequest(PnRequest pnRequest2,Map<String, String> pnRequestDecrypt);

	String callVenderRequest(String vendorRequestJson, CustomerVendorDetailsDto customerVendorDetailsDto) throws JsonMappingException, JsonProcessingException;


}
