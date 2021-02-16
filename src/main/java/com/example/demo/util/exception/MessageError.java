package com.example.demo.util.exception;

import java.util.Map;

public class MessageError {
    private final String errorDesc;
    private final Map<String, String> errorReason;

    public MessageError(String errorDesc, Map<String, String> errorsFound) {
        this.errorDesc = errorDesc;
        this.errorReason = errorsFound;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public Map<String, String> getErrorReason() {
        return errorReason;
    }
}
