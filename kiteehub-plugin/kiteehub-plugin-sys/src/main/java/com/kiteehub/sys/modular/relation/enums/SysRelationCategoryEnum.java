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
package com.kiteehub.sys.modular.relation.enums;

import lombok.Getter;

/**
 * 关系分类枚举
 *
 * @author xuyuxiang
 * @date 2022/4/21 19:56
 **/
@Getter
public enum SysRelationCategoryEnum {

    /** 用户工作台数据 */
    SYS_USER_WORKBENCH_DATA("SYS_USER_WORKBENCH_DATA"),

    /** 用户日程数据 */
    SYS_USER_SCHEDULE_DATA("SYS_USER_SCHEDULE_DATA"),

    /** 用户拥有资源 */
    SYS_USER_HAS_RESOURCE("SYS_USER_HAS_RESOURCE"),

    /** 用户拥有权限 */
    SYS_USER_HAS_PERMISSION("SYS_USER_HAS_PERMISSION"),

    /** 用户拥有角色 */
    SYS_USER_HAS_ROLE("SYS_USER_HAS_ROLE"),

    /** 角色拥有资源 */
    SYS_ROLE_HAS_RESOURCE("SYS_ROLE_HAS_RESOURCE"),

    /** 角色拥有移动端菜单 */
    SYS_ROLE_HAS_MOBILE_MENU("SYS_ROLE_HAS_MOBILE_MENU"),

    /** 角色拥有权限 */
    SYS_ROLE_HAS_PERMISSION("SYS_ROLE_HAS_PERMISSION");

    private final String value;

    SysRelationCategoryEnum(String value) {
        this.value = value;
    }
}
