package com.ai.configer;

import com.google.common.util.concurrent.RateLimiter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletRequest;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 限流器
 */
@Component
public class ServiceRateLimiter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRateLimiter.class);

    // 服务每秒最大请求数
    @Value("${app.service-permits-per-second}")
    private double serviceGlobalPermitsPerSecond;

    // 服务每秒最大请求数
    @Value("${app.ip-permits-per-second}")
    private double ipPermitsPerSecond;

    // 单用户每天最大文字转语言配额
    @Value("${app.word-permits-per-day}")
    private Integer wordPermitsPerDay;

    @Value("${app.word-permits-per-transform}")
    private Integer wordPermitsPerTransform;

    /**
     * 用户限流器集合
     */
    private ConcurrentHashMap<String, RateLimiter> userIpRateLimitMap = new ConcurrentHashMap<>();

    /**
     * 用户限流器 ip -> 时间戳
     */
    private ConcurrentHashMap<String, Long> userIp2TimeStampMap = new ConcurrentHashMap<>();


    /**
     * 用户每天语言转文字配额记录器
     */
    private ConcurrentHashMap<String, AtomicInteger> userIp2WordCountMap = new ConcurrentHashMap<>();

    /**
     * 全局限流器
     */
    private RateLimiter serviceGlobalRateLimiter;


    @PostConstruct
    public void initRateLimit(){
        LOGGER.info("Init serviceRateLimiter...");
        serviceGlobalRateLimiter = RateLimiter.create(serviceGlobalPermitsPerSecond);
    }

    /**
     * 限流校验
     * @param rateLimiter
     * @return
     */
    private boolean serviceRateLimit(RateLimiter rateLimiter) {
        if (rateLimiter.tryAcquire()) {
            // 放行请求
            return false;
        } else {
            // 限流
            LOGGER.error("Service rate limit , current is {}", rateLimiter.getRate());
            return true;
        }
    }

    /**
     * 单用户限流
     * @param servletRequest
     * @return
     */
    public boolean userRateLimit(ServletRequest servletRequest){
        String userIp = servletRequest.getRemoteAddr();
        RateLimiter userRateLimiter;
        if(userIpRateLimitMap.containsKey(userIp) && userIp2TimeStampMap.containsKey(userIp)){
            userRateLimiter = userIpRateLimitMap.get(userIp);
        } else {
            userRateLimiter = RateLimiter.create(ipPermitsPerSecond);
            userIpRateLimitMap.put(userIp,userRateLimiter);
        }
        userIp2TimeStampMap.put(userIp,System.currentTimeMillis());
        LOGGER.info("User rate limit, ip is {}, user rate limit is {}", servletRequest.getRemoteAddr(), userRateLimiter.getRate());
        return serviceRateLimit(userRateLimiter);

    }

    /**
     * 服务全局限流
     */
    public boolean globalServiceRateLimit(){
       return serviceRateLimit(serviceGlobalRateLimiter);
    }

    public double getServiceGlobalPermitsPerSecond() {
        return serviceGlobalPermitsPerSecond;
    }

    public void setServiceGlobalPermitsPerSecond(double serviceGlobalPermitsPerSecond) {
        this.serviceGlobalPermitsPerSecond = serviceGlobalPermitsPerSecond;
    }

    public double getIpPermitsPerSecond() {
        return ipPermitsPerSecond;
    }

    public void setIpPermitsPerSecond(double ipPermitsPerSecond) {
        this.ipPermitsPerSecond = ipPermitsPerSecond;
    }

    public ConcurrentHashMap<String, RateLimiter> getUserIpRateLimitMap() {
        return userIpRateLimitMap;
    }

    public void setUserIpRateLimitMap(ConcurrentHashMap<String, RateLimiter> userIpRateLimitMap) {
        this.userIpRateLimitMap = userIpRateLimitMap;
    }

    public RateLimiter getServiceGlobalRateLimiter() {
        return serviceGlobalRateLimiter;
    }

    public void setServiceGlobalRateLimiter(RateLimiter serviceGlobalRateLimiter) {
        this.serviceGlobalRateLimiter = serviceGlobalRateLimiter;
    }

    public ConcurrentHashMap<String, Long> getUserIp2TimeStampMap() {
        return userIp2TimeStampMap;
    }

    /**
     * 清空用户限流器
     * @param userIp
     */
    public void clearUnActiveUserRateLimit(String userIp){
        userIpRateLimitMap.remove(userIp);
        userIp2TimeStampMap.remove(userIp);
    }

    /**
     * 记录用户当天文字转语音配额
     * @param ip
     * @param count
     */
    public void userTextToSpeechCount(String ip, int count) {
        // 大于最大限额，后续会抛异常，无需记录
        if (count > wordPermitsPerDay) {
            return;
        }
        getUserCurrentPerDayLimit(ip).getAndAdd(count);
    }

    /**
     * 用户当前请求的音频是否超过每日限额
     * @param ip
     * @param count
     * @return
     */
    public boolean isExceedPerDayUserLimit(String ip, int count) {
        return getUserCurrentPerDayLimit(ip).get() + count > wordPermitsPerTransform;
    }

    /**
     * 获取当前用户ip的配额
     * @param ip
     * @return
     */
    public AtomicInteger getUserCurrentPerDayLimit(String ip) {
        AtomicInteger currentCurrentCount = null;
        if (userIp2WordCountMap.containsKey(ip)) {
            currentCurrentCount = userIp2WordCountMap.get(ip);
        } else {
            currentCurrentCount = new AtomicInteger(0);
            userIp2WordCountMap.put(ip, currentCurrentCount);
        }
        return currentCurrentCount;
    }
}
