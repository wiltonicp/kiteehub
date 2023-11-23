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
package com.kiteehub.gen.modular.basic.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 预览代码生成结果
 *
 * @author xuyuxiang
 * @date 2022/10/28 17:03
 **/
@Getter
@Setter
public class GenBasicPreviewResult {

    /** SQL代码结果集 */
    @ApiModelProperty(value = "SQL代码结果集", position = 1)
    private List<GenBasicCodeResult> genBasicCodeSqlResultList;

    /** 前端代码结果集 */
    @ApiModelProperty(value = "前端代码结果集", position = 2)
    private List<GenBasicCodeResult> genBasicCodeFrontendResultList;

    /** 后端代码结果集 */
    @ApiModelProperty(value = "后端代码结果集", position = 3)
    private List<GenBasicCodeResult> genBasicCodeBackendResultList;

    @Getter
    @Setter
    public static class GenBasicCodeResult {

        /** 代码文件名称 */
        @ApiModelProperty(value = "代码文件名称", position = 1)
        private String codeFileName;

        /** 代码文件带路径名称 */
        @ApiModelProperty(value = "代码文件带路径名称", position = 2)
        private String codeFileWithPathName;

        /** 代码文件内容 */
        @ApiModelProperty(value = "代码文件内容", position = 2)
        private String codeFileContent;
    }
}
