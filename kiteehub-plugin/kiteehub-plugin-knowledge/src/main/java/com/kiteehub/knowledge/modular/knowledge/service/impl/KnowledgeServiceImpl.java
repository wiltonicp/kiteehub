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
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kiteehub.knowledge.chain.loader.ResourceLoader;
import com.kiteehub.knowledge.chain.loader.ResourceLoaderFactory;
import com.kiteehub.knowledge.modular.attach.entity.KnowledgeAttach;
import com.kiteehub.knowledge.modular.attach.entity.KnowledgeAttachArea;
import com.kiteehub.knowledge.modular.attach.entity.KnowledgeAttachChunk;
import com.kiteehub.knowledge.modular.attach.enums.KnowledgeGatherEnum;
import com.kiteehub.knowledge.modular.attach.service.KnowledgeAttachAreaService;
import com.kiteehub.knowledge.modular.attach.service.KnowledgeAttachChunkService;
import com.kiteehub.knowledge.modular.attach.service.KnowledgeAttachService;
import com.kiteehub.knowledge.modular.knowledge.param.*;
import com.kiteehub.knowledge.modular.knowledge.result.KnowledgeDetailResult;
import com.kiteehub.knowledge.modular.knowledge.service.EmbeddingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kiteehub.common.enums.CommonSortOrderEnum;
import com.kiteehub.common.exception.CommonException;
import com.kiteehub.common.page.CommonPageRequest;
import com.kiteehub.knowledge.modular.knowledge.entity.Knowledge;
import com.kiteehub.knowledge.modular.knowledge.mapper.KnowledgeMapper;
import com.kiteehub.knowledge.modular.knowledge.service.KnowledgeService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 知识库Service接口实现类
 *
 * @author Ranger
 * @date 2023/12/27 10:20
 **/
@Slf4j
@Service
@AllArgsConstructor
public class KnowledgeServiceImpl extends ServiceImpl<KnowledgeMapper, Knowledge> implements KnowledgeService {

    private final EmbeddingService embeddingService;
    private final KnowledgeAttachService knowledgeAttachService;
    private final ResourceLoaderFactory resourceLoaderFactory;
    private final KnowledgeAttachAreaService knowledgeAttachAreaService;
    private final KnowledgeAttachChunkService knowledgeAttachChunkService;

    @Override
    public Page<Knowledge> page(KnowledgePageParam knowledgePageParam) {
        QueryWrapper<Knowledge> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotEmpty(knowledgePageParam.getKname())) {
            queryWrapper.lambda().like(Knowledge::getKname, knowledgePageParam.getKname());
        }
        if (ObjectUtil.isAllNotEmpty(knowledgePageParam.getSortField(), knowledgePageParam.getSortOrder())) {
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
    public Knowledge queryEntity(String id) {
        Knowledge knowledge = this.getById(id);
        if (ObjectUtil.isEmpty(knowledge)) {
            throw new CommonException("知识库不存在，id值为：{}", id);
        }
        return knowledge;
    }

    @Override
    public void saveOne(KnowledgeSaveParam request, String userId) {
        Knowledge knowledge = new Knowledge();
        knowledge.setKid(RandomUtil.randomString(10));
        knowledge.setUid(userId);
        knowledge.setKname(request.getKname());
        knowledge.setCreateTime(new Date());
        save(knowledge);
        storeContent(request.getFile(), knowledge.getKid(), request.getKname(), request.getAreaIds(), true);
    }

    @Override
    public void upload(KnowledgeUploadParam request) {
        storeContent(request.getFile(), request.getKid(), "", request.getAreaIds(), false);
    }

    @Override
    public void storeContent(MultipartFile file, String kid, String kname, List<String> areaIds, Boolean firstTime) {
        String fileName = file.getOriginalFilename();
        List<String> chunkList = new ArrayList<>();
        KnowledgeAttach knowledgeAttach = new KnowledgeAttach();
        knowledgeAttach.setKid(kid);
        String docId = RandomUtil.randomString(10);
        knowledgeAttach.setDocId(docId);
        knowledgeAttach.setDocName(fileName);
        knowledgeAttach.setDocType(fileName.substring(fileName.lastIndexOf(".") + 1));
        String content = "";
        ResourceLoader resourceLoader = resourceLoaderFactory.getLoaderByFileType(knowledgeAttach.getDocType());
        try {
            content = resourceLoader.getContent(file.getInputStream());
            chunkList = resourceLoader.getChunkList(content);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("store content occur exception ", e);
        }
        knowledgeAttach.setContent(content);
        knowledgeAttach.setCreateTime(new Date());
        knowledgeAttach.setUpdateTime(new Date());
        knowledgeAttach.setTotalData(chunkList.size());
        knowledgeAttach.setGatherState(EnumUtil.toString(KnowledgeGatherEnum.PROGRESSING));
        knowledgeAttachService.save(knowledgeAttach);

        //区域处理
        if (CollectionUtil.isNotEmpty(areaIds)) {
            List<KnowledgeAttachArea> collect = areaIds.stream()
                    .map(c ->
                            KnowledgeAttachArea.builder()
                                    .attachId(knowledgeAttach.getId())
                                    .areaId(c)
                                    .build())
                    .collect(Collectors.toList());
            knowledgeAttachAreaService.saveBatch(collect, 200);
        }

        //附件分片
        List<KnowledgeAttachChunk> attachChunkList = chunkList.stream().map(chunk -> {
            KnowledgeAttachChunk knowledgeAttachChunk = new KnowledgeAttachChunk();
            knowledgeAttachChunk.setKid(kid);
            knowledgeAttachChunk.setDocId(docId);
            knowledgeAttachChunk.setRowId(IdUtil.getSnowflakeNextId());
            knowledgeAttachChunk.setContent(chunk);
            return knowledgeAttachChunk;
        }).collect(Collectors.toList());
        knowledgeAttachChunkService.saveBatch(attachChunkList, 100);

        embeddingService.storeEmbeddings(attachChunkList, kid, kname, docId, firstTime);

        knowledgeAttachService.lambdaUpdate()
                .set(KnowledgeAttach::getGatherState, EnumUtil.toString(KnowledgeGatherEnum.READY))
                .eq(KnowledgeAttach::getId, knowledgeAttach.getId())
                .update();
    }

    @Override
    public KnowledgeDetailResult detail(String kid) {
        KnowledgeDetailResult detail = new KnowledgeDetailResult();
        Map<String, Object> map = new HashMap<>();
        map.put("kid", kid);
        List<Knowledge> knowledgeList = this.listByMap(map);
        if (knowledgeList != null && knowledgeList.size() > 0) {
            Knowledge knowledge = knowledgeList.get(0);
            BeanUtil.copyProperties(knowledge, detail);

            List<KnowledgeAttach> attachList = knowledgeAttachService.listByMap(map);
            detail.setAttachList(attachList);
            return detail;
        }
        return null;
    }

    @Override
    public void removeAttach(KnowledgeAttachRemoveParam request) {
        Map<String, Object> map = new HashMap<>();
        map.put("kid", request.getKid());
        map.put("doc_id", request.getDocId());
        knowledgeAttachService.removeByMap(map);
        embeddingService.removeByDocId(request.getKid(), request.getDocId());
    }

    @Override
    public void removeKnowledge(KnowledgeRemoveParam request) {
        Map<String, Object> map = new HashMap<>();
        map.put("kid", request.getKid());
        this.removeByMap(map);
        knowledgeAttachService.removeByMap(map);
        embeddingService.removeByKid(request.getKid());
    }
}
