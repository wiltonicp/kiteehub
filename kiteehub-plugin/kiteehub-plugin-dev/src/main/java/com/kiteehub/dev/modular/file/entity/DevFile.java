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
package com.kiteehub.dev.modular.file.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import com.kiteehub.common.pojo.CommonEntity;

/**
 * 文件实体
 *
 * @author xuyuxiang
 * @date 2022/2/23 18:27
 **/
@Getter
@Setter
@TableName("DEV_FILE")
public class DevFile extends CommonEntity {

    /** id */
    @ApiModelProperty(value = "id", position = 1)
    private String id;

    /** 存储引擎 */
    @ApiModelProperty(value = "存储引擎", position = 2)
    private String engine;

    /** 存储桶 */
    @ApiModelProperty(value = "存储桶", position = 3)
    private String bucket;

    /** 文件名称 */
    @ApiModelProperty(value = "文件名称", position = 4)
    private String name;

    /** 文件后缀 */
    @ApiModelProperty(value = "文件后缀", position = 5)
    private String suffix;

    /** 文件大小kb */
    @ApiModelProperty(value = "文件大小kb", position = 6)
    private String sizeKb;

    /** 文件大小（格式化后） */
    @ApiModelProperty(value = "文件大小（格式化后）", position = 7)
    private String sizeInfo;

    /** 文件的对象名（唯一名称） */
    @ApiModelProperty(value = "文件的对象名（唯一名称）", position = 8)
    private String objName;

    /** 文件存储路径 */
    @ApiModelProperty(value = "文件存储路径", position = 9)
    private String storagePath;

    /** 文件下载路径 */
    @ApiModelProperty(value = "文件下载路径", position = 10)
    private String downloadPath;

    /** 图片缩略图 */
    @ApiModelProperty(value = "图片缩略图", position = 11)
    private String thumbnail;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 12)
    private String extJson;
}
