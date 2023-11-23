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
package com.kiteehub.dev.modular.config.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kiteehub.dev.modular.config.entity.DevConfig;
import com.kiteehub.dev.modular.config.param.*;

import java.util.List;

/**
 * 配置Service接口
 *
 * @author xuyuxiang
 * @date 2022/4/22 10:41
 **/
public interface DevConfigService extends IService<DevConfig> {

    /**
     * 根据键获取值
     *
     * @author xuyuxiang
     * @date 2022/4/22 14:52
     **/
    String getValueByKey(String key);

    /**
     * 获取配置分页
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    Page<DevConfig> page(DevConfigPageParam devConfigPageParam);

    /**
     * 获取配置列表
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    List<DevConfig> list(DevConfigListParam devConfigListParam);

    /**
     * 添加配置
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:48
     */
    void add(DevConfigAddParam devConfigAddParam);

    /**
     * 编辑配置
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:13
     */
    void edit(DevConfigEditParam devConfigEditParam);

    /**
     * 删除配置
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:18
     */
    void delete(List<DevConfigIdParam> devConfigIdParamList);

    /**
     * 获取配置详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:18
     */
    DevConfig detail(DevConfigIdParam devConfigIdParam);

    /**
     * 获取配置详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:18
     */
    DevConfig queryEntity(String id);

    /**
     * 配置批量更新
     *
     * @author xuyuxiang
     * @date 2022/6/28 11:09
     **/
    void editBatch(List<DevConfigBatchParam> devConfigBatchParamList);
}
