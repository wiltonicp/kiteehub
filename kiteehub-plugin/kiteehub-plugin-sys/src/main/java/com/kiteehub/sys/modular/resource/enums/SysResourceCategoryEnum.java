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
package com.kiteehub.sys.modular.resource.enums;

import lombok.Getter;
import com.kiteehub.common.exception.CommonException;

/**
 * 资源分类枚举
 *
 * @author xuyuxiang
 * @date 2022/4/21 19:56
 **/
@Getter
public enum SysResourceCategoryEnum {

    /** 模块 */
    MODULE("MODULE"),

    /** 单页面 */
    SPA("SPA"),

    /** 菜单 */
    MENU("MENU"),

    /** 按钮 */
    BUTTON("BUTTON");

    private final String value;

    SysResourceCategoryEnum(String value) {
        this.value = value;
    }

    public static void validate(String value) {
        boolean flag = MODULE.getValue().equals(value) || SPA.getValue().equals(value) || MENU.getValue().equals(value)
                || BUTTON.getValue().equals(value);
        if(!flag) {
            throw new CommonException("不支持的资源分类：{}", value);
        }
    }
}
