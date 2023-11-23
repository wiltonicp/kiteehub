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
package com.kiteehub.biz.modular.user.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.kiteehub.biz.modular.org.entity.BizOrg;
import com.kiteehub.biz.modular.position.entity.BizPosition;
import com.kiteehub.biz.modular.user.entity.BizUser;
import com.kiteehub.biz.modular.user.param.*;
import com.kiteehub.biz.modular.user.result.BizUserRoleResult;
import com.kiteehub.biz.modular.user.service.BizUserService;
import com.kiteehub.common.annotation.CommonLog;
import com.kiteehub.common.pojo.CommonResult;
import com.kiteehub.common.pojo.CommonValidList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.util.List;

/**
 * 人员控制器
 *
 * @author xuyuxiang
 * @date 2022/4/22 9:34
 **/
@Api(tags = "人员控制器")
@ApiSupport(author = "KITEE_HUB_TEAM", order = 9)
@RestController
@Validated
public class BizUserController {

    @Resource
    private BizUserService bizUserService;

    /**
     * 获取人员分页
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取人员分页")
    @SaCheckPermission("/biz/user/page")
    @GetMapping("/biz/user/page")
    public CommonResult<Page<BizUser>> page(BizUserPageParam bizUserPageParam) {
        return CommonResult.data(bizUserService.page(bizUserPageParam));
    }

    /**
     * 添加人员
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加人员")
    @CommonLog("添加人员")
    @SaCheckPermission("/biz/user/add")
    @PostMapping("/biz/user/add")
    public CommonResult<String> add(@RequestBody @Valid BizUserAddParam bizUserAddParam) {
        bizUserService.add(bizUserAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑人员
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑人员")
    @CommonLog("编辑人员")
    @SaCheckPermission("/biz/user/edit")
    @PostMapping("/biz/user/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizUserEditParam bizUserEditParam) {
        bizUserService.edit(bizUserEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除人员
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除人员")
    @CommonLog("删除人员")
    @SaCheckPermission("/biz/user/delete")
    @PostMapping("/biz/user/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizUserIdParam> bizUserIdParamList) {
        bizUserService.delete(bizUserIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取人员详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取人员详情")
    @SaCheckPermission("/biz/user/detail")
    @GetMapping("/biz/user/detail")
    public CommonResult<BizUser> detail(@Valid BizUserIdParam bizUserIdParam) {
        return CommonResult.data(bizUserService.detail(bizUserIdParam));
    }

    /**
     * 禁用人员
     *
     * @author xuyuxiang
     * @date 2021/10/13 14:01
     **/
    @ApiOperationSupport(order = 6)
    @ApiOperation("禁用人员")
    @CommonLog("禁用人员")
    @SaCheckPermission("/biz/user/disableUser")
    @PostMapping("/biz/user/disableUser")
    public CommonResult<String> disableUser(@RequestBody BizUserIdParam bizUserIdParam) {
        bizUserService.disableUser(bizUserIdParam);
        return CommonResult.ok();
    }

    /**
     * 启用人员
     *
     * @author xuyuxiang
     * @date 2021/10/13 14:01
     **/
    @ApiOperationSupport(order = 7)
    @ApiOperation("启用人员")
    @CommonLog("启用人员")
    @SaCheckPermission("/biz/user/enableUser")
    @PostMapping("/biz/user/enableUser")
    public CommonResult<String> enableUser(@RequestBody @Valid BizUserIdParam bizUserIdParam) {
        bizUserService.enableUser(bizUserIdParam);
        return CommonResult.ok();
    }

    /**
     * 重置人员密码
     *
     * @author xuyuxiang
     * @date 2021/10/13 14:01
     **/
    @ApiOperationSupport(order = 8)
    @ApiOperation("重置人员密码")
    @CommonLog("重置人员密码")
    @SaCheckPermission("/biz/user/resetPassword")
    @PostMapping("/biz/user/resetPassword")
    public CommonResult<String> resetPassword(@RequestBody @Valid BizUserIdParam bizUserIdParam) {
        bizUserService.resetPassword(bizUserIdParam);
        return CommonResult.ok();
    }

    /**
     * 人员拥有角色
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 9)
    @ApiOperation("获取人员拥有角色")
    @SaCheckPermission("/biz/user/ownRole")
    @GetMapping("/biz/user/ownRole")
    public CommonResult<List<String>> ownRole(@Valid BizUserIdParam bizUserIdParam) {
        return CommonResult.data(bizUserService.ownRole(bizUserIdParam));
    }

    /**
     * 给人员授权角色
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 10)
    @ApiOperation("给人员授权角色")
    @CommonLog("给人员授权角色")
    @SaCheckPermission("/biz/user/grantRole")
    @PostMapping("/biz/user/grantRole")
    public CommonResult<String> grantRole(@RequestBody @Valid BizUserGrantRoleParam bizUserGrantRoleParam) {
        bizUserService.grantRole(bizUserGrantRoleParam);
        return CommonResult.ok();
    }

    /**
     * 人员导出
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 11)
    @ApiOperation("人员导出")
    @CommonLog("人员导出")
    @SaCheckPermission("/biz/user/export")
    @GetMapping(value = "/biz/user/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void exportUser(BizUserExportParam bizUserExportParam, HttpServletResponse response) throws IOException {
        bizUserService.exportUser(bizUserExportParam, response);
    }

    /**
     * 按模板导出人员个人信息
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 12)
    @ApiOperation("导出人员个人信息")
    @CommonLog("导出人员个人信息")
    @SaCheckPermission("/biz/user/exportUserInfo")
    @GetMapping(value = "/biz/user/exportUserInfo", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void exportUserInfo(BizUserIdParam bizUserIdParam, HttpServletResponse response) throws IOException {
        bizUserService.exportUserInfo(bizUserIdParam, response);
    }

    /* ====人员部分所需要用到的选择器==== */

    /**
     * 获取机构树选择器
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 13)
    @ApiOperation("获取机构树选择器")
    @SaCheckPermission("/biz/user/orgTreeSelector")
    @GetMapping("/biz/user/orgTreeSelector")
    public CommonResult<List<Tree<String>>> orgTreeSelector() {
        return CommonResult.data(bizUserService.orgTreeSelector());
    }

    /**
     * 获取机构列表选择器
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 14)
    @ApiOperation("获取机构列表选择器")
    @SaCheckPermission("/biz/user/orgListSelector")
    @GetMapping("/biz/user/orgListSelector")
    public CommonResult<Page<BizOrg>> orgListSelector(BizUserSelectorOrgListParam bizUserSelectorOrgListParam) {
        return CommonResult.data(bizUserService.orgListSelector(bizUserSelectorOrgListParam));
    }

    /**
     * 获取岗位选择器
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 15)
    @ApiOperation("获取岗位选择器")
    @SaCheckPermission("/biz/user/positionSelector")
    @GetMapping("/biz/user/positionSelector")
    public CommonResult<Page<BizPosition>> positionSelector(BizUserSelectorPositionParam bizUserSelectorPositionParam) {
        return CommonResult.data(bizUserService.positionSelector(bizUserSelectorPositionParam));
    }

    /**
     * 获取角色选择器
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 16)
    @ApiOperation("获取角色选择器")
    @SaCheckPermission("/biz/user/roleSelector")
    @GetMapping("/biz/user/roleSelector")
    public CommonResult<Page<BizUserRoleResult>> roleSelector(BizUserSelectorRoleParam bizUserSelectorRoleParam) {
        return CommonResult.data(bizUserService.roleSelector(bizUserSelectorRoleParam));
    }

    /**
     * 获取人员选择器
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 17)
    @ApiOperation("获取人员选择器")
    @SaCheckPermission("/biz/user/userSelector")
    @GetMapping("/biz/user/userSelector")
    public CommonResult<Page<BizUser>> userSelector(BizUserSelectorUserParam bizUserSelectorUserParam) {
        return CommonResult.data(bizUserService.userSelector(bizUserSelectorUserParam));
    }
}
