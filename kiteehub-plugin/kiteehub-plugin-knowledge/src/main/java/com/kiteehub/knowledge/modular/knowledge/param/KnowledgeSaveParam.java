package com.kiteehub.knowledge.modular.knowledge.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@ApiModel(description = "知识库入参")
public class KnowledgeSaveParam {

    @ApiModelProperty(value = "知识库名称")
    private String kname;

    @ApiModelProperty(value = "附件")
    private MultipartFile file;
}
