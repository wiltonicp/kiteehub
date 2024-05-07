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
package com.kiteehub.knowledge.modular.robot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kiteehub.knowledge.modular.robot.entity.KnowledgeRobot;
import com.kiteehub.knowledge.modular.robot.param.KnowledgeRobotAddParam;
import com.kiteehub.knowledge.modular.robot.param.KnowledgeRobotEditParam;
import com.kiteehub.knowledge.modular.robot.param.KnowledgeRobotIdParam;
import com.kiteehub.knowledge.modular.robot.param.KnowledgeRobotPageParam;

import java.util.List;

/**
 * 智能客服Service接口
 *
 * @author Ranger
 * @date  2024/01/03 11:44
 **/
public interface KnowledgeRobotService extends IService<KnowledgeRobot> {

    /**
     * 获取智能客服分页
     *
     * @author Ranger
     * @date  2024/01/03 11:44
     */
    Page<KnowledgeRobot> page(KnowledgeRobotPageParam knowledgeRobotPageParam);

    /**
     * 添加智能客服
     *
     * @author Ranger
     * @date  2024/01/03 11:44
     */
    void add(KnowledgeRobotAddParam knowledgeRobotAddParam);

    /**
     * 编辑智能客服
     *
     * @author Ranger
     * @date  2024/01/03 11:44
     */
    void edit(KnowledgeRobotEditParam knowledgeRobotEditParam);

    /**
     * 删除智能客服
     *
     * @author Ranger
     * @date  2024/01/03 11:44
     */
    void delete(List<KnowledgeRobotIdParam> knowledgeRobotIdParamList);

    /**
     * 获取智能客服详情
     *
     * @author Ranger
     * @date  2024/01/03 11:44
     */
    KnowledgeRobot detail(KnowledgeRobotIdParam knowledgeRobotIdParam);

    /**
     * 获取智能客服详情
     *
     * @author Ranger
     * @date  2024/01/03 11:44
     **/
    KnowledgeRobot queryEntity(String id);
}
