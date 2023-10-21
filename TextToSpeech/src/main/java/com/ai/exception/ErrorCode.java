package com.ai.exception;


import org.springframework.http.HttpStatus;

/**
 * 应用错误码枚举类
 */
public enum ErrorCode {
    INVALID_REQUEST(1000, "The request parameter is invalid.", HttpStatus.BAD_REQUEST);

    private int errorCode;

    private String message;

    private int statusCode;

    ErrorCode(int errorCode, String message, HttpStatus statusCode) {
        this.errorCode = errorCode;
        this.message = message;
        this.statusCode = statusCode.value();
    }


    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
