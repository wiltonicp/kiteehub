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
package com.kiteehub.sys.modular.resource.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kiteehub.common.enums.CommonSortOrderEnum;
import com.kiteehub.common.exception.CommonException;
import com.kiteehub.common.listener.CommonDataChangeEventCenter;
import com.kiteehub.common.page.CommonPageRequest;
import com.kiteehub.sys.core.enums.SysBuildInEnum;
import com.kiteehub.sys.core.enums.SysDataTypeEnum;
import com.kiteehub.sys.modular.resource.entity.SysSpa;
import com.kiteehub.sys.modular.resource.enums.SysResourceCategoryEnum;
import com.kiteehub.sys.modular.resource.enums.SysResourceMenuTypeEnum;
import com.kiteehub.sys.modular.resource.mapper.SysSpaMapper;
import com.kiteehub.sys.modular.resource.param.spa.SysSpaAddParam;
import com.kiteehub.sys.modular.resource.param.spa.SysSpaEditParam;
import com.kiteehub.sys.modular.resource.param.spa.SysSpaIdParam;
import com.kiteehub.sys.modular.resource.param.spa.SysSpaPageParam;
import com.kiteehub.sys.modular.resource.service.SysSpaService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 单页面Service接口实现类
 *
 * @author xuyuxiang
 * @date 2022/6/27 14:25
 **/
@Service
public class SysSpaServiceImpl extends ServiceImpl<SysSpaMapper, SysSpa> implements SysSpaService {

    @Override
    public Page<SysSpa> page(SysSpaPageParam sysSpaPageParam) {
        QueryWrapper<SysSpa> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysSpa::getCategory, SysResourceCategoryEnum.SPA.getValue());
        if(ObjectUtil.isNotEmpty(sysSpaPageParam.getSearchKey())) {
            queryWrapper.lambda().like(SysSpa::getTitle, sysSpaPageParam.getSearchKey());
        }
        if(ObjectUtil.isAllNotEmpty(sysSpaPageParam.getSortField(), sysSpaPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(sysSpaPageParam.getSortOrder());
            queryWrapper.orderBy(true, sysSpaPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(sysSpaPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(SysSpa::getSortCode);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SysSpaAddParam sysSpaAddParam) {
        checkParam(sysSpaAddParam);
        SysSpa sysSpa = BeanUtil.toBean(sysSpaAddParam, SysSpa.class);
        boolean repeatTitle = this.count(new LambdaQueryWrapper<SysSpa>().eq(SysSpa::getCategory,
                SysResourceCategoryEnum.SPA.getValue()).eq(SysSpa::getTitle, sysSpa.getTitle())) > 0;
        if(repeatTitle) {
            throw new CommonException("存在重复的单页面，名称为：{}", sysSpa.getTitle());
        }
        sysSpa.setCode(RandomUtil.randomString(10));
        sysSpa.setCategory(SysResourceCategoryEnum.SPA.getValue());
        this.save(sysSpa);

        // 发布增加事件
        CommonDataChangeEventCenter.doAddWithData(SysDataTypeEnum.RESOURCE.getValue(), JSONUtil.createArray().put(sysSpa));
    }

    @SuppressWarnings("all")
    private void checkParam(SysSpaAddParam sysSpaAddParam) {
        SysResourceMenuTypeEnum.validate(sysSpaAddParam.getMenuType());
        if(SysResourceMenuTypeEnum.MENU.getValue().equals(sysSpaAddParam.getMenuType())) {
            if(ObjectUtil.isEmpty(sysSpaAddParam.getName())) {
                throw new CommonException("name不能为空");
            }
            if(ObjectUtil.isEmpty(sysSpaAddParam.getComponent())) {
                throw new CommonException("component不能为空");
            }
        } else if(SysResourceMenuTypeEnum.IFRAME.getValue().equals(sysSpaAddParam.getMenuType()) ||
                SysResourceMenuTypeEnum.LINK.getValue().equals(sysSpaAddParam.getMenuType())) {
            sysSpaAddParam.setName(RandomUtil.randomNumbers(10));
            sysSpaAddParam.setComponent(null);
        } else {
            sysSpaAddParam.setName(null);
            sysSpaAddParam.setComponent(null);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SysSpaEditParam sysSpaEditParam) {
        SysSpa sysSpa = this.queryEntity(sysSpaEditParam.getId());
        checkParam(sysSpaEditParam);
        BeanUtil.copyProperties(sysSpaEditParam, sysSpa);
        boolean repeatTitle = this.count(new LambdaQueryWrapper<SysSpa>().eq(SysSpa::getCategory,
                SysResourceCategoryEnum.SPA.getValue()).eq(SysSpa::getTitle, sysSpa.getTitle())
                .ne(SysSpa::getId, sysSpa.getId())) > 0;
        if(repeatTitle) {
            throw new CommonException("存在重复的单页面，名称为：{}", sysSpa.getTitle());
        }
        this.updateById(sysSpa);

        // 发布更新事件
        CommonDataChangeEventCenter.doUpdateWithData(SysDataTypeEnum.RESOURCE.getValue(), JSONUtil.createArray().put(sysSpa));
    }

    @SuppressWarnings("all")
    private void checkParam(SysSpaEditParam sysSpaEditParam) {
        SysResourceMenuTypeEnum.validate(sysSpaEditParam.getMenuType());
        if(SysResourceMenuTypeEnum.MENU.getValue().equals(sysSpaEditParam.getMenuType())) {
            if(ObjectUtil.isEmpty(sysSpaEditParam.getName())) {
                throw new CommonException("name不能为空");
            }
            if(ObjectUtil.isEmpty(sysSpaEditParam.getComponent())) {
                throw new CommonException("component不能为空");
            }
        } else if(SysResourceMenuTypeEnum.IFRAME.getValue().equals(sysSpaEditParam.getMenuType()) ||
                SysResourceMenuTypeEnum.LINK.getValue().equals(sysSpaEditParam.getMenuType())) {
            sysSpaEditParam.setName(RandomUtil.randomNumbers(10));
            sysSpaEditParam.setComponent(null);
        } else {
            sysSpaEditParam.setName(null);
            sysSpaEditParam.setComponent(null);
        }
    }

    @Override
    public void delete(List<SysSpaIdParam> sysSpaIdParamList) {
        List<String> sysSpaIdList = CollStreamUtil.toList(sysSpaIdParamList, SysSpaIdParam::getId);
        if(ObjectUtil.isNotEmpty(sysSpaIdList)) {
            boolean containsSystemSpa = this.listByIds(sysSpaIdList).stream().map(SysSpa::getCode)
                    .collect(Collectors.toSet()).contains(SysBuildInEnum.BUILD_IN_SPA_CODE.getValue());
            if(containsSystemSpa) {
                throw new CommonException("不可删除系统内置单页面");
            }
            // 删除
            this.removeByIds(sysSpaIdList);

            // 发布删除事件
            CommonDataChangeEventCenter.doDeleteWithDataId(SysDataTypeEnum.RESOURCE.getValue(), sysSpaIdList);
        }
    }

    @Override
    public SysSpa detail(SysSpaIdParam sysSpaIdParam) {
        return this.queryEntity(sysSpaIdParam.getId());
    }

    @Override
    public SysSpa queryEntity(String id) {
        SysSpa sysSpa = this.getById(id);
        if(ObjectUtil.isEmpty(sysSpa)) {
            throw new CommonException("单页面不存在，id值为：{}", id);
        }
        return sysSpa;
    }
}
