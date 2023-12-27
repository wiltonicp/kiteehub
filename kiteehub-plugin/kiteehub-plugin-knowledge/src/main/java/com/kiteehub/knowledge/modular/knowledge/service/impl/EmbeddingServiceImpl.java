package com.kiteehub.knowledge.modular.knowledge.service.impl;

import com.kiteehub.knowledge.chain.vectorizer.Vectorization;
import com.kiteehub.knowledge.chain.vectorizer.VectorizationFactory;
import com.kiteehub.knowledge.chain.vectorstore.VectorStore;
import com.kiteehub.knowledge.modular.knowledge.service.EmbeddingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 向量数据库库接口
 *
 * @author Ranger
 * @date  2023/12/27
 **/
@Service
@AllArgsConstructor
public class EmbeddingServiceImpl implements EmbeddingService {

    private final VectorStore vectorStore;
    private final VectorizationFactory vectorizationFactory;

    /**
     * 保存向量数据库
     * @param chunkList         文档按行切分的片段
     * @param kid               知识库ID
     * @param docId             文档ID
     */
    @Override
    public void storeEmbeddings(List<String> chunkList, String kid, String docId, Boolean firstTime) {
        Vectorization vectorization = vectorizationFactory.getEmbedding();
        List<List<Double>> vectorList = vectorization.batchVectorization(chunkList);
        vectorStore.storeEmbeddings(chunkList,vectorList,kid,docId,firstTime);
    }

    @Override
    public void removeByDocId(String kid,String docId) {
        vectorStore.removeByDocId(kid,docId);
    }

    @Override
    public void removeByKid(String kid) {
        vectorStore.removeByKid(kid);
    }

    @Override
    public List<Double> getQueryVector(String query) {
        Vectorization vectorization = vectorizationFactory.getEmbedding();
        List<Double> queryVector = vectorization.singleVectorization(query);
        return queryVector;
    }
}
