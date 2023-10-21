package com.ai.utils;

import com.microsoft.cognitiveservices.speech.*;
import java.util.concurrent.ExecutionException;

public class SpeechSynthesis {
    // This example requires environment variables named "SPEECH_KEY" and "SPEECH_REGION"
    private static String speechKey = "ef516fc7ca734bbdb63722c6656515c4";
    private static String speechRegion = "eastasia";

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        SpeechConfig speechConfig = SpeechConfig.fromSubscription(speechKey, speechRegion);
        speechConfig.setSpeechSynthesisOutputFormat(SpeechSynthesisOutputFormat.Riff24Khz16BitMonoPcm);
        speechConfig.setSpeechSynthesisVoiceName("zh-CN-XiaomoNeural");
//        AudioConfig audioConfig = AudioConfig.fromWavFileOutput("src/resources/mp3/file.wav");
        SpeechSynthesizer speechSynthesizer = new SpeechSynthesizer(speechConfig,null);
        // Get text from the console and synthesize to the default speaker.
        System.out.println("Enter some text that you want to speak >");
        String text = "小汤是世界上最美的女孩，我爱小汤";
        SpeechSynthesisResult speechSynthesisResult = speechSynthesizer.SpeakText(text);
        AudioDataStream stream = AudioDataStream.fromResult(speechSynthesisResult);
        stream.saveToWavFile("src/resources/mp3/file.wav");

        if (speechSynthesisResult.getReason() == ResultReason.SynthesizingAudioCompleted) {
            System.out.println("Speech synthesized to speaker for text [" + text + "]");
        }
        else if (speechSynthesisResult.getReason() == ResultReason.Canceled) {
            SpeechSynthesisCancellationDetails cancellation = SpeechSynthesisCancellationDetails.fromResult(speechSynthesisResult);
            System.out.println("CANCELED: Reason=" + cancellation.getReason());

            if (cancellation.getReason() == CancellationReason.Error) {
                System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
                System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
                System.out.println("CANCELED: Did you set the speech resource key and region values?");
            }
        }

        System.exit(0);
    }
}
