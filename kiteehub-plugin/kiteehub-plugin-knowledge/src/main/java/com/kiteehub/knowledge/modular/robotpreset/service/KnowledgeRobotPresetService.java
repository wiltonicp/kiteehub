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
package com.kiteehub.knowledge.modular.robotpreset.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kiteehub.knowledge.modular.robotpreset.entity.KnowledgeRobotPreset;
import com.kiteehub.knowledge.modular.robotpreset.param.KnowledgeRobotPresetAddParam;
import com.kiteehub.knowledge.modular.robotpreset.param.KnowledgeRobotPresetEditParam;
import com.kiteehub.knowledge.modular.robotpreset.param.KnowledgeRobotPresetIdParam;
import com.kiteehub.knowledge.modular.robotpreset.param.KnowledgeRobotPresetPageParam;

import java.util.List;

/**
 * 机器人预设问答Service接口
 *
 * @author Ranger
 * @date  2024/02/02 10:39
 **/
public interface KnowledgeRobotPresetService extends IService<KnowledgeRobotPreset> {

    /**
     * 获取机器人预设问答分页
     *
     * @author Ranger
     * @date  2024/02/02 10:39
     */
    Page<KnowledgeRobotPreset> page(KnowledgeRobotPresetPageParam knowledgeRobotPresetPageParam);

    /**
     * 添加机器人预设问答
     *
     * @author Ranger
     * @date  2024/02/02 10:39
     */
    void add(KnowledgeRobotPresetAddParam knowledgeRobotPresetAddParam);

    /**
     * 编辑机器人预设问答
     *
     * @author Ranger
     * @date  2024/02/02 10:39
     */
    void edit(KnowledgeRobotPresetEditParam knowledgeRobotPresetEditParam);

    /**
     * 删除机器人预设问答
     *
     * @author Ranger
     * @date  2024/02/02 10:39
     */
    void delete(List<KnowledgeRobotPresetIdParam> knowledgeRobotPresetIdParamList);

    /**
     * 获取机器人预设问答详情
     *
     * @author Ranger
     * @date  2024/02/02 10:39
     */
    KnowledgeRobotPreset detail(KnowledgeRobotPresetIdParam knowledgeRobotPresetIdParam);

    /**
     * 获取机器人预设问答详情
     *
     * @author Ranger
     * @date  2024/02/02 10:39
     **/
    KnowledgeRobotPreset queryEntity(String id);
}
