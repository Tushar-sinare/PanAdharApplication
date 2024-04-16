package com.netwin.exception;


import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RuntimeException {
	
	private String resourceName;
	private String fieldName;
	private long fieldValue;
	private HttpStatus code;
    private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String resourceName,String fieldName,long fieldValue) {
        super(String.format("%s Not Found With %s :%s", resourceName,fieldName,fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
	public ResourceNotFoundException(String resourceName,String fieldName,HttpStatus code) {
        super(String.format("%s Not Found With %s :%s", resourceName,fieldName,code));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.code = code;
    }
    public ResourceNotFoundException(){
        super("Resource not found !!");
    }

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public long getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(long fieldValue) {
		this.fieldValue = fieldValue;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public HttpStatus getCode() {
		return code;
	}
	public void setCode(HttpStatus code) {
		this.code = code;
	}
    
}
