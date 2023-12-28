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
package com.kiteehub.knowledge.modular.knowledge.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.kiteehub.common.pojo.CommonEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 知识库实体
 *
 * @author Ranger
 * @date  2023/12/27 10:20
 **/
@Getter
@Setter
@TableName("knowledge")
public class Knowledge extends CommonEntity {

    /** ID */
    @TableId
    @ApiModelProperty(value = "ID", position = 1)
    private String id;

    /** ID */
    @ApiModelProperty(value = "ID", position = 2)
    private String kid;

    /** 用户ID */
    @ApiModelProperty(value = "用户ID", position = 3)
    private String uid;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 4)
    private String kname;

    /** 险别ID */
    @ApiModelProperty(value = "险别ID", position = 5)
    private Long insureTypeId;
}
