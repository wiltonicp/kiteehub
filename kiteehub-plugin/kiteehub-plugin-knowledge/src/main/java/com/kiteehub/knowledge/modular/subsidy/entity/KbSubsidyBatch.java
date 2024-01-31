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
package com.kiteehub.knowledge.modular.subsidy.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.kiteehub.common.pojo.CommonEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 补贴公示实体
 *
 * @author Ranger
 * @date  2024/01/31 14:12
 **/
@Getter
@Setter
@TableName("kb_subsidy_batch")
public class KbSubsidyBatch extends CommonEntity {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 补贴类型 */
    @ApiModelProperty(value = "补贴类型", position = 2)
    private String subsidyType;

    /** 批次 */
    @ApiModelProperty(value = "批次", position = 3)
    private Integer batch;
}
