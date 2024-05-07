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
package com.kiteehub.knowledge.modular.notice.controller;

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
import com.kiteehub.knowledge.modular.notice.entity.KnowledgeNotice;
import com.kiteehub.knowledge.modular.notice.param.KnowledgeNoticeAddParam;
import com.kiteehub.knowledge.modular.notice.param.KnowledgeNoticeEditParam;
import com.kiteehub.knowledge.modular.notice.param.KnowledgeNoticeIdParam;
import com.kiteehub.knowledge.modular.notice.param.KnowledgeNoticePageParam;
import com.kiteehub.knowledge.modular.notice.service.KnowledgeNoticeService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 通知公告控制器
 *
 * @author Ranger
 * @date  2024/01/29 14:39
 */
@Api(tags = "通知公告控制器")
@ApiSupport(author = "KITEEHUB_TEAM", order = 1)
@RestController
@Validated
public class KnowledgeNoticeController {

    @Resource
    private KnowledgeNoticeService knowledgeNoticeService;

    /**
     * 获取通知公告分页
     *
     * @author Ranger
     * @date  2024/01/29 14:39
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取通知公告分页")
    @SaCheckPermission("/knowledge/notice/page")
    @GetMapping("/knowledge/notice/page")
    public CommonResult<Page<KnowledgeNotice>> page(KnowledgeNoticePageParam knowledgeNoticePageParam) {
        return CommonResult.data(knowledgeNoticeService.page(knowledgeNoticePageParam));
    }

    /**
     * 添加通知公告
     *
     * @author Ranger
     * @date  2024/01/29 14:39
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加通知公告")
    @CommonLog("添加通知公告")
    @SaCheckPermission("/knowledge/notice/add")
    @PostMapping("/knowledge/notice/add")
    public CommonResult<String> add(@RequestBody @Valid KnowledgeNoticeAddParam knowledgeNoticeAddParam) {
        knowledgeNoticeService.add(knowledgeNoticeAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑通知公告
     *
     * @author Ranger
     * @date  2024/01/29 14:39
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑通知公告")
    @CommonLog("编辑通知公告")
    @SaCheckPermission("/knowledge/notice/edit")
    @PostMapping("/knowledge/notice/edit")
    public CommonResult<String> edit(@RequestBody @Valid KnowledgeNoticeEditParam knowledgeNoticeEditParam) {
        knowledgeNoticeService.edit(knowledgeNoticeEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除通知公告
     *
     * @author Ranger
     * @date  2024/01/29 14:39
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除通知公告")
    @CommonLog("删除通知公告")
    @SaCheckPermission("/knowledge/notice/delete")
    @PostMapping("/knowledge/notice/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<KnowledgeNoticeIdParam> knowledgeNoticeIdParamList) {
        knowledgeNoticeService.delete(knowledgeNoticeIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取通知公告详情
     *
     * @author Ranger
     * @date  2024/01/29 14:39
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取通知公告详情")
    @SaCheckPermission("/knowledge/notice/detail")
    @GetMapping("/knowledge/notice/detail")
    public CommonResult<KnowledgeNotice> detail(@Valid KnowledgeNoticeIdParam knowledgeNoticeIdParam) {
        return CommonResult.data(knowledgeNoticeService.detail(knowledgeNoticeIdParam));
    }
}
