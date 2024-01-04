package com.kiteehub.knowledge.modular.attach.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 文章区域实体
 *
 * @author Ranger
 * @date  2023/12/28
 **/
@Getter
@Setter
@Builder
@AllArgsConstructor
@TableName("knowledge_article_area")
public class KnowledgeArticleArea {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 文章ID */
    @ApiModelProperty(value = "文章ID", position = 2)
    private String articleId;

    /** 区域ID */
    @ApiModelProperty(value = "区域ID", position = 3)
    private String areaId;
}
