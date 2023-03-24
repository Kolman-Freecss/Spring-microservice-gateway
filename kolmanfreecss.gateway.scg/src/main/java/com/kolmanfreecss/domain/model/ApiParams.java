package com.kolmanfreecss.domain.model;

public enum ApiParams {
    
    HEADER_X_TRACEID("X-Trace-Id");
    
    private String value;
    
    ApiParams(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
}
