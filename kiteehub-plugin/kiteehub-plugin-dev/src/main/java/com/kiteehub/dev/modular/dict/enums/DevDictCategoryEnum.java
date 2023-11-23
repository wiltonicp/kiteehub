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
package com.kiteehub.dev.modular.dict.enums;

import lombok.Getter;
import com.kiteehub.common.exception.CommonException;

/**
 * 字典分类枚举
 *
 * @author xuyuxiang
 * @date 2022/7/6 22:21
 */
@Getter
public enum DevDictCategoryEnum {

    /**
     * 框架
     */
    FRM("FRM"),

    /**
     * 业务
     */
    BIZ("BIZ");

    private final String value;

    DevDictCategoryEnum(String value) {
        this.value = value;
    }

    public static void validate(String value) {
        boolean flag = FRM.getValue().equals(value) || BIZ.getValue().equals(value);
        if(!flag) {
            throw new CommonException("不支持的字典分类：{}", value);
        }
    }
}
