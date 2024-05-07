package com.kiteehub.knowledge.modular.attach.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 知识附件区域实体
 *
 * @author Ranger
 * @date  2023/12/28
 **/
@Getter
@Setter
@Builder
@AllArgsConstructor
@TableName("knowledge_attach_area")
public class KnowledgeAttachArea {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 附件ID */
    @ApiModelProperty(value = "附件ID", position = 2)
    private String attachId;

    /** 区域ID */
    @ApiModelProperty(value = "区域ID", position = 3)
    private String areaId;
}
