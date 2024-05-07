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
package com.kiteehub.knowledge.modular.chatai.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kiteehub.knowledge.modular.chatai.entity.ChatRecord;
import com.kiteehub.knowledge.modular.chatai.param.ChatRecordAddParam;
import com.kiteehub.knowledge.modular.chatai.param.ChatRecordEditParam;
import com.kiteehub.knowledge.modular.chatai.param.ChatRecordIdParam;
import com.kiteehub.knowledge.modular.chatai.param.ChatRecordPageParam;

import java.util.List;

/**
 * 客服记录Service接口
 *
 * @author Ranger
 * @date  2024/01/23 17:00
 **/
public interface ChatRecordService extends IService<ChatRecord> {

    /**
     * 获取客服记录分页
     *
     * @author Ranger
     * @date  2024/01/23 17:00
     */
    Page<ChatRecord> page(ChatRecordPageParam chatRecordPageParam);

    /**
     * 添加客服记录
     *
     * @author Ranger
     * @date  2024/01/23 17:00
     */
    void add(ChatRecordAddParam chatRecordAddParam);

    /**
     * 编辑客服记录
     *
     * @author Ranger
     * @date  2024/01/23 17:00
     */
    void edit(ChatRecordEditParam chatRecordEditParam);

    /**
     * 删除客服记录
     *
     * @author Ranger
     * @date  2024/01/23 17:00
     */
    void delete(List<ChatRecordIdParam> chatRecordIdParamList);

    /**
     * 获取客服记录详情
     *
     * @author Ranger
     * @date  2024/01/23 17:00
     */
    ChatRecord detail(ChatRecordIdParam chatRecordIdParam);

    /**
     * 获取客服记录详情
     *
     * @author Ranger
     * @date  2024/01/23 17:00
     **/
    ChatRecord queryEntity(String id);
}
