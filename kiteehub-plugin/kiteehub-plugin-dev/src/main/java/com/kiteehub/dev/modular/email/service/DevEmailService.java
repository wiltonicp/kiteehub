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
package com.kiteehub.dev.modular.email.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kiteehub.dev.modular.email.entity.DevEmail;
import com.kiteehub.dev.modular.email.param.*;

import java.util.List;

/**
 * 邮件Service接口
 *
 * @author xuyuxiang
 * @date 2022/2/23 18:27
 **/
public interface DevEmailService extends IService<DevEmail> {

    /**
     * 发送邮件——本地TXT
     *
     * @author xuyuxiang
     * @date 2022/6/21 18:37
     **/
    void sendLocal(DevEmailSendLocalTxtParam devEmailSendLocalTxtParam);

    /**
     * 发送邮件——本地HTML
     *
     * @author xuyuxiang
     * @date 2022/6/21 18:37
     **/
    void sendLocal(DevEmailSendLocalHtmlParam devEmailSendLocalHtmlParam);

    /**
     * 发送邮件——阿里云TXT
     *
     * @author xuyuxiang
     * @date 2022/6/21 18:37
     **/
    void sendAliyun(DevEmailSendAliyunTxtParam devEmailSendAliyunTxtParam);

    /**
     * 发送邮件——阿里云HTML
     *
     * @author xuyuxiang
     * @date 2022/6/21 18:37
     **/
    void sendAliyun(DevEmailSendAliyunHtmlParam devEmailSendAliyunHtmlParam);

    /**
     * 发送邮件——阿里云TMP
     *
     * @author xuyuxiang
     * @date 2022/6/21 18:37
     **/
    void sendAliyun(DevEmailSendAliyunTmpParam devEmailSendAliyunTmpParam);

    /**
     * 发送邮件——腾讯云TXT
     *
     * @author xuyuxiang
     * @date 2022/6/21 18:37
     **/
    void sendTencent(DevEmailSendTencentTxtParam devEmailSendTencentTxtParam);

    /**
     * 发送邮件——腾讯云HTML
     *
     * @author xuyuxiang
     * @date 2022/6/21 18:37
     **/
    void sendTencent(DevEmailSendTencentHtmlParam devEmailSendTencentHtmlParam);

    /**
     * 发送邮件——腾讯云TMP
     *
     * @author xuyuxiang
     * @date 2022/6/21 18:37
     **/
    void sendTencent(DevEmailSendTencentTmpParam devEmailSendTencentTmpParam);

    /**
     * 获取邮件分页
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    Page<DevEmail> page(DevEmailPageParam devEmailPageParam);

    /**
     * 删除邮件
     *
     * @author xuyuxiang
     * @date 2022/8/4 10:36
     **/
    void delete(List<DevEmailIdParam> devEmailIdParamList);

    /**
     * 获取邮件详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    DevEmail detail(DevEmailIdParam devEmailIdParam);

    /**
     * 获取邮件详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    DevEmail queryEntity(String id);
}
