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
import com.kiteehub.knowledge.modular.attach.entity.KnowledgeAttachChunk;
import com.kiteehub.knowledge.modular.knowledge.param.KnowledgeUploadParam;
import com.kiteehub.knowledge.modular.knowledge.service.KnowledgeService;
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
import com.kiteehub.knowledge.modular.attach.entity.KnowledgeAttach;
import com.kiteehub.knowledge.modular.attach.param.KnowledgeAttachAddParam;
import com.kiteehub.knowledge.modular.attach.param.KnowledgeAttachEditParam;
import com.kiteehub.knowledge.modular.attach.param.KnowledgeAttachIdParam;
import com.kiteehub.knowledge.modular.attach.param.KnowledgeAttachPageParam;
import com.kiteehub.knowledge.modular.attach.service.KnowledgeAttachService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

/**
 * 知识库附件控制器
 *
 * @author Ranger
 * @date 2023/12/27 14:00
 */
@Api(tags = "知识库附件控制器")
@ApiSupport(author = "KITEEHUB_TEAM", order = 1)
@RestController
@Validated
public class KnowledgeAttachController {

    @Resource
    private KnowledgeAttachService knowledgeAttachService;
    @Resource
    private KnowledgeService knowledgeService;

    /**
     * 获取知识库附件分页
     *
     * @author Ranger
     * @date 2023/12/27 14:00
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取知识库附件分页")
    @SaCheckPermission("/knowledge/attach/page")
    @GetMapping("/knowledge/attach/page")
    public CommonResult<Page<KnowledgeAttach>> page(KnowledgeAttachPageParam knowledgeAttachPageParam) {
        return CommonResult.data(knowledgeAttachService.page(knowledgeAttachPageParam));
    }

    /**
     * 上传知识库附件
     *
     * @param request
     * @return
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("上传知识库附件")
    @CommonLog("上传知识库附件")
    @SaCheckPermission("/knowledge/attach/add")
    @PostMapping(value = "/knowledge/attach/add")
    public CommonResult<String> upload(@Valid KnowledgeUploadParam request) {
        knowledgeService.upload(request);
        return CommonResult.ok("上传知识库文件成功");
    }

    /**
     * 编辑知识库附件名称
     *
     * @author Ranger
     * @date 2023/12/27 14:00
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑知识库附件名称")
    @CommonLog("编辑知识库附件名称")
    @SaCheckPermission("/knowledge/attach/edit")
    @PostMapping("/knowledge/attach/edit")
    public CommonResult<String> edit(@RequestBody @Valid KnowledgeAttachEditParam knowledgeAttachEditParam) {
        knowledgeAttachService.edit(knowledgeAttachEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除知识库附件
     *
     * @author Ranger
     * @date 2023/12/27 14:00
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除知识库附件")
    @CommonLog("删除知识库附件")
    @SaCheckPermission("/knowledge/attach/delete")
    @PostMapping("/knowledge/attach/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                       CommonValidList<KnowledgeAttachIdParam> knowledgeAttachIdParamList) {
        knowledgeAttachService.delete(knowledgeAttachIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取知识库附件详情
     *
     * @author Ranger
     * @date 2023/12/27 14:00
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取知识库附件详情")
    @SaCheckPermission("/knowledge/attach/detail")
    @GetMapping("/knowledge/attach/detail")
    public CommonResult<KnowledgeAttach> detail(@Valid KnowledgeAttachIdParam knowledgeAttachIdParam) {
        return CommonResult.data(knowledgeAttachService.detail(knowledgeAttachIdParam));
    }

    /**
     * 获取知识库区域树
     *
     * @author Ranger
     * @date 2023/12/27 14:00
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取知识库区域树")
    @SaCheckPermission("/knowledge/attach/area/tree")
    @GetMapping("/knowledge/attach/area/tree")
    public CommonResult<?> areaTree() {
        return CommonResult.data(knowledgeAttachService.areaTree());
    }

    /**
     * 区域下附件数量查询
     *
     * @author Ranger
     * @date 2023/12/27 14:00
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("区域下附件数量查询")
    @SaCheckPermission("/knowledge/attach/area/count")
    @GetMapping("/knowledge/attach/area/count")
    public CommonResult<?> areaCount(String areaId) {
        return CommonResult.data(knowledgeAttachService.areaCount(areaId));
    }
}
