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
package com.kiteehub.knowledge.modular.attach.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 知识附件分片表实体
 *
 * @author Ranger
 * @date  2023/12/28 14:52
 **/
@Getter
@Setter
@TableName("knowledge_attach_chunk")
public class KnowledgeAttachChunk {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private Long id;

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

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 6)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 7)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 8)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 9)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 10)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;
}
