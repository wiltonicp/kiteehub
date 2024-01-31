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
import cn.hutool.json.JSONObject;
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
import com.kiteehub.knowledge.modular.subsidy.entity.KbSubsidyBatch;
import com.kiteehub.knowledge.modular.subsidy.param.KbSubsidyBatchAddParam;
import com.kiteehub.knowledge.modular.subsidy.param.KbSubsidyBatchEditParam;
import com.kiteehub.knowledge.modular.subsidy.param.KbSubsidyBatchIdParam;
import com.kiteehub.knowledge.modular.subsidy.param.KbSubsidyBatchPageParam;
import com.kiteehub.knowledge.modular.subsidy.service.KbSubsidyBatchService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 补贴公示控制器
 *
 * @author Ranger
 * @date  2024/01/31 14:12
 */
@Api(tags = "补贴公示控制器")
@ApiSupport(author = "KITEEHUB_TEAM", order = 1)
@RestController
@Validated
public class KbSubsidyBatchController {

    @Resource
    private KbSubsidyBatchService kbSubsidyBatchService;

    /**
     * 获取补贴公示分页
     *
     * @author Ranger
     * @date  2024/01/31 14:12
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取补贴公示分页")
    @SaCheckPermission("/knowledge/subsidy/page")
    @GetMapping("/knowledge/subsidy/page")
    public CommonResult<Page<KbSubsidyBatch>> page(KbSubsidyBatchPageParam kbSubsidyBatchPageParam) {
        return CommonResult.data(kbSubsidyBatchService.page(kbSubsidyBatchPageParam));
    }

    /**
     * 导入补贴公示
     *
     * @author Ranger
     * @date  2024/01/31 14:12
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("导入补贴公示")
    @CommonLog("导入补贴公示")
    @SaCheckPermission("/knowledge/subsidy/import")
    @PostMapping("/knowledge/subsidy/import")
    public CommonResult<JSONObject> importSubsidyBatch(@RequestBody @Valid KbSubsidyBatchAddParam kbSubsidyBatchAddParam) {
        return CommonResult.data(kbSubsidyBatchService.importSubsidyBatch(kbSubsidyBatchAddParam));
    }

    /**
     * 删除补贴公示
     *
     * @author Ranger
     * @date  2024/01/31 14:12
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除补贴公示")
    @CommonLog("删除补贴公示")
    @SaCheckPermission("/knowledge/subsidy/delete")
    @PostMapping("/knowledge/subsidy/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<KbSubsidyBatchIdParam> kbSubsidyBatchIdParamList) {
        kbSubsidyBatchService.delete(kbSubsidyBatchIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取补贴公示详情
     *
     * @author Ranger
     * @date  2024/01/31 14:12
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取补贴公示详情")
    @SaCheckPermission("/knowledge/subsidy/detail")
    @GetMapping("/knowledge/subsidy/detail")
    public CommonResult<KbSubsidyBatch> detail(@Valid KbSubsidyBatchIdParam kbSubsidyBatchIdParam) {
        return CommonResult.data(kbSubsidyBatchService.detail(kbSubsidyBatchIdParam));
    }
}
