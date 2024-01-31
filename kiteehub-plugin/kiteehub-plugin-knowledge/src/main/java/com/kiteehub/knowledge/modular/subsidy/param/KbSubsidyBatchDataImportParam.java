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
package com.kiteehub.knowledge.modular.subsidy.param;

import lombok.Getter;
import lombok.Setter;

/**
 * 待遇补贴批次详情导入参数
 *
 * @author Ranger
 * @date  2024/01/31 15:06
 **/
@Getter
@Setter
public class KbSubsidyBatchDataImportParam {

    /** 姓名 */
    private String fullName;

    /** 身份证号 */
    private String cardId;

    /** 单位编码 */
    private String unitCode;

    /** 单位名称 */
    private String unitName;

    /** 待遇名称 */
    private String subsidyName;

    /** 金额 */
    private String amount;

}
