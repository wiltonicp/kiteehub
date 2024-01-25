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

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kiteehub.knowledge.modular.robot.entity.KnowledgeRobotIndex;
import com.kiteehub.knowledge.modular.robot.service.KnowledgeRobotIndexService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kiteehub.common.enums.CommonSortOrderEnum;
import com.kiteehub.common.exception.CommonException;
import com.kiteehub.common.page.CommonPageRequest;
import com.kiteehub.knowledge.modular.robot.entity.KnowledgeRobot;
import com.kiteehub.knowledge.modular.robot.mapper.KnowledgeRobotMapper;
import com.kiteehub.knowledge.modular.robot.param.KnowledgeRobotAddParam;
import com.kiteehub.knowledge.modular.robot.param.KnowledgeRobotEditParam;
import com.kiteehub.knowledge.modular.robot.param.KnowledgeRobotIdParam;
import com.kiteehub.knowledge.modular.robot.param.KnowledgeRobotPageParam;
import com.kiteehub.knowledge.modular.robot.service.KnowledgeRobotService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 智能客服Service接口实现类
 *
 * @author Ranger
 * @date  2024/01/03 11:44
 **/
@Service
@AllArgsConstructor
public class KnowledgeRobotServiceImpl extends ServiceImpl<KnowledgeRobotMapper, KnowledgeRobot> implements KnowledgeRobotService {

    private final KnowledgeRobotIndexService knowledgeRobotIndexService;

    @Override
    public Page<KnowledgeRobot> page(KnowledgeRobotPageParam knowledgeRobotPageParam) {
        QueryWrapper<KnowledgeRobot> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(knowledgeRobotPageParam.getName())) {
            queryWrapper.lambda().like(KnowledgeRobot::getName, knowledgeRobotPageParam.getName());
        }
        if(ObjectUtil.isAllNotEmpty(knowledgeRobotPageParam.getSortField(), knowledgeRobotPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(knowledgeRobotPageParam.getSortOrder());
            queryWrapper.orderBy(true, knowledgeRobotPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(knowledgeRobotPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(KnowledgeRobot::getId);
        }
        Page<KnowledgeRobot> page = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        page.getRecords().forEach(record ->{
            QueryWrapper<KnowledgeRobotIndex> robotIndexQueryWrapper = new QueryWrapper<>();
            robotIndexQueryWrapper.lambda().eq(KnowledgeRobotIndex::getRid,record.getId());
            List<KnowledgeRobotIndex> list = knowledgeRobotIndexService.list(robotIndexQueryWrapper);

            List<String> collect = list.stream().map(KnowledgeRobotIndex::getKid).collect(Collectors.toList());
            record.setKids(collect);
        });
        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(KnowledgeRobotAddParam knowledgeRobotAddParam) {
        KnowledgeRobot knowledgeRobot = BeanUtil.toBean(knowledgeRobotAddParam, KnowledgeRobot.class);
        this.save(knowledgeRobot);

        knowledgeRobotIndexService.addOrEdit(knowledgeRobot.getId(),knowledgeRobotAddParam.getKids());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(KnowledgeRobotEditParam knowledgeRobotEditParam) {
        KnowledgeRobot knowledgeRobot = this.queryEntity(knowledgeRobotEditParam.getId());
        BeanUtil.copyProperties(knowledgeRobotEditParam, knowledgeRobot);
        this.updateById(knowledgeRobot);

        knowledgeRobotIndexService.addOrEdit(knowledgeRobot.getId(),knowledgeRobotEditParam.getKids());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<KnowledgeRobotIdParam> knowledgeRobotIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(knowledgeRobotIdParamList, KnowledgeRobotIdParam::getId));

        knowledgeRobotIdParamList.forEach(index ->{
            QueryWrapper<KnowledgeRobotIndex> robotIndexQueryWrapper = new QueryWrapper<>();
            robotIndexQueryWrapper.lambda().eq(KnowledgeRobotIndex::getRid,index.getId());
            knowledgeRobotIndexService.remove(robotIndexQueryWrapper);
        });

    }

    @Override
    public KnowledgeRobot detail(KnowledgeRobotIdParam knowledgeRobotIdParam) {
        return this.queryEntity(knowledgeRobotIdParam.getId());
    }

    @Override
    public KnowledgeRobot queryEntity(String id) {
        KnowledgeRobot knowledgeRobot = this.getById(id);
        if(ObjectUtil.isEmpty(knowledgeRobot)) {
            throw new CommonException("智能客服不存在，id值为：{}", id);
        }
        List<KnowledgeRobotIndex> listIndex = knowledgeRobotIndexService.lambdaQuery()
                .eq(KnowledgeRobotIndex::getRid, id)
                .list();
        knowledgeRobot.setKids(listIndex.stream().map(KnowledgeRobotIndex::getKid).collect(Collectors.toList()));
        return knowledgeRobot;
    }
}
