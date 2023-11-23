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
package com.kiteehub.dev.modular.sms.provider;

import org.springframework.stereotype.Service;
import com.kiteehub.dev.api.DevSmsApi;
import com.kiteehub.dev.modular.sms.param.DevSmsSendAliyunParam;
import com.kiteehub.dev.modular.sms.param.DevSmsSendTencentParam;
import com.kiteehub.dev.modular.sms.service.DevSmsService;

import javax.annotation.Resource;

/**
 * 短信API接口提供者
 *
 * @author xuyuxiang
 * @date 2022/6/22 15:33
 **/
@Service
public class DevSmsApiProvider implements DevSmsApi {

    @Resource
    private DevSmsService devSmsService;

    @Override
    public void sendSmsAliyun(String phoneNumbers, String signName, String templateCode, String templateParam) {
        DevSmsSendAliyunParam devSmsSendAliyunParam = new DevSmsSendAliyunParam();
        devSmsSendAliyunParam.setPhoneNumbers(phoneNumbers);
        devSmsSendAliyunParam.setSignName(signName);
        devSmsSendAliyunParam.setTemplateCode(templateCode);
        devSmsSendAliyunParam.setTemplateParam(templateParam);
        devSmsService.sendAliyun(devSmsSendAliyunParam);
    }

    @Override
    public void sendSmsTencent(String sdkAppId, String phoneNumbers, String signName, String templateCode, String templateParam) {
        DevSmsSendTencentParam devSmsSendTencentParam = new DevSmsSendTencentParam();
        devSmsSendTencentParam.setSdkAppId(sdkAppId);
        devSmsSendTencentParam.setPhoneNumbers(phoneNumbers);
        devSmsSendTencentParam.setSignName(signName);
        devSmsSendTencentParam.setTemplateCode(templateCode);
        devSmsSendTencentParam.setTemplateParam(templateParam);
        devSmsService.sendTencent(devSmsSendTencentParam);
    }
}
