package com.netwin.service;



import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.context.annotation.Lazy;

import com.fasterxml.jackson.core.JsonProcessingException;


@Lazy
public interface AharNtwnRequestService {

	String callAharRequest(String aharJson, String reqStatus) throws JsonProcessingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException;

}
