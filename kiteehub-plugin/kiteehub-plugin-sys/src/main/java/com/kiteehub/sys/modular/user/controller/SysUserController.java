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
package com.kiteehub.sys.modular.user.controller;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.kiteehub.common.annotation.CommonLog;
import com.kiteehub.common.pojo.CommonResult;
import com.kiteehub.common.pojo.CommonValidList;
import com.kiteehub.sys.modular.org.entity.SysOrg;
import com.kiteehub.sys.modular.position.entity.SysPosition;
import com.kiteehub.sys.modular.role.entity.SysRole;
import com.kiteehub.sys.modular.user.entity.SysUser;
import com.kiteehub.sys.modular.user.param.*;
import com.kiteehub.sys.modular.user.result.SysUserOwnPermissionResult;
import com.kiteehub.sys.modular.user.result.SysUserOwnResourceResult;
import com.kiteehub.sys.modular.user.service.SysUserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.util.List;

/**
 * 用户控制器
 *
 * @author xuyuxiang
 * @date 2022/4/22 9:34
 **/
@Api(tags = "用户控制器")
@ApiSupport(author = "KITEE_HUB_TEAM", order = 9)
@RestController
@Validated
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    /**
     * 获取用户分页
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取用户分页")
    @GetMapping("/sys/user/page")
    public CommonResult<Page<SysUser>> page(SysUserPageParam sysUserPageParam) {
        return CommonResult.data(sysUserService.page(sysUserPageParam));
    }

    /**
     * 添加用户
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加用户")
    @CommonLog("添加用户")
    @PostMapping("/sys/user/add")
    public CommonResult<String> add(@RequestBody @Valid SysUserAddParam sysUserAddParam) {
        sysUserService.add(sysUserAddParam);
        return CommonResult.ok();
    }

    /**
     * 添加普通用户
     *
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加普通用户")
    @CommonLog("添加普通用户")
    @PostMapping("/sys/user/addOrdinary")
    public CommonResult<String> addOrdinary(@RequestBody SysUserAddParam sysUserAddParam) {
        sysUserAddParam.setDirectorId("1749996725492355074");
        sysUserAddParam.setIdCardType("身份证");
        sysUserAddParam.setOrgId("1543842934270394368");
        sysUserAddParam.setPositionId("1765624490102984706");
        sysUserAddParam.setPhone(sysUserAddParam.getAccount());
        sysUserService.add(sysUserAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑用户
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑用户")
    @CommonLog("编辑用户")
    @PostMapping("/sys/user/edit")
    public CommonResult<String> edit(@RequestBody @Valid SysUserEditParam sysUserEditParam) {
        sysUserService.edit(sysUserEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除用户
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除用户")
    @CommonLog("删除用户")
    @PostMapping("/sys/user/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<SysUserIdParam> sysUserIdParamList) {
        sysUserService.delete(sysUserIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取用户详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取用户详情")
    @GetMapping("/sys/user/detail")
    public CommonResult<SysUser> detail(@Valid SysUserIdParam sysUserIdParam) {
        return CommonResult.data(sysUserService.detail(sysUserIdParam));
    }

    /**
     * 禁用用户
     *
     * @author xuyuxiang
     * @date 2021/10/13 14:01
     **/
    @ApiOperationSupport(order = 6)
    @ApiOperation("禁用用户")
    @CommonLog("禁用用户")
    @PostMapping("/sys/user/disableUser")
    public CommonResult<String> disableUser(@RequestBody SysUserIdParam sysUserIdParam) {
        sysUserService.disableUser(sysUserIdParam);
        return CommonResult.ok();
    }

    /**
     * 启用用户
     *
     * @author xuyuxiang
     * @date 2021/10/13 14:01
     **/
    @ApiOperationSupport(order = 7)
    @ApiOperation("启用用户")
    @CommonLog("启用用户")
    @PostMapping("/sys/user/enableUser")
    public CommonResult<String> enableUser(@RequestBody @Valid SysUserIdParam sysUserIdParam) {
        sysUserService.enableUser(sysUserIdParam);
        return CommonResult.ok();
    }

    /**
     * 重置用户密码
     *
     * @author xuyuxiang
     * @date 2021/10/13 14:01
     **/
    @ApiOperationSupport(order = 8)
    @ApiOperation("重置用户密码")
    @CommonLog("重置用户密码")
    @PostMapping("/sys/user/resetPassword")
    public CommonResult<String> resetPassword(@RequestBody @Valid SysUserIdParam sysUserIdParam) {
        sysUserService.resetPassword(sysUserIdParam);
        return CommonResult.ok();
    }

    /**
     * 用户拥有角色
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 9)
    @ApiOperation("获取用户拥有角色")
    @GetMapping("/sys/user/ownRole")
    public CommonResult<List<String>> ownRole(@Valid SysUserIdParam sysUserIdParam) {
        return CommonResult.data(sysUserService.ownRole(sysUserIdParam));
    }

    /**
     * 给用户授权角色
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 10)
    @ApiOperation("给用户授权角色")
    @CommonLog("给用户授权角色")
    @PostMapping("/sys/user/grantRole")
    public CommonResult<String> grantRole(@RequestBody @Valid SysUserGrantRoleParam sysUserGrantRoleParam) {
        sysUserService.grantRole(sysUserGrantRoleParam);
        return CommonResult.ok();
    }

    /**
     * 获取用户拥有资源
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 11)
    @ApiOperation("获取用户拥有资源")
    @GetMapping("/sys/user/ownResource")
    public CommonResult<SysUserOwnResourceResult> ownResource(@Valid SysUserIdParam sysUserIdParam) {
        return CommonResult.data(sysUserService.ownResource(sysUserIdParam));
    }

    /**
     * 给用户授权资源
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 12)
    @ApiOperation("给用户授权资源")
    @CommonLog("给用户授权资源")
    @PostMapping("/sys/user/grantResource")
    public CommonResult<String> grantResource(@RequestBody @Valid SysUserGrantResourceParam sysUserGrantResourceParam) {
        sysUserService.grantResource(sysUserGrantResourceParam);
        return CommonResult.ok();
    }

    /**
     * 获取用户拥有权限
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 13)
    @ApiOperation("获取用户拥有权限")
    @GetMapping("/sys/user/ownPermission")
    public CommonResult<SysUserOwnPermissionResult> ownPermission(@Valid SysUserIdParam sysUserIdParam) {
        return CommonResult.data(sysUserService.ownPermission(sysUserIdParam));
    }

    /**
     * 给用户授权权限
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 14)
    @ApiOperation("给用户授权权限")
    @CommonLog("给用户授权权限")
    @PostMapping("/sys/user/grantPermission")
    public CommonResult<String> grantPermission(@RequestBody @Valid SysUserGrantPermissionParam sysUserGrantPermissionParam) {
        sysUserService.grantPermission(sysUserGrantPermissionParam);
        return CommonResult.ok();
    }

    /**
     * 下载用户导入模板
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 15)
    @ApiOperation("下载用户导入模板")
    @CommonLog("下载用户导入模板")
    @GetMapping(value = "/sys/user/downloadImportUserTemplate", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void downloadImportUserTemplate(HttpServletResponse response) throws IOException {
        sysUserService.downloadImportUserTemplate(response);
    }

    /**
     * 用户导入
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 16)
    @ApiOperation("用户导入")
    @CommonLog("用户导入")
    @PostMapping("/sys/user/import")
    public CommonResult<JSONObject> importUser(@RequestPart("file") @ApiParam(value="文件", required = true) MultipartFile file) {
        return CommonResult.data(sysUserService.importUser(file));
    }

    /**
     * 用户导出
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 17)
    @ApiOperation("用户导出")
    @CommonLog("用户导出")
    @GetMapping(value = "/sys/user/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void exportUser(SysUserExportParam sysUserExportParam, HttpServletResponse response) throws IOException {
        sysUserService.exportUser(sysUserExportParam, response);
    }

    /**
     * 按模板导出用户个人信息
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 18)
    @ApiOperation("导出用户个人信息")
    @CommonLog("导出用户个人信息")
    @GetMapping(value = "/sys/user/exportUserInfo", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void exportUserInfo(SysUserIdParam sysUserIdParam, HttpServletResponse response) throws IOException {
        sysUserService.exportUserInfo(sysUserIdParam, response);
    }

    /* ====用户部分所需要用到的选择器==== */

    /**
     * 获取组织树选择器
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 19)
    @ApiOperation("获取组织树选择器")
    @GetMapping("/sys/user/orgTreeSelector")
    public CommonResult<List<Tree<String>>> orgTreeSelector() {
        return CommonResult.data(sysUserService.orgTreeSelector());
    }

    /**
     * 获取组织列表选择器
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 20)
    @ApiOperation("获取组织列表选择器")
    @GetMapping("/sys/user/orgListSelector")
    public CommonResult<Page<SysOrg>> orgListSelector(SysUserSelectorOrgListParam sysUserSelectorOrgListParam) {
        return CommonResult.data(sysUserService.orgListSelector(sysUserSelectorOrgListParam));
    }

    /**
     * 获取职位选择器
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 21)
    @ApiOperation("获取职位选择器")
    @GetMapping("/sys/user/positionSelector")
    public CommonResult<Page<SysPosition>> positionSelector(SysUserSelectorPositionParam sysUserSelectorPositionParam) {
        return CommonResult.data(sysUserService.positionSelector(sysUserSelectorPositionParam));
    }

    /**
     * 获取角色选择器
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 22)
    @ApiOperation("获取角色选择器")
    @GetMapping("/sys/user/roleSelector")
    public CommonResult<Page<SysRole>> roleSelector(SysUserSelectorRoleParam sysUserSelectorRoleParam) {
        return CommonResult.data(sysUserService.roleSelector(sysUserSelectorRoleParam));
    }

    /**
     * 获取用户选择器
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 23)
    @ApiOperation("获取用户选择器")
    @GetMapping("/sys/user/userSelector")
    public CommonResult<Page<SysUser>> userSelector(SysUserSelectorUserParam sysUserSelectorUserParam) {
        return CommonResult.data(sysUserService.userSelector(sysUserSelectorUserParam));
    }
}
