package com.netwin.entiry;

import java.util.Map;

public class Result1 {
    private Map<String, Object> resMap;
    private String errorMessage;

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
