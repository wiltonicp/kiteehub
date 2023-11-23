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
package com.kiteehub.sys.modular.user.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 编辑个人信息参数
 *
 * @author xuyuxiang
 * @date 2022/7/27 17:08
 **/
@Getter
@Setter
public class SysUserUpdateInfoParam {

    /** id */
    @ApiModelProperty(value = "id", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", required = true, position = 2)
    @NotBlank(message = "name不能为空")
    private String name;

    /** 手机 */
    @ApiModelProperty(value = "手机", position = 3)
    private String phone;

    /** 昵称 */
    @ApiModelProperty(value = "昵称", position = 4)
    private String nickname;

    /** 性别 */
    @ApiModelProperty(value = "性别", position = 5)
    private String gender;

    /** 出生日期 */
    @ApiModelProperty(value = "出生日期", position = 6)
    private String birthday;

    /** 邮箱 */
    @ApiModelProperty(value = "邮箱", position = 7)
    private String email;

    /** 签名 */
    @ApiModelProperty(value = "签名，图片base64", position = 8)
    private String signature;
}
