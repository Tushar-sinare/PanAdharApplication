package com.netwin.entiry;

import java.util.Map;

public class Result {
    private Object data;
    private String errorMessage;
    private Map<String, String> map;
    private Result1 results;

    public Result(Object data) {
        this.data = data;
    }

    public Result(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Result(Map<String, String> map) {
        this.map = map;
    }

    public Result(Result1 results) {
        this.results = results;
    }

    public boolean isValid() {
        return errorMessage == null;
    }

    public Object getData() {
        return data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public Result1 getResults() {
        return results;
    }

    public void setResults(Result1 results) {
        this.results = results;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
