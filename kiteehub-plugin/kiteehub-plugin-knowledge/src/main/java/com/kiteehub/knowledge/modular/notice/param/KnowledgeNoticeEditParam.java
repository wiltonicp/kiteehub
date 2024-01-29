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
package com.kiteehub.knowledge.modular.notice.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 通知公告编辑参数
 *
 * @author Ranger
 * @date  2024/01/29 14:39
 **/
@Getter
@Setter
public class KnowledgeNoticeEditParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 主题 */
    @ApiModelProperty(value = "主题", required = true, position = 2)
    @NotBlank(message = "subject不能为空")
    private String subject;

    /** 正文 */
    @ApiModelProperty(value = "正文", required = true, position = 3)
    @NotBlank(message = "content不能为空")
    private String content;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 4)
    private String extJson;

}
