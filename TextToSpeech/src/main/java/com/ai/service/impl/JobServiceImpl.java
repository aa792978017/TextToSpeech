package com.ai.service.impl;

import com.ai.configer.ServiceRateLimiter;
import com.ai.dao.JobHistoryDAO;
import com.ai.domain.entity.JobHistory;
import com.ai.domain.response.JobVo;
import com.ai.exception.TextToSpeechException;
import com.ai.service.JobService;
import com.ai.utils.TextToSpeechUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.ai.exception.ErrorCode.*;
import static com.ai.utils.TextToSpeechUtils.BASE_VOICE_FILE_PATH;

/**
 * JobService实现类
 */
@Service
public class JobServiceImpl implements JobService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobServiceImpl.class);

    /**
     * 语言文件缓存，记录转录好的语言文件是否被删除
     */
    private static ConcurrentHashMap<String,Boolean> vaildVoiceFileMap = new ConcurrentHashMap<>();

    @Autowired
    private JobHistoryDAO jobHistoryDAO;

    @Autowired
    private ServiceRateLimiter serviceRateLimiter;

    @Value("${app.max-word-permits-per-transform}")
    private Integer wordPermitsPerTransform;



    @Transactional
    @Override
    public JobVo auditionJob(JobHistory jobHistory) {
        // 单次文本转语音不能超过5000字符
        String textArea = jobHistory.getTextarea();
        String ip = jobHistory.getIp();
        if (StringUtils.isNotEmpty(textArea)) {
            if (textArea.length() > wordPermitsPerTransform) {
                LOGGER.error("Text had exceed the max wordPermitsPerTransform, text length is {}", textArea.length()) ;
                throw new TextToSpeechException(EXCEED_MAX_WORD_PERMITS_PER_TRANSFORM);
            }
        }
        // 是否超过限额
        if (serviceRateLimiter.isExceedPerDayUserQuota(ip, textArea.length())) {
            LOGGER.error("User [{}] today text had exceed, current used quota is {}", ip,
                    serviceRateLimiter.getUserCurrentPerDayLimit(ip).get());
            throw new TextToSpeechException(USER_DAY_QUOTA_EXCEED);
        }
        jobHistoryDAO.insertJobHistory(jobHistory);
        LOGGER.info("Note a auditionJob request to db...");
        JobHistory sameJobHistory = jobHistoryDAO.querySameConfigJobHistory(jobHistory);
        if (ObjectUtils.isNotEmpty(sameJobHistory) && StringUtils.isNotEmpty(sameJobHistory.getFileName())
                && vaildVoiceFileMap.containsKey(sameJobHistory.getFileName())) {
            jobHistory.setFileName(sameJobHistory.getFileName());
            jobHistory.setJobStatus(true);
        }
        // 历史上无留存，重新转换
        if (StringUtils.isEmpty(jobHistory.getFileName())) {
            // 转语音
            TextToSpeechUtils.textToSpeech(jobHistory);
            jobHistoryDAO.updateJobHistory(jobHistory);
            // 缓存本地仍留存的语音文件名
            vaildVoiceFileMap.put(jobHistory.getFileName(), Boolean.TRUE);
            LOGGER.info("Update a auditionJob request to db...");
        }
        JobVo jobVo = covertJobVoFromJobHistory(jobHistory);
        return jobVo;
    }

    /**
     * 读取本地转好的语言文件
     * @param jobHistory
     * @return
     */
    private JobVo covertJobVoFromJobHistory(JobHistory jobHistory) {
        JobVo jobVo = new JobVo();
        jobVo.setJobStatus(jobHistory.isJobStatus());
        String fileName = jobHistory.getFileName();
        String filePath = BASE_VOICE_FILE_PATH + fileName;
        if (vaildVoiceFileMap.containsKey(fileName)) {
            try {
                jobVo.setAudio(Files.readAllBytes(Paths.get(new File(filePath).getPath())));
            } catch (IOException e) {
                LOGGER.error("Read audio file from local error, filePath is [{}], ex is [{}]",
                        filePath, ExceptionUtils.getMessage(e));
                throw new TextToSpeechException(READ_LOCAL_AUDIO_FILE_ERROR);
            }
        }
        return jobVo;
    }

    @Override
    public void downLoadVoice(HttpServletResponse response, String fileName) {
        try {
            if (StringUtils.isNotEmpty(fileName)) {
                if (vaildVoiceFile(fileName)) {
                    response.setHeader("Content-Type", "audio/mpeg;charset=UTF-8");
                    OutputStream out = null;
                    try {
                        out = response.getOutputStream();
                        // 读取本地音频文件
                        File file = new File(TextToSpeechUtils.BASE_VOICE_FILE_PATH + fileName);
                        FileInputStream fileInputStream = new FileInputStream(file);
                        byte[] data = new byte[(int) file.length()];
                        fileInputStream.read(data);
                        fileInputStream.close();
                        out.write(data);
                        LOGGER.info("Download audio file [{}] from local cache success", fileName);
                    } catch (Exception e) {
                        LOGGER.error("Download audio file [{}] from local cache error", fileName);
                        throw new RuntimeException(e);
                    } finally {
                        if (out != null) {
                            try {
                                out.close();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            vaildVoiceFileMap.remove(fileName);
            LOGGER.error("Download audio file [{}] error, clear it from cache", fileName);
            throw new TextToSpeechException(DOWNLOAD_AUDIO_FILE_ERROR);
        }
    }

    @Override
    public boolean vaildVoiceFile(String fileName) {
        return vaildVoiceFileMap.containsKey(fileName);
    }

    @Override
    public List<String> clearAudioFileCache() {
        List<String> audioFileNames = new ArrayList<>(vaildVoiceFileMap.keySet());
        vaildVoiceFileMap.clear();
        return audioFileNames;
    }


}
