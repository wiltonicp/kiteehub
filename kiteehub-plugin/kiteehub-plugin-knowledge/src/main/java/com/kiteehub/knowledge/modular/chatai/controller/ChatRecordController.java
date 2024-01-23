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
package com.kiteehub.knowledge.modular.chatai.controller;

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
import com.kiteehub.knowledge.modular.chatai.entity.ChatRecord;
import com.kiteehub.knowledge.modular.chatai.param.ChatRecordAddParam;
import com.kiteehub.knowledge.modular.chatai.param.ChatRecordEditParam;
import com.kiteehub.knowledge.modular.chatai.param.ChatRecordIdParam;
import com.kiteehub.knowledge.modular.chatai.param.ChatRecordPageParam;
import com.kiteehub.knowledge.modular.chatai.service.ChatRecordService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 客服记录控制器
 *
 * @author Ranger
 * @date  2024/01/23 17:00
 */
@Api(tags = "客服记录控制器")
@ApiSupport(author = "KITEEHUB_TEAM", order = 1)
@RestController
@Validated
public class ChatRecordController {

    @Resource
    private ChatRecordService chatRecordService;

    /**
     * 获取客服记录分页
     *
     * @author Ranger
     * @date  2024/01/23 17:00
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取客服记录分页")
    @SaCheckPermission("/knowledge/chatai/page")
    @GetMapping("/knowledge/chatai/page")
    public CommonResult<Page<ChatRecord>> page(ChatRecordPageParam chatRecordPageParam) {
        return CommonResult.data(chatRecordService.page(chatRecordPageParam));
    }

    /**
     * 编辑客服记录
     *
     * @author Ranger
     * @date  2024/01/23 17:00
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑客服记录")
    @CommonLog("编辑客服记录")
    @SaCheckPermission("/knowledge/chatai/edit")
    @PostMapping("/knowledge/chatai/edit")
    public CommonResult<String> edit(@RequestBody @Valid ChatRecordEditParam chatRecordEditParam) {
        chatRecordService.edit(chatRecordEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除客服记录
     *
     * @author Ranger
     * @date  2024/01/23 17:00
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除客服记录")
    @CommonLog("删除客服记录")
    @SaCheckPermission("/knowledge/chatai/delete")
    @PostMapping("/knowledge/chatai/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<ChatRecordIdParam> chatRecordIdParamList) {
        chatRecordService.delete(chatRecordIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取客服记录详情
     *
     * @author Ranger
     * @date  2024/01/23 17:00
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取客服记录详情")
    @SaCheckPermission("/knowledge/chatai/detail")
    @GetMapping("/knowledge/chatai/detail")
    public CommonResult<ChatRecord> detail(@Valid ChatRecordIdParam chatRecordIdParam) {
        return CommonResult.data(chatRecordService.detail(chatRecordIdParam));
    }
}
