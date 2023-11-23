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
package com.kiteehub.dev.modular.file.enums;

/**
 * 文件存储桶的权限策略枚举
 *
 * @author xuyuxiang
 * @date 2022/1/5 23:29
 */
public enum DevFileBucketAuthEnum {

    /**
     * 私有的（仅有 owner 可以读写）
     */
    PRIVATE,

    /**
     * 公有读，私有写（ owner 可以读写， 其他客户可以读）
     */
    PUBLIC_READ,

    /**
     * 公共读写（即所有人都可以读写，慎用）
     */
    PUBLIC_READ_WRITE

}
