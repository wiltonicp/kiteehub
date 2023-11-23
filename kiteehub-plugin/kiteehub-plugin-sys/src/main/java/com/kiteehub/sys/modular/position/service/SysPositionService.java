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
package com.kiteehub.sys.modular.position.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kiteehub.sys.modular.position.entity.SysPosition;
import com.kiteehub.sys.modular.position.param.*;

import java.util.List;

/**
 * 职位Service接口
 *
 * @author xuyuxiang
 * @date 2022/4/21 18:35
 **/
public interface SysPositionService extends IService<SysPosition> {

    /**
     * 获取职位分页
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    Page<SysPosition> page(SysPositionPageParam sysPositionPageParam);

    /**
     * 添加职位
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:48
     */
    void add(SysPositionAddParam sysPositionAddParam);

    /**
     * 编辑职位
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:13
     */
    void edit(SysPositionEditParam sysPositionEditParam);

    /**
     * 删除职位
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:18
     */
    void delete(List<SysPositionIdParam> sysPositionIdParamList);

    /**
     * 获取职位详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:18
     */
    SysPosition detail(SysPositionIdParam sysPositionIdParam);

    /**
     * 获取职位详情
     *
     * @author xuyuxiang
     * @date 2022/7/25 19:42
     **/
    SysPosition queryEntity(String id);

    /**
     * 根据id获取数据
     *
     * @author xuyuxiang
     * @date 2022/8/15 14:55
     **/
    SysPosition getById(List<SysPosition> originDataList, String id);

    /**
     * 根据组织id和职位名称获取职位id，有则返回，无则创建
     *
     * @author xuyuxiang
     * @date 2022/8/15 14:55
     **/
    String getPositionIdByPositionNameWithCreate(String orgId, String positionName);

    /* ====职位部分所需要用到的选择器==== */

    /**
     * 获取组织树选择器
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    List<Tree<String>> orgTreeSelector();

    /**
     * 获取职位选择器
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    Page<SysPosition> positionSelector(SysPositionSelectorPositionParam sysPositionSelectorPositionParam);
}
