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
package com.kiteehub.knowledge.modular.chatai.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import com.fhs.core.trans.vo.TransPojo;
import com.kiteehub.common.pojo.CommonEntity;
import com.kiteehub.knowledge.modular.robot.entity.KnowledgeRobot;
import com.kiteehub.knowledge.openai.enums.MsgUserType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 客服记录实体
 *
 * @author Ranger
 * @date  2024/01/23 17:00
 **/
@Getter
@Setter
@TableName("chat_record")
public class ChatRecord extends CommonEntity implements TransPojo {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 消息类型 */
    @ApiModelProperty(value = "消息类型", position = 2)
    private MsgUserType msgType;

    /** 客服ID */
    @ApiModelProperty(value = "客服ID", position = 3)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    @Trans(type = TransType.SIMPLE, target = KnowledgeRobot.class, fields = "name",alias = "robot", ref = "robotName")
    private String robotId;

    @TableField(exist = false)
    @ApiModelProperty(value = "客服名称", position = 4)
    private String robotName;

    /** 消息内容 */
    @ApiModelProperty(value = "消息内容", position = 5)
    private String message;

    public void created(String userId){
        this.setCreateUser(userId);
        this.setCreateTime(new Date());
        this.setUpdateUser(userId);
        this.setUpdateTime(new Date());
    }
}
