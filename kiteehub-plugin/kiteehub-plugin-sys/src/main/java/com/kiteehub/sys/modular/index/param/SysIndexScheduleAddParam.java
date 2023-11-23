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
package com.kiteehub.sys.modular.index.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 日程添加参数
 *
 * @author xuyuxiang
 * @date 2022/9/2 11:06
 */
@Getter
@Setter
public class SysIndexScheduleAddParam {

    /** 日程日期 */
    @ApiModelProperty(value = "日程日期", required = true, position = 1)
    @NotBlank(message = "scheduleDate不能为空")
    private String scheduleDate;

    /** 日程时间 */
    @ApiModelProperty(value = "日程时间", required = true, position = 2)
    @NotBlank(message = "scheduleTime不能为空")
    private String scheduleTime;

    /** 日程内容 */
    @ApiModelProperty(value = "日程内容", required = true, position = 3)
    @NotBlank(message = "scheduleContent不能为空")
    private String scheduleContent;

    /** 用户id */
    @ApiModelProperty(value = "用户id", hidden = true, position = 4)
    private String scheduleUserId;

    /** 用户姓名 */
    @ApiModelProperty(value = "用户姓名", hidden = true, position = 5)
    private String scheduleUserName;
}
