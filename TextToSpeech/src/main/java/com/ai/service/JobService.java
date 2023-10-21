package com.ai.service;

import com.ai.pojo.JobInfo;

/**
 * 文字转语音service接口
 */
public interface JobService {
    /**
     * 通过文字转语音
     * @param jobInfo
     * @return
     */
    JobInfo makeVoiceFile(JobInfo jobInfo);

    /**
     * 获取文字转语音对象
     * @param jobInfo
     * @return
     */
    JobInfo auditionJob(JobInfo jobInfo);

    /**
     * 下载音频
     * @param fileName
     */
    void downLoadVoice(String fileName);
}
