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
package com.kiteehub.sys.modular.user.provider;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import com.kiteehub.common.exception.CommonException;
import com.kiteehub.sys.api.SysUserApi;
import com.kiteehub.sys.modular.user.entity.SysUser;
import com.kiteehub.sys.modular.user.param.SysUserGrantRoleParam;
import com.kiteehub.sys.modular.user.param.SysUserIdParam;
import com.kiteehub.sys.modular.user.param.SysUserSelectorUserParam;
import com.kiteehub.sys.modular.user.service.SysUserService;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 用户API接口提供者
 *
 * @author xuyuxiang
 * @date 2022/6/20 18:24
 **/
@Service
public class SysUserApiProvider implements SysUserApi {

    @Resource
    private SysUserService sysUserService;

    @Override
    public JSONObject getUserByIdWithoutException(String userId) {
        SysUser sysUser = sysUserService.getById(userId);
        if(ObjectUtil.isNotEmpty(sysUser)) {
            return JSONUtil.parseObj(sysUser);
        }
        return null;
    }

    @Override
    public List<JSONObject> getUserListByIdListWithoutException(List<String> userIdList) {
        return sysUserService.listByIds(userIdList).stream().map(JSONUtil::parseObj).collect(Collectors.toList());
    }

    @Override
    public JSONObject getUserByIdWithException(String userId) {
        return JSONUtil.parseObj(sysUserService.queryEntity(userId));
    }

    @Override
    public List<JSONObject> getUserListByIdWithException(List<String> userIdList) {
        HashSet<String> userIdSet = CollectionUtil.newHashSet(userIdList);
        List<SysUser> sysUserList = sysUserService.listByIds(userIdSet);
        if(sysUserList.size() != userIdSet.size()) {
            throw new CommonException("某用户不存在，id值集合为：{}", userIdSet);
        }
        return sysUserList.stream().map(JSONUtil::parseObj).collect(Collectors.toList());
    }

    @Override
    public List<String> ownRole(String userId) {
        SysUserIdParam sysUserIdParam = new SysUserIdParam();
        sysUserIdParam.setId(userId);
        return sysUserService.ownRole(sysUserIdParam);
    }

    @Override
    public void grantRole(String userId, List<String> roleIdList) {
        SysUserGrantRoleParam sysUserGrantRoleParam = new SysUserGrantRoleParam();
        sysUserGrantRoleParam.setId(userId);
        sysUserGrantRoleParam.setRoleIdList(roleIdList);
        sysUserService.grantRole(sysUserGrantRoleParam);
    }

    @Override
    public List<String> getUserIdListByOrgIdList(List<String> orgIdList) {
        if(ObjectUtil.isNotEmpty(orgIdList)) {
            return sysUserService.list(new LambdaQueryWrapper<SysUser>().in(SysUser::getOrgId, orgIdList)).stream()
                    .map(SysUser::getId).collect(Collectors.toList());
        }
        return CollectionUtil.newArrayList();
    }

    @Override
    public List<String> getUserIdListByPositionIdList(List<String> positionIdList) {
        if(ObjectUtil.isNotEmpty(positionIdList)) {
            return sysUserService.list(new LambdaQueryWrapper<SysUser>().in(SysUser::getPositionId, positionIdList)).stream()
                    .map(SysUser::getId).collect(Collectors.toList());
        }
        return CollectionUtil.newArrayList();
    }

    @Override
    public String getSupervisorIdByUserIdAndOrgIdAndPositionId(String userId, String orgId, String positionId) {
        SysUser sysUser = sysUserService.queryEntity(userId);
        String userOrgId = sysUser.getOrgId();
        String userPositionId = sysUser.getPositionId();
        String positionJson = sysUser.getPositionJson();
        AtomicReference<String> result = new AtomicReference<>();
        if(ObjectUtil.isAllNotEmpty(userOrgId, userPositionId)) {
            if(userOrgId.equals(orgId) && userPositionId.equals(positionId)) {
                String directorId = sysUser.getDirectorId();
                if(ObjectUtil.isNotEmpty(directorId)) {
                    return directorId;
                }
            }
        }
        if(ObjectUtil.isNotEmpty(positionJson)) {
            JSONUtil.parseArray(positionJson).forEach(object -> {
                JSONObject jsonObject = JSONUtil.parseObj(object);
                String tempPositionId = jsonObject.getStr("positionId");
                String directorId = jsonObject.getStr("directorId");
                if(ObjectUtil.isNotEmpty(tempPositionId) && tempPositionId.equals(positionId)) {
                    if(ObjectUtil.isNotEmpty(directorId)) {
                        result.set(directorId);
                    }
                }
            });
        }
        return result.get();
    }

    @SuppressWarnings("ALL")
    @Override
    public Page<JSONObject> userSelector(String orgId, String searchKey) {
        SysUserSelectorUserParam sysUserSelectorUserParam = new SysUserSelectorUserParam();
        sysUserSelectorUserParam.setOrgId(orgId);
        sysUserSelectorUserParam.setSearchKey(searchKey);
        return BeanUtil.toBean(sysUserService.userSelector(sysUserSelectorUserParam), Page.class);
    }
}
