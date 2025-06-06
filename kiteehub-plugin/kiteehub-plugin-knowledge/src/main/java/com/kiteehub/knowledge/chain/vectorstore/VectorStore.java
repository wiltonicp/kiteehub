package com.kiteehub.knowledge.chain.vectorstore;

import com.kiteehub.knowledge.modular.attach.entity.KnowledgeAttachChunk;

import java.util.List;
import java.util.Map;

/**
 * 向量操作
 * Created by Ranger on 2023/11/21.
 */
public interface VectorStore {

    void createSchema(String kid, String kname);

    void storeEmbeddings(List<KnowledgeAttachChunk> attachChunkList, List<List<Double>> vectorList, String kid, String kname, String docId, Boolean firstTime);

    List<Map<String, Object>> listByKId(String kid, String docId);

    void removeByRowId(String kid, String docId, Long rowId);

    void removeByDocId(String kid, String docId);

    void removeByKid(String kid);

    List<String> nearest(List<Double> queryVector, String kid, List<String> partitionNames);

    List<String> nearest(String query, String kid);
}
