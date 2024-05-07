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
package com.kiteehub.knowledge.modular.guide.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kiteehub.common.pojo.CommonResult;
import com.kiteehub.knowledge.modular.guide.entity.KbWorkGuide;
import com.kiteehub.knowledge.modular.guide.param.KbWorkGuideIdParam;
import com.kiteehub.knowledge.modular.guide.param.KbWorkGuidePageParam;
import com.kiteehub.knowledge.modular.guide.service.KbWorkGuideService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 业务指南控制器
 *
 * @author Ranger
 * @date  2024/01/29 14:15
 */
@Api(tags = "Client端业务指南控制器")
@ApiSupport(author = "KITEEHUB_TEAM", order = 1)
@RestController
@Validated
public class KbWorkGuideClientController {

    @Resource
    private KbWorkGuideService kbWorkGuideService;

    /**
     * 获取业务指南分页
     *
     * @author Ranger
     * @date  2024/01/29 14:15
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("Client端获取业务指南分页")
    @GetMapping("/knowledge/client/guide/page")
    public CommonResult<Page<KbWorkGuide>> page(KbWorkGuidePageParam kbWorkGuidePageParam) {
        return CommonResult.data(kbWorkGuideService.page(kbWorkGuidePageParam));
    }

    /**
     * 获取业务指南详情
     *
     * @author Ranger
     * @date  2024/01/29 14:15
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("Client端获取业务指南详情")
    @GetMapping("/knowledge/client/guide/detail")
    public CommonResult<KbWorkGuide> detail(@Valid KbWorkGuideIdParam kbWorkGuideIdParam) {
        return CommonResult.data(kbWorkGuideService.detail(kbWorkGuideIdParam));
    }
}
