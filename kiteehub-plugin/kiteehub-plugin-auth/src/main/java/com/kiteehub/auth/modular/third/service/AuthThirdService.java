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
package com.kiteehub.auth.modular.third.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import me.zhyd.oauth.model.AuthCallback;
import com.kiteehub.auth.modular.third.entity.AuthThirdUser;
import com.kiteehub.auth.modular.third.param.AuthThirdCallbackParam;
import com.kiteehub.auth.modular.third.param.AuthThirdRenderParam;
import com.kiteehub.auth.modular.third.param.AuthThirdUserPageParam;
import com.kiteehub.auth.modular.third.result.AuthThirdRenderResult;

/**
 * 第三方登录Service接口
 *
 * @author xuyuxiang
 * @date 2022/7/8 16:20
 **/
public interface AuthThirdService extends IService<AuthThirdUser> {

    /**
     * 第三方登录页面渲染，返回授权结果
     *
     * @author xuyuxiang
     * @date 2022/7/8 16:37
     **/
    AuthThirdRenderResult render(AuthThirdRenderParam authThirdRenderParam);

    /**
     * 第三方登录授权回调，返回登录token
     *
     * @author xuyuxiang
     * @date 2022/7/8 16:42
     **/
    String callback(AuthThirdCallbackParam authThirdCallbackParam, AuthCallback authCallback);

    /**
     * 获取三方用户分页
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    Page<AuthThirdUser> page(AuthThirdUserPageParam authThirdUserPageParam);
}
