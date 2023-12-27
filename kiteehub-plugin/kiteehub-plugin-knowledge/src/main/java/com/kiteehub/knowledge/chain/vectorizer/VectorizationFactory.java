package com.kiteehub.knowledge.chain.vectorizer;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 向量化工厂：提供各种向量化模型的能力
 * Created by Ranger on 2023/11/21.
 */
@Slf4j
@Component
public class VectorizationFactory {

    @Value("${chain.vectorizer.type}")
    private String type;
    private final OpenAiVectorization openAiVectorization;
    private final LocalAiVectorization localAiVectorization;

    public VectorizationFactory(OpenAiVectorization openAiVectorization,
                                LocalAiVectorization localAiVectorization) {
        this.openAiVectorization = openAiVectorization;
        this.localAiVectorization = localAiVectorization;
    }

    public Vectorization getEmbedding(){
        if ("openai".equals(type)){
            return openAiVectorization;
        }else if ("local".equals(type)){
            return localAiVectorization;
        }else {
            return null;
        }

    }
}
