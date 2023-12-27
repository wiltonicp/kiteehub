package com.kiteehub.knowledge.modular.knowledge.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "知识库删除附件入参")
public class KnowledgeAttachRemoveParam {

    @ApiModelProperty(value = "知识库id")
    private String kid;

    @ApiModelProperty(value = "文档id")
    private String docId;
}
