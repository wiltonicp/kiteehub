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
package com.kiteehub.sys.api;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * 用户Api
 *
 * @author xuyuxiang
 * @date 2022/6/6 11:33
 **/
public interface SysUserApi {

    /**
     * 根据用户id获取用户对象，没有则返回null
     *
     * @author xuyuxiang
     * @date 2022/6/20 18:19
     **/
    JSONObject getUserByIdWithoutException(String userId);

    /**
     * 根据用户id获取用户对象列表，没有的则为空，都没有则返回空集合
     *
     * @author xuyuxiang
     * @date 2022/6/20 18:19
     **/
    List<JSONObject> getUserListByIdListWithoutException(List<String> userIdList);

    /**
     * 根据用户id获取用户对象，没有则抛出异常
     *
     * @author xuyuxiang
     * @date 2022/6/20 18:19
     **/
    JSONObject getUserByIdWithException(String userId);

    /**
     * 根据用户id获取用户对象列表，只要有一个没有则抛出异常
     *
     * @author xuyuxiang
     * @date 2022/6/20 18:19
     **/
    List<JSONObject> getUserListByIdWithException(List<String> userIdList);

    /**
     * 获取用户拥有角色
     *
     * @author xuyuxiang
     * @date 2022/5/13 21:00
     */
    List<String> ownRole(String userId);

    /**
     * 给用户授权角色
     *
     * @author xuyuxiang
     * @date 2022/8/1 18:28
     */
    void grantRole(String userId, List<String> roleIdList);

    /**
     * 根据组织id集合获取组织下用户id集合
     *
     * @author xuyuxiang
     * @date 2022/6/6 11:40
     **/
    List<String> getUserIdListByOrgIdList(List<String> orgIdList);

    /**
     * 根据职位id集合获取职位下用户id集合
     *
     * @author xuyuxiang
     * @date 2022/6/6 11:44
     **/
    List<String> getUserIdListByPositionIdList(List<String> positionIdList);

    /**
     * 根据用户id和组织id和职位id获取上级主管id
     *
     * @author xuyuxiang
     * @date 2022/6/6 14:50
     **/
    String getSupervisorIdByUserIdAndOrgIdAndPositionId(String userId, String orgId, String positionId);

    /**
     * 获取用户选择器
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    Page<JSONObject> userSelector(String orgId, String searchKey);
}
