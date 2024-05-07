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
import com.kiteehub.common.pojo.CommonEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 知识库附件实体
 *
 * @author Ranger
 * @date  2023/12/27 14:00
 **/
@Getter
@Setter
@TableName("knowledge_attach")
public class KnowledgeAttach extends CommonEntity {

    /** ID */
    @TableId
    @ApiModelProperty(value = "ID", position = 1)
    private String id;

    /** 知识库ID */
    @ApiModelProperty(value = "知识库ID", position = 2)
    private String kid;

    /** 文档ID */
    @ApiModelProperty(value = "文档ID", position = 3)
    private String docId;

    /** 文档名称 */
    @ApiModelProperty(value = "文档名称", position = 4)
    private String docName;

    /** 文档类型 */
    @ApiModelProperty(value = "文档类型", position = 5)
    private String docType;

    /** 文档内容 */
    @ApiModelProperty(value = "文档内容", position = 6)
    private String content;

    /** 数据总量 */
    @ApiModelProperty(value = "数据总量", position = 7)
    private Integer totalData;

    /** 状态 */
    @ApiModelProperty(value = "状态", position = 8)
    private String gatherState;

    /** 社保人员类型 */
    @ApiModelProperty(value = "社保人员类型", position = 9)
    private String personnelType;

    /** 索引模型 */
    @TableField(exist = false)
    @ApiModelProperty(value = "索引模型", position = 10)
    private String indexModel = "Embedding-ada-002";

    /** 知识处理模型 */
    @TableField(exist = false)
    @ApiModelProperty(value = "知识处理模型", position = 11)
    private String fileModel = "Kite-Ai-16k";

    /** 区域ids */
    @TableField(exist = false)
    @ApiModelProperty(value = "区域ids", position = 12)
    private List<String> areaIds;
}
