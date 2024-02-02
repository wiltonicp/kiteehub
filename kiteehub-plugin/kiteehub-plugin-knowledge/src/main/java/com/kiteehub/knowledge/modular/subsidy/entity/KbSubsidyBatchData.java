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
 * 待遇补贴批次详情实体
 *
 * @author Ranger
 * @date  2024/01/31 15:06
 **/
@Getter
@Setter
@TableName("kb_subsidy_batch_data")
public class KbSubsidyBatchData extends CommonEntity {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 批次ID */
    @ApiModelProperty(value = "批次ID", position = 2)
    private String batchId;

    /** 批次顺序 */
    @ApiModelProperty(value = "批次顺序", position = 2)
    private Integer batchNum;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 3)
    private String fullName;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 4)
    private String cardId;

    /** 单位编码 */
    @ApiModelProperty(value = "单位编码", position = 5)
    private String unitCode;

    /** 单位名称 */
    @ApiModelProperty(value = "单位名称", position = 6)
    private String unitName;

    /** 待遇名称 */
    @TableField(exist = false)
    @ApiModelProperty(value = "待遇名称", position = 7)
    private String subsidyName;

    /** 金额 */
    @ApiModelProperty(value = "金额", position = 8)
    private String amount;
}
