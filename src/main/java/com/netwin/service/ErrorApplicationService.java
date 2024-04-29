package com.netwin.service;

public interface ErrorApplicationService {



	void storeError(int errorCode, String errorDesc, int line, String className,String methodName);

}
