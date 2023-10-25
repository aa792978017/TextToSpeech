package com.ai.aop;

import com.ai.dao.ServiceActivityDataDAO;
import com.ai.domain.entity.ServiceActivityData;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

@Aspect
@Component
public class ServiceActivityAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceActivityAspect.class);

    private AtomicInteger auditionCount = new AtomicInteger(0);

    private AtomicInteger downloadCount = new AtomicInteger(0);

    private AtomicInteger wordToSpeechCount = new AtomicInteger(0);

    private AtomicInteger webVisitCount = new AtomicInteger(0);

    private HashSet<String> userIps = new HashSet<>();

    @Autowired
    private ServiceActivityDataDAO serviceActivityDataDAO;


    /**
     * 统计网站访问次数切面
     */
    @Pointcut("execution(* com.ai.controller.*.*(..))")
    public void welcomeDataStatic() {

    }

    /**
     * 统计试听次数切面
     */
    @Pointcut("execution(* com.ai.controller.JobController.audition())")
    public void auditionDataStatic() {


    }


    /**
     * 统计下载音频次数切面
     */
    @Pointcut("execution(* com.ai.controller.JobController.downLoadFile())")
    public void downLoadFileDataStatic() {

    }

    /**
     * 统计生成音频次数切面
     */
    @After("auditionDataStatic()")
    public void afterAudition(){
        LOGGER.info("Note auditionDataStatic...");
        auditionCount.getAndIncrement();

    }


    @After("downLoadFileDataStatic()")
    public void afterDownLoadFile(){
        LOGGER.info("Note downLoadFileDataStatic...");
        downloadCount.getAndIncrement();

    }

    @After("welcomeDataStatic()")
    public void afterWelcome(){
        LOGGER.info("Note welcomeDataStatic...");
        webVisitCount.getAndIncrement();
    }

    public ServiceActivityData getCurrentServiceActivityData(){
        return new ServiceActivityData(auditionCount.get(), downloadCount.get(),
                webVisitCount.get(), wordToSpeechCount.get());
    }

    /**
     * 保存每日服务用户访问数据
     */
    public void saveServiceActivityData(){
        ServiceActivityData serviceActivityData = new ServiceActivityData(auditionCount.get(), downloadCount.get(),
                webVisitCount.get(), wordToSpeechCount.get(), new Date());
        auditionCount.set(0);
        downloadCount.set(0);
        wordToSpeechCount.set(0);
        webVisitCount.set(0);
        try {
            serviceActivityDataDAO.insertServiceActivityData(serviceActivityData);
            LOGGER.info("Save today serviceActivityData success, data is {}", serviceActivityData);
        } catch (Exception ex) {
            LOGGER.info("Save today serviceActivityData fail, data is {}", serviceActivityData);
        }
    }

}
