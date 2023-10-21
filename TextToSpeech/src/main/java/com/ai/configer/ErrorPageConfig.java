package com.ai.configer;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * 后端错误请求处理器
 */
@Component
public class ErrorPageConfig implements ErrorPageRegistrar {
    /**
     * 未知的请求URL到后端后豆都转发到静态资源根路径下
     * @param registry
     */
    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        // 后端404响应都转发到静态资源“/”路径下
        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/");
        registry.addErrorPages(error404Page);
    }
}
