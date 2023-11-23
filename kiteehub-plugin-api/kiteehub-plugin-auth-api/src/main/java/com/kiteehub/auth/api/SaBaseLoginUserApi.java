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
package com.kiteehub.auth.api;

import cn.hutool.json.JSONObject;
import com.kiteehub.auth.core.pojo.SaBaseClientLoginUser;
import com.kiteehub.auth.core.pojo.SaBaseLoginUser;

import java.util.List;

/**
 * 登录用户API，由其他模块实现
 *
 * @author xuyuxiang
 * @date 2021/12/23 21:48
 */
public interface SaBaseLoginUserApi {

    /**
     * 根据id获取B端用户信息，查不到则返回null
     *
     * @author xuyuxiang
     * @date 2022/3/10 16:14
     **/
    SaBaseLoginUser getUserById(String id);

    /**
     * 根据id获取C端用户信息，查不到则返回null
     *
     * @author xuyuxiang
     * @date 2022/3/10 16:14
     **/
    SaBaseClientLoginUser getClientUserById(String id);

    /**
     * 根据账号获取B端用户信息，查不到则返回null
     *
     * @author xuyuxiang
     * @date 2022/3/10 16:14
     **/
    SaBaseLoginUser getUserByAccount(String account);

    /**
     * 根据账号获取C端用户信息，查不到则返回null
     *
     * @author xuyuxiang
     * @date 2022/3/10 16:14
     **/
    SaBaseClientLoginUser getClientUserByAccount(String account);

    /**
     * 根据手机号获取B端用户信息，查不到则返回null
     *
     * @author xuyuxiang
     * @date 2022/3/10 16:14
     **/
    SaBaseLoginUser getUserByPhone(String phone);

    /**
     * 根据手机号获取C端用户信息，查不到则返回null
     *
     * @author xuyuxiang
     * @date 2022/3/10 16:14
     **/
    SaBaseClientLoginUser getClientUserByPhone(String phone);

    /**
     * 根据用户id获取用户集合
     *
     * @author xuyuxiang
     * @date 2022/4/27 22:53
     */
    List<JSONObject> listUserByUserIdList(List<String> userIdList);

    /**
     * 根据用户id获取角色集合
     *
     * @author xuyuxiang
     * @date 2022/4/27 22:53
     */
    List<JSONObject> getRoleListByUserId(String userId);

    /**
     * 根据角色id和用户id集合获取按钮码集合
     *
     * @author xuyuxiang
     * @date 2022/4/27 22:54
     */
    List<String> getButtonCodeListListByUserAndRoleIdList(List<String> userAndRoleIdList);

    /**
     * 根据角色id和用户id集合获取移动端按钮码集合
     *
     * @author xuyuxiang
     * @date 2022/4/27 22:54
     */
    List<String> getMobileButtonCodeListListByUserIdAndRoleIdList(List<String> userAndRoleIdList);

    /**
     * 根据角色id和用户id集合获取权限集合
     *
     * @author xuyuxiang
     * @date 2022/4/27 22:54
     */
    List<JSONObject> getPermissionListByUserIdAndRoleIdList(List<String> userAndRoleIdList, String orgId);

    /**
     * 更新用户的登录时间和登录ip等信息
     *
     * @author xuyuxiang
     * @date 2022/4/27 22:57
     */
    void updateUserLoginInfo(String userId, String device);
}
