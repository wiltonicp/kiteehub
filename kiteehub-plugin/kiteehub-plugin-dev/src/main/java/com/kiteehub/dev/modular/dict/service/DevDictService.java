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
package com.kiteehub.dev.modular.dict.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kiteehub.dev.modular.dict.entity.DevDict;
import com.kiteehub.dev.modular.dict.param.*;

import java.util.List;

/**
 * 字典Service接口
 *
 * @author xuyuxiang
 * @date 2022/4/22 10:41
 **/
public interface DevDictService extends IService<DevDict> {

    /**
     * 获取字典分页
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    Page<DevDict> page(DevDictPageParam devDictPageParam);

    /**
     * 获取字典列表
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    List<DevDict> list(DevDictListParam devDictListParam);

    /**
     * 获取字典树
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    List<Tree<String>> tree(DevDictTreeParam devDictTreeParam);

    /**
     * 添加字典
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:48
     */
    void add(DevDictAddParam devDictAddParam);

    /**
     * 编辑字典
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:13
     */
    void edit(DevDictEditParam devDictEditParam);

    /**
     * 删除字典
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:18
     */
    void delete(List<DevDictIdParam> devDictIdParamList);

    /**
     * 获取字典详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:18
     */
    DevDict detail(DevDictIdParam devDictIdParam);

    /**
     * 获取字典详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:18
     */
    DevDict queryEntity(String id);
}
