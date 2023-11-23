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
package com.kiteehub.auth.modular.login.enums;

import lombok.Getter;

/**
 * 登录异常提示语枚举
 *
 * @author xuyuxiang
 * @date 2021/10/11 14:02
 **/
@Getter
public enum AuthExceptionEnum {

    /**
     * 验证码不能为空
     */
    VALID_CODE_EMPTY("验证码不能为空"),

    /**
     * 验证码请求号不能为空
     */
    VALID_CODE_REQ_NO_EMPTY("验证码请求号不能为空"),

    /**
     * 验证码错误
     */
    VALID_CODE_ERROR("验证码错误"),

    /**
     * 账号错误
     */
    ACCOUNT_ERROR("账号错误"),

    /**
     * 账号已停用
     */
    ACCOUNT_DISABLED("账号已停用"),

    /**
     * 密码错误
     */
    PWD_ERROR("密码错误"),

    /**
     * 手机号格式错误
     */
    PHONE_FORMAT_ERROR("手机号格式错误"),

    /**
     * 手机号不存在
     */
    PHONE_ERROR("手机号不存在"),

    /**
     * 客户端类型不能为空
     */
    CLIENT_TYPE_EMPTY("客户端类型不能为空"),

    /**
     * 客户端类型错误
     */
    CLIENT_TYPE_ERROR("客户端类型错误"),

    /**
     * 密码解密失败，请检查前端公钥
     */
    PWD_DECRYPT_ERROR("密码解密失败，请检查前端公钥");

    private final String value;

    AuthExceptionEnum(String value) {
        this.value = value;
    }
}
