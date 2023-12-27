package com.kiteehub.knowledge.core.config;

import com.kiteehub.knowledge.core.strategy.PollingStrategy;
import com.unfbx.chatgpt.OpenAiClient;
import com.unfbx.chatgpt.interceptor.OpenAILogger;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class OpenAiClientConfig {

    private final OpenAIConfig openAIConfig;

    public OpenAiClientConfig(OpenAIConfig openAIConfig) {
        this.openAIConfig = openAIConfig;
    }

    @Bean
    public OpenAiClient openAiClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new OpenAILogger());
//        GptKeyOpenAiAuthInterceptor gptKeyOpenAiAuthInterceptor = new GptKeyOpenAiAuthInterceptor(openAIConfig.getOpenaiApiKeys());
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                //.proxy(proxy)//自定义代理
                //.addInterceptor(gptKeyOpenAiAuthInterceptor)
                .addInterceptor(httpLoggingInterceptor)//自定义日志
                .connectTimeout(30, TimeUnit.MINUTES)//自定义超时时间
                .writeTimeout(30, TimeUnit.MINUTES)//自定义超时时间
                .readTimeout(30, TimeUnit.MINUTES)//自定义超时时间
                .build();
        return OpenAiClient.builder()
                .apiHost(openAIConfig.getOpenaiApiHost())
                .apiKey(openAIConfig.getOpenaiApiKeys())
                .keyStrategy(new PollingStrategy())
                .okHttpClient(okHttpClient).build();
    }
}
