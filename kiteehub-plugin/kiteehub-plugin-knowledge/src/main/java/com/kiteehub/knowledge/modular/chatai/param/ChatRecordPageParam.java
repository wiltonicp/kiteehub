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
package com.kiteehub.knowledge.modular.chatai.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 客服记录查询参数
 *
 * @author Ranger
 * @date  2024/01/23 17:00
 **/
@Getter
@Setter
public class ChatRecordPageParam {

    /** 当前页 */
    @ApiModelProperty(value = "当前页码")
    private Integer current;

    /** 每页条数 */
    @ApiModelProperty(value = "每页条数")
    private Integer size;

    /** 排序字段 */
    @ApiModelProperty(value = "排序字段，字段驼峰名称，如：userName")
    private String sortField;

    /** 排序方式 */
    @ApiModelProperty(value = "排序方式，升序：ASCEND；降序：DESCEND")
    private String sortOrder;

    /** 关键词 */
    @ApiModelProperty(value = "关键词")
    private String searchKey;

    /** 消息类型 */
    @ApiModelProperty(value = "消息类型")
    private String msgType;

    /** 客服ID */
    @ApiModelProperty(value = "客服ID")
    private String robotId;

    /** 消息内容 */
    @ApiModelProperty(value = "消息内容")
    private String message;

    /** 创建时间开始 */
    @ApiModelProperty(value = "创建时间开始")
    private String startCreateTime;

    /** 创建时间结束 */
    @ApiModelProperty(value = "创建时间结束")
    private String endCreateTime;

}
