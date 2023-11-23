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
package com.kiteehub.sys.modular.org.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kiteehub.sys.modular.org.entity.SysOrg;
import com.kiteehub.sys.modular.org.param.*;
import com.kiteehub.sys.modular.user.entity.SysUser;

import java.util.List;

/**
 * 组织Service接口
 *
 * @author xuyuxiang
 * @date 2022/4/21 18:35
 **/
public interface SysOrgService extends IService<SysOrg> {

    /**
     * 获取组织分页
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    Page<SysOrg> page(SysOrgPageParam sysOrgPageParam);

    /**
     * 获取组织树
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    List<Tree<String>> tree();

    /**
     * 添加组织
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:48
     */
    void add(SysOrgAddParam sysOrgAddParam);

    /**
     * 编辑组织
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:13
     */
    void edit(SysOrgEditParam sysOrgEditParam);

    /**
     * 删除组织
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:18
     */
    void delete(List<SysOrgIdParam> sysOrgIdParamList);

    /**
     * 获取组织详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:18
     */
    SysOrg detail(SysOrgIdParam sysOrgIdParam);

    /**
     * 获取组织详情
     *
     * @author xuyuxiang
     * @date 2022/7/25 19:42
     **/
    SysOrg queryEntity(String id);

    /**
     * 获取所有组织
     *
     * @author xuyuxiang
     * @date 2022/7/25 19:42
     **/
    List<SysOrg> getCachedAllOrgList();

    /**
     * 根据组织全名称获取组织id，有则返回，无则创建
     *
     * @author xuyuxiang
     * @date 2023/3/7 15:44
     **/
    String getOrgIdByOrgFullNameWithCreate(String orgFullName);

    /**
     * 根据id获取父子数据列表
     *
     * @author xuyuxiang
     * @date 2022/8/15 14:55
     **/
    List<SysOrg> getParentAndChildListById(List<SysOrg> originDataList, String id, boolean includeSelf);

    /**
     * 根据id获取所有的子数据列表
     *
     * @author xuyuxiang
     * @date 2022/8/15 14:55
     **/
    List<SysOrg> getChildListById(List<SysOrg> originDataList, String id, boolean includeSelf);

    /**
     * 根据id获取所有的父数据列表
     *
     * @author xuyuxiang
     * @date 2022/8/15 14:55
     **/
    List<SysOrg> getParentListById(List<SysOrg> originDataList, String id, boolean includeSelf);

    /**
     * 根据id获取数据
     *
     * @author xuyuxiang
     * @date 2022/8/15 14:55
     **/
    SysOrg getById(List<SysOrg> originDataList, String id);

    /**
     * 根据id获取父数据
     *
     * @author xuyuxiang
     * @date 2022/8/15 14:55
     **/
    SysOrg getParentById(List<SysOrg> originDataList, String id);

    /**
     * 根据id获取子数据
     *
     * @author xuyuxiang
     * @date 2022/8/15 14:55
     **/
    SysOrg getChildById(List<SysOrg> originDataList, String id);

    /**
     * 获取组织树选择器
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    List<Tree<String>> orgTreeSelector();

    /**
     * 获取组织列表选择器
     *
     * @author xuyuxiang
     * @date 2022/7/22 13:34
     **/
    Page<SysOrg> orgListSelector(SysOrgSelectorOrgListParam sysOrgSelectorOrgListParam);

    /**
     * 获取用户选择器
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    Page<SysUser> userSelector(SysOrgSelectorUserParam sysOrgSelectorUserParam);
}
