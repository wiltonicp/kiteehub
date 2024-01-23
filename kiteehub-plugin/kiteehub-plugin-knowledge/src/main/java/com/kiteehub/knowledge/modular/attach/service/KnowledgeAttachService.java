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
import com.kiteehub.knowledge.modular.attach.entity.KnowledgeAttach;
import com.kiteehub.knowledge.modular.attach.param.*;
import com.kiteehub.knowledge.modular.attach.pojo.Tree;

import java.util.List;
import java.util.Map;

/**
 * 知识库附件Service接口
 *
 * @author Ranger
 * @date 2023/12/27 14:00
 **/
public interface KnowledgeAttachService extends IService<KnowledgeAttach> {

    /**
     * 获取知识库附件分页
     *
     * @author Ranger
     * @date 2023/12/27 14:00
     */
    Page<KnowledgeAttach> page(KnowledgeAttachPageParam knowledgeAttachPageParam);

    /**
     * 添加知识库附件
     *
     * @author Ranger
     * @date 2023/12/27 14:00
     */
    void add(KnowledgeAttachAddParam knowledgeAttachAddParam);

    /**
     * 编辑知识库附件
     *
     * @author Ranger
     * @date 2023/12/27 14:00
     */
    void edit(KnowledgeAttachEditParam knowledgeAttachEditParam);

    /**
     * 删除知识库附件
     *
     * @author Ranger
     * @date 2023/12/27 14:00
     */
    void delete(List<KnowledgeAttachIdParam> knowledgeAttachIdParamList);

    /**
     * 获取知识库附件详情
     *
     * @author Ranger
     * @date 2023/12/27 14:00
     */
    KnowledgeAttach detail(KnowledgeAttachIdParam knowledgeAttachIdParam);

    /**
     * 获取知识库附件详情
     *
     * @author Ranger
     * @date 2023/12/27 14:00
     **/
    KnowledgeAttach queryEntity(String id);

    /**
     * 获取知识库附件详情
     *
     * @author Ranger
     * @date 2023/12/27 14:00
     **/
    KnowledgeAttach queryEntity(String kid, String docId);

    List<? extends Tree<?>> areaTree();

    List<Map<String, Object>> areaCount(String areaId);
}
