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
package com.kiteehub.knowledge.modular.article.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kiteehub.common.enums.CommonSortOrderEnum;
import com.kiteehub.common.exception.CommonException;
import com.kiteehub.common.page.CommonPageRequest;
import com.kiteehub.knowledge.modular.article.entity.KnowledgeHotArticle;
import com.kiteehub.knowledge.modular.article.mapper.KnowledgeHotArticleMapper;
import com.kiteehub.knowledge.modular.article.param.KnowledgeHotArticleAddParam;
import com.kiteehub.knowledge.modular.article.param.KnowledgeHotArticleEditParam;
import com.kiteehub.knowledge.modular.article.param.KnowledgeHotArticleIdParam;
import com.kiteehub.knowledge.modular.article.param.KnowledgeHotArticlePageParam;
import com.kiteehub.knowledge.modular.article.service.KnowledgeHotArticleService;

import java.util.List;

/**
 * 热门动态Service接口实现类
 *
 * @author Ranger
 * @date  2024/01/04 09:54
 **/
@Service
public class KnowledgeHotArticleServiceImpl extends ServiceImpl<KnowledgeHotArticleMapper, KnowledgeHotArticle> implements KnowledgeHotArticleService {

    @Override
    public Page<KnowledgeHotArticle> page(KnowledgeHotArticlePageParam knowledgeHotArticlePageParam) {
        QueryWrapper<KnowledgeHotArticle> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(knowledgeHotArticlePageParam.getKid())) {
            queryWrapper.lambda().eq(KnowledgeHotArticle::getKid, knowledgeHotArticlePageParam.getKid());
        }
        if(ObjectUtil.isNotEmpty(knowledgeHotArticlePageParam.getTitle())) {
            queryWrapper.lambda().like(KnowledgeHotArticle::getTitle, knowledgeHotArticlePageParam.getTitle());
        }
        if(ObjectUtil.isAllNotEmpty(knowledgeHotArticlePageParam.getSortField(), knowledgeHotArticlePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(knowledgeHotArticlePageParam.getSortOrder());
            queryWrapper.orderBy(true, knowledgeHotArticlePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(knowledgeHotArticlePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(KnowledgeHotArticle::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(KnowledgeHotArticleAddParam knowledgeHotArticleAddParam) {
        KnowledgeHotArticle knowledgeHotArticle = BeanUtil.toBean(knowledgeHotArticleAddParam, KnowledgeHotArticle.class);
        this.save(knowledgeHotArticle);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(KnowledgeHotArticleEditParam knowledgeHotArticleEditParam) {
        KnowledgeHotArticle knowledgeHotArticle = this.queryEntity(knowledgeHotArticleEditParam.getId());
        BeanUtil.copyProperties(knowledgeHotArticleEditParam, knowledgeHotArticle);
        this.updateById(knowledgeHotArticle);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<KnowledgeHotArticleIdParam> knowledgeHotArticleIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(knowledgeHotArticleIdParamList, KnowledgeHotArticleIdParam::getId));
    }

    @Override
    public KnowledgeHotArticle detail(KnowledgeHotArticleIdParam knowledgeHotArticleIdParam) {
        return this.queryEntity(knowledgeHotArticleIdParam.getId());
    }

    @Override
    public KnowledgeHotArticle queryEntity(String id) {
        KnowledgeHotArticle knowledgeHotArticle = this.getById(id);
        if(ObjectUtil.isEmpty(knowledgeHotArticle)) {
            throw new CommonException("热门动态不存在，id值为：{}", id);
        }
        return knowledgeHotArticle;
    }
}
