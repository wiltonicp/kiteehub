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
package com.kiteehub.knowledge.modular.attach.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kiteehub.knowledge.modular.attach.entity.KnowledgeAttachChunk;
import com.kiteehub.knowledge.modular.attach.param.KnowledgeAttachChunkAddParam;
import com.kiteehub.knowledge.modular.attach.param.KnowledgeAttachChunkEditParam;
import com.kiteehub.knowledge.modular.attach.param.KnowledgeAttachChunkIdParam;
import com.kiteehub.knowledge.modular.attach.param.KnowledgeAttachChunkPageParam;

import java.util.List;

/**
 * 知识附件分片表Service接口
 *
 * @author Ranger
 * @date  2023/12/28 14:52
 **/
public interface KnowledgeAttachChunkService extends IService<KnowledgeAttachChunk> {

    /**
     * 获取知识附件分片表分页
     *
     * @author Ranger
     * @date  2023/12/28 14:52
     */
    Page<KnowledgeAttachChunk> page(KnowledgeAttachChunkPageParam knowledgeAttachChunkPageParam);

    /**
     * 添加知识附件分片表
     *
     * @author Ranger
     * @date  2023/12/28 14:52
     */
    void add(KnowledgeAttachChunkAddParam knowledgeAttachChunkAddParam);

    /**
     * 编辑知识附件分片表
     *
     * @author Ranger
     * @date  2023/12/28 14:52
     */
    void edit(KnowledgeAttachChunkEditParam knowledgeAttachChunkEditParam);

    /**
     * 删除知识附件分片表
     *
     * @author Ranger
     * @date  2023/12/28 14:52
     */
    void delete(List<KnowledgeAttachChunkIdParam> knowledgeAttachChunkIdParamList);

    /**
     * 获取知识附件分片表详情
     *
     * @author Ranger
     * @date  2023/12/28 14:52
     */
    KnowledgeAttachChunk detail(KnowledgeAttachChunkIdParam knowledgeAttachChunkIdParam);

    /**
     * 获取知识附件分片表详情
     *
     * @author Ranger
     * @date  2023/12/28 14:52
     **/
    KnowledgeAttachChunk queryEntity(String id);
}
