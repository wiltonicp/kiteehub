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
package com.kiteehub.knowledge.modular.robot.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kiteehub.common.pojo.CommonResult;
import com.kiteehub.knowledge.modular.robot.entity.KnowledgeRobot;
import com.kiteehub.knowledge.modular.robot.entity.KnowledgeRobotIndex;
import com.kiteehub.knowledge.modular.robot.service.KnowledgeRobotIndexService;
import com.kiteehub.knowledge.modular.robot.service.KnowledgeRobotService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Client端智能客服控制器
 *
 * @author Ranger
 * @date  2024/01/03 11:44
 */
@Api(tags = "Client端智能客服控制器")
@ApiSupport(author = "KITEEHUB_TEAM", order = 1)
@RestController
@Validated
public class KnowledgeRobotClientController {

    @Resource
    private KnowledgeRobotService knowledgeRobotService;
    @Resource
    private KnowledgeRobotIndexService knowledgeRobotIndexService;

    /**
     * 获取智能客服集合
     *
     * @author Ranger
     * @date  2024/01/03 11:44
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取智能客服集合")
    @GetMapping("/knowledge/client/robot/listByType")
    public CommonResult<List<JSONObject>> listAll(@NotBlank String personnelType) {
        QueryWrapper<KnowledgeRobot> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(KnowledgeRobot::getPersonnelType,personnelType);
        List<JSONObject> collect = knowledgeRobotService.list(queryWrapper).stream().map(x -> {
            List<KnowledgeRobotIndex> listIndex = knowledgeRobotIndexService.lambdaQuery()
                    .eq(KnowledgeRobotIndex::getRid, x.getId())
                    .list();
            return JSONUtil.createObj().set("value", x.getId())
                    .set("label", x.getName())
                    .set("kids", listIndex.stream().map(KnowledgeRobotIndex::getKid).collect(Collectors.toList()));
        }).collect(Collectors.toList());
        return CommonResult.data(collect);
    }
}
