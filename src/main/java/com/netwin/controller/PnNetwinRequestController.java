package com.netwin.controller;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netwin.entity.OurUser;
import com.netwin.repo.OurUserRepo;
import com.netwin.service.ErrorApplicationService;
import com.netwin.service.PnNetwinRequestService;
import com.netwin.util.EncryptionAndDecryptionData;
import com.netwin.util.NtResponse;

@RestController
@RequestMapping("api/v2")
public class PnNetwinRequestController {

	// Add this annotation to inject the service
	private PnNetwinRequestService pnNetwinRequestService;
	private ErrorApplicationService errorApplicationService;
	private NtResponse ntResponse;
	private EncryptionAndDecryptionData encryptionAndDecryptionData;
	private OurUserRepo ourUserRepo;
	 private PasswordEncoder passwordEncoder;

	


	@Autowired
	public PnNetwinRequestController(PnNetwinRequestService pnNetwinRequestService,
			ErrorApplicationService errorApplicationService, NtResponse ntResponse,
			EncryptionAndDecryptionData encryptionAndDecryptionData,OurUserRepo ourUserRepo,PasswordEncoder passwordEncoder) {
		this.pnNetwinRequestService = pnNetwinRequestService;
		this.errorApplicationService = errorApplicationService;
		this.ntResponse = ntResponse;
		this.encryptionAndDecryptionData = encryptionAndDecryptionData;
		this.ourUserRepo = ourUserRepo;
		this.passwordEncoder = passwordEncoder;
	
	}
	
	    
	@PostMapping("/pnrequest")
	public ResponseEntity<String> callPanRequest(@RequestBody String panRequestJson)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		String retrnStr = "Required request body is missing";
		HttpStatus status = HttpStatus.BAD_GATEWAY;
		try {
			if (panRequestJson != null && !panRequestJson.isEmpty()) {
				String pnRequestStr = pnNetwinRequestService.callPanRequest(panRequestJson);
				retrnStr = pnRequestStr;
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception ex) {
			StackTraceElement[] stackTrace = ex.getStackTrace();
			String className = null;
			String methodName = null;
			int lineNumber = 0;
			if (stackTrace.length > 0) {
				StackTraceElement stackTraceElement = stackTrace[0];
				className = stackTraceElement.getClassName();
				methodName = stackTraceElement.getMethodName();
				lineNumber = stackTraceElement.getLineNumber();
			}
			errorApplicationService.storeError(502, ex.getMessage(), lineNumber, className, methodName);

			retrnStr = encryptionAndDecryptionData.getEncryptResponse(ntResponse.getNtResponses(500, ex.getMessage()));
			status = HttpStatus.BAD_GATEWAY;

		}

		return new ResponseEntity<String>(retrnStr, status);
	}
	 @PostMapping("/save")
	    public ResponseEntity<Object> saveUSer(@RequestBody OurUser ourUser){
	      			ourUser.setPassword(passwordEncoder.encode(ourUser.getPassword()));
	      	Optional<OurUser> user  = ourUserRepo.findByUserName(ourUser.getUserName());
	      	if(user.isPresent()) {
	      		return ResponseEntity.status(404).body("User Already Registred.....");

	      	}
	        OurUser result = ourUserRepo.save(ourUser);
	        if (result.getId() > 0){
	            return ResponseEntity.ok("User Was Saved");
	        }
	        return ResponseEntity.status(404).body("Error, User Not Saved");
	    }
}
