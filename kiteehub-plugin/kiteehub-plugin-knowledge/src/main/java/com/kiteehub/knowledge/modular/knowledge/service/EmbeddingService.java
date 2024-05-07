package com.kiteehub.knowledge.modular.knowledge.service;

import com.kiteehub.knowledge.modular.attach.entity.KnowledgeAttachChunk;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Map;

/**
 * 向量数据库库接口
 *
 * @author Ranger
 * @date  2023/12/27
 **/
public interface EmbeddingService {

    void createSchema(String kid, String kname);

    void storeEmbeddings(List<KnowledgeAttachChunk> attachChunkList, String kid, String kname, String docId, Boolean firstTime);

    void removeByDocId(String kid,String docId);

    void removeByRowId(String kid,String docId, Long rowId);

    void removeByKid(String kid);

    List<Double> getQueryVector(String query);

    List<Map<String,Object>> getListByKId(String kid, String docId);
}
