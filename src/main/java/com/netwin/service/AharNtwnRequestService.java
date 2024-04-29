package com.netwin.service;


import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.dozer.MappingException;
import org.json.JSONException;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Lazy
public interface AharNtwnRequestService {

	String callAharRequest(String aharJson, String reqStatus) throws JsonMappingException, JsonProcessingException, DataAccessException, InvalidKeyException, MappingException, JSONException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException;

}
