package com.ai.exception;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorVo {
    private static final String SERVICE_PREFIX = "TTS.";

    @JsonProperty("error_code")
    @JSONField(name = "error_code")
    private String errorCode;

    @JsonProperty("error_msg")
    @JSONField(name = "error_msg")
    private String errorMsg;

    @JsonProperty("detail_msg")
    @JSONField(name = "detail_msg")
    private String detailMsg;

    public ErrorVo(String errorCode, String errorMsg, String detailMsg) {
        this.errorCode = SERVICE_PREFIX + errorCode;
        this.errorMsg = errorMsg;
        this.detailMsg = detailMsg;
    }

    public ErrorVo(String errorCode, String errorMsg) {
        this.errorCode = SERVICE_PREFIX + errorCode;
        this.errorMsg = errorMsg;
    }

    public ErrorVo(ErrorCode errorCode) {
        this.errorCode = SERVICE_PREFIX + String.valueOf(errorCode.getErrorCode());
        this.errorMsg = errorCode.getMessage();
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void setDetailMsg(String detailMsg) {
        this.detailMsg = detailMsg;
    }
}
