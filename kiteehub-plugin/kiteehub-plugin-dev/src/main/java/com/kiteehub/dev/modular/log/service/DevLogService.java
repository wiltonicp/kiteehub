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
package com.kiteehub.dev.modular.log.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kiteehub.dev.modular.log.entity.DevLog;
import com.kiteehub.dev.modular.log.param.DevLogDeleteParam;
import com.kiteehub.dev.modular.log.param.DevLogPageParam;
import com.kiteehub.dev.modular.log.result.DevLogOpBarChartDataResult;
import com.kiteehub.dev.modular.log.result.DevLogOpPieChartDataResult;
import com.kiteehub.dev.modular.log.result.DevLogVisLineChartDataResult;
import com.kiteehub.dev.modular.log.result.DevLogVisPieChartDataResult;

import java.util.List;

/**
 * 日志Service接口
 *
 * @author xuyuxiang
 * @date 2022/9/2 15:04
 */
public interface DevLogService extends IService<DevLog> {

    /**
     * 获取日志分页
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    Page<DevLog> page(DevLogPageParam devLogPageParam);

    /**
     * 清空日志
     *
     * @author xuyuxiang
     * @date 2022/9/6 13:17
     */
    void delete(DevLogDeleteParam devLogDeleteParam);

    /**
     * 获取访问日志折线图数据
     *
     * @author xuyuxiang
     * @date 2022/9/4 21:18
     */
    List<DevLogVisLineChartDataResult> visLogLineChartData();

    /**
     * 获取访问日志饼状图数据
     *
     * @author xuyuxiang
     * @date 2022/9/4 21:18
     */
    List<DevLogVisPieChartDataResult> visLogPieChartData();

    /**
     * 获取操作日志柱状图数据
     *
     * @author xuyuxiang
     * @date 2022/9/4 21:18
     */
    List<DevLogOpBarChartDataResult> opLogBarChartData();

    /**
     * 获取操作日志饼状图数据
     *
     * @author xuyuxiang
     * @date 2022/9/4 21:18
     */
    List<DevLogOpPieChartDataResult> opLogPieChartData();
}
