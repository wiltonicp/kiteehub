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
package com.kiteehub.dev.modular.sms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kiteehub.dev.modular.sms.entity.DevSms;
import com.kiteehub.dev.modular.sms.param.DevSmsIdParam;
import com.kiteehub.dev.modular.sms.param.DevSmsPageParam;
import com.kiteehub.dev.modular.sms.param.DevSmsSendAliyunParam;
import com.kiteehub.dev.modular.sms.param.DevSmsSendTencentParam;

import java.util.List;

/**
 * 短信Service接口
 *
 * @author xuyuxiang
 * @date 2022/2/23 18:27
 **/
public interface DevSmsService extends IService<DevSms> {

    /**
     * 发送短信——阿里云
     *
     * @author xuyuxiang
     * @date 2022/6/21 18:37
     **/
    void sendAliyun(DevSmsSendAliyunParam devSmsSendAliyunParam);

    /**
     * 发送短信——腾讯云
     *
     * @author xuyuxiang
     * @date 2022/6/21 18:37
     **/
    void sendTencent(DevSmsSendTencentParam devSmsSendTencentParam);

    /**
     * 获取短信分页
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    Page<DevSms> page(DevSmsPageParam devSmsPageParam);

    /**
     * 删除短信
     *
     * @author xuyuxiang
     * @date 2022/8/4 10:36
     **/
    void delete(List<DevSmsIdParam> devSmsIdParamList);

    /**
     * 获取短信详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    DevSms detail(DevSmsIdParam devSmsIdParam);

    /**
     * 获取短信详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    DevSms queryEntity(String id);
}
