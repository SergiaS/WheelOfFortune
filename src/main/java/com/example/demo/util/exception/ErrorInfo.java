package com.example.demo.util.exception;

public class ErrorInfo {
    private final String url;
    private final String errorType;
    private final String desc;

    public ErrorInfo(CharSequence url, String errorType, String desc) {
        this.url = url.toString();
        this.errorType = errorType;
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public String getErrorType() {
        return errorType;
    }

    public String getDesc() {
        return desc;
    }
}
