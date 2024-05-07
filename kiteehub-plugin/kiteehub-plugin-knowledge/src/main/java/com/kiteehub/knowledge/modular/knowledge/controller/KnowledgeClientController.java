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

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kiteehub.common.pojo.CommonResult;
import com.kiteehub.knowledge.modular.knowledge.service.KnowledgeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Client端知识库控制器
 *
 * @author Ranger
 * @date  2023/12/27 10:20
 */
@Api(tags = "Client端知识库控制器")
@ApiSupport(author = "KITEEHUB_TEAM", order = 1)
@RestController
@Validated
public class KnowledgeClientController {

    @Resource
    private KnowledgeService knowledgeService;

    /**
     * 获取知识对应网站
     *
     * @author Ranger
     * @date  2023/12/27 10:20
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取知识对应网站")
    @GetMapping("/knowledge/client/knowledge/website")
    public CommonResult<String> website(String kid) {
        return CommonResult.data(knowledgeService.queryEntityWithKid(kid).getWebsite());
    }
}
