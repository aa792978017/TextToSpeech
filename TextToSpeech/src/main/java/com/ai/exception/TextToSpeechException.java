package com.ai.exception;

/**
 * 应用业务异常类
 */
public class TextToSpeechException extends RuntimeException {
    private String[] args;
    private ErrorCode code;

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public ErrorCode getCode() {
        return code;
    }

    public TextToSpeechException() {
        super();

    }

    public TextToSpeechException(ErrorCode code) {
        this.code = code;
    }

    public TextToSpeechException(String[] args, ErrorCode code) {
        this.args = args;
        this.code = code;
    }

    public void setCode(ErrorCode code) {
        this.code = code;
    }
}
