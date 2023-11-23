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
package com.kiteehub.gen.modular.config.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import com.kiteehub.common.pojo.CommonEntity;

/**
 * 代码生成详细配置
 *
 * @author yubaoshan
 * @date 2022/10/25 22:33
 **/
@Getter
@Setter
@TableName("GEN_CONFIG")
public class GenConfig extends CommonEntity {

    /** id */
    @ApiModelProperty(value = "id", position = 1)
    private String id;

    /** 基础ID */
    @ApiModelProperty(value = "基础ID", position = 2)
    private String basicId;

    /** 是否主键 */
    @ApiModelProperty(value = "是否主键", position = 3)
    private String isTableKey;

    /** 字段 */
    @ApiModelProperty(value = "字段", position = 4)
    private String fieldName;

    /** 注释 */
    @ApiModelProperty(value = "注释", position = 5)
    private String fieldRemark;

    /** 类型 */
    @ApiModelProperty(value = "类型", position = 6)
    private String fieldType;

    /** 实体类型 */
    @ApiModelProperty(value = "实体类型", position = 7)
    private String fieldJavaType;

    /** 作用类型 */
    @ApiModelProperty(value = "作用类型", position = 8)
    private String effectType;

    /** 字典 */
    @ApiModelProperty(value = "字典", position = 9)
    private String dictTypeCode;

    /** 列表显示 */
    @ApiModelProperty(value = "列表显示", position = 10)
    private String whetherTable;

    /** 列省略 */
    @ApiModelProperty(value = "列省略", position = 11)
    private String whetherRetract;

    /** 增改 */
    @ApiModelProperty(value = "增改", position = 12)
    private String whetherAddUpdate;

    /** 必填 */
    @ApiModelProperty(value = "必填", position = 13)
    private String whetherRequired;

    /** 查询 */
    @ApiModelProperty(value = "查询", position = 14)
    private String queryWhether;

    /** 查询方式 */
    @ApiModelProperty(value = "查询方式", position = 15)
    private String queryType;

    /** 排序 */
    @ApiModelProperty(value = "排序", position = 16)
    private Integer sortCode;
}
