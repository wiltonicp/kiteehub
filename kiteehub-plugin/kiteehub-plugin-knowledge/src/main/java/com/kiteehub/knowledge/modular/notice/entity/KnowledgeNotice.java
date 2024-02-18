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
package com.kiteehub.knowledge.modular.notice.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.kiteehub.common.pojo.CommonEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 通知公告实体
 *
 * @author Ranger
 * @date  2024/01/29 14:39
 **/
@Getter
@Setter
@TableName("knowledge_notice")
public class KnowledgeNotice extends CommonEntity {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 主题 */
    @ApiModelProperty(value = "主题", position = 2)
    private String subject;

    /** 正文 */
    @ApiModelProperty(value = "正文", position = 3)
    private String content;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 4)
    private String extJson;

    /** 社保人员类型 */
    @ApiModelProperty(value = "社保人员类型", position = 5)
    private String personnelType;

    /** 摘要 */
    @ApiModelProperty(value = "摘要", position = 6)
    @TableField(exist = false)
    private String summary;
}
