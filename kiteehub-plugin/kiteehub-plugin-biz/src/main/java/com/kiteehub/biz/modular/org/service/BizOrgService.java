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
package com.kiteehub.biz.modular.org.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kiteehub.biz.modular.org.entity.BizOrg;
import com.kiteehub.biz.modular.org.param.*;
import com.kiteehub.biz.modular.user.entity.BizUser;

import java.util.List;

/**
 * 机构Service接口
 *
 * @author xuyuxiang
 * @date 2022/4/21 18:35
 **/
public interface BizOrgService extends IService<BizOrg> {

    /**
     * 获取机构分页
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    Page<BizOrg> page(BizOrgPageParam bizOrgPageParam);

    /**
     * 获取机构树
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    List<Tree<String>> tree();

    /**
     * 添加机构
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:48
     */
    void add(BizOrgAddParam bizOrgAddParam);

    /**
     * 编辑机构
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:13
     */
    void edit(BizOrgEditParam bizOrgEditParam);

    /**
     * 删除机构
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:18
     */
    void delete(List<BizOrgIdParam> bizOrgIdParamList);

    /**
     * 获取机构详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:18
     */
    BizOrg detail(BizOrgIdParam bizOrgIdParam);

    /**
     * 获取机构详情
     *
     * @author xuyuxiang
     * @date 2022/7/25 19:42
     **/
    BizOrg queryEntity(String id);

    /**
     * 获取所有机构
     *
     * @author xuyuxiang
     * @date 2022/7/25 19:42
     **/
    List<BizOrg> getAllOrgList();

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
    List<BizOrg> getParentAndChildListById(List<BizOrg> originDataList, String id, boolean includeSelf);

    /**
     * 根据id获取所有的子数据列表
     *
     * @author xuyuxiang
     * @date 2022/8/15 14:55
     **/
    List<BizOrg> getChildListById(List<BizOrg> originDataList, String id, boolean includeSelf);

    /**
     * 根据id获取所有的父数据列表
     *
     * @author xuyuxiang
     * @date 2022/8/15 14:55
     **/
    List<BizOrg> getParentListById(List<BizOrg> originDataList, String id, boolean includeSelf);

    /**
     * 根据id获取数据
     *
     * @author xuyuxiang
     * @date 2022/8/15 14:55
     **/
    BizOrg getById(List<BizOrg> originDataList, String id);

    /**
     * 根据id获取父数据
     *
     * @author xuyuxiang
     * @date 2022/8/15 14:55
     **/
    BizOrg getParentById(List<BizOrg> originDataList, String id);

    /**
     * 根据id获取子数据
     *
     * @author xuyuxiang
     * @date 2022/8/15 14:55
     **/
    BizOrg getChildById(List<BizOrg> originDataList, String id);

    /**
     * 获取机构树选择器
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    List<Tree<String>> orgTreeSelector();

    /**
     * 获取机构列表选择器
     *
     * @author xuyuxiang
     * @date 2022/7/22 13:34
     **/
    List<BizOrg> orgListSelector(BizOrgSelectorOrgListParam bizOrgSelectorOrgListParam);

    /**
     * 获取人员选择器
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    Page<BizUser> userSelector(BizOrgSelectorUserParam bizOrgSelectorUserParam);
}
