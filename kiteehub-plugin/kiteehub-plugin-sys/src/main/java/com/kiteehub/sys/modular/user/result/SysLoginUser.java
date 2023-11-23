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
package com.kiteehub.sys.modular.user.result;

import com.kiteehub.auth.core.pojo.SaBaseLoginUser;
import com.kiteehub.sys.modular.user.enums.SysUserStatusEnum;

/**
 * 登录用户对象
 *
 * @author xuyuxiang
 * @date 2022/4/21 19:33
 **/
public class SysLoginUser extends SaBaseLoginUser {

    /**
     * 实现是否可以登录
     *
     * @author xuyuxiang
     * @date 2022/8/15 15:26
     **/
    @Override
    public Boolean getEnabled() {
        // 仅判断状态是否正常，可自行扩展
        return getUserStatus().equals(SysUserStatusEnum.ENABLE.getValue());
    }
}
