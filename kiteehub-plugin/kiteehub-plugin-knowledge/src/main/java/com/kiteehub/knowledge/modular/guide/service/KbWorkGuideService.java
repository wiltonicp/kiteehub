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
package com.kiteehub.knowledge.modular.guide.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kiteehub.knowledge.modular.guide.entity.KbWorkGuide;
import com.kiteehub.knowledge.modular.guide.param.KbWorkGuideAddParam;
import com.kiteehub.knowledge.modular.guide.param.KbWorkGuideEditParam;
import com.kiteehub.knowledge.modular.guide.param.KbWorkGuideIdParam;
import com.kiteehub.knowledge.modular.guide.param.KbWorkGuidePageParam;

import java.util.List;

/**
 * 业务指南Service接口
 *
 * @author Ranger
 * @date  2024/01/29 14:15
 **/
public interface KbWorkGuideService extends IService<KbWorkGuide> {

    /**
     * 获取业务指南分页
     *
     * @author Ranger
     * @date  2024/01/29 14:15
     */
    Page<KbWorkGuide> page(KbWorkGuidePageParam kbWorkGuidePageParam);

    /**
     * 添加业务指南
     *
     * @author Ranger
     * @date  2024/01/29 14:15
     */
    void add(KbWorkGuideAddParam kbWorkGuideAddParam);

    /**
     * 编辑业务指南
     *
     * @author Ranger
     * @date  2024/01/29 14:15
     */
    void edit(KbWorkGuideEditParam kbWorkGuideEditParam);

    /**
     * 删除业务指南
     *
     * @author Ranger
     * @date  2024/01/29 14:15
     */
    void delete(List<KbWorkGuideIdParam> kbWorkGuideIdParamList);

    /**
     * 获取业务指南详情
     *
     * @author Ranger
     * @date  2024/01/29 14:15
     */
    KbWorkGuide detail(KbWorkGuideIdParam kbWorkGuideIdParam);

    /**
     * 获取业务指南详情
     *
     * @author Ranger
     * @date  2024/01/29 14:15
     **/
    KbWorkGuide queryEntity(String id);
}
