package com.ai.exception;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 统一异常处理类
 */
@RestControllerAdvice
public class CommonControllerAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonControllerAdvice.class);

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private HttpServletRequest request;

    /**
     * 捕获已知业务异常
     * @param ex 已知业务异常
     * @return
     */
    @ExceptionHandler(TextToSpeechException.class)
    public ErrorVo handleTextToSpeechException(TextToSpeechException ex) {
        ErrorCode code = ex.getCode();
        response.setStatus(code.getStatusCode());
        LOGGER.error("URI:{}, due to {}", getRequestURI(request), code.getMessage());
        return new ErrorVo(code);
    }

    /**
     * 捕获未知业务异常
     * @param ex 未知异常
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ErrorVo handleException(Exception ex) {
        LOGGER.error("URI:{}, due to {}", getRequestURI(request), ExceptionUtils.getMessage(ex));
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return new ErrorVo(ErrorCode.INVALID_REQUEST);
    }

    private String getRequestURI(HttpServletRequest request) {
        String uri = request.getContextPath() + request.getServletPath();
        String pathInfo = request.getPathInfo();
        if (StringUtils.isNotEmpty(pathInfo)) {
            uri += pathInfo;
        }
        return uri;
    }
}
