package com.kiteehub.knowledge.modular.knowledge.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@ApiModel(description = "知识库入参")
public class KnowledgeSaveParam {

    @ApiModelProperty(value = "知识库名称")
    private String kname;

    @ApiModelProperty(value = "附件")
    private MultipartFile file;

    /**
     * 区域ID集合
     */
    @ApiModelProperty(value = "区域ID集合", required = true, position = 3)
    private List<String> areaIds;
}
