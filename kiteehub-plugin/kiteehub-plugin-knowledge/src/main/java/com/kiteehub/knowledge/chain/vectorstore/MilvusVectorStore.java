package com.kiteehub.knowledge.chain.vectorstore;

import com.alibaba.fastjson.JSON;
import com.google.protobuf.ProtocolStringList;
import com.kiteehub.knowledge.modular.attach.entity.KnowledgeAttachChunk;
import io.milvus.client.MilvusServiceClient;
import io.milvus.grpc.DataType;
import io.milvus.grpc.MutationResult;
import io.milvus.grpc.SearchResults;
import io.milvus.grpc.ShowPartitionsResponse;
import io.milvus.param.*;
import io.milvus.param.collection.CreateCollectionParam;
import io.milvus.param.collection.DropCollectionParam;
import io.milvus.param.collection.FieldType;
import io.milvus.param.collection.LoadCollectionParam;
import io.milvus.param.dml.DeleteParam;
import io.milvus.param.dml.InsertParam;
import io.milvus.param.dml.SearchParam;
import io.milvus.param.highlevel.dml.DeleteIdsParam;
import io.milvus.param.highlevel.dml.SearchSimpleParam;
import io.milvus.param.highlevel.dml.response.DeleteResponse;
import io.milvus.param.index.CreateIndexParam;
import io.milvus.param.partition.CreatePartitionParam;
import io.milvus.param.partition.DropPartitionParam;
import io.milvus.param.partition.ReleasePartitionsParam;
import io.milvus.param.partition.ShowPartitionsParam;
import io.milvus.response.QueryResultsWrapper;
import io.milvus.response.SearchResultsWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by Ranger on 2023/11/21.
 */
@Slf4j
@Service
public class MilvusVectorStore implements VectorStore {

    @Value("${chain.vector.milvus.host}")
    private String host;
    @Value("${chain.vector.milvus.port}")
    private Integer port;

    @Value("${chain.vector.milvus.dimension}")
    private Integer dimension;

    @Value("${chain.vector.milvus.collection}")
    private String collectionName;

    private MilvusServiceClient milvusServiceClient;

    @PostConstruct
    public void init() {
        milvusServiceClient = new MilvusServiceClient(
                ConnectParam.newBuilder()
                        .withHost(host)
                        .withPort(port)
                        .withDatabaseName("default")
                        .build()
        );
    }

    public MilvusVectorStore() {

    }

    private void createSchema(String kid) {
        FieldType primaryField = FieldType.newBuilder()
                .withName("row_id")
                .withDataType(DataType.Int64)
                .withPrimaryKey(true)
                .withAutoID(false)
                .build();
        FieldType contentField = FieldType.newBuilder()
                .withName("content")
                .withDataType(DataType.VarChar)
                .withMaxLength(1000)
                .build();
        FieldType kidField = FieldType.newBuilder()
                .withName("kid")
                .withDataType(DataType.VarChar)
                .withMaxLength(20)
                .build();
        FieldType docIdField = FieldType.newBuilder()
                .withName("docId")
                .withDataType(DataType.VarChar)
                .withMaxLength(20)
                .build();
        FieldType vectorField = FieldType.newBuilder()
                .withName("fv")
                .withDataType(DataType.FloatVector)
                .withDimension(dimension)
                .build();
        CreateCollectionParam createCollectionReq = CreateCollectionParam.newBuilder()
                .withCollectionName(collectionName + kid)
                .withDescription("local knowledge")
                .addFieldType(primaryField)
                .addFieldType(contentField)
                .addFieldType(kidField)
                .addFieldType(docIdField)
                .addFieldType(vectorField)
                .build();
        milvusServiceClient.createCollection(createCollectionReq);

        // 创建向量的索引
        IndexType INDEX_TYPE = IndexType.IVF_FLAT;
        String INDEX_PARAM = "{\"nlist\":1024}";
        milvusServiceClient.createIndex(
                CreateIndexParam.newBuilder()
                        .withCollectionName(collectionName + kid)
                        .withFieldName("fv")
                        .withIndexType(INDEX_TYPE)
                        .withMetricType(MetricType.IP)
                        .withExtraParam(INDEX_PARAM)
                        .withSyncMode(Boolean.FALSE)
                        .build()
        );

    }

    @Override
    public void storeEmbeddings(List<KnowledgeAttachChunk> attachChunkList, List<List<Double>> vectorList, String kid, String docId, Boolean firstTime) {
        if (firstTime) {
            createSchema(kid);
        }
        milvusServiceClient.createPartition(
                CreatePartitionParam.newBuilder()
                        .withCollectionName(collectionName + kid)
                        .withPartitionName(docId)
                        .build()
        );
        List<List<Float>> vectorFloatList = new ArrayList<>();
        List<String> kidList = new ArrayList<>();
        List<String> docIdList = new ArrayList<>();
        List<String> chunkList = new ArrayList<>();
        List<Long> rowIds = new ArrayList<>();
        for (int i = 0; i < attachChunkList.size(); i++) {
            List<Double> vector = vectorList.get(i);
            List<Float> vfList = new ArrayList<>();
            for (int j = 0; j < vector.size(); j++) {
                Double value = vector.get(j);
                vfList.add(value.floatValue());
            }
            rowIds.add(attachChunkList.get(i).getRowId());
            chunkList.add(attachChunkList.get(i).getContent());
            vectorFloatList.add(vfList);
            kidList.add(kid);
            docIdList.add(docId);
        }
        List<InsertParam.Field> fields = new ArrayList<>();
        fields.add(new InsertParam.Field("row_id", rowIds));
        fields.add(new InsertParam.Field("content", chunkList));
        fields.add(new InsertParam.Field("kid", kidList));
        fields.add(new InsertParam.Field("docId", docIdList));
        fields.add(new InsertParam.Field("fv", vectorFloatList));

        InsertParam insertParam = InsertParam.newBuilder()
                .withCollectionName(collectionName + kid)
                .withPartitionName(docId)
                .withFields(fields)
                .build();
        milvusServiceClient.insert(insertParam);
        // milvus在将数据装载到内存后才能进行向量计算
        milvusServiceClient.loadCollection(LoadCollectionParam.newBuilder().withCollectionName(collectionName + kid).build());
    }

    @Override
    public List<Map<String, Object>> listByKId(String kid, String docId) {
        List<List<Float>> vector = new ArrayList<>();
        SearchParam searchParam = SearchParam.newBuilder()
                .withCollectionName(collectionName + kid)
                .withVectors(vector)
                .withTopK(100).build();
        R<SearchResults> respSearch = milvusServiceClient.search(searchParam);
        SearchResultsWrapper wrapperSearch = new SearchResultsWrapper(respSearch.getData().getResults());
        List<QueryResultsWrapper.RowRecord> rowRecords = wrapperSearch.getRowRecords();

        List<Map<String, Object>> resultList = new ArrayList<>();
        if (resultList != null && resultList.size() > 0) {
            for (int i = 0; i < rowRecords.size(); i++) {
                String rowId = rowRecords.get(i).get("row_id").toString();
                String content = rowRecords.get(i).get("content").toString();
                String fv = rowRecords.get(i).get("fv").toString();
                Map<String, Object> objectHashMap = new HashMap<>();
                objectHashMap.put("rowId", rowId);
                objectHashMap.put("content", content);
                objectHashMap.put("fv", fv);
                resultList.add(objectHashMap);
            }
        }
        log.info("分区下的所有数据:{}", JSON.toJSONString(resultList));
        return resultList;
    }

    @Override
    public void removeByRowId(String kid, String docId, Long rowId) {
        R<DeleteResponse> response = milvusServiceClient.delete(
                DeleteIdsParam.newBuilder()
                .withCollectionName(collectionName + kid)
                .withPrimaryIds(Collections.singletonList(rowId))
                .build());
        log.info("removeByDocId------------->{}", response);
    }

    @Override
    public void removeByDocId(String kid, String docId) {
        /*释放分区*/
        milvusServiceClient.releasePartitions(
                ReleasePartitionsParam.newBuilder()
                        .withCollectionName(collectionName + kid)
                        .withPartitionNames(Collections.singletonList(docId))
                        .build()
        );
        /*删除分区*/
        R<RpcStatus> response = milvusServiceClient.dropPartition(
                DropPartitionParam.newBuilder()
                        .withCollectionName(collectionName + kid)
                        .withPartitionName(docId)
                        .build()
        );
        log.info("removeByDocId------------->{}", response);
    }

    @Override
    public void removeByKid(String kid) {
        R<RpcStatus> response = milvusServiceClient.dropCollection(
                DropCollectionParam.newBuilder()
                        .withCollectionName(collectionName + kid)
                        .build()
        );
        log.info("removeByKid------------->{}", response);
    }

    @Override
    public List<String> nearest(List<Double> queryVector, String kid) {
        List<String> search_output_fields = Arrays.asList("content", "fv");
        List<Float> fv = new ArrayList<>();
        for (int i = 0; i < queryVector.size(); i++) {
            fv.add(queryVector.get(i).floatValue());
        }
        List<List<Float>> vectors = new ArrayList<>();
        vectors.add(fv);
        String search_param = "{\"nprobe\":10, \"offset\":0}";
        SearchParam searchParam = SearchParam.newBuilder()
                .withCollectionName(collectionName + kid)
                .withMetricType(MetricType.IP)
                .withOutFields(search_output_fields)
                .withTopK(10)
                .withVectors(vectors)
                .withVectorFieldName("fv")
                .withParams(search_param)
                .build();
        R<SearchResults> respSearch = milvusServiceClient.search(searchParam);
        SearchResultsWrapper wrapperSearch = new SearchResultsWrapper(respSearch.getData().getResults());
        List<QueryResultsWrapper.RowRecord> rowRecords = wrapperSearch.getRowRecords();

        List<String> resultList = new ArrayList<>();
        if (resultList != null && resultList.size() > 0) {
            for (int i = 0; i < rowRecords.size(); i++) {
                String content = rowRecords.get(i).get("content").toString();
                resultList.add(content);
            }
        }
        return resultList;
    }

    /**
     * milvus 不支持通过文本检索相似性
     *
     * @param query
     * @param kid
     * @return
     */
    @Override
    public List<String> nearest(String query, String kid) {
        return null;
    }
}
