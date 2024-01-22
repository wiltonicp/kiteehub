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
package com.kiteehub.knowledge.modular.article.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kiteehub.common.pojo.CommonResult;
import com.kiteehub.knowledge.modular.article.entity.KnowledgeHotArticle;
import com.kiteehub.knowledge.modular.article.param.KnowledgeHotArticleIdParam;
import com.kiteehub.knowledge.modular.article.param.KnowledgeHotArticlePageParam;
import com.kiteehub.knowledge.modular.article.service.KnowledgeHotArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * Client端热门动态控制器
 *
 * @author Ranger
 * @date  2024/01/04 09:54
 */
@Api(tags = "Client端热门动态控制器")
@ApiSupport(author = "KITEEHUB_TEAM", order = 1)
@RestController
@Validated
public class KnowledgeClientHotArticleController {

    @Resource
    private KnowledgeHotArticleService knowledgeHotArticleService;

    /**
     * 获取热门动态分页
     *
     * @author Ranger
     * @date  2024/01/04 09:54
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取热门动态分页")
    @PostMapping("/knowledge/client/article/page")
    public CommonResult<Page<KnowledgeHotArticle>> page(@RequestBody KnowledgeHotArticlePageParam knowledgeHotArticlePageParam) {
        return CommonResult.data(knowledgeHotArticleService.page(knowledgeHotArticlePageParam));
    }
    /**
     * 获取热门动态详情
     *
     * @author Ranger
     * @date  2024/01/04 09:54
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("获取热门动态详情")
    @GetMapping("/knowledge/client/article/detail")
    public CommonResult<KnowledgeHotArticle> detail(@Valid KnowledgeHotArticleIdParam knowledgeHotArticleIdParam) {
        return CommonResult.data(knowledgeHotArticleService.detail(knowledgeHotArticleIdParam));
    }
}
