package com.netwin.exception;

public class PnNetwinRequestException extends Exception {
  
	private static final long serialVersionUID = 1L;

	public PnNetwinRequestException(String message) {
        super(message);
    }

    public PnNetwinRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}