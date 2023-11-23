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
package com.kiteehub.dev.modular.sms.controller;

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
import com.kiteehub.dev.modular.sms.entity.DevSms;
import com.kiteehub.dev.modular.sms.param.DevSmsIdParam;
import com.kiteehub.dev.modular.sms.param.DevSmsPageParam;
import com.kiteehub.dev.modular.sms.param.DevSmsSendAliyunParam;
import com.kiteehub.dev.modular.sms.param.DevSmsSendTencentParam;
import com.kiteehub.dev.modular.sms.service.DevSmsService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 短信控制器
 *
 * @author xuyuxiang
 * @date 2022/2/23 18:26
 **/
@Api(tags = "短信控制器")
@ApiSupport(author = "KITEE_HUB_TEAM", order = 5)
@RestController
@Validated
public class DevSmsController {

    @Resource
    private DevSmsService devSmsService;

    /**
     * 发送短信——阿里云
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("发送阿里云短信")
    @CommonLog("发送阿里云短信")
    @PostMapping("/dev/sms/sendAliyun")
    public CommonResult<String> sendAliyun(@RequestBody @Valid DevSmsSendAliyunParam devSmsSendAliyunParam) {
        devSmsService.sendAliyun(devSmsSendAliyunParam);
        return CommonResult.ok();
    }

    /**
     * 发送短信——腾讯云
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("发送腾讯云短信")
    @CommonLog("发送腾讯云短信")
    @PostMapping("/dev/sms/sendTencent")
    public CommonResult<String> sendTencent(@RequestBody @Valid DevSmsSendTencentParam devSmsSendTencentParam) {
        devSmsService.sendTencent(devSmsSendTencentParam);
        return CommonResult.ok();
    }

    /**
     * 获取短信分页
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("获取短信分页")
    @GetMapping("/dev/sms/page")
    public CommonResult<Page<DevSms>> page(DevSmsPageParam devSmsPageParam) {
        return CommonResult.data(devSmsService.page(devSmsPageParam));
    }

    /**
     * 删除短信
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除短信")
    @CommonLog("删除短信")
    @PostMapping("/dev/sms/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                               CommonValidList<DevSmsIdParam> devSmsIdParamList) {
        devSmsService.delete(devSmsIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取短信详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取短信详情")
    @GetMapping("/dev/sms/detail")
    public CommonResult<DevSms> detail(@Valid DevSmsIdParam devSmsIdParam) {
        return CommonResult.data(devSmsService.detail(devSmsIdParam));
    }
}
