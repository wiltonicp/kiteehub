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
package com.kiteehub.knowledge.modular.robot.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
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
import com.kiteehub.knowledge.modular.robot.entity.KnowledgeRobot;
import com.kiteehub.knowledge.modular.robot.param.KnowledgeRobotAddParam;
import com.kiteehub.knowledge.modular.robot.param.KnowledgeRobotEditParam;
import com.kiteehub.knowledge.modular.robot.param.KnowledgeRobotIdParam;
import com.kiteehub.knowledge.modular.robot.param.KnowledgeRobotPageParam;
import com.kiteehub.knowledge.modular.robot.service.KnowledgeRobotService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 智能客服控制器
 *
 * @author Ranger
 * @date  2024/01/03 11:44
 */
@Api(tags = "智能客服控制器")
@ApiSupport(author = "KITEEHUB_TEAM", order = 1)
@RestController
@Validated
public class KnowledgeRobotController {

    @Resource
    private KnowledgeRobotService knowledgeRobotService;

    /**
     * 获取智能客服分页
     *
     * @author Ranger
     * @date  2024/01/03 11:44
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取智能客服分页")
    @SaCheckPermission("/knowledge/robot/page")
    @GetMapping("/knowledge/robot/page")
    public CommonResult<Page<KnowledgeRobot>> page(KnowledgeRobotPageParam knowledgeRobotPageParam) {
        return CommonResult.data(knowledgeRobotService.page(knowledgeRobotPageParam));
    }

    /**
     * 添加智能客服
     *
     * @author Ranger
     * @date  2024/01/03 11:44
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加智能客服")
    @CommonLog("添加智能客服")
    @SaCheckPermission("/knowledge/robot/add")
    @PostMapping("/knowledge/robot/add")
    public CommonResult<String> add(@RequestBody @Valid KnowledgeRobotAddParam knowledgeRobotAddParam) {
        knowledgeRobotService.add(knowledgeRobotAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑智能客服
     *
     * @author Ranger
     * @date  2024/01/03 11:44
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑智能客服")
    @CommonLog("编辑智能客服")
    @SaCheckPermission("/knowledge/robot/edit")
    @PostMapping("/knowledge/robot/edit")
    public CommonResult<String> edit(@RequestBody @Valid KnowledgeRobotEditParam knowledgeRobotEditParam) {
        knowledgeRobotService.edit(knowledgeRobotEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除智能客服
     *
     * @author Ranger
     * @date  2024/01/03 11:44
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除智能客服")
    @CommonLog("删除智能客服")
    @SaCheckPermission("/knowledge/robot/delete")
    @PostMapping("/knowledge/robot/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<KnowledgeRobotIdParam> knowledgeRobotIdParamList) {
        knowledgeRobotService.delete(knowledgeRobotIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取智能客服详情
     *
     * @author Ranger
     * @date  2024/01/03 11:44
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取智能客服详情")
    @SaCheckPermission("/knowledge/robot/detail")
    @GetMapping("/knowledge/robot/detail")
    public CommonResult<KnowledgeRobot> detail(@Valid KnowledgeRobotIdParam knowledgeRobotIdParam) {
        return CommonResult.data(knowledgeRobotService.detail(knowledgeRobotIdParam));
    }

    /**
     * 获取智能客服集合
     *
     * @author Ranger
     * @date  2024/01/03 11:44
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取智能客服集合")
    @SaCheckPermission("/knowledge/robot/list")
    @GetMapping("/knowledge/robot/list")
    public CommonResult<List<JSONObject>> listAll() {
        List<JSONObject> collect = knowledgeRobotService.list().stream().map(x -> JSONUtil.createObj().set("value", x.getId()).set("label", x.getName()))
                .collect(Collectors.toList());
        return CommonResult.data(collect);
    }
}
