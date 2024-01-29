package com.kiteehub.dev.core.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 树型结构
 *
 * @author Ranger
 * @date 2022/9/2 15:58
 */
@Data
public class DictNode {

    /** id */
    @ApiModelProperty(value = "id", position = 1)
    private String id;

    /** 父id */
    @ApiModelProperty(value = "父id", position = 2)
    private String parentId;

    /** 字典文字 */
    @ApiModelProperty(value = "字典文字", position = 3)
    private String dictLabel;

    /** 字典值 */
    @ApiModelProperty(value = "字典值", position = 4)
    private String dictValue;

    /** 分类 */
    @ApiModelProperty(value = "分类", position = 5)
    private String category;

    /** 排序码 */
    @ApiModelProperty(value = "排序码", position = 6)
    private Integer sortCode;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 7)
    private String extJson;
}
