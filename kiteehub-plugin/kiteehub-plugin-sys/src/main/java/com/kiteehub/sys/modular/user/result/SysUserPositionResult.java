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
package com.kiteehub.sys.modular.user.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户职位信息
 *
 * @author xuyuxiang
 * @date 2022/8/22 9:00
 **/
@Getter
@Setter
public class SysUserPositionResult {

    /** 组织id */
    @ApiModelProperty(value = "组织id", position = 1)
    private String orgId;

    /** 组织名称 */
    @ApiModelProperty(value = "组织名称", position = 2)
    private String orgName;

    /** 职位id */
    @ApiModelProperty(value = "职位id", position = 3)
    private String positionId;

    /** 职位名称 */
    @ApiModelProperty(value = "职位名称", position = 4)
    private String positionName;

    /** 组织分类 */
    @ApiModelProperty(value = "组织分类", position = 5)
    private String category;

    /** 职位类型 */
    @ApiModelProperty(value = "职位类型", position = 6)
    private String type;
}
