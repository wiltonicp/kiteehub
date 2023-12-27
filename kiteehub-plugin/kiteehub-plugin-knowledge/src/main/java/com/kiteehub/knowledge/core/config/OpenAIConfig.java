package com.kiteehub.knowledge.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created By Ranger on 2023/4/4.
 */
@Getter
@Setter
@Configuration
public class OpenAIConfig {

    @Value("${chain.vectorizer.openai.keys}")
    private List<String> openaiApiKeys = new ArrayList<>();

    private List<String> azureApiKeys = new ArrayList<>();

    private Map<String,String> sparkDeskParam = new HashMap<>();
    @Value("${chain.vectorizer.openai.host}")
    private String openaiApiHost;

    private String azureApiHost;


}
