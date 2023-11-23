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
package com.kiteehub.mobile.modular.resource.controller;

import cn.hutool.core.lang.tree.Tree;
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
import com.kiteehub.mobile.modular.resource.entity.MobileMenu;
import com.kiteehub.mobile.modular.resource.entity.MobileModule;
import com.kiteehub.mobile.modular.resource.param.menu.*;
import com.kiteehub.mobile.modular.resource.service.MobileMenuService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 移动端菜单控制器
 *
 * @author yubaoshan
 * @date  2023/01/28 22:42
 */
@Api(tags = "移动端菜单控制器")
@ApiSupport(author = "KITEE_HUB_TEAM", order = 1)
@RestController
@Validated
public class MobileMenuController {

    @Resource
    private MobileMenuService mobileMenuService;

    /**
     * 获取移动端菜单tree
     *
     * @author yubaoshan
     * @date  2023/01/28 22:42
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取移动端菜单tree")
    @GetMapping("/mobile/menu/tree")
    public CommonResult<List<Tree<String>>> tree(MobileMenuTreeParam mobileMenuTreeParam) {
        return CommonResult.data(mobileMenuService.tree(mobileMenuTreeParam));
    }

    /**
     * 添加移动端菜单
     *
     * @author yubaoshan
     * @date  2023/01/28 22:42
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加移动端菜单")
    @CommonLog("添加移动端菜单")
    @PostMapping("/mobile/menu/add")
    public CommonResult<String> add(@RequestBody @Valid MobileMenuAddParam mobileMenuAddParam) {
        mobileMenuService.add(mobileMenuAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑移动端菜单
     *
     * @author yubaoshan
     * @date  2023/01/28 22:42
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑移动端菜单")
    @CommonLog("编辑移动端菜单")
    @PostMapping("/mobile/menu/edit")
    public CommonResult<String> edit(@RequestBody @Valid MobileMenuEditParam mobileMenuEditParam) {
        mobileMenuService.edit(mobileMenuEditParam);
        return CommonResult.ok();
    }

    /**
     * 更改移动端菜单所属模块
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("更改移动端菜单所属模块")
    @CommonLog("更改移动端菜单所属模块")
    @PostMapping("/mobile/menu/changeModule")
    public CommonResult<String> changeModule(@RequestBody @Valid MobileMenuChangeModuleParam mobileMenuChangeModuleParam) {
        mobileMenuService.changeModule(mobileMenuChangeModuleParam);
        return CommonResult.ok();
    }

    /**
     * 删除移动端菜单
     *
     * @author yubaoshan
     * @date  2023/01/28 22:42
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除移动端菜单")
    @CommonLog("删除移动端菜单")
    @PostMapping("/mobile/menu/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<MobileMenuIdParam> mobileMenuIdParamList) {
        mobileMenuService.delete(mobileMenuIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取移动端菜单详情
     *
     * @author yubaoshan
     * @date  2023/01/28 22:42
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取移动端菜单详情")
    @GetMapping("/mobile/menu/detail")
    public CommonResult<MobileMenu> detail(@Valid MobileMenuIdParam mobileMenuIdParam) {
        return CommonResult.data(mobileMenuService.detail(mobileMenuIdParam));
    }

    /* ====菜单部分所需要用到的选择器==== */

    /**
     * 获取模块选择器
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取模块选择器")
    @GetMapping("/mobile/menu/moduleSelector")
    public CommonResult<List<MobileModule>> moduleSelector(MobileMenuSelectorModuleParam mobileMenuSelectorModuleParam) {
        return CommonResult.data(mobileMenuService.moduleSelector(mobileMenuSelectorModuleParam));
    }

    /**
     * 获取菜单树选择器
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("获取菜单树选择器")
    @GetMapping("/mobile/menu/menuTreeSelector")
    public CommonResult<List<Tree<String>>> menuTreeSelector(MobileMenuSelectorMenuParam mobileMenuSelectorMenuParam) {
        return CommonResult.data(mobileMenuService.menuTreeSelector(mobileMenuSelectorMenuParam));
    }
}
