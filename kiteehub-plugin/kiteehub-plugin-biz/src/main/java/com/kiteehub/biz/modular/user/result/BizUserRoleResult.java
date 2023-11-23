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
package com.kiteehub.biz.modular.user.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色选择器结果
 *
 * @author xuyuxiang
 * @date 2022/7/22 14:29
 **/
@Getter
@Setter
public class BizUserRoleResult {

    /** id */
    @ApiModelProperty(value = "id", position = 1)
    private String id;

    /** 组织id */
    @ApiModelProperty(value = "机构id", position = 2)
    private String orgId;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 3)
    private String name;

    /** 分类 */
    @ApiModelProperty(value = "分类", position = 4)
    private String category;

    /** 排序码 */
    @ApiModelProperty(value = "排序码", position = 5)
    private Integer sortCode;
}
