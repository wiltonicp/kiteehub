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

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kiteehub.knowledge.modular.subsidy.entity.KbSubsidyBatch;
import com.kiteehub.knowledge.modular.subsidy.param.KbSubsidyBatchAddParam;
import com.kiteehub.knowledge.modular.subsidy.param.KbSubsidyBatchEditParam;
import com.kiteehub.knowledge.modular.subsidy.param.KbSubsidyBatchIdParam;
import com.kiteehub.knowledge.modular.subsidy.param.KbSubsidyBatchPageParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 补贴公示Service接口
 *
 * @author Ranger
 * @date  2024/01/31 14:12
 **/
public interface KbSubsidyBatchService extends IService<KbSubsidyBatch> {

    /**
     * 获取补贴公示分页
     *
     * @author Ranger
     * @date  2024/01/31 14:12
     */
    Page<KbSubsidyBatch> page(KbSubsidyBatchPageParam kbSubsidyBatchPageParam);

    /**
     * 导入补贴公示
     *
     * @author Ranger
     * @date  2024/01/31 14:12
     */
    JSONObject importSubsidyBatch(KbSubsidyBatchAddParam kbSubsidyBatchAddParam);

    /**
     * 编辑补贴公示
     *
     * @author Ranger
     * @date  2024/01/31 14:12
     */
    void edit(KbSubsidyBatchEditParam kbSubsidyBatchEditParam);

    /**
     * 删除补贴公示
     *
     * @author Ranger
     * @date  2024/01/31 14:12
     */
    void delete(List<KbSubsidyBatchIdParam> kbSubsidyBatchIdParamList);

    /**
     * 获取补贴公示详情
     *
     * @author Ranger
     * @date  2024/01/31 14:12
     */
    KbSubsidyBatch detail(KbSubsidyBatchIdParam kbSubsidyBatchIdParam);

    /**
     * 获取补贴公示详情
     *
     * @author Ranger
     * @date  2024/01/31 14:12
     **/
    KbSubsidyBatch queryEntity(String id);

    void downloadImportSubsidyTemplate(HttpServletResponse response) throws IOException;
}
