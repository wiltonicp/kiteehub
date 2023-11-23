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
package com.kiteehub.dev.modular.message.controller;

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
import com.kiteehub.dev.modular.message.entity.DevMessage;
import com.kiteehub.dev.modular.message.param.DevMessageIdParam;
import com.kiteehub.dev.modular.message.param.DevMessagePageParam;
import com.kiteehub.dev.modular.message.param.DevMessageSendParam;
import com.kiteehub.dev.modular.message.result.DevMessageResult;
import com.kiteehub.dev.modular.message.service.DevMessageService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 站内信控制器
 *
 * @author xuyuxiang
 * @date 2022/6/21 14:57
 **/
@Api(tags = "站内信控制器")
@ApiSupport(author = "KITEE_HUB_TEAM", order = 6)
@RestController
@Validated
public class DevMessageController {

    @Resource
    private DevMessageService devMessageService;

    /**
     * 发送站内信
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("发送站内信")
    @CommonLog("发送站内信")
    @PostMapping("/dev/message/send")
    public CommonResult<String> send(@RequestBody @Valid DevMessageSendParam devMessageSendParam) {
        devMessageService.send(devMessageSendParam);
        return CommonResult.ok();
    }

    /**
     * 获取站内信分页
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("获取站内信分页")
    @GetMapping("/dev/message/page")
    public CommonResult<Page<DevMessage>> page(DevMessagePageParam devMessagePageParam) {
        return CommonResult.data(devMessageService.page(devMessagePageParam));
    }

    /**
     * 删除站内信
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("删除站内信")
    @CommonLog("删除站内信")
    @PostMapping("/dev/message/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                               CommonValidList<DevMessageIdParam> devMessageIdParamList) {
        devMessageService.delete(devMessageIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取站内信详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("获取站内信详情")
    @GetMapping("/dev/message/detail")
    public CommonResult<DevMessageResult> detail(@Valid DevMessageIdParam devMessageIdParam) {
        return CommonResult.data(devMessageService.detail(devMessageIdParam));
    }
}
