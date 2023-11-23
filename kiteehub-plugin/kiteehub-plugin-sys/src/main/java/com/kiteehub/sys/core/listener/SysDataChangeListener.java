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
package com.kiteehub.sys.core.listener;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONArray;
import org.springframework.stereotype.Component;
import com.kiteehub.auth.core.pojo.SaBaseLoginUser;
import com.kiteehub.auth.core.util.StpLoginUserUtil;
import com.kiteehub.common.cache.CommonCacheOperator;
import com.kiteehub.common.listener.CommonDataChangeListener;
import com.kiteehub.sys.core.enums.SysDataTypeEnum;
import com.kiteehub.sys.modular.org.service.impl.SysOrgServiceImpl;
import com.kiteehub.sys.modular.user.service.impl.SysUserServiceImpl;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统模块数据变化侦听器
 *
 * @author xuyuxiang
 * @date 2023/3/3 10:44
 **/
@Component
public class SysDataChangeListener implements CommonDataChangeListener {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Override
    public void doAddWithDataIdList(String dataType, List<String> dataIdList) {
        // 如果检测到机构增加，则将机构的数据缓存清除
        if(dataType.equals(SysDataTypeEnum.ORG.getValue())) {
            commonCacheOperator.remove(SysOrgServiceImpl.ORG_CACHE_ALL_KEY);
            // 并将该机构加入到当前登录用户的数据范围缓存
            SaBaseLoginUser saBaseLoginUser = StpLoginUserUtil.getLoginUser();
            saBaseLoginUser.getDataScopeList().forEach(dataScope -> dataScope.getDataScope().addAll(dataIdList));
            saBaseLoginUser.setDataScopeList(saBaseLoginUser.getDataScopeList());
            // 重新缓存当前登录用户信息
            StpUtil.getTokenSession().set("loginUser", saBaseLoginUser);
        }
        // 如果检测到用户增加，则将用户数据缓存清除
        if(dataType.equals(SysDataTypeEnum.USER.getValue())) {
            commonCacheOperator.remove(SysUserServiceImpl.USER_CACHE_ALL_KEY);
        }
    }

    @Override
    public void doAddWithDataList(String dataType, JSONArray jsonArray) {
        // 此处可做额外处理
    }

    @Override
    public void doUpdateWithDataIdList(String dataType, List<String> dataIdList) {
        // 如果检测到机构更新，则将机构的数据缓存清除
        if(dataType.equals(SysDataTypeEnum.ORG.getValue())) {
            commonCacheOperator.remove(SysOrgServiceImpl.ORG_CACHE_ALL_KEY);
        }
        // 如果检测到用户更新，则将用户数据缓存清除
        if(dataType.equals(SysDataTypeEnum.USER.getValue())) {
            commonCacheOperator.remove(SysUserServiceImpl.USER_CACHE_ALL_KEY);
        }
    }

    @Override
    public void doUpdateWithDataList(String dataType, JSONArray jsonArray) {
        // 此处可做额外处理
    }

    @Override
    public void doDeleteWithDataIdList(String dataType, List<String> dataIdList) {
        // 如果检测到机构增加，则将机构的数据缓存清除
        if(dataType.equals(SysDataTypeEnum.ORG.getValue())) {
            commonCacheOperator.remove(SysOrgServiceImpl.ORG_CACHE_ALL_KEY);
        }
        // 如果检测到用户删除，则将用户数据缓存清除，并将这些用户踢下线
        if(dataType.equals(SysDataTypeEnum.USER.getValue())) {
            commonCacheOperator.remove(SysUserServiceImpl.USER_CACHE_ALL_KEY);
            dataIdList.forEach(StpUtil::kickout);
        }
    }
}
