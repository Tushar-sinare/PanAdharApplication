package com.netwin.service;

import java.lang.reflect.InvocationTargetException;

import org.springframework.context.annotation.Lazy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.netwin.exception.AESOperationException;
@Lazy
public interface AharNtwnRequestService {

	String callAharRequest(String aharJson, String reqStatus) throws AESOperationException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, JsonMappingException, JsonProcessingException;

}
