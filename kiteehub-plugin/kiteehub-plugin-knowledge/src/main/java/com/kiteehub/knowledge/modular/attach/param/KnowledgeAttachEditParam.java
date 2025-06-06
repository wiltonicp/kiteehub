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
import java.util.List;

/**
 * 知识库附件编辑参数
 *
 * @author Ranger
 * @date  2023/12/27 14:00
 **/
@Getter
@Setter
public class KnowledgeAttachEditParam {

    /** ID */
    @ApiModelProperty(value = "ID", required = true, position = 1)
    @NotNull(message = "id不能为空")
    private String id;

    /** 文档名称 */
    @ApiModelProperty(value = "文档名称", position = 2)
    @NotNull(message = "docName不能为空")
    private String docName;

    @ApiModelProperty(value = "区域ids", position = 3)
    private List<String> areaIds;

    /** 社保人员类型 */
    @ApiModelProperty(value = "社保人员类型", position = 4)
    private String personnelType;

}
