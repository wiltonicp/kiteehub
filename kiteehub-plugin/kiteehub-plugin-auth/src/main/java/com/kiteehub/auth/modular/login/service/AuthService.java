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
package com.kiteehub.auth.modular.login.service;

import com.kiteehub.auth.core.pojo.SaBaseClientLoginUser;
import com.kiteehub.auth.core.pojo.SaBaseLoginUser;
import com.kiteehub.auth.modular.login.param.AuthAccountPasswordLoginParam;
import com.kiteehub.auth.modular.login.param.AuthGetPhoneValidCodeParam;
import com.kiteehub.auth.modular.login.param.AuthPhoneValidCodeLoginParam;
import com.kiteehub.auth.modular.login.result.AuthPicValidCodeResult;

/**
 * 登录Service接口
 *
 * @author xuyuxiang
 * @date 2021/12/23 21:51
 */
public interface AuthService {

    /**
     * 获取图片验证码
     *
     * @author xuyuxiang
     * @date 2021/12/28 14:46
     **/
    AuthPicValidCodeResult getPicCaptcha(String type);

    /**
     * 获取手机验证码
     *
     * @author xuyuxiang
     * @date 2021/12/28 14:46
     **/
    String getPhoneValidCode(AuthGetPhoneValidCodeParam authGetPhoneValidCodeParam, String type);

    /**
     * 账号密码登录
     *
     * @author xuyuxiang
     * @date 2021/12/28 14:46
     **/
    String doLogin(AuthAccountPasswordLoginParam authAccountPasswordLoginParam, String type);

    /**
     * 手机验证码登录
     *
     * @author xuyuxiang
     * @date 2021/12/28 14:46
     **/
    String doLoginByPhone(AuthPhoneValidCodeLoginParam authPhoneValidCodeLoginParam, String type);

    /**
     * 获取B端登录用户信息
     *
     * @author xuyuxiang
     * @date 2021/10/12 15:59
     **/
    SaBaseLoginUser getLoginUser();

    /**
     * 获取C端登录用户信息
     *
     * @author xuyuxiang
     * @date 2021/10/12 15:59
     **/
    SaBaseClientLoginUser getClientLoginUser();

    /**
     * 根据用户id和客户端类型登录，用于第三方登录
     *
     * @author xuyuxiang
     * @date 2022/7/9 14:44
     */
    String doLoginById(String userId, String device, String type);
}
