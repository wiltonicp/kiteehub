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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kiteehub.mobile.modular.resource.entity.MobileModule;
import com.kiteehub.mobile.modular.resource.param.module.MobileModuleAddParam;
import com.kiteehub.mobile.modular.resource.param.module.MobileModuleEditParam;
import com.kiteehub.mobile.modular.resource.param.module.MobileModuleIdParam;
import com.kiteehub.mobile.modular.resource.param.module.MobileModulePageParam;

import java.util.List;

/**
 * 移动端模块Service接口
 *
 * @author xuyuxiang
 * @date 2022/6/27 14:03
 **/
public interface MobileModuleService extends IService<MobileModule> {

    /**
     * 获取移动端模块分页
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    Page<MobileModule> page(MobileModulePageParam mobileModulePageParam);

    /**
     * 添加移动端模块
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:48
     */
    void add(MobileModuleAddParam mobileModuleAddParam);

    /**
     * 编辑移动端模块
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:13
     */
    void edit(MobileModuleEditParam mobileModuleEditParam);

    /**
     * 删除移动端模块
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:18
     */
    void delete(List<MobileModuleIdParam> mobileModuleIdParamList);

    /**
     * 获取移动端模块详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:18
     */
    MobileModule detail(MobileModuleIdParam mobileModuleIdParam);

    /**
     * 获取移动端模块详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:18
     */
    MobileModule queryEntity(String id);
}
