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
package com.kiteehub.auth.modular.monitor.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 会话统计结果
 *
 * @author xuyuxiang
 * @date 2022/7/19 9:29
 **/
@Getter
@Setter
public class AuthSessionAnalysisResult {

    /** 当前会话总数量 */
    @ApiModelProperty(value = "当前会话总数量", position = 1)
    private String currentSessionTotalCount;

    /** 最大签发令牌数 */
    @ApiModelProperty(value = "最大签发令牌数", position = 2)
    private String maxTokenCount;

    /** 最近1小时会话数 */
    @ApiModelProperty(value = "最近1小时会话数", position = 3)
    private String oneHourNewlyAdded;

    /** BC端会话比例 */
    @ApiModelProperty(value = "BC端会话比例", position = 4)
    private String proportionOfBAndC;
}
