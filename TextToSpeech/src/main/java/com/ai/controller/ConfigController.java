package com.ai.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/config")
public class ConfigController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigController.class);

    @Value("${app.max-word-permits-per-transform}")
    private Integer wordPermitsPerTransform;

    @GetMapping
    public Map<String, String> globalConfig(){
        HashMap<String, String> config = new HashMap<>();
        config.put("max_word_permits_per_transform", String.valueOf(wordPermitsPerTransform));
        LOGGER.info("Get global config...");
        return config;
    }
}
