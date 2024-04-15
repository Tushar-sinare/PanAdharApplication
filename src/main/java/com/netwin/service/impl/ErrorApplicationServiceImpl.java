package com.netwin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netwin.entiry.ErrorApplication;
import com.netwin.repo.ErrorApplicationRepo;
import com.netwin.service.ErrorApplicationService;

@Service
public class ErrorApplicationServiceImpl implements ErrorApplicationService {
@Autowired
private ErrorApplicationRepo errorApplicationRepo;
	@Override
	public void storeError(int errorCode, String errorDesc) {
		ErrorApplication errorApplication = new ErrorApplication();
		errorApplication.setErrorCode(errorCode);
		errorApplication.setErroDesc(errorDesc);
		errorApplicationRepo.save(errorApplication);
		errorApplication=null;
	}

}
