package com.ai.pojo;

import com.ai.utils.Constants;
import com.microsoft.cognitiveservices.speech.AudioDataStream;
import com.microsoft.cognitiveservices.speech.SpeechSynthesisResult;

import java.util.Date;

/**
 * 文字转语音任务类
 */
public class JobInfo {

    private String ssml;
    private int id;
    private String ip;
    private String textarea;
    private String fileName;
    private Date time;
    private SpeechSynthesisResult speechSynthesisResult;

    private boolean jobStatus;

    private String language = Constants.LANGUAGE_ZH_CN;

    private String voiceName = Constants.LANGUAGE_ZH_CN_XIAOCHEN;

    private int voiceQuality = 4;


    private String role = Constants.ROLE_DEFAULT;

    private String style = Constants.STYLE_DEFAULT;

    // rate% 例如 +50%
    private String rate = Constants.RATE_DEFAULT;

    // pitchHz 例如 +50Hz
    private String pitch = Constants.PITCH_DEFAULT;


    public String getSsml() {
        return ssml;
    }

    public void setSsml(String ssml) {
        this.ssml = ssml;
    }

    public String getVoiceName() {
        return voiceName;
    }

    public void setVoiceName(String voiceName) {
        this.voiceName = voiceName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getVoiceQuality() {
        return voiceQuality;
    }

    public void setVoiceQuality(int voiceQuality) {
        this.voiceQuality = voiceQuality;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getPitch() {
        return pitch;
    }

    public void setPitch(String pitch) {
        this.pitch = pitch;
    }

    public boolean isJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(boolean jobStatus) {
        this.jobStatus = jobStatus;
    }


    public JobInfo() {
    }

    public JobInfo(String textarea){
        this.textarea = textarea;
        this.jobStatus = false;
    }

    public JobInfo(String textarea, String fileName){
        this.textarea = textarea;
        this.fileName = fileName;
        this.jobStatus = false;
    }

    public SpeechSynthesisResult getSpeechSynthesisResult() {
        return speechSynthesisResult;
    }

    public void setSpeechSynthesisResult(SpeechSynthesisResult speechSynthesisResult) {
        this.speechSynthesisResult = speechSynthesisResult;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getTextarea() {
        return textarea;
    }

    public void setTextarea(String textarea) {
        this.textarea = textarea;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String setFileNameByVoiceQuality(String randomName){
        // 默认是MP3格式
        fileName = randomName + ".mp3";
         if(voiceQuality >= 4 && voiceQuality <= 6) {
            fileName = randomName + ".wav";
        }
         return fileName;
    }
}
