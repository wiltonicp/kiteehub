package com.kiteehub.knowledge.chain.vectorizer;

import com.unfbx.chatgpt.OpenAiClient;
import com.unfbx.chatgpt.entity.embeddings.Embedding;
import com.unfbx.chatgpt.entity.embeddings.EmbeddingResponse;
import com.unfbx.chatgpt.entity.embeddings.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * openai向量转换
 * Created by Ranger on 2023/11/21.
 */
@Slf4j
@Primary
@Component
public class OpenAiVectorization implements Vectorization {

    @Value("${chain.vectorizer.openai.model}")
    private String embeddingModel;

    @Autowired
    private OpenAiClient openAiClient;

    @Override
    public List<List<Double>> batchVectorization(List<String> chunkList) {
        Embedding embeddingRequest = Embedding
                .builder()
                .model(embeddingModel)
                .input(chunkList)
                .build();
        EmbeddingResponse embeddings = openAiClient.embeddings(embeddingRequest);
        List<List<Double>> vectorList = new ArrayList<>();
        for (Item embeddingData : embeddings.getData()){
            List<BigDecimal> embedding = embeddingData.getEmbedding();
            List<Double> vector = convertBigDecimalListToDoubleList(embedding);
            vectorList.add(vector);
        }
        return vectorList;
    }

    @Override
    public List<Double> singleVectorization(String chunk) {
        List<String> chunkList = new ArrayList<>();
        chunkList.add(chunk);
        List<List<Double>> vectorList = batchVectorization(chunkList);
        return vectorList.get(0);
    }

    public static List<Double> convertBigDecimalListToDoubleList(List<BigDecimal> bigDecimalList) {
        return bigDecimalList.stream()
                .map(BigDecimal::doubleValue)
                .collect(Collectors.toList());
    }
}