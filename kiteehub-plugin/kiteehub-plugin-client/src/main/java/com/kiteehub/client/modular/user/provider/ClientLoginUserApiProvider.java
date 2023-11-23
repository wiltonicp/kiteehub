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
package com.kiteehub.client.modular.user.provider;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Service;
import com.kiteehub.auth.api.SaBaseLoginUserApi;
import com.kiteehub.auth.core.pojo.SaBaseClientLoginUser;
import com.kiteehub.auth.core.pojo.SaBaseLoginUser;
import com.kiteehub.client.modular.user.result.ClientLoginUser;
import com.kiteehub.client.modular.user.service.ClientUserService;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * C端登录用户API接口实现类
 *
 * @author xuyuxiang
 * @date 2022/4/29 13:36
 **/
@Service("clientLoginUserApi")
public class ClientLoginUserApiProvider implements SaBaseLoginUserApi {

    @Resource
    private ClientUserService clientUserService;

    /**
     * 不实现B端用户信息
     *
     * @author xuyuxiang
     * @date 2022/7/8 10:36
     **/
    @Override
    public SaBaseLoginUser getUserById(String id) {
        return null;
    }

    /**
     * 根据id获取C端用户信息，查不到则返回null
     *
     * @author xuyuxiang
     * @date 2021/12/28 15:35
     **/
    @Override
    public SaBaseClientLoginUser getClientUserById(String id) {
        return clientUserService.getUserById(id);
    }

    /**
     * 不实现B端用户信息
     *
     * @author xuyuxiang
     * @date 2022/7/8 10:36
     **/
    @Override
    public SaBaseLoginUser getUserByAccount(String account) {
        return null;
    }

    /**
     * 根据账号获取C端用户信息，查不到则返回null
     *
     * @author xuyuxiang
     * @date 2021/12/28 15:35
     **/
    @Override
    public ClientLoginUser getClientUserByAccount(String account) {
        return clientUserService.getUserByAccount(account);
    }

    /**
     * 不实现B端用户信息
     *
     * @author xuyuxiang
     * @date 2022/8/25 14:08
     **/
    @Override
    public SaBaseLoginUser getUserByPhone(String phone) {
        return null;
    }

    /**
     * 根据手机号获取C端用户信息，查不到则返回null
     *
     * @author xuyuxiang
     * @date 2022/8/25 14:08
     **/
    @Override
    public SaBaseClientLoginUser getClientUserByPhone(String phone) {
        return clientUserService.getUserByPhone(phone);
    }

    /**
     * 根据用户id获取用户集合
     *
     * @author xuyuxiang
     * @date 2022/4/27 22:53
     */
    @Override
    public List<JSONObject> listUserByUserIdList(List<String> userIdList) {
        return clientUserService.listByIds(userIdList).stream().map(JSONUtil::parseObj).collect(Collectors.toList());
    }

    /**
     * 根据用户id获取角色集合
     *
     * @author xuyuxiang
     * @date 2022/4/27 22:53
     */
    @Override
    public List<JSONObject> getRoleListByUserId(String userId) {
        // TODO C端用户暂无角色
        return CollectionUtil.newArrayList();
    }

    /**
     * 根据角色id和用户id集合获取按钮码集合
     *
     * @author xuyuxiang
     * @date 2022/4/27 22:54
     */
    @Override
    public List<String> getButtonCodeListListByUserAndRoleIdList(List<String> userAndRoleIdList) {
        // TODO C端用户暂无按钮码
        return CollectionUtil.newArrayList();
    }

    /**
     * 根据角色id和用户id集合获取移动端按钮码集合
     *
     * @author xuyuxiang
     * @date 2022/4/27 22:54
     */
    @Override
    public List<String> getMobileButtonCodeListListByUserIdAndRoleIdList(List<String> userAndRoleIdList) {
        // TODO C端用户暂无移动端按钮码
        return CollectionUtil.newArrayList();
    }

    /**
     * 根据角色id和用户id集合获取权限集合
     *
     * @author xuyuxiang
     * @date 2022/4/27 22:54
     */
    @Override
    public List<JSONObject> getPermissionListByUserIdAndRoleIdList(List<String> userAndRoleIdList, String orgId) {
        // TODO C端用户暂无权限码
        return CollectionUtil.newArrayList();
    }

    /**
     * 更新用户的登录时间和登录ip等信息
     *
     * @author xuyuxiang
     * @date 2022/4/27 22:57
     */
    @Override
    public void updateUserLoginInfo(String userId, String device) {
        clientUserService.updateUserLoginInfo(userId, device);
    }
}