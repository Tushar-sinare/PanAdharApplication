package com.netwin.util;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netwin.service.ErrorApplicationService;
@Component
public class EncryptionData {
	@Autowired
	ErrorApplicationService errorApplicationService;
	

public String getEncryptResponse(String response) throws Exception {
String strEncrypt = null;

		strEncrypt = AESExample.encrypt(response,ConstantVariable.SECRETKEY);
	
	
	return strEncrypt;
}
}
