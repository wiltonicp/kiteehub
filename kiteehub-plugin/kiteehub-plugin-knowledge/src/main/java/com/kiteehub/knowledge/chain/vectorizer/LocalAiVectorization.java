package com.kiteehub.knowledge.chain.vectorizer;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 本地向量化（这里指 weaviate 内置的向量模型）
 * 说明：文本保存到向量库时自动生成文本的向量，所有方法的返回结果为 null
 * Created by Ranger on 2023/11/21.
 */
@Slf4j
@Component
public class LocalAiVectorization implements Vectorization {
    @Override
    public List<List<Double>> batchVectorization(List<String> chunkList) {
        return null;
    }

    @Override
    public List<Double> singleVectorization(String chunk) {
        return null;
    }
}
