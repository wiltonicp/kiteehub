/*
 * Copyright [2022] [https://www.kiteehub.com]
 *
 * Kiteehub是内部代码，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Kiteehub源码头部的版权声明。
 * 3.本项目代码使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.kiteehub.com
 * 5.本项目只可用于内部开发，如有问题可联系团队wilton.icp@gmail.com商议合作。
 */
package com.kiteehub.knowledge.modular.attach.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 知识附件分片表添加参数
 *
 * @author Ranger
 * @date  2023/12/28 14:52
 **/
@Getter
@Setter
public class KnowledgeAttachChunkAddParam {

    /** 知识库ID */
    @ApiModelProperty(value = "知识库ID", position = 2)
    private String kid;

    /** 文档ID */
    @ApiModelProperty(value = "文档ID", position = 3)
    private String docId;

    /** 行ID */
    @ApiModelProperty(value = "行ID", position = 4)
    private Long rowId;

    /** 切片文章内容 */
    @ApiModelProperty(value = "切片文章内容", position = 5)
    private String content;

}
