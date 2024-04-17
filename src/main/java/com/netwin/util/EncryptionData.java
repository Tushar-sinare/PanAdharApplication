package com.netwin.util;




import org.springframework.stereotype.Component;

@Component
public class EncryptionData {

public String getEncryptResponse(String response) throws Exception {
String strEncrypt = null;

		strEncrypt = AESExample.encrypt(response,ConstantVariable.SECRETEKEY);
	
	
	return strEncrypt;
}
}
