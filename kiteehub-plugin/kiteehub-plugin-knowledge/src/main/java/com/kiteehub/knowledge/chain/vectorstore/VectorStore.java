package com.kiteehub.knowledge.chain.vectorstore;

import java.util.List;
import java.util.Map;

/**
 * 向量操作
 * Created by Ranger on 2023/11/21.
 */
public interface VectorStore {

    void storeEmbeddings(List<String> chunkList, List<List<Double>> vectorList, String kid, String docId, Boolean firstTime);
    List<Map<String,Object>> listByKId(String kid, String docId);
    void removeByRowId(String kid,String docId,Long rowId);
    void removeByDocId(String kid,String docId);
    void removeByKid(String kid);
    List<String> nearest(List<Double> queryVector,String kid);
    List<String> nearest(String query,String kid);
}
