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
package com.kiteehub.knowledge.modular.robot.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kiteehub.knowledge.modular.robot.entity.KnowledgeRobotIndex;
import com.kiteehub.knowledge.modular.robot.mapper.KnowledgeRobotIndexMapper;
import com.kiteehub.knowledge.modular.robot.service.KnowledgeRobotIndexService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 智能客服关联知识库Service接口实现类
 *
 * @author Ranger
 * @date  2024/01/03 11:44
 **/
@Service
public class KnowledgeRobotIndexServiceImpl extends ServiceImpl<KnowledgeRobotIndexMapper, KnowledgeRobotIndex> implements KnowledgeRobotIndexService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addOrEdit(String rid, List<String> kids) {
        QueryWrapper<KnowledgeRobotIndex> robotIndexQueryWrapper = new QueryWrapper<>();
        robotIndexQueryWrapper.lambda().eq(KnowledgeRobotIndex::getRid,rid);
        this.remove(robotIndexQueryWrapper);

        List<KnowledgeRobotIndex> collect = kids.stream().map(c ->
                        KnowledgeRobotIndex.builder()
                                .kid(c)
                                .rid(rid)
                                .build())
                .collect(Collectors.toList());
        this.saveBatch(collect);
    }
}
