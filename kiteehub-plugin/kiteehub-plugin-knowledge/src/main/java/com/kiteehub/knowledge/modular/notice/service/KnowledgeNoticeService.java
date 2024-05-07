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
package com.kiteehub.knowledge.modular.notice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kiteehub.knowledge.modular.notice.entity.KnowledgeNotice;
import com.kiteehub.knowledge.modular.notice.param.KnowledgeNoticeAddParam;
import com.kiteehub.knowledge.modular.notice.param.KnowledgeNoticeEditParam;
import com.kiteehub.knowledge.modular.notice.param.KnowledgeNoticeIdParam;
import com.kiteehub.knowledge.modular.notice.param.KnowledgeNoticePageParam;

import java.util.List;

/**
 * 通知公告Service接口
 *
 * @author Ranger
 * @date  2024/01/29 14:39
 **/
public interface KnowledgeNoticeService extends IService<KnowledgeNotice> {

    /**
     * 获取通知公告分页
     *
     * @author Ranger
     * @date  2024/01/29 14:39
     */
    Page<KnowledgeNotice> page(KnowledgeNoticePageParam knowledgeNoticePageParam);

    /**
     * 添加通知公告
     *
     * @author Ranger
     * @date  2024/01/29 14:39
     */
    void add(KnowledgeNoticeAddParam knowledgeNoticeAddParam);

    /**
     * 编辑通知公告
     *
     * @author Ranger
     * @date  2024/01/29 14:39
     */
    void edit(KnowledgeNoticeEditParam knowledgeNoticeEditParam);

    /**
     * 删除通知公告
     *
     * @author Ranger
     * @date  2024/01/29 14:39
     */
    void delete(List<KnowledgeNoticeIdParam> knowledgeNoticeIdParamList);

    /**
     * 获取通知公告详情
     *
     * @author Ranger
     * @date  2024/01/29 14:39
     */
    KnowledgeNotice detail(KnowledgeNoticeIdParam knowledgeNoticeIdParam);

    /**
     * 获取通知公告详情
     *
     * @author Ranger
     * @date  2024/01/29 14:39
     **/
    KnowledgeNotice queryEntity(String id);
}
