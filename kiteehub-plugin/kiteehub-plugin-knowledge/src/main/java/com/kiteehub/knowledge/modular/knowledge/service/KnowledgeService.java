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
package com.kiteehub.knowledge.modular.knowledge.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kiteehub.knowledge.modular.knowledge.entity.Knowledge;
import com.kiteehub.knowledge.modular.knowledge.param.*;
import com.kiteehub.knowledge.modular.knowledge.result.KnowledgeDetailResult;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

/**
 * 知识库Service接口
 *
 * @author Ranger
 * @date  2023/12/27 10:20
 **/
public interface KnowledgeService extends IService<Knowledge> {

    /**
     * 获取知识库分页
     *
     * @author Ranger
     * @date  2023/12/27 10:20
     */
    Page<Knowledge> page(KnowledgePageParam knowledgePageParam);

    /**
     * 添加知识库
     *
     * @author Ranger
     * @date  2023/12/27 10:20
     */
    void add(KnowledgeAddParam knowledgeAddParam);

    /**
     * 编辑知识库
     *
     * @author Ranger
     * @date  2023/12/27 10:20
     */
    void edit(KnowledgeEditParam knowledgeEditParam);

    /**
     * 删除知识库
     *
     * @author Ranger
     * @date  2023/12/27 10:20
     */
    void delete(List<KnowledgeIdParam> knowledgeIdParamList);

    /**
     * 获取知识库详情
     *
     * @author Ranger
     * @date  2023/12/27 10:20
     */
    Knowledge detail(KnowledgeIdParam knowledgeIdParam);

    /**
     * 获取知识库详情
     *
     * @author Ranger
     * @date  2023/12/27 10:20
     **/
    Knowledge queryEntity(Long id);


    void saveOne(KnowledgeSaveParam request, Long userId);

    void upload(KnowledgeUploadParam request);

    void storeContent(MultipartFile file, String kid, Boolean firstTime) throws IOException;

    KnowledgeDetailResult detail(String kid);

    void removeAttach(KnowledgeAttachRemoveParam request);

    void removeKnowledge(KnowledgeRemoveParam request);
}
