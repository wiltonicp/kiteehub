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
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kiteehub.knowledge.modular.attach.entity.KnowledgeAttach;
import com.kiteehub.knowledge.modular.subsidy.param.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kiteehub.common.enums.CommonSortOrderEnum;
import com.kiteehub.common.exception.CommonException;
import com.kiteehub.common.page.CommonPageRequest;
import com.kiteehub.knowledge.modular.subsidy.entity.KbSubsidyBatchData;
import com.kiteehub.knowledge.modular.subsidy.mapper.KbSubsidyBatchDataMapper;
import com.kiteehub.knowledge.modular.subsidy.service.KbSubsidyBatchDataService;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 待遇补贴批次详情Service接口实现类
 *
 * @author Ranger
 * @date 2024/01/31 15:06
 **/
@Service
public class KbSubsidyBatchDataServiceImpl extends ServiceImpl<KbSubsidyBatchDataMapper, KbSubsidyBatchData> implements KbSubsidyBatchDataService {

    @Override
    public Page<KbSubsidyBatchData> page(KbSubsidyBatchDataPageParam kbSubsidyBatchDataPageParam) {
        QueryWrapper<KbSubsidyBatchData> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isAllNotEmpty(kbSubsidyBatchDataPageParam.getSortField(), kbSubsidyBatchDataPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(kbSubsidyBatchDataPageParam.getSortOrder());
            queryWrapper.orderBy(true, kbSubsidyBatchDataPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(kbSubsidyBatchDataPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(KbSubsidyBatchData::getId);
        }
        queryWrapper.lambda().eq(ObjectUtil.isNotEmpty(kbSubsidyBatchDataPageParam.getCardId()), KbSubsidyBatchData::getCardId, kbSubsidyBatchDataPageParam.getCardId());
        queryWrapper.lambda().eq(ObjectUtil.isNotEmpty(kbSubsidyBatchDataPageParam.getBatchId()), KbSubsidyBatchData::getBatchId, kbSubsidyBatchDataPageParam.getBatchId());
        queryWrapper.lambda().eq(ObjectUtil.isNotEmpty(kbSubsidyBatchDataPageParam.getBatchNum()), KbSubsidyBatchData::getBatchNum, kbSubsidyBatchDataPageParam.getBatchNum());
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
        if (ObjectUtil.isEmpty(kbSubsidyBatchData)) {
            throw new CommonException("待遇补贴批次详情不存在，id值为：{}", id);
        }
        return kbSubsidyBatchData;
    }

    @Override
    public JSONArray groupBatch(KbSubsidyBatchIdParam kbSubsidyBatchIdParam) {
        QueryWrapper<KbSubsidyBatchData> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(KbSubsidyBatchData::getBatchId, kbSubsidyBatchIdParam.getId());
        queryWrapper.lambda().eq(ObjectUtil.isNotEmpty(kbSubsidyBatchIdParam.getCardId()), KbSubsidyBatchData::getCardId, kbSubsidyBatchIdParam.getCardId());
        List<KbSubsidyBatchData> list = this.list(queryWrapper);
        Map<Integer, List<KbSubsidyBatchData>> collect = list.stream().collect(Collectors.groupingBy(KbSubsidyBatchData::getBatchNum));

        JSONArray array = JSONUtil.createArray();
        for (Map.Entry<Integer, List<KbSubsidyBatchData>> stringListEntry : collect.entrySet()) {
            Date createTime = stringListEntry.getValue().stream().findFirst().get().getCreateTime();
            String format = DateUtil.format(createTime, DatePattern.NORM_DATETIME_MINUTE_PATTERN);
            JSONObject obj = JSONUtil.createObj();
            obj.set("name", format);
            obj.set("batchNum", stringListEntry.getKey());
            array.put(obj);
        }
        return array;
    }
}
