package com.kiteehub.knowledge.modular.knowledge.service;

import java.util.List;
import java.util.Map;

/**
 * 向量数据库库接口
 *
 * @author Ranger
 * @date  2023/12/27
 **/
public interface EmbeddingService {

    void storeEmbeddings(List<String> chunkList, String kid, String docId,Boolean firstTime);

    void removeByDocId(String kid,String docId);

    void removeByKid(String kid);

    List<Double> getQueryVector(String query);

    List<Map<String,Object>> getListByKId(String kid, String docId);
}
