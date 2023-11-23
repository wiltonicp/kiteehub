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
package com.kiteehub.auth.modular.third.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import com.kiteehub.common.pojo.CommonEntity;

/**
 * 第三方登录实体
 *
 * @author xuyuxiang
 * @date 2022/7/9 14:29
 */
@Getter
@Setter
@TableName("AUTH_THIRD_USER")
public class AuthThirdUser extends CommonEntity {

    /** id */
    @ApiModelProperty(value = "id", position = 1)
    private String id;

    /** 三方用户id */
    @ApiModelProperty(value = "三方用户id", position = 2)
    private String thirdId;

    /** 系统用户id */
    @ApiModelProperty(value = "系统用户id", position = 3)
    private String userId;

    /** 头像 */
    @ApiModelProperty(value = "头像", position = 4)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String avatar;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 5)
    private String name;

    /** 昵称 */
    @ApiModelProperty(value = "昵称", position = 6)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String nickname;

    /** 性别 */
    @ApiModelProperty(value = "性别", position = 7)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String gender;

    /** 分类 */
    @ApiModelProperty(value = "分类", position = 8)
    private String category;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 9)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String extJson;
}
