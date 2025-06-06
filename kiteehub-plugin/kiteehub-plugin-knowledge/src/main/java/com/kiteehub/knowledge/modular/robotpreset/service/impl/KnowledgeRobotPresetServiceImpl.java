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
package com.kiteehub.knowledge.modular.robotpreset.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kiteehub.common.enums.CommonSortOrderEnum;
import com.kiteehub.common.exception.CommonException;
import com.kiteehub.common.page.CommonPageRequest;
import com.kiteehub.knowledge.modular.robotpreset.entity.KnowledgeRobotPreset;
import com.kiteehub.knowledge.modular.robotpreset.mapper.KnowledgeRobotPresetMapper;
import com.kiteehub.knowledge.modular.robotpreset.param.KnowledgeRobotPresetAddParam;
import com.kiteehub.knowledge.modular.robotpreset.param.KnowledgeRobotPresetEditParam;
import com.kiteehub.knowledge.modular.robotpreset.param.KnowledgeRobotPresetIdParam;
import com.kiteehub.knowledge.modular.robotpreset.param.KnowledgeRobotPresetPageParam;
import com.kiteehub.knowledge.modular.robotpreset.service.KnowledgeRobotPresetService;

import java.util.List;

/**
 * 机器人预设问答Service接口实现类
 *
 * @author Ranger
 * @date  2024/02/02 10:39
 **/
@Service
public class KnowledgeRobotPresetServiceImpl extends ServiceImpl<KnowledgeRobotPresetMapper, KnowledgeRobotPreset> implements KnowledgeRobotPresetService {

    @Override
    public Page<KnowledgeRobotPreset> page(KnowledgeRobotPresetPageParam knowledgeRobotPresetPageParam) {
        QueryWrapper<KnowledgeRobotPreset> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(knowledgeRobotPresetPageParam.getRobotId())) {
            queryWrapper.lambda().eq(KnowledgeRobotPreset::getRobotId, knowledgeRobotPresetPageParam.getRobotId());
        }
        if(ObjectUtil.isNotEmpty(knowledgeRobotPresetPageParam.getQuestion())) {
            queryWrapper.lambda().like(KnowledgeRobotPreset::getQuestion, knowledgeRobotPresetPageParam.getQuestion());
        }
        if(ObjectUtil.isAllNotEmpty(knowledgeRobotPresetPageParam.getSortField(), knowledgeRobotPresetPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(knowledgeRobotPresetPageParam.getSortOrder());
            queryWrapper.orderBy(true, knowledgeRobotPresetPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(knowledgeRobotPresetPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(KnowledgeRobotPreset::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(KnowledgeRobotPresetAddParam knowledgeRobotPresetAddParam) {
        KnowledgeRobotPreset knowledgeRobotPreset = BeanUtil.toBean(knowledgeRobotPresetAddParam, KnowledgeRobotPreset.class);
        this.save(knowledgeRobotPreset);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(KnowledgeRobotPresetEditParam knowledgeRobotPresetEditParam) {
        KnowledgeRobotPreset knowledgeRobotPreset = this.queryEntity(knowledgeRobotPresetEditParam.getId());
        BeanUtil.copyProperties(knowledgeRobotPresetEditParam, knowledgeRobotPreset);
        this.updateById(knowledgeRobotPreset);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<KnowledgeRobotPresetIdParam> knowledgeRobotPresetIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(knowledgeRobotPresetIdParamList, KnowledgeRobotPresetIdParam::getId));
    }

    @Override
    public KnowledgeRobotPreset detail(KnowledgeRobotPresetIdParam knowledgeRobotPresetIdParam) {
        return this.queryEntity(knowledgeRobotPresetIdParam.getId());
    }

    @Override
    public KnowledgeRobotPreset queryEntity(String id) {
        KnowledgeRobotPreset knowledgeRobotPreset = this.getById(id);
        if(ObjectUtil.isEmpty(knowledgeRobotPreset)) {
            throw new CommonException("机器人预设问答不存在，id值为：{}", id);
        }
        return knowledgeRobotPreset;
    }
}
