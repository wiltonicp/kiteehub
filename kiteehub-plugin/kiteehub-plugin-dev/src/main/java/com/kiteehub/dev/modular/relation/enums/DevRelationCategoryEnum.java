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
package com.kiteehub.dev.modular.relation.enums;

import lombok.Getter;

/**
 * 关系分类枚举
 *
 * @author xuyuxiang
 * @date 2022/4/21 19:56
 **/
@Getter
public enum DevRelationCategoryEnum {

    /* ====文件与业务关系==== */

    /** 文件与业务默认关联关系，后续有多种类型关联，可扩展 */
    FILE_TO_BIZ_DEFAULT("FILE_TO_BIZ_DEFAULT"),

    /* ====站内信与用户关系==== */

    /** 站内信与接收用户 */
    MSG_TO_USER("MSG_TO_USER");

    private final String value;

    DevRelationCategoryEnum(String value) {
        this.value = value;
    }
}
