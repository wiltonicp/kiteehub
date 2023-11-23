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
package com.kiteehub.dev.modular.dict.controller;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import com.kiteehub.dev.modular.dict.entity.DevDict;
import com.kiteehub.dev.modular.dict.param.*;
import com.kiteehub.dev.modular.dict.service.DevDictService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 字典控制器
 *
 * @author xuyuxiang
 * @date 2022/6/21 14:58
 **/
@Api(tags = "字典控制器")
@ApiSupport(author = "KITEE_HUB_TEAM", order = 2)
@RestController
@Validated
public class DevDictController {

    @Resource
    private DevDictService devDictService;

    /**
     * 获取字典分页
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取字典分页")
    @GetMapping("/dev/dict/page")
    public CommonResult<Page<DevDict>> page(DevDictPageParam devDictPageParam) {
        return CommonResult.data(devDictService.page(devDictPageParam));
    }

    /**
     * 获取字典列表
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("获取字典列表")
    @GetMapping("/dev/dict/list")
    public CommonResult<List<DevDict>> list(DevDictListParam devDictListParam) {
        return CommonResult.data(devDictService.list(devDictListParam));
    }

    /**
     * 获取字典树
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("获取字典树")
    @GetMapping("/dev/dict/tree")
    public CommonResult<List<Tree<String>>> tree(DevDictTreeParam devDictTreeParam) {
        return CommonResult.data(devDictService.tree(devDictTreeParam));
    }

    /**
     * 添加字典
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("添加字典")
    @CommonLog("添加字典")
    @PostMapping("/dev/dict/add")
    public CommonResult<String> add(@RequestBody @Valid DevDictAddParam devDictAddParam) {
        devDictService.add(devDictAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑字典
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("编辑字典")
    @CommonLog("编辑字典")
    @PostMapping("/dev/dict/edit")
    public CommonResult<String> edit(@RequestBody @Valid DevDictEditParam devDictEditParam) {
        devDictService.edit(devDictEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除字典
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("删除字典")
    @CommonLog("删除字典")
    @PostMapping("/dev/dict/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                           CommonValidList<DevDictIdParam> devDictIdParamList) {
        devDictService.delete(devDictIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取字典详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("获取字典详情")
    @GetMapping("/dev/dict/detail")
    public CommonResult<DevDict> detail(@Valid DevDictIdParam devDictIdParam) {
        return CommonResult.data(devDictService.detail(devDictIdParam));
    }
}
