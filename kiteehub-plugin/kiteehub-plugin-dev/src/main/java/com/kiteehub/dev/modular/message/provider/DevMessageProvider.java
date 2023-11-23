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
package com.kiteehub.dev.modular.message.provider;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import com.kiteehub.dev.api.DevMessageApi;
import com.kiteehub.dev.modular.message.param.DevMessageIdParam;
import com.kiteehub.dev.modular.message.param.DevMessageListParam;
import com.kiteehub.dev.modular.message.param.DevMessageSendParam;
import com.kiteehub.dev.modular.message.service.DevMessageService;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 站内信API接口提供者
 *
 * @author xuyuxiang
 * @date 2022/6/22 15:32
 **/
@Service
public class DevMessageProvider implements DevMessageApi {

    @Resource
    private DevMessageService devMessageService;

    @Override
    public void sendMessage(List<String> receiverIdList, String subject) {
        this.sendMessage(receiverIdList, subject, null);
    }

    @Override
    public void sendMessage(List<String> receiverIdList, String subject, String content) {
        this.sendMessageWithContent(receiverIdList, subject, null);
    }

    @Override
    public void sendMessageWithContent(List<String> receiverIdList, String subject, String content) {
        this.sendMessageWithContent(receiverIdList, null, subject, content);
    }

    @Override
    public void sendMessageWithContent(List<String> receiverIdList, String category, String subject, String content) {
        DevMessageSendParam devMessageSendParam = new DevMessageSendParam();
        devMessageSendParam.setReceiverIdList(receiverIdList);
        devMessageSendParam.setCategory(category);
        devMessageSendParam.setSubject(subject);
        devMessageSendParam.setContent(ObjectUtil.isEmpty(content)?subject:content);
        devMessageService.send(devMessageSendParam);
    }

    @Override
    public List<JSONObject> list(List<String> receiverIdList, Integer limit) {
        DevMessageListParam devMessageListParam = new DevMessageListParam();
        devMessageListParam.setReceiverIdList(receiverIdList);
        devMessageListParam.setLimit(limit);
        return devMessageService.list(devMessageListParam).stream().map(JSONUtil::parseObj).collect(Collectors.toList());
    }

    @Override
    public Page<JSONObject> page(List<String> receiverIdList, String category) {
        return devMessageService.page(receiverIdList, category);
    }

    @Override
    public JSONObject detail(String id) {
        DevMessageIdParam devMessageIdParam = new DevMessageIdParam();
        devMessageIdParam.setId(id);
        return JSONUtil.parseObj(devMessageService.detail(devMessageIdParam));
    }
}
