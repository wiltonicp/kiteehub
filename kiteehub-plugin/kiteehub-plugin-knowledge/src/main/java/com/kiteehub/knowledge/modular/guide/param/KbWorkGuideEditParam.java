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
package com.kiteehub.knowledge.modular.guide.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务指南编辑参数
 *
 * @author Ranger
 * @date  2024/01/29 14:15
 **/
@Getter
@Setter
public class KbWorkGuideEditParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 分类 */
    @ApiModelProperty(value = "分类", required = true, position = 2)
    @NotBlank(message = "category不能为空")
    private String category;

    /** 事项代码 */
    @ApiModelProperty(value = "事项代码", position = 3)
    private String eventCode;

    /** 办理对象 */
    @ApiModelProperty(value = "办理对象", position = 4)
    private String handleObj;

    /** 办理依据 */
    @ApiModelProperty(value = "办理依据", position = 5)
    private String handleBasis;

    /** 办理部门 */
    @ApiModelProperty(value = "办理部门", position = 6)
    private String handleDept;

    /** 办理材料 */
    @ApiModelProperty(value = "办理材料", position = 7)
    private String handleMaterial;

    /** 办理时限 */
    @ApiModelProperty(value = "办理时限", position = 8)
    private String handleTime;

    /** 流程图 */
    @ApiModelProperty(value = "流程图", position = 9)
    private String flowChart;

}
