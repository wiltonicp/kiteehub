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
package com.kiteehub.knowledge.modular.robot.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

/**
 * 智能客服添加参数
 *
 * @author Ranger
 * @date  2024/01/03 11:44
 **/
@Getter
@Setter
public class KnowledgeRobotAddParam {

    /** 客服名称 */
    @ApiModelProperty(value = "客服名称", required = true, position = 2)
    @NotBlank(message = "name不能为空")
    private String name;

    /** 描述 */
    @ApiModelProperty(value = "描述", position = 3)
    private String description;

    /** 模型 */
    @ApiModelProperty(value = "模型", required = true, position = 4)
    @NotBlank(message = "model不能为空")
    private String model;

    /** 采样温度 */
    @ApiModelProperty(value = "采样温度", position = 5)
    private BigDecimal temperature;

    /** 回复上限 */
    @ApiModelProperty(value = "回复上限", position = 6)
    private Integer maxTokens;

    /** 提示词 */
    @ApiModelProperty(value = "提示词", position = 7)
    private String prompt;

    /** 对话开场白 */
    @ApiModelProperty(value = "对话开场白", position = 8)
    private String prologue;

    /** 搜索参数_字典值 */
    @ApiModelProperty(value = "搜索参数", position = 9)
    private String searchParam;

    /** 搜索参数_字典值 */
    @ApiModelProperty(value = "空搜索回复", position = 10)
    private String emptySearchReply;

    /** 知识库ID集合 */
    @ApiModelProperty(value = "知识库ID集合", position = 11)
    private List<String> kids;

}
