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
package com.kiteehub.dev.modular.email.param;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 邮件发送——腾讯云TMP参数
 *
 * @author xuyuxiang
 * @date 2022/6/21 15:38
 **/
@Getter
@Setter
public class DevEmailSendTencentTmpParam {

    /** 发件人邮箱 */
    @ApiModelProperty(value = "管理控制台中配置的发信地址", required = true, position = 1)
    @NotBlank(message = "sendAccount不能为空")
    private String sendAccount;

    /** 接收人 */
    @ApiModelProperty(value = "预先创建且上传了收件人的收件人列表id", required = true, position = 2)
    @NotBlank(message = "receiveAccounts不能为空")
    private String receiveAccounts;

    /** 模板名 */
    @ApiModelProperty(value = "预先创建且通过审核的模板Id", required = true, position = 3)
    @NotBlank(message = "templateName不能为空")
    private String templateName;

    /** 邮件主题 */
    @ApiModelProperty(value = "邮件主题", required = true, position = 4)
    @NotBlank(message = "subject不能为空")
    private String subject;

    /** 发件人昵称 */
    @ApiModelProperty(value = "发件人昵称", position = 5)
    private String sendUser;

    /** 发送参数 */
    @ApiModelProperty(value = "预先创建且通过审核的模板的参数json", position = 6)
    private String templateParam;

    /** 附件列表 */
    @ApiModelProperty(value = "附件列表", position = 7, hidden = true)
    List<JSONObject> attachmentList = CollectionUtil.newArrayList();
}
