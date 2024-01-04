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
import com.kiteehub.knowledge.modular.article.entity.KnowledgeHotArticle;
import com.kiteehub.knowledge.modular.article.param.KnowledgeHotArticleAddParam;
import com.kiteehub.knowledge.modular.article.param.KnowledgeHotArticleEditParam;
import com.kiteehub.knowledge.modular.article.param.KnowledgeHotArticleIdParam;
import com.kiteehub.knowledge.modular.article.param.KnowledgeHotArticlePageParam;
import com.kiteehub.knowledge.modular.article.service.KnowledgeHotArticleService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 热门动态控制器
 *
 * @author Ranger
 * @date  2024/01/04 09:54
 */
@Api(tags = "热门动态控制器")
@ApiSupport(author = "KITEEHUB_TEAM", order = 1)
@RestController
@Validated
public class KnowledgeHotArticleController {

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
    @SaCheckPermission("/knowledge/article/page")
    @GetMapping("/knowledge/article/page")
    public CommonResult<Page<KnowledgeHotArticle>> page(KnowledgeHotArticlePageParam knowledgeHotArticlePageParam) {
        return CommonResult.data(knowledgeHotArticleService.page(knowledgeHotArticlePageParam));
    }

    /**
     * 添加热门动态
     *
     * @author Ranger
     * @date  2024/01/04 09:54
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加热门动态")
    @CommonLog("添加热门动态")
    @SaCheckPermission("/knowledge/article/add")
    @PostMapping("/knowledge/article/add")
    public CommonResult<String> add(@RequestBody @Valid KnowledgeHotArticleAddParam knowledgeHotArticleAddParam) {
        knowledgeHotArticleService.add(knowledgeHotArticleAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑热门动态
     *
     * @author Ranger
     * @date  2024/01/04 09:54
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑热门动态")
    @CommonLog("编辑热门动态")
    @SaCheckPermission("/knowledge/article/edit")
    @PostMapping("/knowledge/article/edit")
    public CommonResult<String> edit(@RequestBody @Valid KnowledgeHotArticleEditParam knowledgeHotArticleEditParam) {
        knowledgeHotArticleService.edit(knowledgeHotArticleEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除热门动态
     *
     * @author Ranger
     * @date  2024/01/04 09:54
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除热门动态")
    @CommonLog("删除热门动态")
    @SaCheckPermission("/knowledge/article/delete")
    @PostMapping("/knowledge/article/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<KnowledgeHotArticleIdParam> knowledgeHotArticleIdParamList) {
        knowledgeHotArticleService.delete(knowledgeHotArticleIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取热门动态详情
     *
     * @author Ranger
     * @date  2024/01/04 09:54
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取热门动态详情")
    @SaCheckPermission("/knowledge/article/detail")
    @GetMapping("/knowledge/article/detail")
    public CommonResult<KnowledgeHotArticle> detail(@Valid KnowledgeHotArticleIdParam knowledgeHotArticleIdParam) {
        return CommonResult.data(knowledgeHotArticleService.detail(knowledgeHotArticleIdParam));
    }
}
