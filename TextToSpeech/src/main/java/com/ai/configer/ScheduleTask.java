package com.ai.configer;

import com.ai.utils.CommonsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 定时任务组件
 */
@Component
public class ScheduleTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleTask.class);

    @Autowired
    private ServiceRateLimiter serviceRateLimiter;

    /**
     * 每30分钟清理异常15分钟内不活跃的用户限流器
     */
    @Scheduled(fixedDelay = CommonsUtils.HALF_HOUR_TIME)
    public void clearAppRateLimit(){
        LOGGER.info("Clear app user rate limiter");
        List<String> userIps = new ArrayList<>(serviceRateLimiter.getUserIp2TimeStampMap().keySet());
        for (String userIp : userIps) {
            Long timeStamp = serviceRateLimiter.getUserIp2TimeStampMap().get(userIp);
            if (CommonsUtils.isExpired(timeStamp, CommonsUtils.ONE_QUARTER_TIME)) {
                serviceRateLimiter.clearUnActiveUserRateLimit(userIp);
            }
        }
    }
}
