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
package com.kiteehub.sys.modular.resource.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kiteehub.sys.modular.resource.entity.SysMenu;
import com.kiteehub.sys.modular.resource.entity.SysModule;
import com.kiteehub.sys.modular.resource.param.menu.*;

import java.util.List;

/**
 * 菜单Service接口
 *
 * @author xuyuxiang
 * @date 2022/6/27 14:02
 **/
public interface SysMenuService extends IService<SysMenu> {


    /**
     * 获取菜单分页
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    Page<SysMenu> page(SysMenuPageParam sysMenuPageParam);

    /**
     * 获取菜单树
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    List<Tree<String>> tree(SysMenuTreeParam sysMenuTreeParam);

    /**
     * 添加菜单
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:48
     */
    void add(SysMenuAddParam sysMenuAddParam);

    /**
     * 代码生成菜单插入
     *
     * @author xuyuxiang
     * @date 2022/11/1 14:06
     **/
    String addForGenMenu(String parentId, String busName, String title, String module, String path);

    /**
     * 编辑菜单
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:13
     */
    void edit(SysMenuEditParam sysMenuEditParam);

    /**
     * 更改菜单所属模块
     *
     * @author xuyuxiang
     * @date 2022/9/26 13:09
     **/
    void changeModule(SysMenuChangeModuleParam sysMenuChangeModuleParam);

    /**
     * 删除菜单
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:18
     */
    void delete(List<SysMenuIdParam> sysMenuIdParamList);

    /**
     * 获取菜单详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:18
     */
    SysMenu detail(SysMenuIdParam sysMenuIdParam);

    /**
     * 获取菜单详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:18
     */
    SysMenu queryEntity(String id);

    /* ====以下为各种递归方法==== */

    /**
     * 根据id获取所有的子数据列表
     *
     * @author xuyuxiang
     * @date 2022/8/2 14:52
     */
    List<SysMenu> getChildListById(List<SysMenu> originDataList, String id, boolean includeSelf);

    /**
     * 根据id获取所有的父数据列表
     *
     * @author xuyuxiang
     * @date 2022/8/2 14:52
     */
    List<SysMenu> getParentListById(List<SysMenu> originDataList, String id, boolean includeSelf);

    /* ====菜单部分所需要用到的选择器==== */

    /**
     * 获取模块选择器
     *
     * @author xuyuxiang
     * @date 2022/8/2 14:53
     */
    List<SysModule> moduleSelector(SysMenuSelectorModuleParam sysMenuSelectorModuleParam);

    /**
     * 获取菜单树选择器
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    List<Tree<String>> menuTreeSelector(SysMenuSelectorMenuParam sysMenuSelectorMenuParam);
}
