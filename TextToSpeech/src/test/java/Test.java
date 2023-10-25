import com.ai.domain.entity.JobHistory;
import com.ai.utils.Constants;
import com.ai.utils.TextToSpeechUtils;
import com.microsoft.cognitiveservices.speech.*;

import java.util.concurrent.ExecutionException;

public class Test {

    private static final String SPEECH_KEY = "68bd2b5261c5424dbb0d32db861c98e0";
    private static final String SPEECH_REGION = "eastasia";

    void main01() {
//        SpeechConfig speechConfig = SpeechConfig.fromSubscription(SPEECH_KEY,SPEECH_REGION);
//        speechConfig.setSpeechSynthesisLanguage(Constants.LANGUAGE_ZH_CN);
//        speechConfig.setSpeechSynthesisVoiceName(Constants.LANGUAGE_ZH_CN_XIAOCHEN);
//        String path = "D:\\project\\TextToSpeech\\src\\main\\resources\\mp3\\file.wav";
//        AudioConfig audioConfig = AudioConfig.fromWavFileOutput(path);
//
//        SpeechSynthesizer synthesizer = new SpeechSynthesizer(speechConfig, audioConfig);
//        synthesizer.SpeakText("I'm excited to try text-to-speech");


        JobHistory info = new JobHistory();
        info.setTextarea("我是小王，我爱小汤");
        info.setVoiceName(Constants.LANGUAGE_ZH_CN_XIAORUI);
        info.setRole("Girl");
        info.setStyle("clam");
        info.setVoiceQuality(0);
        info.setRate("default");
        info.setPitch("default");
        TextToSpeechUtils.textToSpeech(info);
    }

    void test02() throws InterruptedException, ExecutionException {
        SpeechConfig speechConfig = SpeechConfig.fromSubscription(SPEECH_KEY, SPEECH_REGION);
        speechConfig.setSpeechSynthesisOutputFormat(SpeechSynthesisOutputFormat.Riff24Khz16BitMonoPcm);
        speechConfig.setSpeechSynthesisVoiceName("zh-CN-XiaomoNeural");
//        AudioConfig audioConfig = AudioConfig.fromWavFileOutput("src/resources/mp3/file.wav");
        SpeechSynthesizer speechSynthesizer = new SpeechSynthesizer(speechConfig,null);
        // Get text from the console and synthesize to the default speaker.
        System.out.println("Enter some text that you want to speak >");
        String text = "小红是世界上最美的女孩";
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
