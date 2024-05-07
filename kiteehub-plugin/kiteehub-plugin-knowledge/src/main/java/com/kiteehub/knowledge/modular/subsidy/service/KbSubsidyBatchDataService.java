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
package com.kiteehub.knowledge.modular.subsidy.service;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kiteehub.knowledge.modular.subsidy.entity.KbSubsidyBatchData;
import com.kiteehub.knowledge.modular.subsidy.param.*;

import java.util.List;

/**
 * 待遇补贴批次详情Service接口
 *
 * @author Ranger
 * @date  2024/01/31 15:06
 **/
public interface KbSubsidyBatchDataService extends IService<KbSubsidyBatchData> {

    /**
     * 获取待遇补贴批次详情分页
     *
     * @author Ranger
     * @date  2024/01/31 15:06
     */
    Page<KbSubsidyBatchData> page(KbSubsidyBatchDataPageParam kbSubsidyBatchDataPageParam);

    /**
     * 添加待遇补贴批次详情
     *
     * @author Ranger
     * @date  2024/01/31 15:06
     */
    void add(KbSubsidyBatchDataAddParam kbSubsidyBatchDataAddParam);

    /**
     * 编辑待遇补贴批次详情
     *
     * @author Ranger
     * @date  2024/01/31 15:06
     */
    void edit(KbSubsidyBatchDataEditParam kbSubsidyBatchDataEditParam);

    /**
     * 删除待遇补贴批次详情
     *
     * @author Ranger
     * @date  2024/01/31 15:06
     */
    void delete(List<KbSubsidyBatchDataIdParam> kbSubsidyBatchDataIdParamList);

    /**
     * 获取待遇补贴批次详情详情
     *
     * @author Ranger
     * @date  2024/01/31 15:06
     */
    KbSubsidyBatchData detail(KbSubsidyBatchDataIdParam kbSubsidyBatchDataIdParam);

    /**
     * 获取待遇补贴批次详情详情
     *
     * @author Ranger
     * @date  2024/01/31 15:06
     **/
    KbSubsidyBatchData queryEntity(String id);

    /**
     * 获取待遇补贴批次分组
     * @param kbSubsidyBatchIdParam
     * @return
     */
    JSONArray groupBatch(KbSubsidyBatchIdParam kbSubsidyBatchIdParam);
}
