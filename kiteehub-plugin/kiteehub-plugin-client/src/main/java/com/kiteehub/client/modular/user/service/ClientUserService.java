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
package com.kiteehub.client.modular.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kiteehub.client.modular.user.entity.ClientUser;
import com.kiteehub.client.modular.user.param.ClientUserAddParam;
import com.kiteehub.client.modular.user.param.ClientUserEditParam;
import com.kiteehub.client.modular.user.param.ClientUserIdParam;
import com.kiteehub.client.modular.user.param.ClientUserPageParam;
import com.kiteehub.client.modular.user.result.ClientLoginUser;

import java.util.List;

/**
 * C端用户Service接口
 *
 * @author xuyuxiang
 * @date 2022/4/21 18:35
 **/
public interface ClientUserService extends IService<ClientUser> {

    /**
     * 根据id获取用户信息，查不到则返回null
     *
     * @author xuyuxiang
     * @date 2022/4/27 21:38
     */
    ClientLoginUser getUserById(String id);

    /**
     * 根据账户获取用户信息，查不到则返回null
     *
     * @author xuyuxiang
     * @date 2022/4/27 21:38
     */
    ClientLoginUser getUserByAccount(String account);

    /**
     * 根据手机号获取用户信息，查不到则返回null
     *
     * @author xuyuxiang
     * @date 2022/4/27 21:38
     */
    ClientLoginUser getUserByPhone(String phone);

    /**
     * 根据邮箱获取用户信息，查不到则返回null
     *
     * @author xuyuxiang
     * @date 2022/4/27 21:38
     */
    ClientLoginUser getUserByEmail(String email);

    /**
     * 获取C端用户分页
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    Page<ClientUser> page(ClientUserPageParam clientUserPageParam);

    /**
     * 添加C端用户
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:48
     */
    void add(ClientUserAddParam clientUserAddParam);

    /**
     * 编辑C端用户
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:13
     */
    void edit(ClientUserEditParam clientUserEditParam);

    /**
     * 删除C端用户
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:18
     */
    void delete(List<ClientUserIdParam> clientUserIdParamList);

    /**
     * 获取C端用户详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:18
     */
    ClientUser detail(ClientUserIdParam clientUserIdParam);

    /**
     * 更新C端用户的登录时间和登录ip等信息
     *
     * @author xuyuxiang
     * @date 2022/4/27 22:58
     */
    void updateUserLoginInfo(String userId, String device);

    /**
     * 获取C端用户详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:18
     */
    ClientUser queryEntity(String id);
}
