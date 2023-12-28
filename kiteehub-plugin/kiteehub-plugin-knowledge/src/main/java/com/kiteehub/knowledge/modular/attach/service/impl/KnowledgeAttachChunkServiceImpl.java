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
import com.kiteehub.knowledge.modular.attach.entity.KnowledgeAttach;
import com.kiteehub.knowledge.modular.attach.service.KnowledgeAttachService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kiteehub.common.enums.CommonSortOrderEnum;
import com.kiteehub.common.exception.CommonException;
import com.kiteehub.common.page.CommonPageRequest;
import com.kiteehub.knowledge.modular.attach.entity.KnowledgeAttachChunk;
import com.kiteehub.knowledge.modular.attach.mapper.KnowledgeAttachChunkMapper;
import com.kiteehub.knowledge.modular.attach.param.KnowledgeAttachChunkAddParam;
import com.kiteehub.knowledge.modular.attach.param.KnowledgeAttachChunkEditParam;
import com.kiteehub.knowledge.modular.attach.param.KnowledgeAttachChunkIdParam;
import com.kiteehub.knowledge.modular.attach.param.KnowledgeAttachChunkPageParam;
import com.kiteehub.knowledge.modular.attach.service.KnowledgeAttachChunkService;

import java.util.List;

/**
 * 知识附件分片表Service接口实现类
 *
 * @author Ranger
 * @date  2023/12/28 14:52
 **/
@Service
@AllArgsConstructor
public class KnowledgeAttachChunkServiceImpl extends ServiceImpl<KnowledgeAttachChunkMapper, KnowledgeAttachChunk> implements KnowledgeAttachChunkService {

    private final KnowledgeAttachService knowledgeAttachService;

    @Override
    public Page<KnowledgeAttachChunk> page(KnowledgeAttachChunkPageParam knowledgeAttachChunkPageParam) {
        KnowledgeAttach knowledgeAttach = knowledgeAttachService.queryEntity(knowledgeAttachChunkPageParam.getId());
        QueryWrapper<KnowledgeAttachChunk> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(KnowledgeAttachChunk::getKid,knowledgeAttach.getKid());
        queryWrapper.lambda().eq(KnowledgeAttachChunk::getDocId,knowledgeAttach.getDocId());
        if(ObjectUtil.isAllNotEmpty(knowledgeAttachChunkPageParam.getSortField(), knowledgeAttachChunkPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(knowledgeAttachChunkPageParam.getSortOrder());
            queryWrapper.orderBy(true, knowledgeAttachChunkPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(knowledgeAttachChunkPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(KnowledgeAttachChunk::getRowId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(KnowledgeAttachChunkAddParam knowledgeAttachChunkAddParam) {
        KnowledgeAttachChunk knowledgeAttachChunk = BeanUtil.toBean(knowledgeAttachChunkAddParam, KnowledgeAttachChunk.class);
        this.save(knowledgeAttachChunk);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(KnowledgeAttachChunkEditParam knowledgeAttachChunkEditParam) {
        KnowledgeAttachChunk knowledgeAttachChunk = this.queryEntity(knowledgeAttachChunkEditParam.getId());
        BeanUtil.copyProperties(knowledgeAttachChunkEditParam, knowledgeAttachChunk);
        this.updateById(knowledgeAttachChunk);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<KnowledgeAttachChunkIdParam> knowledgeAttachChunkIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(knowledgeAttachChunkIdParamList, KnowledgeAttachChunkIdParam::getId));
    }

    @Override
    public KnowledgeAttachChunk detail(KnowledgeAttachChunkIdParam knowledgeAttachChunkIdParam) {
        return this.queryEntity(knowledgeAttachChunkIdParam.getId());
    }

    @Override
    public KnowledgeAttachChunk queryEntity(String id) {
        KnowledgeAttachChunk knowledgeAttachChunk = this.getById(id);
        if(ObjectUtil.isEmpty(knowledgeAttachChunk)) {
            throw new CommonException("知识附件分片表不存在，id值为：{}", id);
        }
        return knowledgeAttachChunk;
    }
}
