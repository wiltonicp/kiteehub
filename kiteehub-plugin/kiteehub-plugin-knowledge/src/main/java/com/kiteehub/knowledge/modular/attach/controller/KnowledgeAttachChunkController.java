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
package com.kiteehub.knowledge.modular.attach.controller;

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
import com.kiteehub.knowledge.modular.attach.entity.KnowledgeAttachChunk;
import com.kiteehub.knowledge.modular.attach.param.KnowledgeAttachChunkAddParam;
import com.kiteehub.knowledge.modular.attach.param.KnowledgeAttachChunkEditParam;
import com.kiteehub.knowledge.modular.attach.param.KnowledgeAttachChunkIdParam;
import com.kiteehub.knowledge.modular.attach.param.KnowledgeAttachChunkPageParam;
import com.kiteehub.knowledge.modular.attach.service.KnowledgeAttachChunkService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 知识附件分片表控制器
 *
 * @author Ranger
 * @date  2023/12/28 14:52
 */
@Api(tags = "知识附件分片表控制器")
@ApiSupport(author = "KITEEHUB_TEAM", order = 1)
@RestController
@Validated
public class KnowledgeAttachChunkController {

    @Resource
    private KnowledgeAttachChunkService knowledgeAttachChunkService;

    /**
     * 获取知识附件分片表分页
     *
     * @author Ranger
     * @date  2023/12/28 14:52
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取知识附件分片表分页")
    @SaCheckPermission("/knowledge/attach-chunk/page")
    @GetMapping("/knowledge/attach-chunk/page")
    public CommonResult<Page<KnowledgeAttachChunk>> page(KnowledgeAttachChunkPageParam knowledgeAttachChunkPageParam) {
        return CommonResult.data(knowledgeAttachChunkService.page(knowledgeAttachChunkPageParam));
    }

    /**
     * 添加知识附件分片表
     *
     * @author Ranger
     * @date  2023/12/28 14:52
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加知识附件分片表")
    @CommonLog("添加知识附件分片表")
    @SaCheckPermission("/knowledge/attach-chunk/add")
    @PostMapping("/knowledge/attach-chunk/add")
    public CommonResult<String> add(@RequestBody @Valid KnowledgeAttachChunkAddParam knowledgeAttachChunkAddParam) {
        knowledgeAttachChunkService.add(knowledgeAttachChunkAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑知识附件分片表
     *
     * @author Ranger
     * @date  2023/12/28 14:52
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑知识附件分片表")
    @CommonLog("编辑知识附件分片表")
    @SaCheckPermission("/knowledge/attach-chunk/edit")
    @PostMapping("/knowledge/attach-chunk/edit")
    public CommonResult<String> edit(@RequestBody @Valid KnowledgeAttachChunkEditParam knowledgeAttachChunkEditParam) {
        knowledgeAttachChunkService.edit(knowledgeAttachChunkEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除知识附件分片表
     *
     * @author Ranger
     * @date  2023/12/28 14:52
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除知识附件分片表")
    @CommonLog("删除知识附件分片表")
    @SaCheckPermission("/knowledge/attach-chunk/delete")
    @PostMapping("/knowledge/attach-chunk/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<KnowledgeAttachChunkIdParam> knowledgeAttachChunkIdParamList) {
        knowledgeAttachChunkService.delete(knowledgeAttachChunkIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取知识附件分片表详情
     *
     * @author Ranger
     * @date  2023/12/28 14:52
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取知识附件分片表详情")
    @SaCheckPermission("/knowledge/attach-chunk/detail")
    @GetMapping("/knowledge/attach-chunk/detail")
    public CommonResult<KnowledgeAttachChunk> detail(@Valid KnowledgeAttachChunkIdParam knowledgeAttachChunkIdParam) {
        return CommonResult.data(knowledgeAttachChunkService.detail(knowledgeAttachChunkIdParam));
    }
}
