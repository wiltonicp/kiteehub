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
package com.kiteehub.knowledge.modular.article.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kiteehub.knowledge.modular.article.entity.KnowledgeHotArticle;
import com.kiteehub.knowledge.modular.article.param.KnowledgeHotArticleAddParam;
import com.kiteehub.knowledge.modular.article.param.KnowledgeHotArticleEditParam;
import com.kiteehub.knowledge.modular.article.param.KnowledgeHotArticleIdParam;
import com.kiteehub.knowledge.modular.article.param.KnowledgeHotArticlePageParam;

import java.util.List;

/**
 * 热门动态Service接口
 *
 * @author Ranger
 * @date  2024/01/04 09:54
 **/
public interface KnowledgeHotArticleService extends IService<KnowledgeHotArticle> {

    /**
     * 获取热门动态分页
     *
     * @author Ranger
     * @date  2024/01/04 09:54
     */
    Page<KnowledgeHotArticle> page(KnowledgeHotArticlePageParam knowledgeHotArticlePageParam);

    /**
     * 添加热门动态
     *
     * @author Ranger
     * @date  2024/01/04 09:54
     */
    void add(KnowledgeHotArticleAddParam knowledgeHotArticleAddParam);

    /**
     * 编辑热门动态
     *
     * @author Ranger
     * @date  2024/01/04 09:54
     */
    void edit(KnowledgeHotArticleEditParam knowledgeHotArticleEditParam);

    /**
     * 删除热门动态
     *
     * @author Ranger
     * @date  2024/01/04 09:54
     */
    void delete(List<KnowledgeHotArticleIdParam> knowledgeHotArticleIdParamList);

    /**
     * 获取热门动态详情
     *
     * @author Ranger
     * @date  2024/01/04 09:54
     */
    KnowledgeHotArticle detail(KnowledgeHotArticleIdParam knowledgeHotArticleIdParam);

    /**
     * 获取热门动态详情
     *
     * @author Ranger
     * @date  2024/01/04 09:54
     **/
    KnowledgeHotArticle queryEntity(String id);
}
