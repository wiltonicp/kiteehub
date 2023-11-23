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
package com.kiteehub.auth.modular.third.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 第三方登录回调参数
 *
 * @author xuyuxiang
 * @date 2022/7/8 20:38
 */
@Getter
@Setter
public class AuthThirdCallbackParam {

    /** 第三方平台标识 */
    @ApiModelProperty(value = "第三方平台标识", required = true, position = 1)
    @NotBlank(message = "platform不能为空")
    private String platform;

    /** 第三方回调code */
    @ApiModelProperty(value = "第三方回调code", required = true, position = 2)
    @NotBlank(message = "code不能为空")
    private String code;

    /** 第三方回调state */
    @ApiModelProperty(value = "第三方回调state", required = true, position = 3)
    @NotBlank(message = "state不能为空")
    private String state;
}
