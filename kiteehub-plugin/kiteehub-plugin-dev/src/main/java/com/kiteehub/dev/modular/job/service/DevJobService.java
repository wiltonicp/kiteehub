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
package com.kiteehub.dev.modular.job.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kiteehub.dev.modular.job.entity.DevJob;
import com.kiteehub.dev.modular.job.param.*;

import java.util.List;

/**
 * 定时任务Service接口
 *
 * @author xuyuxiang
 * @date 2022/8/5 10:46
 **/
public interface DevJobService extends IService<DevJob> {

    /**
     * 获取定时任务分页
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    Page<DevJob> page(DevJobPageParam devJobPageParam);

    /**
     * 获取定时任务列表
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    List<DevJob> list(DevJobListParam devJobListParam);

    /**
     * 添加定时任务
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:48
     */
    void add(DevJobAddParam devJobAddParam);

    /**
     * 编辑定时任务
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:13
     */
    void edit(DevJobEditParam devJobEditParam);

    /**
     * 删除定时任务
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:18
     */
    void delete(List<DevJobIdParam> devJobIdParamList);

    /**
     * 获取定时任务详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:18
     */
    DevJob detail(DevJobIdParam devJobIdParam);

    /**
     * 获取定时任务详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:18
     */
    DevJob queryEntity(String id);

    /**
     * 停止定时任务
     *
     * @author xuyuxiang
     * @date 2022/7/5 18:20
     **/
    void stopJob(DevJobIdParam devJobIdParam);

    /**
     * 运行定时任务
     *
     * @author xuyuxiang
     * @date 2022/7/5 18:21
     **/
    void runJob(DevJobIdParam devJobIdParam);

    /**
     * 立即运行定时任务
     *
     * @author xuyuxiang
     * @date 2023/3/3 15:50
     **/
    void runJobNow(DevJobIdParam devJobIdParam);

    /**
     * 获取定时任务类
     *
     * @author xuyuxiang
     * @date 2022/8/22 9:35
     **/
    List<String> getActionClass();
}
