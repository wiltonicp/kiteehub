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
package com.kiteehub.knowledge.modular.notice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kiteehub.knowledge.modular.article.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kiteehub.common.enums.CommonSortOrderEnum;
import com.kiteehub.common.exception.CommonException;
import com.kiteehub.common.page.CommonPageRequest;
import com.kiteehub.knowledge.modular.notice.entity.KnowledgeNotice;
import com.kiteehub.knowledge.modular.notice.mapper.KnowledgeNoticeMapper;
import com.kiteehub.knowledge.modular.notice.param.KnowledgeNoticeAddParam;
import com.kiteehub.knowledge.modular.notice.param.KnowledgeNoticeEditParam;
import com.kiteehub.knowledge.modular.notice.param.KnowledgeNoticeIdParam;
import com.kiteehub.knowledge.modular.notice.param.KnowledgeNoticePageParam;
import com.kiteehub.knowledge.modular.notice.service.KnowledgeNoticeService;

import java.util.List;

/**
 * 通知公告Service接口实现类
 *
 * @author Ranger
 * @date  2024/01/29 14:39
 **/
@Service
public class KnowledgeNoticeServiceImpl extends ServiceImpl<KnowledgeNoticeMapper, KnowledgeNotice> implements KnowledgeNoticeService {

    @Override
    public Page<KnowledgeNotice> page(KnowledgeNoticePageParam knowledgeNoticePageParam) {
        QueryWrapper<KnowledgeNotice> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(knowledgeNoticePageParam.getSubject())) {
            queryWrapper.lambda().like(KnowledgeNotice::getSubject, knowledgeNoticePageParam.getSubject());
        }
        if(ObjectUtil.isNotEmpty(knowledgeNoticePageParam.getPersonnelType())) {
            queryWrapper.lambda().like(KnowledgeNotice::getPersonnelType, knowledgeNoticePageParam.getPersonnelType());
        }
        if(ObjectUtil.isAllNotEmpty(knowledgeNoticePageParam.getSortField(), knowledgeNoticePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(knowledgeNoticePageParam.getSortOrder());
            queryWrapper.orderBy(true, knowledgeNoticePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(knowledgeNoticePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(KnowledgeNotice::getId);
        }
        Page<KnowledgeNotice> page = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        page.getRecords().forEach(record ->{
            record.setSummary(StringUtil.truncateString(record.getContent(),30));
        });
        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(KnowledgeNoticeAddParam knowledgeNoticeAddParam) {
        KnowledgeNotice knowledgeNotice = BeanUtil.toBean(knowledgeNoticeAddParam, KnowledgeNotice.class);
        this.save(knowledgeNotice);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(KnowledgeNoticeEditParam knowledgeNoticeEditParam) {
        KnowledgeNotice knowledgeNotice = this.queryEntity(knowledgeNoticeEditParam.getId());
        BeanUtil.copyProperties(knowledgeNoticeEditParam, knowledgeNotice);
        this.updateById(knowledgeNotice);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<KnowledgeNoticeIdParam> knowledgeNoticeIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(knowledgeNoticeIdParamList, KnowledgeNoticeIdParam::getId));
    }

    @Override
    public KnowledgeNotice detail(KnowledgeNoticeIdParam knowledgeNoticeIdParam) {
        return this.queryEntity(knowledgeNoticeIdParam.getId());
    }

    @Override
    public KnowledgeNotice queryEntity(String id) {
        KnowledgeNotice knowledgeNotice = this.getById(id);
        if(ObjectUtil.isEmpty(knowledgeNotice)) {
            throw new CommonException("通知公告不存在，id值为：{}", id);
        }
        return knowledgeNotice;
    }
}
