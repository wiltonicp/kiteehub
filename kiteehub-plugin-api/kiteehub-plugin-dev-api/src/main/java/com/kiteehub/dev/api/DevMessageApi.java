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
package com.kiteehub.dev.api;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * 站内信API接口
 *
 * @author xuyuxiang
 * @date 2022/6/22 17:33
 **/
public interface DevMessageApi {

    /**
     * 发送站内信，默认：分类系统通知
     *
     * @param receiverIdList 接收的用户id集合
     * @param subject 主题
     * @author xuyuxiang
     * @date 2022/6/22 17:35
     **/
    void sendMessage(List<String> receiverIdList, String subject);

    /**
     * 发送站内信指定分类
     *
     * @param receiverIdList 接收的用户id集合
     * @param subject 主题
     * @author xuyuxiang
     * @date 2022/6/22 17:35
     **/
    void sendMessage(List<String> receiverIdList, String category, String subject);

    /**
     * 发送站内信带内容，默认：分类系统通知
     *
     * @param receiverIdList 接收的用户id集合
     * @param subject 主题
     * @param content 站内信内容
     * @author xuyuxiang
     * @date 2022/6/22 17:35
     **/
    void sendMessageWithContent(List<String> receiverIdList, String subject, String content);

    /**
     * 发送站内信带内容，指定分类
     *
     * @param receiverIdList 接收的用户id集合
     * @param subject 主题
     * @param content 站内信内容
     * @author xuyuxiang
     * @date 2022/6/22 17:35
     **/
    void sendMessageWithContent(List<String> receiverIdList, String category, String subject, String content);

    /**
     * 获取未读站内信列表
     *
     * @author xuyuxiang
     * @date 2022/9/2 11:48
     */
    List<JSONObject> list(List<String> receiverIdList, Integer limit);

    /**
     * 获取站内信分页
     *
     * @author xuyuxiang
     * @date 2022/9/2 11:48
     */
    Page<JSONObject> page(List<String> receiverIdList, String category);

    /**
     * 获取站内信详情
     *
     * @param id 站内信id
     * @author xuyuxiang
     * @date 2022/4/24 20:08
     */
    JSONObject detail(String id);

}
