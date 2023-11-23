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
package com.kiteehub.auth.core.util;

import cn.hutool.core.collection.CollectionUtil;
import com.kiteehub.auth.core.pojo.SaBaseClientLoginUser;

import java.util.List;

/**
 * C端登录用户工具类
 *
 * @author xuyuxiang
 * @date 2022/7/8 10:40
 **/
public class StpClientLoginUserUtil {

    /**
     * 获取当前C端登录用户
     *
     * @author xuyuxiang
     * @date 2022/7/8 10:41
     **/
    public static SaBaseClientLoginUser getClientLoginUser() {
        return (SaBaseClientLoginUser) StpClientUtil.getTokenSession().get("loginUser");
    }

    /**
     * 获取当前C端登录用户的当前请求接口的数据范围（暂无数据范围）
     *
     * @author xuyuxiang
     * @date 2022/7/8 10:41
     **/
    public static List<String> getLoginUserDataScope() {
        return CollectionUtil.newArrayList();
    }
}
