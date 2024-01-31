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
package com.kiteehub.knowledge.modular.subsidy.service.impl;

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
import com.kiteehub.knowledge.modular.subsidy.entity.KbSubsidyBatchData;
import com.kiteehub.knowledge.modular.subsidy.mapper.KbSubsidyBatchDataMapper;
import com.kiteehub.knowledge.modular.subsidy.param.KbSubsidyBatchDataAddParam;
import com.kiteehub.knowledge.modular.subsidy.param.KbSubsidyBatchDataEditParam;
import com.kiteehub.knowledge.modular.subsidy.param.KbSubsidyBatchDataIdParam;
import com.kiteehub.knowledge.modular.subsidy.param.KbSubsidyBatchDataPageParam;
import com.kiteehub.knowledge.modular.subsidy.service.KbSubsidyBatchDataService;

import java.util.List;

/**
 * 待遇补贴批次详情Service接口实现类
 *
 * @author Ranger
 * @date  2024/01/31 15:06
 **/
@Service
public class KbSubsidyBatchDataServiceImpl extends ServiceImpl<KbSubsidyBatchDataMapper, KbSubsidyBatchData> implements KbSubsidyBatchDataService {

    @Override
    public Page<KbSubsidyBatchData> page(KbSubsidyBatchDataPageParam kbSubsidyBatchDataPageParam) {
        QueryWrapper<KbSubsidyBatchData> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(kbSubsidyBatchDataPageParam.getSortField(), kbSubsidyBatchDataPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(kbSubsidyBatchDataPageParam.getSortOrder());
            queryWrapper.orderBy(true, kbSubsidyBatchDataPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(kbSubsidyBatchDataPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(KbSubsidyBatchData::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(KbSubsidyBatchDataAddParam kbSubsidyBatchDataAddParam) {
        KbSubsidyBatchData kbSubsidyBatchData = BeanUtil.toBean(kbSubsidyBatchDataAddParam, KbSubsidyBatchData.class);
        this.save(kbSubsidyBatchData);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(KbSubsidyBatchDataEditParam kbSubsidyBatchDataEditParam) {
        KbSubsidyBatchData kbSubsidyBatchData = this.queryEntity(kbSubsidyBatchDataEditParam.getId());
        BeanUtil.copyProperties(kbSubsidyBatchDataEditParam, kbSubsidyBatchData);
        this.updateById(kbSubsidyBatchData);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<KbSubsidyBatchDataIdParam> kbSubsidyBatchDataIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(kbSubsidyBatchDataIdParamList, KbSubsidyBatchDataIdParam::getId));
    }

    @Override
    public KbSubsidyBatchData detail(KbSubsidyBatchDataIdParam kbSubsidyBatchDataIdParam) {
        return this.queryEntity(kbSubsidyBatchDataIdParam.getId());
    }

    @Override
    public KbSubsidyBatchData queryEntity(String id) {
        KbSubsidyBatchData kbSubsidyBatchData = this.getById(id);
        if(ObjectUtil.isEmpty(kbSubsidyBatchData)) {
            throw new CommonException("待遇补贴批次详情不存在，id值为：{}", id);
        }
        return kbSubsidyBatchData;
    }
}
