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
package com.kiteehub.biz.modular.position.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kiteehub.auth.core.util.StpLoginUserUtil;
import com.kiteehub.biz.core.enums.BizDataTypeEnum;
import com.kiteehub.biz.modular.org.entity.BizOrg;
import com.kiteehub.biz.modular.org.service.BizOrgService;
import com.kiteehub.biz.modular.position.entity.BizPosition;
import com.kiteehub.biz.modular.position.enums.BizPositionCategoryEnum;
import com.kiteehub.biz.modular.position.mapper.BizPositionMapper;
import com.kiteehub.biz.modular.position.param.*;
import com.kiteehub.biz.modular.position.service.BizPositionService;
import com.kiteehub.biz.modular.user.entity.BizUser;
import com.kiteehub.biz.modular.user.service.BizUserService;
import com.kiteehub.common.enums.CommonSortOrderEnum;
import com.kiteehub.common.exception.CommonException;
import com.kiteehub.common.listener.CommonDataChangeEventCenter;
import com.kiteehub.common.page.CommonPageRequest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 岗位Service接口实现类
 *
 * @author xuyuxiang
 * @date 2022/2/23 18:43
 **/
@Service
public class BizPositionServiceImpl extends ServiceImpl<BizPositionMapper, BizPosition> implements BizPositionService {

    @Resource
    private BizOrgService bizOrgService;

    @Resource
    private BizUserService bizUserService;

    @Override
    public Page<BizPosition> page(BizPositionPageParam bizPositionPageParam) {
        QueryWrapper<BizPosition> queryWrapper = new QueryWrapper<>();
        // 查询部分字段
        queryWrapper.lambda().select(BizPosition::getId, BizPosition::getOrgId, BizPosition::getName,
                BizPosition::getCategory, BizPosition::getSortCode);
        if(ObjectUtil.isNotEmpty(bizPositionPageParam.getOrgId())) {
            queryWrapper.lambda().eq(BizPosition::getOrgId, bizPositionPageParam.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(bizPositionPageParam.getCategory())) {
            queryWrapper.lambda().eq(BizPosition::getCategory, bizPositionPageParam.getCategory());
        }
        if(ObjectUtil.isAllNotEmpty(bizPositionPageParam.getSortField(), bizPositionPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizPositionPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizPositionPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizPositionPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizPosition::getSortCode);
        }
        // 校验数据范围
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
            queryWrapper.lambda().in(BizPosition::getOrgId, loginUserDataScope);
        } else {
            return new Page<>();
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizPositionAddParam bizPositionAddParam) {
        BizPositionCategoryEnum.validate(bizPositionAddParam.getCategory());
        // 校验数据范围
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
            if(!loginUserDataScope.contains(bizPositionAddParam.getOrgId())) {
                throw new CommonException("您没有权限在该机构下增加岗位，机构id：{}", bizPositionAddParam.getOrgId());
            }
        } else {
            throw new CommonException("您没有权限在该机构下增加岗位，机构id：{}", bizPositionAddParam.getOrgId());
        }
        BizPosition bizPosition = BeanUtil.toBean(bizPositionAddParam, BizPosition.class);
        boolean repeatName = this.count(new LambdaQueryWrapper<BizPosition>().eq(BizPosition::getOrgId, bizPosition.getOrgId())
                .eq(BizPosition::getName, bizPosition.getName())) > 0;
        if(repeatName) {
            throw new CommonException("同机构下存在重复的岗位，名称为：{}", bizPosition.getName());
        }
        bizPosition.setCode(RandomUtil.randomString(10));
        this.save(bizPosition);

        // 发布增加事件
        CommonDataChangeEventCenter.doAddWithData(BizDataTypeEnum.POSITION.getValue(), JSONUtil.createArray().put(bizPosition));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizPositionEditParam bizPositionEditParam) {
        BizPositionCategoryEnum.validate(bizPositionEditParam.getCategory());
        BizPosition bizPosition = this.queryEntity(bizPositionEditParam.getId());
        // 校验数据范围
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
            if(!loginUserDataScope.contains(bizPositionEditParam.getOrgId())) {
                throw new CommonException("您没有权限编辑该机构下的岗位，机构id：{}", bizPositionEditParam.getOrgId());
            }
        } else {
            if(!bizPositionEditParam.getId().equals(StpUtil.getLoginIdAsString())) {
                throw new CommonException("您没有权限编辑该机构下的岗位，机构id：{}", bizPositionEditParam.getOrgId());
            }
        }
        BeanUtil.copyProperties(bizPositionEditParam, bizPosition);
        boolean repeatName = this.count(new LambdaQueryWrapper<BizPosition>().eq(BizPosition::getOrgId, bizPosition.getOrgId())
                .eq(BizPosition::getName, bizPosition.getName()).ne(BizPosition::getId, bizPosition.getId())) > 0;
        if(repeatName) {
            throw new CommonException("同机构下存在重复的岗位，名称为：{}", bizPosition.getName());
        }
        this.updateById(bizPosition);

        // 发布更新事件
        CommonDataChangeEventCenter.doUpdateWithData(BizDataTypeEnum.POSITION.getValue(), JSONUtil.createArray().put(bizPosition));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizPositionIdParam> bizPositionIdParamList) {
        List<String> positionIdList = CollStreamUtil.toList(bizPositionIdParamList, BizPositionIdParam::getId);
        if(ObjectUtil.isNotEmpty(positionIdList)) {
            // 获取这些岗位的的机构id集合
            Set<String> positionOrgIdList = this.listByIds(positionIdList).stream().map(BizPosition::getOrgId).collect(Collectors.toSet());
            // 校验数据范围
            List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
            if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
                if(!loginUserDataScope.containsAll(positionOrgIdList)) {
                    throw new CommonException("您没有权限删除这些机构下的岗位，机构id：{}", positionOrgIdList);
                }
            } else {
                throw new CommonException("您没有权限删除这些机构下的岗位，机构id：{}", positionOrgIdList);
            }
            // 岗位下有人不能删除（直属岗位）
            boolean hasOrgUser = bizUserService.count(new LambdaQueryWrapper<BizUser>().in(BizUser::getPositionId, positionIdList)) > 0;
            if(hasOrgUser) {
                throw new CommonException("请先删除岗位下的用户");
            }
            // 岗位下有人不能删除（兼任岗位）
            List<String> positionJsonList = bizUserService.list(new LambdaQueryWrapper<BizUser>()
                    .isNotNull(BizUser::getPositionJson)).stream().map(BizUser::getPositionJson).collect(Collectors.toList());
            if(ObjectUtil.isNotEmpty(positionJsonList)) {
                List<String> extPositionIdList = CollectionUtil.newArrayList();
                positionJsonList.forEach(positionJson -> JSONUtil.toList(JSONUtil.parseArray(positionJson), JSONObject.class)
                        .forEach(jsonObject -> extPositionIdList.add(jsonObject.getStr("positionId"))));
                boolean hasPositionUser = CollectionUtil.intersectionDistinct(positionIdList, CollectionUtil.removeNull(extPositionIdList)).size() > 0;
                if(hasPositionUser) {
                    throw new CommonException("请先删除岗位下的用户");
                }
            }
            // 执行删除
            this.removeByIds(positionIdList);

            // 发布删除事件
            CommonDataChangeEventCenter.doDeleteWithDataId(BizDataTypeEnum.POSITION.getValue(), positionIdList);
        }
    }

    @Override
    public BizPosition detail(BizPositionIdParam bizPositionIdParam) {
        return this.queryEntity(bizPositionIdParam.getId());
    }

    @Override
    public BizPosition queryEntity(String id) {
        BizPosition bizPosition = this.getById(id);
        if(ObjectUtil.isEmpty(bizPosition)) {
            throw new CommonException("岗位不存在，id值为：{}", id);
        }
        return bizPosition;
    }

    @Override
    public String getPositionIdByPositionNameWithCreate(String orgId, String positionName) {
        return null;
    }

    /* ====岗位部分所需要用到的选择器==== */

    @Override
    public List<Tree<String>> orgTreeSelector() {
        LambdaQueryWrapper<BizOrg> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 校验数据范围
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
            lambdaQueryWrapper.in(BizOrg::getId, loginUserDataScope);
        } else {
            return CollectionUtil.newArrayList();
        }
        lambdaQueryWrapper.orderByAsc(BizOrg::getSortCode);
        List<BizOrg> bizOrgList = bizOrgService.list(lambdaQueryWrapper);
        List<TreeNode<String>> treeNodeList = bizOrgList.stream().map(bizOrg ->
                new TreeNode<>(bizOrg.getId(), bizOrg.getParentId(), bizOrg.getName(), bizOrg.getSortCode()))
                .collect(Collectors.toList());
        return TreeUtil.build(treeNodeList, "0");
    }

    @Override
    public Page<BizPosition> positionSelector(BizPositionSelectorPositionParam bizPositionSelectorPositionParam) {
        LambdaQueryWrapper<BizPosition> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 校验数据范围
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
            lambdaQueryWrapper.in(BizPosition::getOrgId, loginUserDataScope);
        } else {
            return new Page<>();
        }
        // 查询部分字段
        lambdaQueryWrapper.select(BizPosition::getId, BizPosition::getOrgId, BizPosition::getName,
                BizPosition::getCategory, BizPosition::getSortCode);
        if(ObjectUtil.isNotEmpty(bizPositionSelectorPositionParam.getOrgId())) {
            lambdaQueryWrapper.eq(BizPosition::getOrgId, bizPositionSelectorPositionParam.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(bizPositionSelectorPositionParam.getSearchKey())) {
            lambdaQueryWrapper.like(BizPosition::getName, bizPositionSelectorPositionParam.getSearchKey());
        }
        lambdaQueryWrapper.orderByAsc(BizPosition::getSortCode);
        return this.page(CommonPageRequest.defaultPage(), lambdaQueryWrapper);
    }
}
