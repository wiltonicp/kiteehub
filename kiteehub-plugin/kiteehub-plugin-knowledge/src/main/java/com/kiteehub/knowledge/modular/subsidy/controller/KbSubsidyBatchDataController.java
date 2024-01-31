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
package com.kiteehub.knowledge.modular.subsidy.controller;

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
import com.kiteehub.knowledge.modular.subsidy.entity.KbSubsidyBatchData;
import com.kiteehub.knowledge.modular.subsidy.param.KbSubsidyBatchDataAddParam;
import com.kiteehub.knowledge.modular.subsidy.param.KbSubsidyBatchDataEditParam;
import com.kiteehub.knowledge.modular.subsidy.param.KbSubsidyBatchDataIdParam;
import com.kiteehub.knowledge.modular.subsidy.param.KbSubsidyBatchDataPageParam;
import com.kiteehub.knowledge.modular.subsidy.service.KbSubsidyBatchDataService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 待遇补贴批次详情控制器
 *
 * @author Ranger
 * @date  2024/01/31 15:06
 */
@Api(tags = "待遇补贴批次详情控制器")
@ApiSupport(author = "KITEEHUB_TEAM", order = 1)
@RestController
@Validated
public class KbSubsidyBatchDataController {

    @Resource
    private KbSubsidyBatchDataService kbSubsidyBatchDataService;

    /**
     * 获取待遇补贴批次详情分页
     *
     * @author Ranger
     * @date  2024/01/31 15:06
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取待遇补贴批次详情分页")
    @SaCheckPermission("/knowledge/subsidy/data/page")
    @GetMapping("/knowledge/subsidy/data/page")
    public CommonResult<Page<KbSubsidyBatchData>> page(KbSubsidyBatchDataPageParam kbSubsidyBatchDataPageParam) {
        return CommonResult.data(kbSubsidyBatchDataService.page(kbSubsidyBatchDataPageParam));
    }

    /**
     * 添加待遇补贴批次详情
     *
     * @author Ranger
     * @date  2024/01/31 15:06
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加待遇补贴批次详情")
    @CommonLog("添加待遇补贴批次详情")
    @SaCheckPermission("/knowledge/subsidy/data/add")
    @PostMapping("/knowledge/subsidy/data/add")
    public CommonResult<String> add(@RequestBody @Valid KbSubsidyBatchDataAddParam kbSubsidyBatchDataAddParam) {
        kbSubsidyBatchDataService.add(kbSubsidyBatchDataAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑待遇补贴批次详情
     *
     * @author Ranger
     * @date  2024/01/31 15:06
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑待遇补贴批次详情")
    @CommonLog("编辑待遇补贴批次详情")
    @SaCheckPermission("/knowledge/subsidy/data/edit")
    @PostMapping("/knowledge/subsidy/data/edit")
    public CommonResult<String> edit(@RequestBody @Valid KbSubsidyBatchDataEditParam kbSubsidyBatchDataEditParam) {
        kbSubsidyBatchDataService.edit(kbSubsidyBatchDataEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除待遇补贴批次详情
     *
     * @author Ranger
     * @date  2024/01/31 15:06
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除待遇补贴批次详情")
    @CommonLog("删除待遇补贴批次详情")
    @SaCheckPermission("/knowledge/subsidy/data/delete")
    @PostMapping("/knowledge/subsidy/data/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<KbSubsidyBatchDataIdParam> kbSubsidyBatchDataIdParamList) {
        kbSubsidyBatchDataService.delete(kbSubsidyBatchDataIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取待遇补贴批次详情详情
     *
     * @author Ranger
     * @date  2024/01/31 15:06
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取待遇补贴批次详情详情")
    @SaCheckPermission("/knowledge/subsidy/data/detail")
    @GetMapping("/knowledge/subsidy/data/detail")
    public CommonResult<KbSubsidyBatchData> detail(@Valid KbSubsidyBatchDataIdParam kbSubsidyBatchDataIdParam) {
        return CommonResult.data(kbSubsidyBatchDataService.detail(kbSubsidyBatchDataIdParam));
    }
}
