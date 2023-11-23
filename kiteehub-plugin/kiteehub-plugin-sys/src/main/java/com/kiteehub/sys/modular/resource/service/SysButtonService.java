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
import com.kiteehub.sys.modular.resource.entity.SysButton;
import com.kiteehub.sys.modular.resource.param.button.SysButtonAddParam;
import com.kiteehub.sys.modular.resource.param.button.SysButtonEditParam;
import com.kiteehub.sys.modular.resource.param.button.SysButtonIdParam;
import com.kiteehub.sys.modular.resource.param.button.SysButtonPageParam;

import java.util.List;

/**
 * 按钮Service接口
 *
 * @author xuyuxiang
 * @date 2022/6/27 14:01
 **/
public interface SysButtonService extends IService<SysButton> {

    /**
     * 获取按钮分页
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    Page<SysButton> page(SysButtonPageParam sysButtonPageParam);

    /**
     * 添加按钮
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:48
     */
    void add(SysButtonAddParam sysButtonAddParam);

    /**
     * 代码生成按钮插入
     *
     * @author xuyuxiang
     * @date 2022/11/1 15:34
     * @return java.lang.String
     **/
    void addForGenButton(String menuId, String className, String functionName);

    /**
     * 编辑按钮
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:13
     */
    void edit(SysButtonEditParam sysButtonEditParam);

    /**
     * 删除按钮
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:18
     */
    void delete(List<SysButtonIdParam> sysButtonIdParamList);

    /**
     * 获取按钮详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:18
     */
    SysButton detail(SysButtonIdParam sysButtonIdParam);

    /**
     * 获取按钮详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:18
     */
    SysButton queryEntity(String id);
}
