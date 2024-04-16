package com.netwin.exception;


import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RuntimeException {
	
	private final String resourceName;
	private final String fieldName;
	private final long fieldValue;
	private final HttpStatus code;
    private static final long serialVersionUID = 1L;


	public ResourceNotFoundException(String resourceName,String fieldName,long fieldValue,HttpStatus code) {
      
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.code = code;
    }
    

	public String getResourceName() {
		return resourceName;
	}

	

	public String getFieldName() {
		return fieldName;
	}

	

	public long getFieldValue() {
		return fieldValue;
	}

	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public HttpStatus getCode() {
		return code;
	}
	
}
