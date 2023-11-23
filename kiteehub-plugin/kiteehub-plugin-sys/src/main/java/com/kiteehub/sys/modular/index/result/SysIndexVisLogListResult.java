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
package com.kiteehub.sys.modular.index.result;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 访问日志结果
 *
 * @author xuyuxiang
 * @date 2022/9/2 15:02
 */
@Getter
@Setter
public class SysIndexVisLogListResult {

    /** id */
    @ApiModelProperty(value = "id", position = 1)
    private String id;

    /** 日志分类 */
    @ApiModelProperty(value = "日志分类", position = 2)
    private String category;

    /** 日志名称 */
    @ApiModelProperty(value = "日志名称", position = 3)
    private String name;

    /** 执行状态 */
    @ApiModelProperty(value = "执行状态", position = 4)
    private String exeStatus;

    /** 具体消息 */
    @ApiModelProperty(value = "具体消息", position = 5)
    private String exeMessage;

    /** 操作ip */
    @ApiModelProperty(value = "操作ip", position = 6)
    private String opIp;

    /** 操作地址 */
    @ApiModelProperty(value = "操作地址", position = 7)
    private String opAddress;

    /** 操作浏览器 */
    @ApiModelProperty(value = "操作浏览器", position = 8)
    private String opBrowser;

    /** 操作系统 */
    @ApiModelProperty(value = "操作系统", position = 9)
    private String opOs;

    /** 操作时间 */
    @ApiModelProperty(value = "操作时间", position = 10)
    private Date opTime;

    /** 操作人姓名 */
    @ApiModelProperty(value = "操作人姓名", position = 11)
    private String opUser;

    /** 签名数据 */
    @ApiModelProperty(value = "签名数据", position = 12)
    private String signData;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 13)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 14)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 15)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 16)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;
}
