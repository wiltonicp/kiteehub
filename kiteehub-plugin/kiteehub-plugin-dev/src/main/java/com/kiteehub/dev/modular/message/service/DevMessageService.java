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
package com.kiteehub.dev.modular.message.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kiteehub.dev.modular.message.entity.DevMessage;
import com.kiteehub.dev.modular.message.param.DevMessageIdParam;
import com.kiteehub.dev.modular.message.param.DevMessageListParam;
import com.kiteehub.dev.modular.message.param.DevMessagePageParam;
import com.kiteehub.dev.modular.message.param.DevMessageSendParam;
import com.kiteehub.dev.modular.message.result.DevMessageResult;

import java.util.List;

/**
 * 站内信Service接口
 *
 * @author xuyuxiang
 * @date 2022/6/21 14:54
 **/
public interface DevMessageService extends IService<DevMessage> {

    /**
     * 发送站内信
     *
     * @author xuyuxiang
     * @date 2022/6/21 18:37
     **/
    void send(DevMessageSendParam devMessageSendParam);

    /**
     * 获取站内信分页
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    Page<DevMessage> page(DevMessagePageParam devMessagePageParam);

    /**
     * 获取站内信分页，返回JSONObject分页
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    Page<JSONObject> page(List<String> receiverIdList, String category);

    /**
     * 获取站内信列表
     *
     * @author xuyuxiang
     * @date 2022/9/2 11:50
     */
    List<DevMessage> list(DevMessageListParam devMessageListParam);

    /**
     * 删除站内信
     *
     * @author xuyuxiang
     * @date 2022/8/4 10:36
     **/
    void delete(List<DevMessageIdParam> devMessageIdParamList);

    /**
     * 获取站内信详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    DevMessageResult detail(DevMessageIdParam devMessageIdParam);

    /**
     * 获取站内信详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    DevMessage queryEntity(String id);
}
