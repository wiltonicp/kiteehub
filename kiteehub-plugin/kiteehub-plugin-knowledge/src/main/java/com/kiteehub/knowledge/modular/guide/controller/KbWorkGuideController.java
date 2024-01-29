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

import cn.dev33.satoken.annotation.SaCheckPermission;
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
import com.kiteehub.knowledge.modular.guide.entity.KbWorkGuide;
import com.kiteehub.knowledge.modular.guide.param.KbWorkGuideAddParam;
import com.kiteehub.knowledge.modular.guide.param.KbWorkGuideEditParam;
import com.kiteehub.knowledge.modular.guide.param.KbWorkGuideIdParam;
import com.kiteehub.knowledge.modular.guide.param.KbWorkGuidePageParam;
import com.kiteehub.knowledge.modular.guide.service.KbWorkGuideService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 业务指南控制器
 *
 * @author Ranger
 * @date  2024/01/29 14:15
 */
@Api(tags = "业务指南控制器")
@ApiSupport(author = "KITEEHUB_TEAM", order = 1)
@RestController
@Validated
public class KbWorkGuideController {

    @Resource
    private KbWorkGuideService kbWorkGuideService;

    /**
     * 获取业务指南分页
     *
     * @author Ranger
     * @date  2024/01/29 14:15
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务指南分页")
    @SaCheckPermission("/knowledge/guide/page")
    @GetMapping("/knowledge/guide/page")
    public CommonResult<Page<KbWorkGuide>> page(KbWorkGuidePageParam kbWorkGuidePageParam) {
        return CommonResult.data(kbWorkGuideService.page(kbWorkGuidePageParam));
    }

    /**
     * 添加业务指南
     *
     * @author Ranger
     * @date  2024/01/29 14:15
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加业务指南")
    @CommonLog("添加业务指南")
    @SaCheckPermission("/knowledge/guide/add")
    @PostMapping("/knowledge/guide/add")
    public CommonResult<String> add(@RequestBody @Valid KbWorkGuideAddParam kbWorkGuideAddParam) {
        kbWorkGuideService.add(kbWorkGuideAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑业务指南
     *
     * @author Ranger
     * @date  2024/01/29 14:15
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑业务指南")
    @CommonLog("编辑业务指南")
    @SaCheckPermission("/knowledge/guide/edit")
    @PostMapping("/knowledge/guide/edit")
    public CommonResult<String> edit(@RequestBody @Valid KbWorkGuideEditParam kbWorkGuideEditParam) {
        kbWorkGuideService.edit(kbWorkGuideEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除业务指南
     *
     * @author Ranger
     * @date  2024/01/29 14:15
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除业务指南")
    @CommonLog("删除业务指南")
    @SaCheckPermission("/knowledge/guide/delete")
    @PostMapping("/knowledge/guide/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<KbWorkGuideIdParam> kbWorkGuideIdParamList) {
        kbWorkGuideService.delete(kbWorkGuideIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取业务指南详情
     *
     * @author Ranger
     * @date  2024/01/29 14:15
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取业务指南详情")
    @SaCheckPermission("/knowledge/guide/detail")
    @GetMapping("/knowledge/guide/detail")
    public CommonResult<KbWorkGuide> detail(@Valid KbWorkGuideIdParam kbWorkGuideIdParam) {
        return CommonResult.data(kbWorkGuideService.detail(kbWorkGuideIdParam));
    }
}
