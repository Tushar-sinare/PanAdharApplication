package com.netwin.validation;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
@Component
public class AharRequestValidation {
	 private static final Pattern AADHAR_PATTERN = Pattern.compile("\\d{12}");

	    // Method to validate Aadhar number
	    public  boolean isValidAadhar(String aadharNumber) {
	        // Check if Aadhar number matches the pattern and is not null or empty
	        return aadharNumber != null && !aadharNumber.isEmpty() && AADHAR_PATTERN.matcher(aadharNumber).matches();
	              
	    }

}
