package com.netwin.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netwin.entiry.PnNetwinRequest;
import com.netwin.entiry.PnRequest;
import com.netwin.entiry.PnResponse;
import com.netwin.entiry.PnVndrRequest;
import com.netwin.entiry.Result;
import com.netwin.entiry.Result1;
import com.netwin.repo.PnVndrRequestRepo;
import com.netwin.service.ErrorApplicationService;
import com.netwin.service.PnVndrRequestService;
import com.netwin.service.PnVndrResponseService;
import com.netwin.util.PnNetwinDecrypt;
import com.netwin.validation.PnVndrValidation;

@Service
public class PnVndrRequestServiceImpl implements PnVndrRequestService {

private final PnVndrValidation pnVndrValidation;

private final PnVndrResponseService pnVndrResponseService;

private final PnNetwinDecrypt pnNetwinDecrypt;

private final PnVndrRequestRepo pnVndrRequestRepo;

private final ErrorApplicationService errorApplicationService;
@Autowired
public PnVndrRequestServiceImpl(PnVndrValidation pnVndrValidation, PnVndrResponseService pnVndrResponseService,PnNetwinDecrypt pnNetwinDecrypt,PnVndrRequestRepo pnVndrRequestRepo,ErrorApplicationService errorApplicationService) {
	this.pnVndrValidation=pnVndrValidation;
	this.pnVndrResponseService=pnVndrResponseService;
	this.pnNetwinDecrypt =pnNetwinDecrypt;
	this.pnVndrRequestRepo= pnVndrRequestRepo;
	this.errorApplicationService = errorApplicationService;
}
private static final Logger logger = LoggerFactory.getLogger(PnVndrRequestServiceImpl.class);
	@Override
	public Result<String> fetchPnVndrRequest(PnRequest pnReqData,Map<String, String> pnRequestDecrypt) {
	
		PnNetwinRequest pnRequestId = pnReqData.getPnNetwinRequest();
		
		 Map<String,String> vendorValue=null;
		 //Check Vendor Mapping
		Result<PnRequest> result = pnVndrValidation.checkMappingVendor(pnRequestId,pnReqData,pnRequestDecrypt);
		if (result.isValid()) {
			vendorValue =result.getMap();
			//call Vendor Request Store method
			
			String pnVndrResponse = callVendorMethod(vendorValue,pnReqData);
			return new Result<>(pnVndrResponse);
			 } else {
			    String errorMessage = result.getErrorMessage();
			    // Handle the error message
			    logger.error(errorMessage);
			    errorApplicationService.storeError(109, errorMessage);
			  return new Result<>(errorMessage);
			}
		
	}
	private String callVendorMethod(Map<String, String> vendorValue, PnRequest pnRequest2) {
	
		PnVndrRequest pnVndrRequest=new PnVndrRequest();
		PnNetwinRequest pnNetwinRequest = pnRequest2.getPnNetwinRequest();
		pnVndrRequest.setCallingIpAdr(pnNetwinRequest.getCallingIpAdr());
		pnVndrRequest.setEntryDate(pnRequest2.getAppDate());
		pnVndrRequest.setReqDecrypt(vendorValue.toString());
		pnVndrRequest.setReqEncrypt(pnNetwinDecrypt.getPnRequestEncryptData(vendorValue).toString());
		PnVndrRequest pnVndrRequest2 = pnVndrRequestRepo.save(pnVndrRequest);
		//Call Vendor Api to fetch Response
		
		Result1<PnResponse> pnVndrApiResponse = pnVndrResponseService.fetchPanApiResponse(pnVndrRequest2,pnRequest2);
								
				return  pnVndrApiResponse.getResMap().toString();
		

	}

}
