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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kiteehub.sys.modular.resource.entity.SysSpa;
import com.kiteehub.sys.modular.resource.param.spa.SysSpaAddParam;
import com.kiteehub.sys.modular.resource.param.spa.SysSpaEditParam;
import com.kiteehub.sys.modular.resource.param.spa.SysSpaIdParam;
import com.kiteehub.sys.modular.resource.param.spa.SysSpaPageParam;

import java.util.List;

/**
 * 单页面Service接口
 *
 * @author xuyuxiang
 * @date 2022/6/27 14:03
 **/
public interface SysSpaService extends IService<SysSpa> {

    /**
     * 获取单页面分页
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    Page<SysSpa> page(SysSpaPageParam sysSpaPageParam);

    /**
     * 添加单页面
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:48
     */
    void add(SysSpaAddParam sysSpaAddParam);

    /**
     * 编辑单页面
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:13
     */
    void edit(SysSpaEditParam sysSpaEditParam);

    /**
     * 删除单页面
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:18
     */
    void delete(List<SysSpaIdParam> sysSpaIdParamList);

    /**
     * 获取单页面详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:18
     */
    SysSpa detail(SysSpaIdParam sysSpaIdParam);

    /**
     * 获取单页面详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:18
     */
    SysSpa queryEntity(String id);
}
