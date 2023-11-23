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
package com.kiteehub.common.enums;

import lombok.Getter;

/**
 * 异常码枚举
 *
 * @author xuyuxiang
 * @date 2022/8/15 16:09
 **/
@Getter
public enum CommonExceptionEnum {

    OK200(200, "请求成功"),
    ERROR401(401, "未登录"),
    ERROR403(403, "无权限"),
    ERROR404(404, "路径不存在"),
    ERROR405(405, "请求方法不正确"),
    ERROR415(415, "参数传递异常"),
    ERROR500(500, "业务异常");

    private final Integer code;

    private final String message;

    CommonExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
