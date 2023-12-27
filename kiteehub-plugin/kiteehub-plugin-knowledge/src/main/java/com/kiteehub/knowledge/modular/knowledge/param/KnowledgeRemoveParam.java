package com.kiteehub.knowledge.modular.knowledge.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "知识库删除入参")
public class KnowledgeRemoveParam {

    @ApiModelProperty(value = "知识库id")
    private String kid;
}
