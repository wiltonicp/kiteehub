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
package com.kiteehub.dev.modular.dict.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fhs.trans.service.impl.DictionaryTransService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import com.kiteehub.common.enums.CommonSortOrderEnum;
import com.kiteehub.common.exception.CommonException;
import com.kiteehub.common.page.CommonPageRequest;
import com.kiteehub.dev.modular.dict.entity.DevDict;
import com.kiteehub.dev.modular.dict.enums.DevDictCategoryEnum;
import com.kiteehub.dev.modular.dict.mapper.DevDictMapper;
import com.kiteehub.dev.modular.dict.param.*;
import com.kiteehub.dev.modular.dict.service.DevDictService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 字典Service接口实现类
 *
 * @author xuyuxiang
 * @date 2022/4/22 10:41
 **/
@Service
public class DevDictServiceImpl extends ServiceImpl<DevDictMapper, DevDict> implements DevDictService, InitializingBean {

    private static final String ROOT_PARENT_ID = "0";

    @Resource
    private DictionaryTransService dictionaryTransService;

    @Override
    public Page<DevDict> page(DevDictPageParam devDictPageParam) {
        QueryWrapper<DevDict> queryWrapper = new QueryWrapper<>();
        // 查询部分字段
        queryWrapper.lambda().select(DevDict::getId, DevDict::getParentId, DevDict::getCategory, DevDict::getDictLabel,
                DevDict::getDictValue, DevDict::getSortCode);
        if (ObjectUtil.isNotEmpty(devDictPageParam.getParentId())) {
            queryWrapper.lambda().eq(DevDict::getParentId, devDictPageParam.getParentId())
                    .or().eq(DevDict::getId, devDictPageParam.getParentId());
        }
        if (ObjectUtil.isNotEmpty(devDictPageParam.getCategory())) {
            queryWrapper.lambda().eq(DevDict::getCategory, devDictPageParam.getCategory());
        }
        if (ObjectUtil.isNotEmpty(devDictPageParam.getSearchKey())) {
            queryWrapper.lambda().like(DevDict::getDictLabel, devDictPageParam.getSearchKey());
        }
        if (ObjectUtil.isAllNotEmpty(devDictPageParam.getSortField(), devDictPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(devDictPageParam.getSortOrder());
            queryWrapper.orderBy(true, devDictPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(devDictPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(DevDict::getSortCode);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Override
    public List<DevDict> list(DevDictListParam devDictListParam) {
        LambdaQueryWrapper<DevDict> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotEmpty(devDictListParam.getParentId())) {
            lambdaQueryWrapper.eq(DevDict::getParentId, devDictListParam.getParentId());
        }
        if (ObjectUtil.isNotEmpty(devDictListParam.getCategory())) {
            lambdaQueryWrapper.eq(DevDict::getCategory, devDictListParam.getCategory());
        }
        return this.list(lambdaQueryWrapper);
    }

    @Override
    public List<Tree<String>> tree(DevDictTreeParam devDictTreeParam) {
        LambdaQueryWrapper<DevDict> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByAsc(DevDict::getSortCode);
        if (ObjectUtil.isNotEmpty(devDictTreeParam.getCategory())) {
            lambdaQueryWrapper.eq(DevDict::getCategory, devDictTreeParam.getCategory());
        }
        List<DevDict> devDictList = this.list(lambdaQueryWrapper);
        List<TreeNode<String>> treeNodeList = devDictList.stream().map(devDict ->
                        new TreeNode<>(devDict.getId(), devDict.getParentId(),
                                devDict.getDictLabel(), devDict.getSortCode()).setExtra(JSONUtil.parseObj(devDict)))
                .collect(Collectors.toList());
        return TreeUtil.build(treeNodeList, ObjectUtil.isNotEmpty(devDictTreeParam.getParentId()) ? devDictTreeParam.getParentId() : "0");
    }

    @Override
    public void add(DevDictAddParam devDictAddParam) {
        checkParam(devDictAddParam);
        DevDict devDict = BeanUtil.toBean(devDictAddParam, DevDict.class);
        this.save(devDict);
        refreshTransCache();
    }

    private void checkParam(DevDictAddParam devDictAddParam) {
        DevDictCategoryEnum.validate(devDictAddParam.getCategory());
        boolean hasSameDictLabel = this.count(new LambdaQueryWrapper<DevDict>()
                .eq(DevDict::getParentId, devDictAddParam.getParentId())
                .eq(DevDict::getCategory, devDictAddParam.getCategory())
                .eq(DevDict::getDictLabel, devDictAddParam.getDictLabel())) > 0;
        if (hasSameDictLabel) {
            throw new CommonException("存在重复的字典文字，名称为：{}", devDictAddParam.getDictLabel());
        }

        boolean hasSameDictValue = this.count(new LambdaQueryWrapper<DevDict>()
                .eq(DevDict::getParentId, devDictAddParam.getParentId())
                .eq(DevDict::getDictValue, devDictAddParam.getDictValue())) > 0;
        if (hasSameDictValue) {
            throw new CommonException("存在重复的字典值，名称为：{}", devDictAddParam.getDictValue());
        }
    }

    @Override
    public void edit(DevDictEditParam devDictEditParam) {
        DevDict devDict = this.queryEntity(devDictEditParam.getId());
        checkParam(devDictEditParam);
        BeanUtil.copyProperties(devDictEditParam, devDict);
        this.updateById(devDict);
        refreshTransCache();
    }

    private void checkParam(DevDictEditParam devDictEditParam) {
        DevDictCategoryEnum.validate(devDictEditParam.getCategory());
        boolean hasSameDictLabel = this.count(new LambdaQueryWrapper<DevDict>()
                .eq(DevDict::getParentId, devDictEditParam.getParentId())
                .eq(DevDict::getCategory, devDictEditParam.getCategory())
                .eq(DevDict::getDictLabel, devDictEditParam.getDictLabel())
                .ne(DevDict::getId, devDictEditParam.getId())) > 0;
        if (hasSameDictLabel) {
            throw new CommonException("存在重复的字典文字，名称为：{}", devDictEditParam.getDictLabel());
        }

        boolean hasSameDictValue = this.count(new LambdaQueryWrapper<DevDict>()
                .eq(DevDict::getParentId, devDictEditParam.getParentId())
                .eq(DevDict::getDictValue, devDictEditParam.getDictValue())
                .ne(DevDict::getId, devDictEditParam.getId())) > 0;
        if (hasSameDictValue) {
            throw new CommonException("存在重复的字典值，名称为：{}", devDictEditParam.getDictValue());
        }
    }

    @Override
    public void delete(List<DevDictIdParam> devDictIdParamList) {
        List<String> devDictIdList = CollStreamUtil.toList(devDictIdParamList, DevDictIdParam::getId);
        if (ObjectUtil.isNotEmpty(devDictIdList)) {
            boolean systemDict = this.listByIds(devDictIdList).stream().map(DevDict::getCategory)
                    .collect(Collectors.toSet()).contains(DevDictCategoryEnum.FRM.getValue());
            if (systemDict) {
                throw new CommonException("不可删除系统内置字典");
            }
            // 删除
            this.removeByIds(devDictIdList);
        }
    }

    @Override
    public DevDict detail(DevDictIdParam devDictIdParam) {
        return this.queryEntity(devDictIdParam.getId());
    }

    @Override
    public DevDict queryEntity(String id) {
        DevDict devDict = this.getById(id);
        if (ObjectUtil.isEmpty(devDict)) {
            throw new CommonException("字典不存在，id值为：{}", id);
        }
        return devDict;
    }

    @Override
    public void afterPropertiesSet() {
        refreshTransCache();
    }

    private void refreshTransCache() {
        // 异步不阻塞主线程，不会 增加启动用时
        CompletableFuture.supplyAsync(() -> {
            // 使用redis能解决共享问题，但是性能没有直接取缓存的好。
            dictionaryTransService.makeUseRedis();
            List<DevDict> devDictList = super.list(new LambdaQueryWrapper<>());
            // 非root级别的字典根据ParentId分组
            Map<String, List<DevDict>> devDictGroupByPIDMap = devDictList.stream().filter(dict -> !ROOT_PARENT_ID
                    .equals(dict.getParentId())).collect(Collectors.groupingBy(DevDict::getParentId));
            Map<String, String> parentDictIdValMap = devDictList.stream().filter(dict -> ROOT_PARENT_ID
                    .equals(dict.getParentId())).collect(Collectors.toMap(DevDict::getId, DevDict::getDictValue));
            for (String parentId : parentDictIdValMap.keySet()) {
                if (devDictGroupByPIDMap.containsKey(parentId)) {
                    dictionaryTransService.refreshCache(parentDictIdValMap.get(parentId), devDictGroupByPIDMap.get(parentId).stream()
                            .collect(Collectors.toMap(DevDict::getDictValue, DevDict::getDictLabel)));
                }
            }
            return null;
        });
    }
}
