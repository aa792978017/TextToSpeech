import com.ai.pojo.JobInfo;
import com.ai.utils.Constants;
import com.ai.utils.TextToSpeechUtils;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechSynthesizer;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;

public class Test {

    private static final String SPEECH_KEY = "68bd2b5261c5424dbb0d32db861c98e0";
    private static final String SPEECH_REGION = "eastasia";
    public static void main(String[] args) {
//        SpeechConfig speechConfig = SpeechConfig.fromSubscription(SPEECH_KEY,SPEECH_REGION);
//        speechConfig.setSpeechSynthesisLanguage(Constants.LANGUAGE_ZH_CN);
//        speechConfig.setSpeechSynthesisVoiceName(Constants.LANGUAGE_ZH_CN_XIAOCHEN);
//        String path = "D:\\project\\TextToSpeech\\src\\main\\resources\\mp3\\file.wav";
//        AudioConfig audioConfig = AudioConfig.fromWavFileOutput(path);
//
//        SpeechSynthesizer synthesizer = new SpeechSynthesizer(speechConfig, audioConfig);
//        synthesizer.SpeakText("I'm excited to try text-to-speech");


        JobInfo info = new JobInfo();
        info.setTextarea("我是小王，我爱小汤");
        info.setVoiceName(Constants.LANGUAGE_ZH_CN_XIAORUI);
        info.setRole("Girl");
        info.setStyle("clam");
        info.setVoiceQuality(0);
        info.setRate("default");
        info.setPitch("default");
        TextToSpeechUtils.textToSpeech(info);
    }
}
