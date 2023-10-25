package com.ai.controller;

import com.ai.domain.entity.JobHistory;
import com.ai.domain.response.JobVo;
import com.ai.service.JobService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/text-to-speech")
@CrossOrigin
public class JobController {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobController.class);

    @Autowired
    private JobService jobService;

    /**
     * 返回试听音频
     * @param body
     * @return
     */
    @PostMapping("/audition")
    public void audition(HttpServletResponse response, HttpServletRequest request, @RequestBody String body) {
        JobHistory jobHistory = getJobInfoFromRequestBody(request, body);
        LOGGER.info("Get a test audition with jobInfo {}", jobHistory.toString());
        JobVo jobVo = jobService.auditionJob(jobHistory);
        if (jobVo.isJobStatus()) {
            response.setHeader("Content-Type", "audio/mpeg;charset=UTF-8");
            outPutVoiceFileByAudioData(response, jobVo.getAudio());
        }
    }

    /**
     * 通过字节流设置response返回体的中的音频
     * @param response
     * @param audioData
     */
    private void outPutVoiceFileByAudioData(HttpServletResponse response, byte[] audioData) {
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            out.write(audioData);
        } catch (Exception e) {
            LOGGER.error("Write audio to response error, ex is {}", ExceptionUtils.getMessage(e));
            e.printStackTrace();
        }finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    LOGGER.error("Close response outputStream error, ex is {}", ExceptionUtils.getMessage(e));
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 解析请求体
     * @param request
     * @param RequestBody
     * @return
     */
    private JobHistory getJobInfoFromRequestBody(HttpServletRequest request, String RequestBody){
        // 提取文本
        JSONObject jsonObject = JSONObject.parseObject(RequestBody);
        JobHistory jobHistory = new JobHistory();
        Object textArea = jsonObject.get("textArea");
        if(textArea != null) {
            // 去除空格和换行符号
            jobHistory.setTextarea(textArea.toString()
                    .replace("/r","")
                    .replace("/n","")
                    .replace(" ",""));
        }
        Object language = jsonObject.get("language");
        if(language != null) {
            jobHistory.setLanguage(language.toString());
        }
        Object voiceName = jsonObject.get("voiceName");
        if(voiceName != null) {
            jobHistory.setVoiceName(voiceName.toString());
        }
        Object role = jsonObject.get("role");
        if(role != null) {
            jobHistory.setRole(role.toString());
        }
        Object style = jsonObject.get("style");
        if(style != null) {
            jobHistory.setStyle(style.toString());
        }
        Object rate = jsonObject.get("rate");
        if(rate != null) {
            jobHistory.setRate(rate.toString());
        }
        Object pitch = jsonObject.get("pitch");
        if(pitch != null) {
            jobHistory.setPitch(pitch.toString());
        }
        String userIp = request.getRemoteAddr();
        jobHistory.setIp(userIp);
        jobHistory.setTime(new Date());
        jobHistory.setJobStatus(false);
        return jobHistory;

    }


    @PostMapping(value = "/vaild-voice-file")
    public Map<String,Object> vaildVoiceFile(@RequestBody String requestBody){
        // 提取文本
        JSONObject jsonObject = JSONObject.parseObject(requestBody);
        String fileName = jsonObject.getString("fileName");
        Map<String,Object> result = new HashMap<>();
        if(StringUtils.isNotEmpty(fileName)) {
            if (jobService.vaildVoiceFile(fileName)){
                result.put("fileName", fileName);
                result.put("msg", "音频文件存在，且在有效期内");
                result.put("code", 200);
            }else {
                result.put("fileName", fileName);
                result.put("msg", "音频文件不存在或已过期，请重新生成音频文件");
                result.put("code", 201);
            }
        }else{
            result.put("fileName", fileName);
            result.put("msg", "音频文件名异常");
            result.put("code", 400);
        }
        return result;


    }


    @GetMapping(value = "/download-file")
    public void downLoadFile(HttpServletResponse response, @RequestParam(value = "fileName") String fileName) {
        jobService.downLoadVoice(response, fileName);
    }



}
