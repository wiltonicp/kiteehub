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
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kiteehub.knowledge.modular.attach.entity.KnowledgeAttachArea;
import com.kiteehub.knowledge.modular.attach.mapper.KnowledgeAttachChunkMapper;
import com.kiteehub.knowledge.modular.attach.service.KnowledgeAttachAreaService;
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
import java.util.stream.Collectors;

/**
 * 知识库附件Service接口实现类
 *
 * @author Ranger
 * @date 2023/12/27 14:00
 **/
@Service
@AllArgsConstructor
public class KnowledgeAttachServiceImpl extends ServiceImpl<KnowledgeAttachMapper, KnowledgeAttach> implements KnowledgeAttachService {

    private final EmbeddingService embeddingService;
    private final KnowledgeAttachChunkMapper knowledgeAttachChunkMapper;
    private final KnowledgeAttachAreaService knowledgeAttachAreaService;

    @Override
    public Page<KnowledgeAttach> page(KnowledgeAttachPageParam knowledgeAttachPageParam) {
        QueryWrapper<KnowledgeAttach> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(KnowledgeAttach.class,attach -> !attach.getColumn().equals("content"));
        if (ObjectUtil.isNotEmpty(knowledgeAttachPageParam.getKid())) {
            queryWrapper.lambda().eq(KnowledgeAttach::getKid, knowledgeAttachPageParam.getKid());
        }
        if (ObjectUtil.isNotEmpty(knowledgeAttachPageParam.getDocName())) {
            queryWrapper.lambda().like(KnowledgeAttach::getDocName, knowledgeAttachPageParam.getDocName());
        }
        if (ObjectUtil.isNotEmpty(knowledgeAttachPageParam.getGatherState())) {
            queryWrapper.lambda().eq(KnowledgeAttach::getGatherState, knowledgeAttachPageParam.getGatherState());
        }
        if (ObjectUtil.isAllNotEmpty(knowledgeAttachPageParam.getSortField(), knowledgeAttachPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(knowledgeAttachPageParam.getSortOrder());
            queryWrapper.orderBy(true, knowledgeAttachPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(knowledgeAttachPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(KnowledgeAttach::getUpdateTime);
        }
        Page<KnowledgeAttach> page = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        page.getRecords().forEach(record ->{
            //区域处理
            QueryWrapper<KnowledgeAttachArea> areaQueryWrapper = new QueryWrapper<>();
            areaQueryWrapper.lambda().eq(KnowledgeAttachArea::getAttachId,record.getId());
            List<KnowledgeAttachArea> listArea = knowledgeAttachAreaService.list(areaQueryWrapper);
            List<String> collect = listArea.stream().map(KnowledgeAttachArea::getAreaId).collect(Collectors.toList());
            record.setAreaIds(collect);
        });
        return page;
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

        if(CollectionUtil.isNotEmpty(knowledgeAttachEditParam.getAreaIds())){
            //区域处理
            QueryWrapper<KnowledgeAttachArea> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(KnowledgeAttachArea::getAttachId,knowledgeAttach.getId());
            knowledgeAttachAreaService.remove(queryWrapper);
            List<KnowledgeAttachArea> collect = knowledgeAttachEditParam.getAreaIds().stream()
                    .map(c ->
                            KnowledgeAttachArea.builder()
                                    .attachId(knowledgeAttach.getId())
                                    .areaId(c)
                                    .build())
                    .collect(Collectors.toList());
            knowledgeAttachAreaService.saveBatch(collect,200);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<KnowledgeAttachIdParam> knowledgeAttachIdParamList) {
        List<KnowledgeAttach> knowledgeAttaches = this.listByIds(CollStreamUtil.toList(knowledgeAttachIdParamList, KnowledgeAttachIdParam::getId));
        // 执行删除
        knowledgeAttaches.forEach(attache -> {
            Map<String, Object> map = new HashMap<>();
            map.put("kid", attache.getKid());
            map.put("doc_id", attache.getDocId());
            this.removeByMap(map);
            this.knowledgeAttachChunkMapper.deleteByMap(map);
            embeddingService.removeByDocId(attache.getKid(), attache.getDocId());

            //区域删除
            QueryWrapper<KnowledgeAttachArea> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(KnowledgeAttachArea::getAttachId,attache.getId());
            knowledgeAttachAreaService.remove(queryWrapper);
        });
    }

    @Override
    public KnowledgeAttach detail(KnowledgeAttachIdParam knowledgeAttachIdParam) {
        return this.queryEntity(knowledgeAttachIdParam.getId());
    }

    @Override
    public KnowledgeAttach queryEntity(String id) {
        KnowledgeAttach knowledgeAttach = this.getById(id);
        if (ObjectUtil.isEmpty(knowledgeAttach)) {
            throw new CommonException("知识库附件不存在，id值为：{}", id);
        }
        return knowledgeAttach;
    }

    @Override
    public KnowledgeAttach queryEntity(String kid, String docId) {
        KnowledgeAttach knowledgeAttach = this.lambdaQuery()
                .eq(KnowledgeAttach::getKid, kid)
                .eq(KnowledgeAttach::getDocId, docId)
                .one();
        if (ObjectUtil.isEmpty(knowledgeAttach)) {
            throw new CommonException("知识库附件不存在，kid值为：{}, docId值为：{}", kid, docId);
        }
        return knowledgeAttach;
    }
}
