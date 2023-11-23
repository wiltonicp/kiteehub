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
package com.kiteehub.dev.modular.monitor.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kiteehub.common.pojo.CommonResult;
import com.kiteehub.dev.modular.monitor.result.DevMonitorServerResult;
import com.kiteehub.dev.modular.monitor.service.DevMonitorService;

import javax.annotation.Resource;

/**
 * 监控控制器
 *
 * @author xuyuxiang
 * @date 2022/6/21 14:57
 **/
@Api(tags = "监控控制器")
@ApiSupport(author = "KITEE_HUB_TEAM", order = 8)
@RestController
@Validated
public class DevMonitorController {

    @Resource
    private DevMonitorService devMonitorService;

    /**
     * 获取服务器监控信息
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取服务器监控信息")
    @GetMapping("/dev/monitor/serverInfo")
    public CommonResult<DevMonitorServerResult> serverInfo() {
        return CommonResult.data(devMonitorService.serverInfo());
    }
}
