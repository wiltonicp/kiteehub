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
package com.kiteehub.knowledge.modular.guide.service.impl;

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
import com.kiteehub.knowledge.modular.guide.entity.KbWorkGuide;
import com.kiteehub.knowledge.modular.guide.mapper.KbWorkGuideMapper;
import com.kiteehub.knowledge.modular.guide.param.KbWorkGuideAddParam;
import com.kiteehub.knowledge.modular.guide.param.KbWorkGuideEditParam;
import com.kiteehub.knowledge.modular.guide.param.KbWorkGuideIdParam;
import com.kiteehub.knowledge.modular.guide.param.KbWorkGuidePageParam;
import com.kiteehub.knowledge.modular.guide.service.KbWorkGuideService;

import java.util.List;

/**
 * 业务指南Service接口实现类
 *
 * @author Ranger
 * @date  2024/01/29 14:15
 **/
@Service
public class KbWorkGuideServiceImpl extends ServiceImpl<KbWorkGuideMapper, KbWorkGuide> implements KbWorkGuideService {

    @Override
    public Page<KbWorkGuide> page(KbWorkGuidePageParam kbWorkGuidePageParam) {
        QueryWrapper<KbWorkGuide> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(kbWorkGuidePageParam.getCategory())) {
            queryWrapper.lambda().eq(KbWorkGuide::getCategory, kbWorkGuidePageParam.getCategory());
        }
        if(ObjectUtil.isAllNotEmpty(kbWorkGuidePageParam.getSortField(), kbWorkGuidePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(kbWorkGuidePageParam.getSortOrder());
            queryWrapper.orderBy(true, kbWorkGuidePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(kbWorkGuidePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(KbWorkGuide::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(KbWorkGuideAddParam kbWorkGuideAddParam) {
        KbWorkGuide kbWorkGuide = BeanUtil.toBean(kbWorkGuideAddParam, KbWorkGuide.class);
        this.save(kbWorkGuide);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(KbWorkGuideEditParam kbWorkGuideEditParam) {
        KbWorkGuide kbWorkGuide = this.queryEntity(kbWorkGuideEditParam.getId());
        BeanUtil.copyProperties(kbWorkGuideEditParam, kbWorkGuide);
        this.updateById(kbWorkGuide);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<KbWorkGuideIdParam> kbWorkGuideIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(kbWorkGuideIdParamList, KbWorkGuideIdParam::getId));
    }

    @Override
    public KbWorkGuide detail(KbWorkGuideIdParam kbWorkGuideIdParam) {
        return this.queryEntity(kbWorkGuideIdParam.getId());
    }

    @Override
    public KbWorkGuide queryEntity(String id) {
        KbWorkGuide kbWorkGuide = this.getById(id);
        if(ObjectUtil.isEmpty(kbWorkGuide)) {
            throw new CommonException("业务指南不存在，id值为：{}", id);
        }
        return kbWorkGuide;
    }
}
