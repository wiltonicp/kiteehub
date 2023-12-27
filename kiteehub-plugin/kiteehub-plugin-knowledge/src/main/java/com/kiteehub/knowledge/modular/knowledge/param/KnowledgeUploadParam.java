package com.kiteehub.knowledge.modular.knowledge.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@ApiModel(description = "知识库上传附件入参")
public class KnowledgeUploadParam {

    @ApiModelProperty(value = "知识库id")
    private String kid;

    @ApiModelProperty(value = "附件")
    private MultipartFile file;
}
