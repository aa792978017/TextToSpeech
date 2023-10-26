package com.ai.configer;

import com.ai.aop.ServiceActivityAspect;
import com.ai.service.JobService;
import com.ai.utils.CommonsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 定时任务组件
 */
@Component
public class ScheduleTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleTask.class);

    @Autowired
    private ServiceRateLimiter serviceRateLimiter;

    @Autowired
    private JobService jobService;

    @Autowired
    private ServiceActivityAspect serviceActivityAspect;

    /**
     * 每30分钟清理异常15分钟内不活跃的用户限流器
     */
    @Scheduled(fixedDelay = CommonsUtils.HALF_HOUR_TIME)
    public void clearAppRateLimit(){
        LOGGER.info("Scheduled task: [clearAppRateLimit], time: [{}]", new Date());
        List<String> userIps = new ArrayList<>(serviceRateLimiter.getUserIp2TimeStampMap().keySet());
        for (String userIp : userIps) {
            Long timeStamp = serviceRateLimiter.getUserIp2TimeStampMap().get(userIp);
            if (CommonsUtils.isExpired(timeStamp, CommonsUtils.ONE_QUARTER_TIME)) {
                serviceRateLimiter.clearUnActiveUserRateLimit(userIp);
            }
        }
    }

    /**
     * 每周一清空服务器内缓存音频，释放磁盘空间
     */
    @Scheduled(cron = "0 0 0 ? * Mon")
    public void clearLocalAudioFileWeekly(){
        LOGGER.info("Scheduled task: [clearLocalAudioFileWeekly], time: [{}]", new Date());
        List<String> audioCacheFileNames = jobService.clearAudioFileCache();
        audioCacheFileNames.forEach(fileName ->{
            try {
                Files.deleteIfExists(Paths.get(new File(fileName).getPath()));
            } catch (IOException e) {
                LOGGER.error("Delete audio cache file error, file name is {}",fileName);
            }
        });
    }

    /**
     * 每天0点保存前一天服务用户数据和初始化用户配额
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void saveServiceActivityDataDailyAndUserQuota(){
        LOGGER.info("Scheduled task: [saveServiceActivityDataDailyAndUserQuota], time: [{}]", new Date());
        serviceActivityAspect.saveServiceActivityData();
        serviceRateLimiter.initPerDayUserQuota();
    }
}
