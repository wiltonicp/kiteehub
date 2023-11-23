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
package com.kiteehub.gen.modular.basic.enums;

import lombok.Getter;

/**
 * 作用类型枚举
 *
 * @author xuyuxiang
 * @date 2022/10/28 9:57
 **/
@Getter
public enum GenEffectTypeEnum {

    /** 输入框 */
    INPUT("INPUT"),

    /** 文本框 */
    TEXTAREA("TEXTAREA"),

    /** 下拉框 */
    SELECT("SELECT"),

    /** 单选框 */
    RADIO("RADIO"),

    /** 复选框 */
    CHECKBOX("CHECKBOX"),

    /** 日期选择器 */
    DATEPICKER("DATEPICKER"),

    /** 时间选择器 */
    TIMEPICKER("TIMEPICKER"),

    /** 数字输入框 */
    INPUTNUMBER("INPUTNUMBER"),

    /** 滑动数字条 */
    SLIDER("SLIDER");

    private final String value;

    GenEffectTypeEnum(String value) {
        this.value = value;
    }
}
