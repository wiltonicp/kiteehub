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
package com.kiteehub.knowledge.modular.knowledge.service.impl;

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
import com.kiteehub.knowledge.modular.knowledge.entity.Knowledge;
import com.kiteehub.knowledge.modular.knowledge.mapper.KnowledgeMapper;
import com.kiteehub.knowledge.modular.knowledge.param.KnowledgeAddParam;
import com.kiteehub.knowledge.modular.knowledge.param.KnowledgeEditParam;
import com.kiteehub.knowledge.modular.knowledge.param.KnowledgeIdParam;
import com.kiteehub.knowledge.modular.knowledge.param.KnowledgePageParam;
import com.kiteehub.knowledge.modular.knowledge.service.KnowledgeService;

import java.util.List;

/**
 * 知识库Service接口实现类
 *
 * @author Ranger
 * @date  2023/12/27 10:20
 **/
@Service
public class KnowledgeServiceImpl extends ServiceImpl<KnowledgeMapper, Knowledge> implements KnowledgeService {

    @Override
    public Page<Knowledge> page(KnowledgePageParam knowledgePageParam) {
        QueryWrapper<Knowledge> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(knowledgePageParam.getKname())) {
            queryWrapper.lambda().like(Knowledge::getKname, knowledgePageParam.getKname());
        }
        if(ObjectUtil.isAllNotEmpty(knowledgePageParam.getSortField(), knowledgePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(knowledgePageParam.getSortOrder());
            queryWrapper.orderBy(true, knowledgePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(knowledgePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(Knowledge::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(KnowledgeAddParam knowledgeAddParam) {
        Knowledge knowledge = BeanUtil.toBean(knowledgeAddParam, Knowledge.class);
        this.save(knowledge);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(KnowledgeEditParam knowledgeEditParam) {
        Knowledge knowledge = this.queryEntity(knowledgeEditParam.getId());
        BeanUtil.copyProperties(knowledgeEditParam, knowledge);
        this.updateById(knowledge);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<KnowledgeIdParam> knowledgeIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(knowledgeIdParamList, KnowledgeIdParam::getId));
    }

    @Override
    public Knowledge detail(KnowledgeIdParam knowledgeIdParam) {
        return this.queryEntity(knowledgeIdParam.getId());
    }

    @Override
    public Knowledge queryEntity(Long id) {
        Knowledge knowledge = this.getById(id);
        if(ObjectUtil.isEmpty(knowledge)) {
            throw new CommonException("知识库不存在，id值为：{}", id);
        }
        return knowledge;
    }
}
