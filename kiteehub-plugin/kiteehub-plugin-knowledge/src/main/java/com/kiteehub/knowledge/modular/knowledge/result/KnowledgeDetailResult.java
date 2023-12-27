package com.kiteehub.knowledge.modular.knowledge.result;

import com.kiteehub.knowledge.modular.attach.entity.KnowledgeAttach;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ranger
 * @date  2023/12/27
 **/
@Data
public class KnowledgeDetailResult {

    private Integer id;

    /**
     * 知识库ID
     */
    private String kid;

    /**
     * 知识库名称
     */
    private String kname;

    /**
     *
     */
    private List<KnowledgeAttach> attachList = new ArrayList<>();
}
