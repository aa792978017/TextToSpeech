package com.ai.service;

import com.ai.domain.entity.JobHistory;
import com.ai.domain.response.JobVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 文字转语音service接口
 */
public interface JobService {


    /**
     * 获取文字转语音对象
     * @param jobHistory
     * @return
     */
    JobVo auditionJob(JobHistory jobHistory);

    /**
     * 下载音频
     *
     * @param response
     * @param fileName
     */
    void downLoadVoice(HttpServletResponse response, String fileName);

    /**
     * 校验音频文件是否存在
     * @param fileName
     * @return
     */
    boolean vaildVoiceFile(String fileName);

    /**
     * 清空音频缓存文件
     */
    List<String> clearAudioFileCache();
}
