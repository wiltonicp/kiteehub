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
package com.kiteehub.dev.modular.email.util;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import com.kiteehub.common.exception.CommonException;
import com.kiteehub.dev.api.DevConfigApi;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 本地邮件工具类
 *
 * @author xuyuxiang
 * @date 2022/6/17 11:15
 **/
@Slf4j
public class DevEmailLocalUtil {

    private static MailAccount mailAccount;

    private static final String KITEE_EMAIL_LOCAL_FROM_KEY = "KITEE_EMAIL_LOCAL_FROM";
    private static final String KITEE_EMAIL_LOCAL_PASSWORD_KEY = "KITEE_EMAIL_LOCAL_PASSWORD";

    /**
     * 初始化操作的客户端
     *
     * @author xuyuxiang
     * @date 2022/1/5 23:24
     */
    private static void initClient() {

        DevConfigApi devConfigApi = SpringUtil.getBean(DevConfigApi.class);

        /* 发件人（必须正确，否则发送失败） */
        String from = devConfigApi.getValueByKey(KITEE_EMAIL_LOCAL_FROM_KEY);

        if(ObjectUtil.isEmpty(from)) {
            throw new CommonException("本地邮件操作客户端未正确配置：from为空");
        }

        /* 密码（注意，某些邮箱需要为SMTP服务单独设置授权码，详情查看相关帮助） */
        String pass = devConfigApi.getValueByKey(KITEE_EMAIL_LOCAL_PASSWORD_KEY);

        if(ObjectUtil.isEmpty(pass)) {
            throw new CommonException("本地邮件操作客户端未正确配置：pass为空");
        }

        mailAccount = new MailAccount();
        mailAccount.setFrom(from);
        mailAccount.setPass(pass);
    }

    public static MailAccount getClient() {
        initClient();
        return mailAccount;
    }

    /**
     * 发送纯文本邮件
     *
     * @param tos 收件人邮箱，逗号拼接
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param files 附件列表
     * @return 发送成功的回执id
     * @author xuyuxiang
     * @date 2022/2/7 22:29
     */
    public static String sendTextEmail(String tos, String subject, String content, List<File> files) {
        try {
            initClient();
            return MailUtil.send(mailAccount, tos, subject, content, false, ArrayUtil.toArray(files, File.class));
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    /**
     * 发送HTML邮件
     *
     * @param tos 收件人邮箱列表，逗号拼接
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param imageMap – 图片与占位符，占位符格式为cid:$IMAGE_PLACEHOLDER
     * @param files 附件列表
     * @return 发送成功的回执id
     * @author xuyuxiang
     * @date 2022/2/7 22:29
     */
    public static String sendHtmlEmail(String tos, String subject, String content, Map<String, InputStream> imageMap, List<File> files) {
        try {
            initClient();
            return MailUtil.send(mailAccount, tos, subject, content, imageMap, true, ArrayUtil.toArray(files, File.class));
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }
}
