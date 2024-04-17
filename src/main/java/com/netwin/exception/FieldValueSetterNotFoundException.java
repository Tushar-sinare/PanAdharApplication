package com.netwin.exception;

public class FieldValueSetterNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;

	public FieldValueSetterNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
