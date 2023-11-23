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
package com.kiteehub.dev.modular.sms.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 短信发送——阿里云参数
 *
 * @author xuyuxiang
 * @date 2022/7/31 15:21
 */
@Getter
@Setter
public class DevSmsSendAliyunParam {

    /** 手机号 */
    @ApiModelProperty(value = "手机号，多个逗号拼接", required = true, position = 1)
    @NotBlank(message = "phoneNumbers不能为空")
    private String phoneNumbers;

    /** 模板编码 */
    @ApiModelProperty(value = "短信服务控制台配置且审核通过的模板编码", required = true, position = 2)
    @NotBlank(message = "templateCode不能为空")
    private String templateCode;

    /** 发送参数 */
    @ApiModelProperty(value = "短信模板变量对应的实际值，JSON格式", position = 3)
    private String templateParam;

    /** 短信签名 */
    @ApiModelProperty(value = "短信服务控制台配置且审核通过的短信签名", position = 4)
    private String signName;
}
