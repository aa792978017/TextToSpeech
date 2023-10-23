package com.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ServletComponentScan
public class TextToSpeechApplication {

  public static void main(String[] args) {
    SpringApplication.run(TextToSpeechApplication.class, args);
  }

}
