package com.netwin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netwin.entity.ErrorApplication;
import com.netwin.repo.ErrorApplicationRepo;
import com.netwin.service.ErrorApplicationService;

@Service
public class ErrorApplicationServiceImpl implements ErrorApplicationService {

private ErrorApplicationRepo errorApplicationRepo;
@Autowired
public ErrorApplicationServiceImpl(ErrorApplicationRepo errorApplicationRepo) {
	this.errorApplicationRepo = errorApplicationRepo;
}

	@Override
	public void storeError(int errorCode, String errorDesc, int line , String className,String methodName) {
		ErrorApplication errorApplication = new ErrorApplication();
		errorApplication.setErrorCode(errorCode);
		errorApplication.setErroDesc(errorDesc);
		errorApplication.setLineNumber(line);
		errorApplication.setClassName(className);
		errorApplication.setMethoName(methodName);
		errorApplicationRepo.save(errorApplication);
		errorApplication=null;
	}

}
