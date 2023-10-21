package com.ai.service.impl;

import com.ai.dao.JobDAO;
import com.ai.pojo.JobInfo;
import com.ai.service.JobService;
import com.ai.utils.CommonsUtils;
import com.ai.utils.TextToSpeechUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * JobService实现类
 */
@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobDAO jobDAO;

    @Override
    public JobInfo makeVoiceFile(JobInfo jobInfo) {
        TextToSpeechUtils.textToSpeech(jobInfo);
        TextToSpeechUtils.saveVoiceFile(jobInfo);
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
