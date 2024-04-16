package com.netwin.entiry;
import java.util.Map;

public class Result<T> {
   public T data;
    public String errorMessage;
    public Map<String, String> map;
    public Result1<T> results;

    public Result(T data) {
        this.data = data;
    }

    public Result(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Result(Map<String, String> map) {
        this.map = map;
    }



    public Result(Result1<T> results) {
		super();
		this.results = results;
	}

	public boolean isValid() {
        return errorMessage == null;
    }

    public T getData() {
        return data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Map<String, String> getMap() {
        return map;
    }

	public Result1<T> getResult() {
		return results;
	}

	public Result1<T> getResults() {
		return results;
	}

	public void setResults(Result1<T> results) {
		this.results = results;
	}

	public void setData(T data) {
		this.data = data;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	
 
}
