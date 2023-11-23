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
package com.kiteehub.gen.modular.config.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.kiteehub.common.annotation.CommonLog;
import com.kiteehub.common.pojo.CommonResult;
import com.kiteehub.common.pojo.CommonValidList;
import com.kiteehub.gen.modular.config.entity.GenConfig;
import com.kiteehub.gen.modular.config.param.GenConfigEditParam;
import com.kiteehub.gen.modular.config.param.GenConfigIdParam;
import com.kiteehub.gen.modular.config.param.GenConfigListParam;
import com.kiteehub.gen.modular.config.service.GenConfigService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 代码生成详细配置控制器
 *
 * @author yubaoshan
 * @date 2022/10/25 22:33
 **/
@Api(tags = "代码生成详细配置控制器")
@ApiSupport(author = "KITEE_HUB_TEAM", order = 2)
@RestController
@Validated
public class GenConfigController {

    @Resource
    private GenConfigService genConfigService;

    /**
     * 获取代码生成详细配置分页
     *
     * @author yubaoshan
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取代码生成详细配置分页")
    @GetMapping("/gen/config/list")
    public CommonResult<List<GenConfig>> list(GenConfigListParam genConfigListParam) {
        return CommonResult.data(genConfigService.list(genConfigListParam));
    }

    /**
     * 编辑代码生成详细配置
     *
     * @author yubaoshan
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("编辑代码生成详细配置")
    @CommonLog("编辑代码生成详细配置")
    @PostMapping("/gen/config/edit")
    public CommonResult<String> edit(@RequestBody @Valid GenConfigEditParam genConfigEditParam) {
        genConfigService.edit(genConfigEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除代码生成详细配置
     *
     * @author yubaoshan
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("删除代码生成详细配置")
    @CommonLog("删除代码生成详细配置")
    @PostMapping("/gen/config/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                               CommonValidList<GenConfigIdParam> genConfigIdParamList) {
        genConfigService.delete(genConfigIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取代码生成详细配置详情
     *
     * @author yubaoshan
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("获取代码生成详细配置详情")
    @GetMapping("/gen/config/detail")
    public CommonResult<GenConfig> detail(@Valid GenConfigIdParam genConfigIdParam) {
        return CommonResult.data(genConfigService.detail(genConfigIdParam));
    }

    /**
     * 批量编辑代码生成详细配置
     *
     * @author yubaoshan
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("批量编辑代码生成详细配置")
    @CommonLog("批量编辑代码生成详细配置")
    @PostMapping("/gen/config/editBatch")
    public CommonResult<String> editBatch(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                      CommonValidList<GenConfigEditParam> genConfigEditParamList) {
        genConfigService.editBatch(genConfigEditParamList);
        return CommonResult.ok();
    }
}
