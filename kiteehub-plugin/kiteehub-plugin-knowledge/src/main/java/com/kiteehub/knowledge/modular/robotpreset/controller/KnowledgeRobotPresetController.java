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
package com.kiteehub.knowledge.modular.robotpreset.controller;

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
import com.kiteehub.knowledge.modular.robotpreset.entity.KnowledgeRobotPreset;
import com.kiteehub.knowledge.modular.robotpreset.param.KnowledgeRobotPresetAddParam;
import com.kiteehub.knowledge.modular.robotpreset.param.KnowledgeRobotPresetEditParam;
import com.kiteehub.knowledge.modular.robotpreset.param.KnowledgeRobotPresetIdParam;
import com.kiteehub.knowledge.modular.robotpreset.param.KnowledgeRobotPresetPageParam;
import com.kiteehub.knowledge.modular.robotpreset.service.KnowledgeRobotPresetService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 机器人预设问答控制器
 *
 * @author Ranger
 * @date  2024/02/02 10:39
 */
@Api(tags = "机器人预设问答控制器")
@ApiSupport(author = "KITEEHUB_TEAM", order = 1)
@RestController
@Validated
public class KnowledgeRobotPresetController {

    @Resource
    private KnowledgeRobotPresetService knowledgeRobotPresetService;

    /**
     * 获取机器人预设问答分页
     *
     * @author Ranger
     * @date  2024/02/02 10:39
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取机器人预设问答分页")
    @SaCheckPermission("/knowledge/robotpreset/page")
    @GetMapping("/knowledge/robotpreset/page")
    public CommonResult<Page<KnowledgeRobotPreset>> page(KnowledgeRobotPresetPageParam knowledgeRobotPresetPageParam) {
        return CommonResult.data(knowledgeRobotPresetService.page(knowledgeRobotPresetPageParam));
    }

    /**
     * 添加机器人预设问答
     *
     * @author Ranger
     * @date  2024/02/02 10:39
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加机器人预设问答")
    @CommonLog("添加机器人预设问答")
    @SaCheckPermission("/knowledge/robotpreset/add")
    @PostMapping("/knowledge/robotpreset/add")
    public CommonResult<String> add(@RequestBody @Valid KnowledgeRobotPresetAddParam knowledgeRobotPresetAddParam) {
        knowledgeRobotPresetService.add(knowledgeRobotPresetAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑机器人预设问答
     *
     * @author Ranger
     * @date  2024/02/02 10:39
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑机器人预设问答")
    @CommonLog("编辑机器人预设问答")
    @SaCheckPermission("/knowledge/robotpreset/edit")
    @PostMapping("/knowledge/robotpreset/edit")
    public CommonResult<String> edit(@RequestBody @Valid KnowledgeRobotPresetEditParam knowledgeRobotPresetEditParam) {
        knowledgeRobotPresetService.edit(knowledgeRobotPresetEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除机器人预设问答
     *
     * @author Ranger
     * @date  2024/02/02 10:39
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除机器人预设问答")
    @CommonLog("删除机器人预设问答")
    @SaCheckPermission("/knowledge/robotpreset/delete")
    @PostMapping("/knowledge/robotpreset/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<KnowledgeRobotPresetIdParam> knowledgeRobotPresetIdParamList) {
        knowledgeRobotPresetService.delete(knowledgeRobotPresetIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取机器人预设问答详情
     *
     * @author Ranger
     * @date  2024/02/02 10:39
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取机器人预设问答详情")
    @SaCheckPermission("/knowledge/robotpreset/detail")
    @GetMapping("/knowledge/robotpreset/detail")
    public CommonResult<KnowledgeRobotPreset> detail(@Valid KnowledgeRobotPresetIdParam knowledgeRobotPresetIdParam) {
        return CommonResult.data(knowledgeRobotPresetService.detail(knowledgeRobotPresetIdParam));
    }
}
