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
package com.kiteehub.dev.modular.email.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailAccount;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kiteehub.common.enums.CommonSortOrderEnum;
import com.kiteehub.common.exception.CommonException;
import com.kiteehub.common.page.CommonPageRequest;
import com.kiteehub.common.util.CommonEmailUtil;
import com.kiteehub.dev.modular.email.entity.DevEmail;
import com.kiteehub.dev.modular.email.enums.DevEmailEngineTypeEnum;
import com.kiteehub.dev.modular.email.mapper.DevEmailMapper;
import com.kiteehub.dev.modular.email.param.*;
import com.kiteehub.dev.modular.email.service.DevEmailService;
import com.kiteehub.dev.modular.email.util.DevEmailAliyunUtil;
import com.kiteehub.dev.modular.email.util.DevEmailLocalUtil;
import com.kiteehub.dev.modular.email.util.DevEmailTencentUtil;

import java.util.List;

/**
 * 邮件Service接口实现类
 *
 * @author xuyuxiang
 * @date 2022/2/23 18:43
 **/
@Service
public class DevEmailServiceImpl extends ServiceImpl<DevEmailMapper, DevEmail> implements DevEmailService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void sendLocal(DevEmailSendLocalTxtParam devEmailSendLocalTxtParam) {
        CommonEmailUtil.validEmail(devEmailSendLocalTxtParam.getReceiveAccounts());
        String receiptInfo = DevEmailLocalUtil.sendTextEmail(devEmailSendLocalTxtParam.getReceiveAccounts(),
                devEmailSendLocalTxtParam.getSubject(), devEmailSendLocalTxtParam.getContent(), devEmailSendLocalTxtParam.getFiles());
        DevEmail devEmail = new DevEmail();
        BeanUtil.copyProperties(devEmailSendLocalTxtParam, devEmail);
        devEmail.setEngine(DevEmailEngineTypeEnum.LOCAL.getValue());
        devEmail.setReceiptInfo(receiptInfo);
        MailAccount client = DevEmailLocalUtil.getClient();
        devEmail.setSendAccount(client.getFrom());
        devEmail.setSendUser(client.getUser());
        this.save(devEmail);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void sendLocal(DevEmailSendLocalHtmlParam devEmailSendLocalHtmlParam) {
        CommonEmailUtil.validEmail(devEmailSendLocalHtmlParam.getReceiveAccounts());
        String receiptInfo = DevEmailLocalUtil.sendHtmlEmail(devEmailSendLocalHtmlParam.getReceiveAccounts(),
                devEmailSendLocalHtmlParam.getSubject(), devEmailSendLocalHtmlParam.getContent(), devEmailSendLocalHtmlParam.getImageMap(),
                devEmailSendLocalHtmlParam.getFiles());
        DevEmail devEmail = new DevEmail();
        BeanUtil.copyProperties(devEmailSendLocalHtmlParam, devEmail);
        devEmail.setEngine(DevEmailEngineTypeEnum.LOCAL.getValue());
        devEmail.setReceiptInfo(receiptInfo);
        MailAccount client = DevEmailLocalUtil.getClient();
        devEmail.setSendAccount(client.getFrom());
        devEmail.setSendUser(client.getUser());
        this.save(devEmail);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void sendAliyun(DevEmailSendAliyunTxtParam devEmailSendAliyunTxtParam) {
        CommonEmailUtil.validEmail(devEmailSendAliyunTxtParam.getReceiveAccounts());
        String receiptInfo = DevEmailAliyunUtil.sendTextEmail(devEmailSendAliyunTxtParam.getSendAccount(),
                devEmailSendAliyunTxtParam.getSendUser(), devEmailSendAliyunTxtParam.getReceiveAccounts(),
                devEmailSendAliyunTxtParam.getSubject(), devEmailSendAliyunTxtParam.getContent());
        DevEmail devEmail = new DevEmail();
        BeanUtil.copyProperties(devEmailSendAliyunTxtParam, devEmail);
        devEmail.setEngine(DevEmailEngineTypeEnum.ALIYUN.getValue());
        devEmail.setReceiptInfo(receiptInfo);
        this.save(devEmail);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void sendAliyun(DevEmailSendAliyunHtmlParam devEmailSendAliyunHtmlParam) {
        CommonEmailUtil.validEmail(devEmailSendAliyunHtmlParam.getReceiveAccounts());
        String receiptInfo = DevEmailAliyunUtil.sendHtmlEmail(devEmailSendAliyunHtmlParam.getSendAccount(),
                devEmailSendAliyunHtmlParam.getSendUser(), devEmailSendAliyunHtmlParam.getReceiveAccounts(),
                devEmailSendAliyunHtmlParam.getSubject(), devEmailSendAliyunHtmlParam.getContent());
        DevEmail devEmail = new DevEmail();
        BeanUtil.copyProperties(devEmailSendAliyunHtmlParam, devEmail);
        devEmail.setEngine(DevEmailEngineTypeEnum.ALIYUN.getValue());
        devEmail.setReceiptInfo(receiptInfo);
        this.save(devEmail);
    }

    @Override
    public void sendAliyun(DevEmailSendAliyunTmpParam devEmailSendAliyunTmpParam) {
        CommonEmailUtil.validEmail(devEmailSendAliyunTmpParam.getReceiveAccounts());
        String receiptInfo = DevEmailAliyunUtil.sendEmailWithTemplate(devEmailSendAliyunTmpParam.getSendAccount(),
                devEmailSendAliyunTmpParam.getTagName(), devEmailSendAliyunTmpParam.getReceiveAccounts(),
                devEmailSendAliyunTmpParam.getTemplateName());
        DevEmail devEmail = new DevEmail();
        BeanUtil.copyProperties(devEmailSendAliyunTmpParam, devEmail);
        devEmail.setEngine(DevEmailEngineTypeEnum.ALIYUN.getValue());
        devEmail.setReceiptInfo(receiptInfo);
        this.save(devEmail);
    }

    @Override
    public void sendTencent(DevEmailSendTencentTxtParam devEmailSendTencentTxtParam) {
        CommonEmailUtil.validEmail(devEmailSendTencentTxtParam.getReceiveAccounts());
        String receiptInfo = DevEmailTencentUtil.sendTextEmail(devEmailSendTencentTxtParam.getSendAccount(),
                devEmailSendTencentTxtParam.getSendUser(),  devEmailSendTencentTxtParam.getReceiveAccounts(),
                devEmailSendTencentTxtParam.getSubject(), devEmailSendTencentTxtParam.getContent(),
                devEmailSendTencentTxtParam.getAttachmentList());
        DevEmail devEmail = new DevEmail();
        BeanUtil.copyProperties(devEmailSendTencentTxtParam, devEmail);
        devEmail.setEngine(DevEmailEngineTypeEnum.TENCENT.getValue());
        devEmail.setReceiptInfo(receiptInfo);
        this.save(devEmail);
    }

    @Override
    public void sendTencent(DevEmailSendTencentHtmlParam devEmailSendTencentHtmlParam) {
        CommonEmailUtil.validEmail(devEmailSendTencentHtmlParam.getReceiveAccounts());
        String receiptInfo = DevEmailTencentUtil.sendHtmlEmail(devEmailSendTencentHtmlParam.getSendAccount(),
                devEmailSendTencentHtmlParam.getSendUser(),  devEmailSendTencentHtmlParam.getReceiveAccounts(),
                devEmailSendTencentHtmlParam.getSubject(), devEmailSendTencentHtmlParam.getContent(),
                devEmailSendTencentHtmlParam.getAttachmentList());
        DevEmail devEmail = new DevEmail();
        BeanUtil.copyProperties(devEmailSendTencentHtmlParam, devEmail);
        devEmail.setEngine(DevEmailEngineTypeEnum.TENCENT.getValue());
        devEmail.setReceiptInfo(receiptInfo);
        this.save(devEmail);
    }

    @Override
    public void sendTencent(DevEmailSendTencentTmpParam devEmailSendTencentTmpParam) {
        CommonEmailUtil.validEmail(devEmailSendTencentTmpParam.getReceiveAccounts());
        String receiptInfo = DevEmailTencentUtil.sendEmailWithTemplate(devEmailSendTencentTmpParam.getSendAccount(),
                devEmailSendTencentTmpParam.getSendUser(),  devEmailSendTencentTmpParam.getReceiveAccounts(),
                devEmailSendTencentTmpParam.getTemplateName(), devEmailSendTencentTmpParam.getTemplateParam(),
                devEmailSendTencentTmpParam.getSubject(), CollectionUtil.newArrayList());
        DevEmail devEmail = new DevEmail();
        BeanUtil.copyProperties(devEmailSendTencentTmpParam, devEmail);
        devEmail.setEngine(DevEmailEngineTypeEnum.TENCENT.getValue());
        devEmail.setReceiptInfo(receiptInfo);
        this.save(devEmail);
    }

    @Override
    public Page<DevEmail> page(DevEmailPageParam devEmailPageParam) {
        QueryWrapper<DevEmail> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(devEmailPageParam.getEngine())) {
            queryWrapper.lambda().eq(DevEmail::getEngine, devEmailPageParam.getEngine());
        }
        if(ObjectUtil.isNotEmpty(devEmailPageParam.getSearchKey())) {
            queryWrapper.lambda().like(DevEmail::getSubject, devEmailPageParam.getSearchKey());
        }
        if(ObjectUtil.isAllNotEmpty(devEmailPageParam.getSortField(), devEmailPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(devEmailPageParam.getSortOrder());
            queryWrapper.orderBy(true, devEmailPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(devEmailPageParam.getSortField()));
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Override
    public void delete(List<DevEmailIdParam> devEmailIdParamList) {
        this.removeByIds(CollStreamUtil.toList(devEmailIdParamList, DevEmailIdParam::getId));
    }

    @Override
    public DevEmail detail(DevEmailIdParam devEmailIdParam) {
        return this.queryEntity(devEmailIdParam.getId());
    }

    @Override
    public DevEmail queryEntity(String id) {
        DevEmail devEmail = this.getById(id);
        if(ObjectUtil.isEmpty(devEmail)) {
            throw new CommonException("邮件发送记录不存在，id值为：{}", id);
        }
        return devEmail;
    }
}
