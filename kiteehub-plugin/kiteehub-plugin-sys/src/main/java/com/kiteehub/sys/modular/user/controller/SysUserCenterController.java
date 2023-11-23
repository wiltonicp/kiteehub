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

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.kiteehub.common.annotation.CommonLog;
import com.kiteehub.common.pojo.CommonResult;
import com.kiteehub.sys.modular.org.entity.SysOrg;
import com.kiteehub.sys.modular.position.entity.SysPosition;
import com.kiteehub.sys.modular.role.entity.SysRole;
import com.kiteehub.sys.modular.user.entity.SysUser;
import com.kiteehub.sys.modular.user.param.*;
import com.kiteehub.sys.modular.user.result.SysUserMessageDetailResult;
import com.kiteehub.sys.modular.user.result.SysUserMessageResult;
import com.kiteehub.sys.modular.user.result.SysUserPicValidCodeResult;
import com.kiteehub.sys.modular.user.result.SysUserPositionResult;
import com.kiteehub.sys.modular.user.service.SysUserService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 用户个人控制器
 *
 * @author xuyuxiang
 * @date 2022/4/22 9:34
 **/
@Api(tags = "用户个人控制器")
@ApiSupport(author = "KITEE_HUB_TEAM", order = 10)
@RestController
@Validated
public class SysUserCenterController {

    @Resource
    private SysUserService sysUserService;

    /**
     * 获取图片验证码
     *
     * @author xuyuxiang
     * @date 2022/7/8 9:26
     **/
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取图片验证码")
    @GetMapping("/sys/userCenter/getPicCaptcha")
    public CommonResult<SysUserPicValidCodeResult> getPicCaptcha() {
        return CommonResult.data(sysUserService.getPicCaptcha());
    }

    /**
     * 找回密码获取手机验证码
     *
     * @author xuyuxiang
     * @date 2021/10/13 14:01
     **/
    @ApiOperationSupport(order = 2)
    @ApiOperation("找回密码获取手机验证码")
    @GetMapping("/sys/userCenter/findPasswordGetPhoneValidCode")
    public CommonResult<String> findPasswordGetPhoneValidCode(@Valid SysUserGetPhoneValidCodeParam sysUserGetPhoneValidCodeParam) {
        return CommonResult.data(sysUserService.findPasswordGetPhoneValidCode(sysUserGetPhoneValidCodeParam));
    }

    /**
     * 找回密码获取邮箱验证码
     *
     * @author xuyuxiang
     * @date 2021/10/13 14:01
     **/
    @ApiOperationSupport(order = 3)
    @ApiOperation("找回密码获取邮箱验证码")
    @GetMapping("/sys/userCenter/findPasswordGetEmailValidCode")
    public CommonResult<String> findPasswordGetEmailValidCode(@Valid SysUserGetEmailValidCodeParam sysUserGetEmailValidCodeParam) {
        return CommonResult.data(sysUserService.findPasswordGetEmailValidCode(sysUserGetEmailValidCodeParam));
    }

    /**
     * 通过手机号找回用户密码
     *
     * @author xuyuxiang
     * @date 2021/10/13 14:01
     **/
    @ApiOperationSupport(order = 4)
    @ApiOperation("通过手机号找回用户密码")
    @CommonLog("通过手机号找回用户密码")
    @PostMapping("/sys/userCenter/findPasswordByPhone")
    public CommonResult<String> findPasswordByPhone(@RequestBody @Valid SysUserFindPwdByPhoneParam sysUserFindPwdByPhoneParam) {
        sysUserService.findPasswordByPhone(sysUserFindPwdByPhoneParam);
        return CommonResult.ok();
    }

    /**
     * 通过邮箱找回用户密码
     *
     * @author xuyuxiang
     * @date 2021/10/13 14:01
     **/
    @ApiOperationSupport(order = 5)
    @ApiOperation("通过邮箱找回用户密码")
    @CommonLog("通过邮箱找回用户密码")
    @PostMapping("/sys/userCenter/findPasswordByEmail")
    public CommonResult<String> findPasswordByEmail(@RequestBody @Valid SysUserFindPwdByEmailParam sysUserFindPwdByEmailParam) {
        sysUserService.findPasswordByEmail(sysUserFindPwdByEmailParam);
        return CommonResult.ok();
    }

    /**
     * 修改用户密码
     *
     * @author xuyuxiang
     * @date 2021/10/13 14:01
     **/
    @ApiOperationSupport(order = 6)
    @ApiOperation("修改用户密码")
    @CommonLog("修改用户密码")
    @PostMapping("/sys/userCenter/updatePassword")
    public CommonResult<String> updatePassword(@RequestBody @Valid SysUserUpdatePwdParam sysUserUpdatePwdParam) {
        sysUserService.updatePassword(sysUserUpdatePwdParam);
        return CommonResult.ok();
    }

    /**
     * 修改用户头像
     *
     * @author xuyuxiang
     * @date 2021/10/13 14:01
     **/
    @ApiOperationSupport(order = 7)
    @ApiOperation("修改用户头像")
    @CommonLog("修改用户头像")
    @PostMapping("/sys/userCenter/updateAvatar")
    public CommonResult<String> updateAvatar(@RequestPart("file") @ApiParam(value="文件", required = true) MultipartFile file) {
        return CommonResult.data(sysUserService.updateAvatar(file));
    }

    /**
     * 修改用户签名图片
     *
     * @author xuyuxiang
     * @date 2021/10/13 14:01
     **/
    @ApiOperationSupport(order = 8)
    @ApiOperation("修改用户签名图片")
    @CommonLog("修改用户签名图片")
    @PostMapping("/sys/userCenter/updateSignature")
    public CommonResult<String> updateSignature(@RequestBody @Valid SysUserSignatureParam sysUserSignatureParam) {
        sysUserService.updateSignature(sysUserSignatureParam);
        return CommonResult.ok();
    }

    /**
     * 获取登录用户的菜单（B端、PC端菜单）
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 9)
    @ApiOperation("获取登录用户PC端菜单")
    @GetMapping("/sys/userCenter/loginMenu")
    public CommonResult<List<Tree<String>>> loginMenu() {
        SysUserIdParam sysUserIdParam = new SysUserIdParam();
        sysUserIdParam.setId(StpUtil.getLoginIdAsString());
        return CommonResult.data(sysUserService.ownMenu(sysUserIdParam));
    }

    /**
     * 获取登录用户的菜单（B端、移动端菜单）
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 10)
    @ApiOperation("获取登录用户移动端菜单")
    @GetMapping("/sys/userCenter/loginMobileMenu")
    public CommonResult<List<Tree<String>>> loginMobileMenu() {
        SysUserIdParam sysUserIdParam = new SysUserIdParam();
        sysUserIdParam.setId(StpUtil.getLoginIdAsString());
        return CommonResult.data(sysUserService.ownMobileMenu(sysUserIdParam));
    }

    /**
     * 获取登录用户组织树
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 11)
    @ApiOperation("获取登录用户组织树")
    @GetMapping("/sys/userCenter/loginOrgTree")
    public CommonResult<List<Tree<String>>> loginOrgTree() {
        SysUserIdParam sysUserIdParam = new SysUserIdParam();
        sysUserIdParam.setId(StpUtil.getLoginIdAsString());
        return CommonResult.data(sysUserService.loginOrgTree(sysUserIdParam));
    }

    /**
     * 获取登录用户的职位信息
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 12)
    @ApiOperation("获取登录用户的职位信息")
    @GetMapping("/sys/userCenter/loginPositionInfo")
    public CommonResult<List<SysUserPositionResult>> loginPositionInfo() {
        SysUserIdParam sysUserIdParam = new SysUserIdParam();
        sysUserIdParam.setId(StpUtil.getLoginIdAsString());
        return CommonResult.data(sysUserService.loginPositionInfo(sysUserIdParam));
    }

    /**
     * 编辑个人信息
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 13)
    @ApiOperation("编辑个人信息")
    @CommonLog("编辑个人信息")
    @PostMapping("/sys/userCenter/updateUserInfo")
    public CommonResult<String> updateUserInfo(@RequestBody @Valid SysUserUpdateInfoParam sysUserUpdateInfoParam) {
        sysUserService.updateUserInfo(sysUserUpdateInfoParam);
        return CommonResult.ok();
    }

    /**
     * 编辑个人工作台
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 14)
    @ApiOperation("编辑个人工作台")
    @CommonLog("编辑个人工作台")
    @PostMapping("/sys/userCenter/updateUserWorkbench")
    public CommonResult<String> updateUserWorkbench(@RequestBody @Valid SysUserUpdateWorkbenchParam sysUserUpdateWorkbenchParam) {
        sysUserService.updateUserWorkbench(sysUserUpdateWorkbenchParam);
        return CommonResult.ok();
    }

    /**
     * 获取登录用户的工作台
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 15)
    @ApiOperation("获取登录用户的工作台")
    @GetMapping("/sys/userCenter/loginWorkbench")
    public CommonResult<String> loginWorkbench() {
        SysUserIdParam sysUserIdParam = new SysUserIdParam();
        sysUserIdParam.setId(StpUtil.getLoginIdAsString());
        return CommonResult.data(sysUserService.loginWorkbench(sysUserIdParam));
    }

    /**
     * 获取登录用户的站内信分页
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 16)
    @ApiOperation("获取登录用户的站内信分页")
    @GetMapping("/sys/userCenter/loginUnreadMessagePage")
    public CommonResult<Page<SysUserMessageResult>> loginMessagePage(SysUserMessagePageParam sysUserMessagePageParam) {
        return CommonResult.data(sysUserService.loginMessagePage(sysUserMessagePageParam));
    }

    /**
     * 读取登录用户站内信详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 17)
    @ApiOperation("读取登录用户站内信详情")
    @GetMapping("/sys/userCenter/loginUnreadMessageDetail")
    public CommonResult<SysUserMessageDetailResult> loginMessageDetail(@Valid SysUserMessageIdParam sysUserMessageIdParam) {
        return CommonResult.data(sysUserService.loginMessageDetail(sysUserMessageIdParam));
    }

    /**
     * 根据id集合获取组织集合
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 18)
    @ApiOperation("根据id集合获取组织集合")
    @PostMapping("/sys/userCenter/getOrgListByIdList")
    public CommonResult<List<SysOrg>> getOrgListByIdList(@RequestBody @Valid SysUserIdListParam sysUserIdListParam) {
        return CommonResult.data(sysUserService.getOrgListByIdList(sysUserIdListParam));
    }

    /**
     * 根据id集合获取用户集合
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 19)
    @ApiOperation("根据id集合获取用户集合")
    @PostMapping("/sys/userCenter/getUserListByIdList")
    public CommonResult<List<SysUser>> getUserListByIdList(@RequestBody @Valid SysUserIdListParam sysUserIdListParam) {
        return CommonResult.data(sysUserService.getUserListByIdList(sysUserIdListParam));
    }

    /**
     * 根据id集合获取职位集合
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 20)
    @ApiOperation("根据id集合获取职位集合")
    @PostMapping("/sys/userCenter/getPositionListByIdList")
    public CommonResult<List<SysPosition>> getPositionListByIdList(@RequestBody @Valid SysUserIdListParam sysUserIdListParam) {
        return CommonResult.data(sysUserService.getPositionListByIdList(sysUserIdListParam));
    }

    /**
     * 根据id集合获取角色集合
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 21)
    @ApiOperation("根据id集合获取角色集合")
    @PostMapping("/sys/userCenter/getRoleListByIdList")
    public CommonResult<List<SysRole>> getRoleListByIdList(@RequestBody @Valid SysUserIdListParam sysUserIdListParam) {
        return CommonResult.data(sysUserService.getRoleListByIdList(sysUserIdListParam));
    }
}
