package com.ai.controller;

import com.ai.pojo.JobInfo;
import com.ai.service.JobService;
import com.ai.utils.TextToSpeechUtils;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping(value = "/textToSpeech")
@CrossOrigin
public class JobController {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobController.class);

    private static ConcurrentHashMap<String,Boolean> vaildVoiceFileMap = new ConcurrentHashMap<>();


    @Autowired
    private JobService jobService;

    /**
     * 返回试听音频
     * @param body
     * @return
     */
    @PostMapping("/audition")
    public void audition(HttpServletResponse response, HttpServletRequest request, @RequestBody String body) {
        JobInfo jobInfo = getJobInfoFromRequestBody(body);
        LOGGER.info("Get a test audition with jobInfo {}", jobInfo.toString());
        jobInfo = jobService.auditionJob(jobInfo);
        if (jobInfo.isJobStatus()) {
            response.setHeader("Content-Type", "audio/mpeg;charset=UTF-8");
            outPutVoiceFileByAudioData(response,jobInfo.getSpeechSynthesisResult().getAudioData());
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
            e.printStackTrace();
        }finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public JobInfo getJobInfoFromRequestBody(String RequestBody){
        // 提取文本
        JSONObject jsonObject = JSONObject.parseObject(RequestBody);
        JobInfo jobInfo = new JobInfo();
        Object textArea = jsonObject.get("textArea");
        if(textArea != null) {
            // 去除空格和换行符号
            jobInfo.setTextarea(textArea.toString()
                    .replace("/r","")
                    .replace("/n","")
                    .replace(" ",""));
        }
        Object language = jsonObject.get("language");
        if(language != null) {
            jobInfo.setLanguage(language.toString());
        }
        Object voiceName = jsonObject.get("voiceName");
        if(voiceName != null) {
            jobInfo.setVoiceName(voiceName.toString());
        }
        Object role = jsonObject.get("role");
        if(role != null) {
            jobInfo.setRole(role.toString());
        }
        Object style = jsonObject.get("style");
        if(style != null) {
            jobInfo.setStyle(style.toString());
        }
        Object rate = jsonObject.get("rate");
        if(rate != null) {
            jobInfo.setRate(rate.toString());
        }
        Object pitch = jsonObject.get("pitch");
        if(pitch != null) {
            jobInfo.setPitch(pitch.toString());
        }
        return jobInfo;

    }

    @PostMapping("/makeJobVoice")
    public Map<String,Object> makeJobVoice(@RequestBody String body) {
        JobInfo jobInfo = getJobInfoFromRequestBody(body);
        jobInfo = jobService.makeVoiceFile(jobInfo);
        Map<String,Object> result = new HashMap<>();
        if (jobInfo.getFileName() != null && !jobInfo.getFileName().equals("")) {
            vaildVoiceFileMap.put(jobInfo.getFileName(), Boolean.TRUE);
            result.put("fileName", jobInfo.getFileName());
            result.put("msg", "生成音频文件成功，文件保留15分钟");
            result.put("code", 200);
        } else {
            result.put("fileName", jobInfo.getFileName());
            result.put("msg", "生成音频文件失败，请重试或联系管理员");
            result.put("code", 404);
        }
        return result;
    }

    @PostMapping(value = "/vaildVoiceFile")
    public Map<String,Object> vaildVoiceFile(@RequestBody String requestBody){
        // 提取文本
        JSONObject jsonObject = JSONObject.parseObject(requestBody);
        Object fileName = jsonObject.get("fileName");
        Map<String,Object> result = new HashMap<>();
        if(fileName != null && !fileName.equals("")) {
            if (vaildVoiceFileMap.containsKey(fileName)){
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


    @GetMapping(value = "/downLoadFile")
    public void downLoadFile(HttpServletResponse response, HttpServletRequest request, @RequestParam(value = "fileName") String fileName) {
        Map<String,Object> result = new HashMap<>();
        if (fileName == null || fileName.equals("null") || fileName.equals("")){
        }else {
            if (vaildVoiceFileMap.containsKey(fileName)){
                response.setHeader("Content-Type", "audio/mpeg;charset=UTF-8");
                outPutVoiceFileByFilName(response, fileName);
            }
        }

    }

    /**
     * 输出音频文件到response流
     * @param response
     * @param voiceFileName
     */
    private void outPutVoiceFileByFilName(HttpServletResponse response, String voiceFileName){
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            File file = new File(TextToSpeechUtils.BASE_VOICE_FILE_PATH + voiceFileName);
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fileInputStream.read(data);
            fileInputStream.close();
            out.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
