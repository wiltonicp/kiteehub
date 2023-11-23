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
package com.kiteehub.mobile.modular.resource.param.module;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 移动端模块添加参数
 *
 * @author xuyuxiang
 * @date 2022/7/27 18:40
 **/
@Getter
@Setter
public class MobileModuleAddParam {

    /** 标题 */
    @ApiModelProperty(value = "标题", required = true, position = 1)
    @NotBlank(message = "title不能为空")
    private String title;

    /** 图标 */
    @ApiModelProperty(value = "图标", required = true, position = 2)
    @NotBlank(message = "icon不能为空")
    private String icon;

    /** 颜色 */
    @ApiModelProperty(value = "颜色", required = true, position = 3)
    @NotBlank(message = "color不能为空")
    private String color;

    /** 排序码 */
    @ApiModelProperty(value = "排序码", required = true, position = 4)
    @NotNull(message = "sortCode不能为空")
    private Integer sortCode;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 5)
    private String extJson;
}
