package com.ai.domain.entity;

import java.util.Date;

public class ServiceActivityData {
    private int id;
    private Date date;

    private int webVisitCount;

    private int auditionCount;
    private int downloadCount;

    private int wordToSpeechCount;

    public ServiceActivityData(int auditionCount, int downloadCount, int webVisitCount, int wordToSpeechCount) {
        this.auditionCount = auditionCount;
        this.downloadCount = downloadCount;
        this.webVisitCount = webVisitCount;
        this.wordToSpeechCount = wordToSpeechCount;

    }

    public ServiceActivityData(int auditionCount, int downloadCount, int webVisitCount, int wordToSpeechCount, Date date) {
        this.auditionCount = auditionCount;
        this.downloadCount = downloadCount;
        this.webVisitCount = webVisitCount;
        this.wordToSpeechCount = wordToSpeechCount;
        this.date = date;

    }

    public ServiceActivityData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getWebVisitCount() {
        return webVisitCount;
    }

    public void setWebVisitCount(int webVisitCount) {
        this.webVisitCount = webVisitCount;
    }

    public int getAuditionCount() {
        return auditionCount;
    }

    public void setAuditionCount(int auditionCount) {
        this.auditionCount = auditionCount;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public int getWordToSpeechCount() {
        return wordToSpeechCount;
    }

    public void setWordToSpeechCount(int wordToSpeechCount) {
        this.wordToSpeechCount = wordToSpeechCount;
    }
}
