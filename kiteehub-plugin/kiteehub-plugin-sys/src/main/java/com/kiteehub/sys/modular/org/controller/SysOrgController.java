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
package com.kiteehub.sys.modular.org.controller;

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
import com.kiteehub.sys.modular.org.entity.SysOrg;
import com.kiteehub.sys.modular.org.param.*;
import com.kiteehub.sys.modular.org.service.SysOrgService;
import com.kiteehub.sys.modular.user.entity.SysUser;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 组织控制器
 *
 * @author xuyuxiang
 * @date 2022/4/24 19:55
 */
@Api(tags = "组织控制器")
@ApiSupport(author = "KITEE_HUB_TEAM", order = 1)
@RestController
@Validated
public class SysOrgController {

    @Resource
    private SysOrgService sysOrgService;

    /**
     * 获取组织分页
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取组织分页")
    @GetMapping("/sys/org/page")
    public CommonResult<Page<SysOrg>> page(SysOrgPageParam sysOrgPageParam) {
        return CommonResult.data(sysOrgService.page(sysOrgPageParam));
    }

    /**
     * 获取组织树
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("获取组织树")
    @GetMapping("/sys/org/tree")
    public CommonResult<List<Tree<String>>> tree() {
        return CommonResult.data(sysOrgService.tree());
    }

    /**
     * 添加组织
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("添加组织")
    @CommonLog("添加组织")
    @PostMapping("/sys/org/add")
    public CommonResult<String> add(@RequestBody @Valid SysOrgAddParam sysOrgAddParam) {
        sysOrgService.add(sysOrgAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑组织
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("编辑组织")
    @CommonLog("编辑组织")
    @PostMapping("/sys/org/edit")
    public CommonResult<String> edit(@RequestBody @Valid SysOrgEditParam sysOrgEditParam) {
        sysOrgService.edit(sysOrgEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除组织
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("删除组织")
    @CommonLog("删除组织")
    @PostMapping("/sys/org/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<SysOrgIdParam> sysOrgIdParamList) {
        sysOrgService.delete(sysOrgIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取组织详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取组织详情")
    @GetMapping("/sys/org/detail")
    public CommonResult<SysOrg> detail(@Valid SysOrgIdParam sysOrgIdParam) {
        return CommonResult.data(sysOrgService.detail(sysOrgIdParam));
    }

    /* ====组织部分所需要用到的选择器==== */

    /**
     * 获取组织树选择器
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("获取组织树选择器")
    @GetMapping("/sys/org/orgTreeSelector")
    public CommonResult<List<Tree<String>>> orgTreeSelector() {
        return CommonResult.data(sysOrgService.orgTreeSelector());
    }

    /**
     * 获取用户选择器
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 8)
    @ApiOperation("获取用户选择器")
    @GetMapping("/sys/org/userSelector")
    public CommonResult<Page<SysUser>> userSelector(SysOrgSelectorUserParam sysOrgSelectorUserParam) {
        return CommonResult.data(sysOrgService.userSelector(sysOrgSelectorUserParam));
    }
}
