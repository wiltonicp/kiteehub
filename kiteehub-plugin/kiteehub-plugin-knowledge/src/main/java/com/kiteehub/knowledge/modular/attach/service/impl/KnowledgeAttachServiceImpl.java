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
package com.kiteehub.knowledge.modular.attach.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kiteehub.knowledge.modular.knowledge.service.EmbeddingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kiteehub.common.enums.CommonSortOrderEnum;
import com.kiteehub.common.exception.CommonException;
import com.kiteehub.common.page.CommonPageRequest;
import com.kiteehub.knowledge.modular.attach.entity.KnowledgeAttach;
import com.kiteehub.knowledge.modular.attach.mapper.KnowledgeAttachMapper;
import com.kiteehub.knowledge.modular.attach.param.KnowledgeAttachAddParam;
import com.kiteehub.knowledge.modular.attach.param.KnowledgeAttachEditParam;
import com.kiteehub.knowledge.modular.attach.param.KnowledgeAttachIdParam;
import com.kiteehub.knowledge.modular.attach.param.KnowledgeAttachPageParam;
import com.kiteehub.knowledge.modular.attach.service.KnowledgeAttachService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 知识库附件Service接口实现类
 *
 * @author Ranger
 * @date  2023/12/27 14:00
 **/
@Service
@AllArgsConstructor
public class KnowledgeAttachServiceImpl extends ServiceImpl<KnowledgeAttachMapper, KnowledgeAttach> implements KnowledgeAttachService {

    private final EmbeddingService embeddingService;

    @Override
    public Page<KnowledgeAttach> page(KnowledgeAttachPageParam knowledgeAttachPageParam) {
        QueryWrapper<KnowledgeAttach> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(knowledgeAttachPageParam.getDocName())) {
            queryWrapper.lambda().like(KnowledgeAttach::getDocName, knowledgeAttachPageParam.getDocName());
        }
        if(ObjectUtil.isNotEmpty(knowledgeAttachPageParam.getGatherState())) {
            queryWrapper.lambda().eq(KnowledgeAttach::getGatherState, knowledgeAttachPageParam.getGatherState());
        }
        if(ObjectUtil.isAllNotEmpty(knowledgeAttachPageParam.getSortField(), knowledgeAttachPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(knowledgeAttachPageParam.getSortOrder());
            queryWrapper.orderBy(true, knowledgeAttachPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(knowledgeAttachPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(KnowledgeAttach::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(KnowledgeAttachAddParam knowledgeAttachAddParam) {
        KnowledgeAttach knowledgeAttach = BeanUtil.toBean(knowledgeAttachAddParam, KnowledgeAttach.class);
        this.save(knowledgeAttach);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(KnowledgeAttachEditParam knowledgeAttachEditParam) {
        KnowledgeAttach knowledgeAttach = this.queryEntity(knowledgeAttachEditParam.getId());
        BeanUtil.copyProperties(knowledgeAttachEditParam, knowledgeAttach);
        this.updateById(knowledgeAttach);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<KnowledgeAttachIdParam> knowledgeAttachIdParamList) {
        List<KnowledgeAttach> knowledgeAttaches = this.listByIds(CollStreamUtil.toList(knowledgeAttachIdParamList, KnowledgeAttachIdParam::getId));
        knowledgeAttaches.forEach(attache ->{
            Map<String,Object> map = new HashMap<>();
            map.put("kid",attache.getKid());
            map.put("doc_id",attache.getDocId());
            this.removeByMap(map);
            embeddingService.removeByDocId(attache.getKid(),attache.getDocId());
        });
    }

    @Override
    public List<Map<String, Object>> detail(KnowledgeAttachIdParam knowledgeAttachIdParam) {
        KnowledgeAttach knowledgeAttach = this.queryEntity(knowledgeAttachIdParam.getId());
        return embeddingService.getListByKId(knowledgeAttach.getKid(), knowledgeAttach.getDocId());
    }

    @Override
    public KnowledgeAttach queryEntity(Long id) {
        KnowledgeAttach knowledgeAttach = this.getById(id);
        if(ObjectUtil.isEmpty(knowledgeAttach)) {
            throw new CommonException("知识库附件不存在，id值为：{}", id);
        }
        return knowledgeAttach;
    }
}
