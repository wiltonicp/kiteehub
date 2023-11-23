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
package com.kiteehub.dev.modular.email.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.kiteehub.common.annotation.CommonLog;
import com.kiteehub.common.pojo.CommonResult;
import com.kiteehub.common.pojo.CommonValidList;
import com.kiteehub.dev.modular.email.entity.DevEmail;
import com.kiteehub.dev.modular.email.param.*;
import com.kiteehub.dev.modular.email.service.DevEmailService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 邮件控制器
 *
 * @author xuyuxiang
 * @date 2022/2/23 18:26
 **/
@Api(tags = "邮件控制器")
@ApiSupport(author = "KITEE_HUB_TEAM", order = 3)
@RestController
@Validated
public class DevEmailController {

    @Resource
    private DevEmailService devEmailService;

    /**
     * 发送邮件——本地TXT
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("发送本地文本邮件")
    @CommonLog("发送本地文本邮件")
    @PostMapping("/dev/email/sendLocalTxt")
    public CommonResult<String> sendLocal(@RequestBody @Valid DevEmailSendLocalTxtParam devEmailSendLocalTxtParam) {
        devEmailService.sendLocal(devEmailSendLocalTxtParam);
        return CommonResult.ok();
    }

    /**
     * 发送邮件——本地HTML
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("发送本地HTML邮件")
    @CommonLog("发送本地HTML邮件")
    @PostMapping("/dev/email/sendLocalHtml")
    public CommonResult<String> sendLocal(@RequestBody @Valid DevEmailSendLocalHtmlParam devEmailSendLocalHtmlParam) {
        devEmailService.sendLocal(devEmailSendLocalHtmlParam);
        return CommonResult.ok();
    }

    /**
     * 发送邮件——阿里云TXT
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("发送阿里云文本邮件")
    @CommonLog("发送阿里云文本邮件")
    @PostMapping("/dev/email/sendAliyunTxt")
    public CommonResult<String> sendAliyun(@RequestBody @Valid DevEmailSendAliyunTxtParam devEmailSendAliyunTxtParam) {
        devEmailService.sendAliyun(devEmailSendAliyunTxtParam);
        return CommonResult.ok();
    }

    /**
     * 发送邮件——阿里云HTML
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("发送阿里云HTML邮件")
    @CommonLog("发送阿里云HTML邮件")
    @PostMapping("/dev/email/sendAliyunHtml")
    public CommonResult<String> sendAliyun(@RequestBody @Valid DevEmailSendAliyunHtmlParam devEmailSendAliyunHtmlParam) {
        devEmailService.sendAliyun(devEmailSendAliyunHtmlParam);
        return CommonResult.ok();
    }

    /**
     * 发送邮件——阿里云TMP
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("发送阿里云模板邮件")
    @CommonLog("发送阿里云模板邮件")
    @PostMapping("/dev/email/sendAliyunTmp")
    public CommonResult<String> sendAliyun(@RequestBody @Valid DevEmailSendAliyunTmpParam devEmailSendAliyunTmpParam) {
        devEmailService.sendAliyun(devEmailSendAliyunTmpParam);
        return CommonResult.ok();
    }

    /**
     * 发送邮件——腾讯云TXT
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("发送腾讯云文本邮件")
    @CommonLog("发送腾讯云文本邮件")
    @PostMapping("/dev/email/sendTencentTxt")
    public CommonResult<String> sendTencent(@RequestBody @Valid DevEmailSendTencentTxtParam devEmailSendTencentTxtParam) {
        devEmailService.sendTencent(devEmailSendTencentTxtParam);
        return CommonResult.ok();
    }

    /**
     * 发送邮件——腾讯云HTML
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("发送腾讯云HTML邮件")
    @CommonLog("发送腾讯云HTML邮件")
    @PostMapping("/dev/email/sentTencentHtml")
    public CommonResult<String> sendTencent(@RequestBody @Valid DevEmailSendTencentHtmlParam devEmailSendTencentHtmlParam) {
        devEmailService.sendTencent(devEmailSendTencentHtmlParam);
        return CommonResult.ok();
    }

    /**
     * 发送邮件——腾讯云TMP
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 8)
    @ApiOperation("发送腾讯云模板邮件")
    @CommonLog("发送腾讯云模板邮件")
    @PostMapping("/dev/email/sentTencentTmp")
    public CommonResult<String> sendTencent(@RequestBody @Valid DevEmailSendTencentTmpParam devEmailSendTencentTmpParam) {
        devEmailService.sendTencent(devEmailSendTencentTmpParam);
        return CommonResult.ok();
    }

    /**
     * 获取邮件分页
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 9)
    @ApiOperation("获取邮件分页")
    @GetMapping("/dev/email/page")
    public CommonResult<Page<DevEmail>> page(DevEmailPageParam devEmailPageParam) {
        return CommonResult.data(devEmailService.page(devEmailPageParam));
    }

    /**
     * 删除邮件
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 10)
    @ApiOperation("删除邮件")
    @CommonLog("删除邮件")
    @PostMapping("/dev/email/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                               CommonValidList<DevEmailIdParam> devEmailIdParamList) {
        devEmailService.delete(devEmailIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取邮件详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 11)
    @ApiOperation("获取邮件详情")
    @GetMapping("/dev/email/detail")
    public CommonResult<DevEmail> detail(@Valid DevEmailIdParam devEmailIdParam) {
        return CommonResult.data(devEmailService.detail(devEmailIdParam));
    }
}
