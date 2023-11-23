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
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kiteehub.common.enums.CommonSortOrderEnum;
import com.kiteehub.common.exception.CommonException;
import com.kiteehub.common.listener.CommonDataChangeEventCenter;
import com.kiteehub.common.page.CommonPageRequest;
import com.kiteehub.sys.core.enums.SysDataTypeEnum;
import com.kiteehub.sys.modular.relation.entity.SysRelation;
import com.kiteehub.sys.modular.relation.enums.SysRelationCategoryEnum;
import com.kiteehub.sys.modular.relation.service.SysRelationService;
import com.kiteehub.sys.modular.resource.entity.SysButton;
import com.kiteehub.sys.modular.resource.entity.SysMenu;
import com.kiteehub.sys.modular.resource.enums.SysResourceCategoryEnum;
import com.kiteehub.sys.modular.resource.mapper.SysButtonMapper;
import com.kiteehub.sys.modular.resource.param.button.SysButtonAddParam;
import com.kiteehub.sys.modular.resource.param.button.SysButtonEditParam;
import com.kiteehub.sys.modular.resource.param.button.SysButtonIdParam;
import com.kiteehub.sys.modular.resource.param.button.SysButtonPageParam;
import com.kiteehub.sys.modular.resource.service.SysButtonService;
import com.kiteehub.sys.modular.resource.service.SysMenuService;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 按钮Service接口实现类
 *
 * @author xuyuxiang
 * @date 2022/6/27 14:25
 **/
@Service
public class SysButtonServiceImpl extends ServiceImpl<SysButtonMapper, SysButton> implements SysButtonService {

    @Resource
    private SysRelationService sysRelationService;

    @Resource
    private SysMenuService sysMenuService;

    @Override
    public Page<SysButton> page(SysButtonPageParam sysButtonPageParam) {
        QueryWrapper<SysButton> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysButton::getCategory, SysResourceCategoryEnum.BUTTON.getValue());
        if(ObjectUtil.isNotEmpty(sysButtonPageParam.getParentId())) {
            queryWrapper.lambda().eq(SysButton::getParentId, sysButtonPageParam.getParentId());
        }
        if(ObjectUtil.isNotEmpty(sysButtonPageParam.getSearchKey())) {
            queryWrapper.lambda().like(SysButton::getTitle, sysButtonPageParam.getSearchKey());
        }
        if(ObjectUtil.isAllNotEmpty(sysButtonPageParam.getSortField(), sysButtonPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(sysButtonPageParam.getSortOrder());
            queryWrapper.orderBy(true, sysButtonPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(sysButtonPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(SysButton::getSortCode);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SysButtonAddParam sysButtonAddParam) {
        SysButton sysButton = BeanUtil.toBean(sysButtonAddParam, SysButton.class);
        boolean repeatCode = this.count(new LambdaQueryWrapper<SysButton>()
                .eq(SysButton::getCategory, SysResourceCategoryEnum.BUTTON.getValue())
                .eq(SysButton::getCode, sysButton.getCode())) > 0;
        if(repeatCode) {
            throw new CommonException("存在重复的按钮，编码为：{}", sysButton.getCode());
        }
        sysButton.setCategory(SysResourceCategoryEnum.BUTTON.getValue());
        this.save(sysButton);

        // 发布增加事件
        CommonDataChangeEventCenter.doAddWithData(SysDataTypeEnum.RESOURCE.getValue(), JSONUtil.createArray().put(sysButton));
    }

    @Override
    public void addForGenButton(String menuId, String className, String functionName) {
        SysMenu sysMenu = sysMenuService.queryEntity(menuId);
        String classNameFirstLower = StrUtil.lowerFirst(className);
        CollectionUtil.newArrayList(JSONUtil.createObj().set("title", "新增" + functionName).set("code", classNameFirstLower + "Add").set("sortCode", 1),
                JSONUtil.createObj().set("title", "编辑" + functionName).set("code", classNameFirstLower + "Edit").set("sortCode", 2),
                JSONUtil.createObj().set("title", "删除" + functionName).set("code", classNameFirstLower + "Delete").set("sortCode", 3),
                JSONUtil.createObj().set("title", "批量删除").set("code", classNameFirstLower + "BatchDelete").set("sortCode", 4)).forEach(jsonObject -> {
                    SysButtonAddParam sysButtonAddParam = new SysButtonAddParam();
                    BeanUtil.copyProperties(jsonObject, sysButtonAddParam);
                    sysButtonAddParam.setParentId(sysMenu.getId());
                    this.add(sysButtonAddParam);
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SysButtonEditParam sysButtonEditParam) {
        SysButton sysButton = this.queryEntity(sysButtonEditParam.getId());
        BeanUtil.copyProperties(sysButtonEditParam, sysButton);
        boolean repeatCode = this.count(new LambdaQueryWrapper<SysButton>()
                .eq(SysButton::getCategory, SysResourceCategoryEnum.BUTTON.getValue())
                .eq(SysButton::getCode, sysButton.getCode())
                .ne(SysButton::getId, sysButtonEditParam.getId())) > 0;
        if(repeatCode) {
            throw new CommonException("存在重复的按钮，编码为：{}", sysButton.getCode());
        }
        this.updateById(sysButton);

        // 发布更新事件
        CommonDataChangeEventCenter.doUpdateWithData(SysDataTypeEnum.RESOURCE.getValue(), JSONUtil.createArray().put(sysButton));
    }

    @Override
    public void delete(List<SysButtonIdParam> sysButtonIdParamList) {
        List<String> buttonIdList = CollStreamUtil.toList(sysButtonIdParamList, SysButtonIdParam::getId);
        if(ObjectUtil.isNotEmpty(buttonIdList)) {
            // 获取按钮的父菜单id集合
            List<String> parentMenuIdList = sysMenuService.list(new LambdaQueryWrapper<SysMenu>().in(SysMenu::getId, buttonIdList)
                    .eq(SysMenu::getCategory, SysResourceCategoryEnum.BUTTON.getValue())).stream().map(SysMenu::getParentId)
                    .collect(Collectors.toList());
            if(ObjectUtil.isNotEmpty(parentMenuIdList)) {
                sysRelationService.list(new LambdaQueryWrapper<SysRelation>().in(SysRelation::getTargetId, parentMenuIdList)
                        .eq(SysRelation::getCategory, SysRelationCategoryEnum.SYS_ROLE_HAS_RESOURCE.getValue())
                        .isNotNull(SysRelation::getExtJson)).forEach(sysRelation -> {
                    JSONObject extJsonObject = JSONUtil.parseObj(sysRelation.getExtJson());
                    List<String> buttonInfoList = extJsonObject.getBeanList("buttonInfo", String.class);
                    if (ObjectUtil.isNotEmpty(buttonInfoList)) {
                        Set<String> intersectionDistinct = CollectionUtil.intersectionDistinct(buttonIdList, buttonInfoList);
                        if(ObjectUtil.isNotEmpty(intersectionDistinct)) {
                            Collection<String> disjunction = CollectionUtil.disjunction(buttonInfoList, intersectionDistinct);
                            extJsonObject.set("buttonInfo", disjunction);
                        }
                    }
                    // 清除对应的角色与资源信息中的【授权的按钮信息】
                    sysRelationService.update(new LambdaUpdateWrapper<SysRelation>().eq(SysRelation::getId, sysRelation.getId())
                            .set(SysRelation::getExtJson, JSONUtil.toJsonStr(extJsonObject)));
                });
                // 执行删除
                this.removeByIds(buttonIdList);

                // 发布删除事件
                CommonDataChangeEventCenter.doDeleteWithDataId(SysDataTypeEnum.RESOURCE.getValue(), buttonIdList);
            }
        }
    }

    @Override
    public SysButton detail(SysButtonIdParam sysButtonIdParam) {
        return this.queryEntity(sysButtonIdParam.getId());
    }

    @Override
    public SysButton queryEntity(String id) {
        SysButton sysButton = this.getById(id);
        if(ObjectUtil.isEmpty(sysButton)) {
            throw new CommonException("按钮不存在，id值为：{}", id);
        }
        return sysButton;
    }
}
