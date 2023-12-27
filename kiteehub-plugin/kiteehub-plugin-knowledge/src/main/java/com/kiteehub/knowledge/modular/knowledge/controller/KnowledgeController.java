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
package com.kiteehub.knowledge.modular.knowledge.controller;

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
import com.kiteehub.knowledge.modular.knowledge.entity.Knowledge;
import com.kiteehub.knowledge.modular.knowledge.param.KnowledgeAddParam;
import com.kiteehub.knowledge.modular.knowledge.param.KnowledgeEditParam;
import com.kiteehub.knowledge.modular.knowledge.param.KnowledgeIdParam;
import com.kiteehub.knowledge.modular.knowledge.param.KnowledgePageParam;
import com.kiteehub.knowledge.modular.knowledge.service.KnowledgeService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 知识库控制器
 *
 * @author Ranger
 * @date  2023/12/27 10:20
 */
@Api(tags = "知识库控制器")
@ApiSupport(author = "KITEEHUB_TEAM", order = 1)
@RestController
@Validated
public class KnowledgeController {

    @Resource
    private KnowledgeService knowledgeService;

    /**
     * 获取知识库分页
     *
     * @author Ranger
     * @date  2023/12/27 10:20
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取知识库分页")
    @SaCheckPermission("/knowledge/knowledge/page")
    @GetMapping("/knowledge/knowledge/page")
    public CommonResult<Page<Knowledge>> page(KnowledgePageParam knowledgePageParam) {
        return CommonResult.data(knowledgeService.page(knowledgePageParam));
    }

    /**
     * 添加知识库
     *
     * @author Ranger
     * @date  2023/12/27 10:20
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加知识库")
    @CommonLog("添加知识库")
    @SaCheckPermission("/knowledge/knowledge/add")
    @PostMapping("/knowledge/knowledge/add")
    public CommonResult<String> add(@RequestBody @Valid KnowledgeAddParam knowledgeAddParam) {
        knowledgeService.add(knowledgeAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑知识库
     *
     * @author Ranger
     * @date  2023/12/27 10:20
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑知识库")
    @CommonLog("编辑知识库")
    @SaCheckPermission("/knowledge/knowledge/edit")
    @PostMapping("/knowledge/knowledge/edit")
    public CommonResult<String> edit(@RequestBody @Valid KnowledgeEditParam knowledgeEditParam) {
        knowledgeService.edit(knowledgeEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除知识库
     *
     * @author Ranger
     * @date  2023/12/27 10:20
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除知识库")
    @CommonLog("删除知识库")
    @SaCheckPermission("/knowledge/knowledge/delete")
    @PostMapping("/knowledge/knowledge/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<KnowledgeIdParam> knowledgeIdParamList) {
        knowledgeService.delete(knowledgeIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取知识库详情
     *
     * @author Ranger
     * @date  2023/12/27 10:20
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取知识库详情")
    @SaCheckPermission("/knowledge/knowledge/detail")
    @GetMapping("/knowledge/knowledge/detail")
    public CommonResult<Knowledge> detail(@Valid KnowledgeIdParam knowledgeIdParam) {
        return CommonResult.data(knowledgeService.detail(knowledgeIdParam));
    }
}
