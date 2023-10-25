package com.ai.utils;

import com.ai.domain.entity.JobHistory;
import com.microsoft.cognitiveservices.speech.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Date;

/**
 * 文字转语音核心工具类
 */
public class TextToSpeechUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(TextToSpeechUtils.class);

    private static final String SPEECH_KEY = "68bd2b5261c5424dbb0d32db861c98e0";
    private static final String SPEECH_REGION = "eastasia";
    public static final String BASE_VOICE_FILE_PATH = "TextToSpeech/src/main/resources/mp3/";

    /**
     * 默认文字转语音任务
     * @param jobHistory
     * @return
     */
    public static JobHistory textToSpeech(JobHistory jobHistory){
        // 获取语音解析器
        SpeechConfig speechConfig = getSpeechConfigFromJobHistory(jobHistory);
        SpeechSynthesizer speechSynthesizer = new SpeechSynthesizer(speechConfig,null);
        // 构建ssml文本
        String ssml = "<speak version=\"1.0\" xmlns=\"http://www.w3.org/2001/10/synthesis\"\n" +
                "       xmlns:mstts=\"https://www.w3.org/2001/mstts\" xml:lang=\"$language$\">\n" +
                "    <voice name=\"$voiceName$\">\n" +
                "        <mstts:express-as role=\"$role$\" style=\"$style$\">\n" +
                "             <prosody rate=\"$rate$\" pitch=\"$pitch$\">\n" +
                "                $textArea$\n" +
                "             </prosody>\n" +
                "        </mstts:express-as>\n" +
                "    </voice>\n" +
                "</speak>";
        ssml = ssml
                .replace("$language$", jobHistory.getLanguage())
                .replace("$voiceName$", jobHistory.getVoiceName())
                .replace("$role$", jobHistory.getRole())
                .replace("$style$", jobHistory.getStyle())
                .replace("$rate$", jobHistory.getRate())
                .replace("$pitch$", jobHistory.getPitch())
                .replace("$textArea$", jobHistory.getTextarea());
        jobHistory.setSsml(ssml);
        // 通过ssml构建语音文件
        SpeechSynthesisResult speechSynthesisResult = speechSynthesizer.SpeakSsml(ssml);
        // 获取生成好的语音字节流
        if (speechSynthesisResult.getReason() == ResultReason.SynthesizingAudioCompleted) {
            jobHistory.setSpeechSynthesisResult(speechSynthesisResult);

            LOGGER.info("Text to speech success, text is [{}]", jobHistory.getTextarea());
            // 保存到本地
            String fileName = jobHistory.setFileNameByVoiceQuality(CommonsUtils.getRandomFileNameByTime());
            AudioDataStream stream = AudioDataStream.fromResult(jobHistory.getSpeechSynthesisResult());
            stream.saveToWavFile(BASE_VOICE_FILE_PATH + fileName);
            boolean saving = true;
            int waitTime = 3;
            while (saving){
                File audioFile = new File(BASE_VOICE_FILE_PATH + fileName);
                if (audioFile.exists()){
                    jobHistory.setFileName(fileName);
                    jobHistory.setJobStatus(true);
                    saving = false;
                    break;
                }else {
                    if (waitTime > 0){
                        try {
                            waitTime--;
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }else {
                        break;
                    }
                }

            }
        }
        else if (speechSynthesisResult.getReason() == ResultReason.Canceled) {
            SpeechSynthesisCancellationDetails cancellation = SpeechSynthesisCancellationDetails.fromResult(speechSynthesisResult);
            LOGGER.error("Text to speech canceled, text is [{}], canceled reason is [{}] ",
                    jobHistory.getTextarea(), cancellation.getReason());
            if (cancellation.getReason() == CancellationReason.Error) {
                LOGGER.error("CANCELED: ErrorCode= [{}], CANCELED: ErrorDetails= [{}], ",
                        cancellation.getErrorCode(), cancellation.getErrorDetails());
            }
        }
        return jobHistory;
    }


    public static SpeechConfig getSpeechConfigFromJobHistory(JobHistory jobHistory){
        SpeechConfig speechConfig = SpeechConfig.fromSubscription(SPEECH_KEY, SPEECH_REGION);
        setVoiceName(speechConfig, jobHistory.getVoiceName());
        return speechConfig;
    }

    /**
     * 设置语言
     * @param speechConfig
     * @param language
     */
    public static void setLanguage(SpeechConfig speechConfig, String language){
        if (language != null){
            switch (language){
                case Constants.LANGUAGE_YUE_CN:
                    speechConfig.setSpeechSynthesisLanguage(Constants.LANGUAGE_YUE_CN);
                    break;
                case Constants.LANGUAGE_DONGBEI:
                    speechConfig.setSpeechSynthesisLanguage(Constants.LANGUAGE_DONGBEI);
                    break;
                case Constants.LANGUAGE_HENAN_CN:
                    speechConfig.setSpeechSynthesisLanguage(Constants.LANGUAGE_HENAN_CN);
                    break;
                case Constants.LANGUAGE_SICHUAN_CN:
                    speechConfig.setSpeechSynthesisLanguage(Constants.LANGUAGE_SICHUAN_CN);
                    break;
                case Constants.LANGUAGE_SHANDONG_CN:
                    speechConfig.setSpeechSynthesisLanguage(Constants.LANGUAGE_SHANDONG_CN);
                    break;
                case Constants.LANGUAGE_TW_CN:
                    speechConfig.setSpeechSynthesisLanguage(Constants.LANGUAGE_TW_CN);
                    break;
                case Constants.LANGUAGE_HK_CN:
                    speechConfig.setSpeechSynthesisLanguage(Constants.LANGUAGE_HK_CN);
                    break;
                case Constants.LANGUAGE_SHAANNXI_CN:
                    speechConfig.setSpeechSynthesisLanguage(Constants.LANGUAGE_SHAANNXI_CN);
                    break;
                default: speechConfig.setSpeechSynthesisLanguage(Constants.LANGUAGE_ZH_CN);
            }
        }

    }

    /**
     * 设置语音包
     * @param speechConfig
     * @param voiceName
     */
    public static void setVoiceName(SpeechConfig speechConfig, String voiceName){
        if (voiceName != null) {
            switch (voiceName){
                case Constants.LANGUAGE_ZH_CN_XIAONAN:
                    speechConfig.setSpeechSynthesisVoiceName(Constants.LANGUAGE_ZH_CN_XIAONAN);
                    break;
                case Constants.LANGUAGE_ZH_CN_XIAOMENG:
                    speechConfig.setSpeechSynthesisVoiceName(Constants.LANGUAGE_ZH_CN_XIAOMENG);
                    break;
                case Constants.LANGUAGE_ZH_CN_XIAOMO:
                    speechConfig.setSpeechSynthesisVoiceName(Constants.LANGUAGE_ZH_CN_XIAOMO);
                    break;
                case Constants.LANGUAGE_ZH_CN_XIAOQIU:
                    speechConfig.setSpeechSynthesisVoiceName(Constants.LANGUAGE_ZH_CN_XIAOQIU);
                    break;
                case Constants.LANGUAGE_ZH_CN_XIAORUI:
                    speechConfig.setSpeechSynthesisVoiceName(Constants.LANGUAGE_ZH_CN_XIAORUI);
                    break;
                case Constants.LANGUAGE_ZH_CN_XIAOSHUANG:
                    speechConfig.setSpeechSynthesisVoiceName(Constants.LANGUAGE_ZH_CN_XIAOSHUANG);
                    break;
                case Constants.LANGUAGE_YUE_CN_XIAOMIN:
                    speechConfig.setSpeechSynthesisVoiceName(Constants.LANGUAGE_YUE_CN_XIAOMIN);
                    break;
                case Constants.LANGUAGE_YUE_CN_YUNSONG:
                    speechConfig.setSpeechSynthesisVoiceName(Constants.LANGUAGE_YUE_CN_YUNSONG);
                    break;
                default: speechConfig.setSpeechSynthesisLanguage(Constants.LANGUAGE_ZH_CN_XIAOCHEN);
            }
        }

    }


    /**
     * 设置speechConfig的音频质量
     * @param speechConfig
     * @param voiceQuality
     */
    public static void setVoiceQuality(SpeechConfig speechConfig, int voiceQuality){
        switch (voiceQuality){
            case 1:
                speechConfig.setSpeechSynthesisOutputFormat(SpeechSynthesisOutputFormat.Audio16Khz32KBitRateMonoMp3);
                break;
            case 2:
                speechConfig.setSpeechSynthesisOutputFormat(SpeechSynthesisOutputFormat.Audio16Khz128KBitRateMonoMp3);
                break;
            case 3:
                speechConfig.setSpeechSynthesisOutputFormat(SpeechSynthesisOutputFormat.Audio24Khz160KBitRateMonoMp3);
                break;
            case 5:
                speechConfig.setSpeechSynthesisOutputFormat(SpeechSynthesisOutputFormat.Riff16Khz16BitMonoPcm);
                break;
            case 6:
                speechConfig.setSpeechSynthesisOutputFormat(SpeechSynthesisOutputFormat.Riff24Khz16BitMonoPcm);
                break;
            case 7:
                speechConfig.setSpeechSynthesisOutputFormat(SpeechSynthesisOutputFormat.Riff48Khz16BitMonoPcm);
                break;
            default: speechConfig.setSpeechSynthesisOutputFormat(SpeechSynthesisOutputFormat.Audio48Khz192KBitRateMonoMp3);
        }
    }

}
