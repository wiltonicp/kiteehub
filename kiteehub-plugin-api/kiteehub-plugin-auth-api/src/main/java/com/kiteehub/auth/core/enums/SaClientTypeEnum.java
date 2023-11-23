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
package com.kiteehub.auth.core.enums;

import lombok.Getter;
import com.kiteehub.common.exception.CommonException;

/**
 * 登录端类型枚举
 *
 * @author xuyuxiang
 * @date 2021/10/11 14:02
 **/
@Getter
public enum SaClientTypeEnum {

    /**
     * B端用户
     */
    B("B"),

    /**
     * C端用户
     */
    C("C");

    private final String value;

    SaClientTypeEnum(String value) {
        this.value = value;
    }

    public static void validate(String value) {
        boolean flag = B.getValue().equals(value) || C.getValue().equals(value);
        if(!flag) {
            throw new CommonException("不支持的登录端类型：{}", value);
        }
    }
}
