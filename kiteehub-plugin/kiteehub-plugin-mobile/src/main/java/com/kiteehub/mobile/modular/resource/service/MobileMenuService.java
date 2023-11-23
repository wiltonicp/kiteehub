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
package com.kiteehub.mobile.modular.resource.service;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kiteehub.mobile.modular.resource.entity.MobileMenu;
import com.kiteehub.mobile.modular.resource.entity.MobileModule;
import com.kiteehub.mobile.modular.resource.param.menu.*;

import java.util.List;

/**
 * 移动端菜单Service接口
 *
 * @author yubaoshan
 * @date  2023/01/28 22:42
 **/
public interface MobileMenuService extends IService<MobileMenu> {

    /**
     * 获取移动端菜单tree
     *
     * @author yubaoshan
     * @date  2023/01/28 22:42
     */
    List<Tree<String>> tree(MobileMenuTreeParam mobileMenuTreeParam);

    /**
     * 添加移动端菜单
     *
     * @author yubaoshan
     * @date  2023/01/28 22:42
     */
    void add(MobileMenuAddParam mobileMenuAddParam);

    /**
     * 编辑移动端菜单
     *
     * @author yubaoshan
     * @date  2023/01/28 22:42
     */
    void edit(MobileMenuEditParam mobileMenuEditParam);

    /**
     * 更改移动端菜单所属模块
     *
     * @author xuyuxiang
     * @date 2022/9/26 13:09
     **/
    void changeModule(MobileMenuChangeModuleParam mobileMenuChangeModuleParam);

    /**
     * 删除移动端菜单
     *
     * @author yubaoshan
     * @date  2023/01/28 22:42
     */
    void delete(List<MobileMenuIdParam> mobileMenuIdParamList);

    /**
     * 获取移动端菜单详情
     *
     * @author yubaoshan
     * @date  2023/01/28 22:42
     */
    MobileMenu detail(MobileMenuIdParam mobileMenuIdParam);

    /**
     * 获取移动端菜单详情
     *
     * @author yubaoshan
     * @date  2023/01/28 22:42
     **/
    MobileMenu queryEntity(String id);

    /* ====以下为各种递归方法==== */

    /**
     * 根据id获取所有的子数据列表
     *
     * @author xuyuxiang
     * @date 2022/8/2 14:52
     */
    List<MobileMenu> getChildListById(List<MobileMenu> originDataList, String id, boolean includeSelf);

    /**
     * 根据id获取所有的父数据列表
     *
     * @author xuyuxiang
     * @date 2022/8/2 14:52
     */
    List<MobileMenu> getParentListById(List<MobileMenu> originDataList, String id, boolean includeSelf);

    /**
     * 获取模块选择器
     *
     * @author yubaoshan
     * @date  2023/01/28 22:42
     **/
    List<MobileModule> moduleSelector(MobileMenuSelectorModuleParam mobileMenuSelectorModuleParam);

    /**
     * 获取菜单树选择器
     *
     * @author yubaoshan
     * @date  2023/01/28 22:42
     **/
    List<Tree<String>> menuTreeSelector(MobileMenuSelectorMenuParam mobileMenuSelectorMenuParam);

    /**
     * 获取移动端菜单授权树
     *
     * @author xuyuxiang
     * @date 2023/1/31 10:14
     **/
    List<JSONObject> mobileMenuTreeSelector();

    /**
     * 获取移动端登录菜单树
     *
     * @author xuyuxiang
     * @date 2023/1/31 10:30
     **/
    List<Tree<String>> loginMobileMenuTree(List<String> menuIdList);
}
