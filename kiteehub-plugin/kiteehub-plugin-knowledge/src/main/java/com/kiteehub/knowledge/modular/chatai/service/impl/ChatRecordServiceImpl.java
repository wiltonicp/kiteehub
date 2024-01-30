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
package com.kiteehub.knowledge.modular.chatai.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kiteehub.knowledge.modular.robot.entity.KnowledgeRobot;
import com.kiteehub.knowledge.modular.robot.service.KnowledgeRobotService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kiteehub.common.enums.CommonSortOrderEnum;
import com.kiteehub.common.exception.CommonException;
import com.kiteehub.common.page.CommonPageRequest;
import com.kiteehub.knowledge.modular.chatai.entity.ChatRecord;
import com.kiteehub.knowledge.modular.chatai.mapper.ChatRecordMapper;
import com.kiteehub.knowledge.modular.chatai.param.ChatRecordAddParam;
import com.kiteehub.knowledge.modular.chatai.param.ChatRecordEditParam;
import com.kiteehub.knowledge.modular.chatai.param.ChatRecordIdParam;
import com.kiteehub.knowledge.modular.chatai.param.ChatRecordPageParam;
import com.kiteehub.knowledge.modular.chatai.service.ChatRecordService;

import java.util.List;

/**
 * 客服记录Service接口实现类
 *
 * @author Ranger
 * @date  2024/01/23 17:00
 **/
@Service
@AllArgsConstructor
public class ChatRecordServiceImpl extends ServiceImpl<ChatRecordMapper, ChatRecord> implements ChatRecordService {

    private final KnowledgeRobotService knowledgeRobotService;

    @Override
    public Page<ChatRecord> page(ChatRecordPageParam chatRecordPageParam) {
        QueryWrapper<ChatRecord> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(chatRecordPageParam.getMsgType())) {
            queryWrapper.lambda().eq(ChatRecord::getMsgType, chatRecordPageParam.getMsgType());
        }
        if(ObjectUtil.isNotEmpty(chatRecordPageParam.getRobotId())) {
            queryWrapper.lambda().eq(ChatRecord::getRobotId, chatRecordPageParam.getRobotId());
        }
        if(ObjectUtil.isNotEmpty(chatRecordPageParam.getMessage())) {
            queryWrapper.lambda().like(ChatRecord::getMessage, chatRecordPageParam.getMessage());
        }
        if(ObjectUtil.isNotEmpty(chatRecordPageParam.getStartCreateTime()) && ObjectUtil.isNotEmpty(chatRecordPageParam.getEndCreateTime())) {
            queryWrapper.lambda().between(ChatRecord::getCreateTime, chatRecordPageParam.getStartCreateTime(), chatRecordPageParam.getEndCreateTime());
        }
        if(ObjectUtil.isAllNotEmpty(chatRecordPageParam.getSortField(), chatRecordPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(chatRecordPageParam.getSortOrder());
            queryWrapper.orderBy(true, chatRecordPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(chatRecordPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(ChatRecord::getId);
        }
        Page<ChatRecord> page = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        page.getRecords().forEach(x ->{
            KnowledgeRobot knowledgeRobot = knowledgeRobotService.queryEntity(x.getRobotId());
            x.setRobotName(knowledgeRobot.getName());
        });
        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ChatRecordAddParam chatRecordAddParam) {
        ChatRecord chatRecord = BeanUtil.toBean(chatRecordAddParam, ChatRecord.class);
        this.save(chatRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ChatRecordEditParam chatRecordEditParam) {
        ChatRecord chatRecord = this.queryEntity(chatRecordEditParam.getId());
        BeanUtil.copyProperties(chatRecordEditParam, chatRecord);
        this.updateById(chatRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ChatRecordIdParam> chatRecordIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(chatRecordIdParamList, ChatRecordIdParam::getId));
    }

    @Override
    public ChatRecord detail(ChatRecordIdParam chatRecordIdParam) {
        return this.queryEntity(chatRecordIdParam.getId());
    }

    @Override
    public ChatRecord queryEntity(String id) {
        ChatRecord chatRecord = this.getById(id);
        if(ObjectUtil.isEmpty(chatRecord)) {
            throw new CommonException("客服记录不存在，id值为：{}", id);
        }
        return chatRecord;
    }
}
