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
package com.kiteehub.auth.modular.monitor.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kiteehub.auth.modular.monitor.param.AuthExitSessionParam;
import com.kiteehub.auth.modular.monitor.param.AuthExitTokenParam;
import com.kiteehub.auth.modular.monitor.param.AuthSessionPageParam;
import com.kiteehub.auth.modular.monitor.result.AuthSessionAnalysisResult;
import com.kiteehub.auth.modular.monitor.result.AuthSessionPageResult;

import java.util.List;

/**
 * 会话治理Service接口
 *
 * @author xuyuxiang
 * @date 2022/6/24 15:46S
 **/
public interface AuthSessionService {

    /**
     * 会话统计
     *
     * @author xuyuxiang
     * @date 2022/7/19 9:33
     **/
    AuthSessionAnalysisResult analysis();

    /**
     * 查询B端会话
     *
     * @author xuyuxiang
     * @date 2022/6/24 22:30
     */
    Page<AuthSessionPageResult> pageForB(AuthSessionPageParam authSessionPageParam);

    /**
     * 查询C端会话
     *
     * @author xuyuxiang
     * @date 2022/6/24 22:30
     */
    Page<AuthSessionPageResult> pageForC(AuthSessionPageParam authSessionPageParam);

    /**
     * 强退B端会话
     *
     * @author xuyuxiang
     * @date 2022/6/29 21:47
     */
    void exitSessionForB(List<AuthExitSessionParam> authExitSessionParamList);

    /**
     * 强退C端会话
     *
     * @author xuyuxiang
     * @date 2022/6/29 21:47
     */
    void exitSessionForC(List<AuthExitSessionParam> authExitSessionParamList);

    /**
     * 强退B端token
     *
     * @author xuyuxiang
     * @date 2022/6/29 21:47
     */
    void exitTokenForB(List<AuthExitTokenParam> authExitTokenParamList);

    /**
     * 强退C端token
     *
     * @author xuyuxiang
     * @date 2022/6/29 21:47
     */
    void exitTokenForC(List<AuthExitTokenParam> authExitTokenParamList);
}
