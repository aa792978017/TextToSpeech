package com.ai.service.impl;

import com.ai.dao.JobDAO;
import com.ai.pojo.JobInfo;
import com.ai.service.JobService;
import com.ai.utils.TextToSpeechUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * JobService实现类
 */
@Service
public class JobServiceImpl implements JobService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobServiceImpl.class);

    @Autowired
    private JobDAO jobDAO;

    @Override
    public JobInfo makeVoiceFile(JobInfo jobInfo) {
        TextToSpeechUtils.textToSpeech(jobInfo);
        LOGGER.info("Text to speech success, text is '{}'", jobInfo.getTextarea());
        TextToSpeechUtils.saveVoiceFile(jobInfo);
        LOGGER.info("Save success, fileName is '{}'", jobInfo.getFileName());
        return jobInfo;
    }

    @Override
    public JobInfo auditionJob(JobInfo jobInfo) {
        return TextToSpeechUtils.textToSpeech(jobInfo);
    }

    @Override
    public void downLoadVoice(String fileName) {

    }


}
