package com.kiteehub.knowledge.modular.knowledge.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ApiModel(description = "知识库上传附件入参")
public class KnowledgeUploadParam {

    @ApiModelProperty(value = "知识库id", required = true, position = 1)
    @NotBlank(message = "kid不能为空")
    private String kid;

    @ApiModelProperty(value = "附件", required = true, position = 2)
    private MultipartFile file;
}
