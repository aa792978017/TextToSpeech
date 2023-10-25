package com.ai.filter;

import com.ai.aop.ServiceActivityAspect;
import com.ai.configer.ServiceRateLimiter;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;


/**
 * 服务过滤器，用作服务限流，避免服务过载、恶意刷API，
 */
@WebFilter(urlPatterns = "/textToSpeech/*", filterName = "ServiceFilter")
public class ServiceFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceFilter.class);

    @Autowired
    private ServiceRateLimiter serviceRateLimiter;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("ServiceFilter init...");
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain){
        // 服务限流
        serviceRateLimit(servletRequest, servletResponse, filterChain);
    }

    /**
     * 服务限流过滤
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     */
    private void serviceRateLimit(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        // 单用户限流校验
        boolean ifUserRateLimit = serviceRateLimiter.userRateLimit(servletRequest);
        // 全局限流校验
        boolean ifGlobalRateLimit = serviceRateLimiter.globalServiceRateLimit();
        // 限流
        if (ifUserRateLimit || ifGlobalRateLimit) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            httpServletResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            LOGGER.error("Service rate limit, ifUserRateLimit is {}, ifGlobalRateLimit is {}",ifUserRateLimit, ifGlobalRateLimit);
        } else {
            // 放行
            try {
                filterChain.doFilter(servletRequest, servletResponse);
            } catch (Exception exception) {
                LOGGER.error("service rate limit filter with exception, {}", ExceptionUtils.getMessage(exception));
                exception.printStackTrace();
            }

        }
    }

    @Override
    public void destroy() {
        LOGGER.info("Web filter destroy");
        Filter.super.destroy();
    }




}
