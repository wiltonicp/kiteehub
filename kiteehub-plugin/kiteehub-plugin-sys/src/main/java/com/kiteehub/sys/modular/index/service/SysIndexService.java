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
package com.kiteehub.sys.modular.index.service;

import com.kiteehub.common.pojo.CommonValidList;
import com.kiteehub.sys.modular.index.param.*;
import com.kiteehub.sys.modular.index.result.*;

import java.util.List;

/**
 * 系统首页Service接口
 *
 * @author xuyuxiang
 * @date 2022/9/2 10:45
 */
public interface SysIndexService {

    /**
     * 添加当前用户日程
     *
     * @author xuyuxiang
     * @date 2022/9/2 11:13
     */
    void addSchedule(SysIndexScheduleAddParam sysIndexScheduleAddParam);

    /**
     * 删除日程
     *
     * @author xuyuxiang
     * @date 2022/9/2 11:32
     */
    void deleteSchedule(CommonValidList<SysIndexScheduleIdParam> sysIndexScheduleIdParamList);

    /**
     * 获取当前用户日程列表
     *
     * @author xuyuxiang
     * @date 2022/9/2 11:23
     */
    List<SysIndexScheduleListResult> scheduleList(SysIndexScheduleListParam sysIndexScheduleListParam);

    /**
     * 获取当前用户站内信列表
     *
     * @author xuyuxiang
     * @date 2022/9/2 11:36
     */
    List<SysIndexMessageListResult> messageList(SysIndexMessageListParam sysIndexMessageListParam);

    /**
     * 获取站内信详情
     *
     * @author xuyuxiang
     * @date 2022/9/2 11:44
     */
    SysIndexMessageDetailResult messageDetail(SysIndexMessageIdParam sysIndexMessageIdParam);

    /**
     * 获取当前用户访问日志列表
     *
     * @author xuyuxiang
     * @date 2022/9/4 15:11
     */
    List<SysIndexVisLogListResult> visLogList();

    /**
     * 获取当前用户操作日志列表
     *
     * @author xuyuxiang
     * @date 2022/9/4 15:11
     */
    List<SysIndexOpLogListResult> opLogList();
}
