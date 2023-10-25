package com.ai.exception;


import org.springframework.http.HttpStatus;

/**
 * 应用错误码枚举类
 */
public enum ErrorCode {
    INVALID_REQUEST(1000, "The request parameter is invalid.", HttpStatus.BAD_REQUEST),
    EXCEED_MAX_WORD_PERMITS_PER_TRANSFORM(10000, "Text exceed the max limit", HttpStatus.BAD_REQUEST),

    USER_DAY_QUOTA_EXCEED(10001, "User day quota exceed limit", HttpStatus.BAD_REQUEST),

    READ_LOCAL_AUDIO_FILE_ERROR(10002, "Read audio file from local error", HttpStatus.BAD_REQUEST),

    DOWNLOAD_AUDIO_FILE_ERROR(10003, "Download audio file error", HttpStatus.BAD_REQUEST);


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
