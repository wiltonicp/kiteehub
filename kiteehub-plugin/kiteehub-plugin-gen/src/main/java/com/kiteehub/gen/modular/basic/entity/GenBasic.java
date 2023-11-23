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
package com.kiteehub.gen.modular.basic.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import com.kiteehub.common.pojo.CommonEntity;

/**
 * 代码生成基础
 *
 * @author yubaoshan
 * @date 2022/10/25 22:33
 **/
@Getter
@Setter
@TableName("GEN_BASIC")
public class GenBasic extends CommonEntity {

    /** id */
    @ApiModelProperty(value = "id", position = 1)
    private String id;

    /** 主表名称 */
    @ApiModelProperty(value = "主表名称", position = 2)
    private String dbTable;

    /** 主表主键 */
    @ApiModelProperty(value = "主表主键", position = 3)
    private String dbTableKey;

    /** 插件名 */
    @ApiModelProperty(value = "插件名", position = 4)
    private String pluginName;

    /** 模块名 */
    @ApiModelProperty(value = "模块名", position = 5)
    private String moduleName;

    /** 表前缀移除 */
    @ApiModelProperty(value = "表前缀移除", position = 6)
    private String tablePrefix;

    /** 生成方式 */
    @ApiModelProperty(value = "生成方式", position = 7)
    private String generateType;

    /** 所属模块 */
    @ApiModelProperty(value = "所属模块", position = 8)
    private String module;

    /** 上级目录 */
    @ApiModelProperty(value = "上级目录", position = 9)
    private String menuPid;

    /** 功能名 */
    @ApiModelProperty(value = "功能名", position = 10)
    private String functionName;

    /** 业务名 */
    @ApiModelProperty(value = "业务名", position = 11)
    private String busName;

    /** 类名 */
    @ApiModelProperty(value = "类名", position = 12)
    private String className;

    /** 表单布局 */
    @ApiModelProperty(value = "表单布局", position = 13)
    private String formLayout;

    /** 使用栅格 */
    @ApiModelProperty(value = "使用栅格", position = 14)
    private String gridWhether;

    /** 排序 */
    @ApiModelProperty(value = "排序", position = 15)
    private Integer sortCode;

    /** 包名 */
    @ApiModelProperty(value = "包名", position = 16)
    private String packageName;

    /** 作者 */
    @ApiModelProperty(value = "作者", position = 17)
    private String authorName;
}
