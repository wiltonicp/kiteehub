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
package com.kiteehub.sys.modular.resource.controller;

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
import com.kiteehub.sys.modular.resource.entity.SysModule;
import com.kiteehub.sys.modular.resource.param.module.SysModuleAddParam;
import com.kiteehub.sys.modular.resource.param.module.SysModuleEditParam;
import com.kiteehub.sys.modular.resource.param.module.SysModuleIdParam;
import com.kiteehub.sys.modular.resource.param.module.SysModulePageParam;
import com.kiteehub.sys.modular.resource.service.SysModuleService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 模块控制器
 *
 * @author xuyuxiang
 * @date 2022/6/27 14:12
 **/
@Api(tags = "模块控制器")
@ApiSupport(author = "KITEE_HUB_TEAM", order = 6)
@RestController
@Validated
public class SysModuleController {

    @Resource
    private SysModuleService sysModuleService;

    /**
     * 获取模块分页
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取模块分页")
    @GetMapping("/sys/module/page")
    public CommonResult<Page<SysModule>> page(SysModulePageParam sysModulePageParam) {
        return CommonResult.data(sysModuleService.page(sysModulePageParam));
    }

    /**
     * 添加模块
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加模块")
    @CommonLog("添加模块")
    @PostMapping("/sys/module/add")
    public CommonResult<String> add(@RequestBody @Valid SysModuleAddParam sysModuleAddParam) {
        sysModuleService.add(sysModuleAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑模块
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑模块")
    @CommonLog("编辑模块")
    @PostMapping("/sys/module/edit")
    public CommonResult<String> edit(@RequestBody @Valid SysModuleEditParam sysModuleEditParam) {
        sysModuleService.edit(sysModuleEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除模块
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除模块")
    @CommonLog("删除模块")
    @PostMapping("/sys/module/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<SysModuleIdParam> sysModuleIdParamList) {
        sysModuleService.delete(sysModuleIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取模块详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取模块详情")
    @GetMapping("/sys/module/detail")
    public CommonResult<SysModule> detail(@Valid SysModuleIdParam sysModuleIdParam) {
        return CommonResult.data(sysModuleService.detail(sysModuleIdParam));
    }
}
