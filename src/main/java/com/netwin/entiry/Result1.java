package com.netwin.entiry;

import java.util.Map;

public class Result1<T> {
	public Map<String, Object> resMap;
	  public String errorMessage;
    public Result1(Map<String, Object> resMap) {
        this.resMap = resMap;
    }
    public Map<String, Object> getResMap() {
        return resMap;
    }
    public Result1(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    
}
