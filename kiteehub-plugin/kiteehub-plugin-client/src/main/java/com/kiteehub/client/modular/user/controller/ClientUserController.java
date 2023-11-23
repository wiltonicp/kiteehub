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
package com.kiteehub.client.modular.user.controller;

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
import com.kiteehub.client.modular.user.entity.ClientUser;
import com.kiteehub.client.modular.user.param.ClientUserAddParam;
import com.kiteehub.client.modular.user.param.ClientUserEditParam;
import com.kiteehub.client.modular.user.param.ClientUserIdParam;
import com.kiteehub.client.modular.user.param.ClientUserPageParam;
import com.kiteehub.client.modular.user.service.ClientUserService;
import com.kiteehub.common.annotation.CommonLog;
import com.kiteehub.common.pojo.CommonResult;
import com.kiteehub.common.pojo.CommonValidList;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * C端用户控制器
 *
 * @author xuyuxiang
 * @date 2022/4/22 9:34
 **/
@Api(tags = "C端用户控制器")
@ApiSupport(author = "KITEE_HUB_TEAM", order = 1)
@RestController
@Validated
public class ClientUserController {

    @Resource
    private ClientUserService clientUserService;

    /**
     * 获取C端用户分页
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取C端用户分页")
    @GetMapping("/client/user/page")
    public CommonResult<Page<ClientUser>> page(ClientUserPageParam clientUserPageParam) {
        return CommonResult.data(clientUserService.page(clientUserPageParam));
    }

    /**
     * 添加C端用户
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加C端用户")
    @CommonLog("添加C端用户")
    @PostMapping("/client/user/add")
    public CommonResult<String> add(@RequestBody @Valid ClientUserAddParam clientUserAddParam) {
        clientUserService.add(clientUserAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑C端用户
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑C端用户")
    @CommonLog("编辑C端用户")
    @PostMapping("/client/user/edit")
    public CommonResult<String> edit(@RequestBody @Valid ClientUserEditParam clientUserEditParam) {
        clientUserService.edit(clientUserEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除C端用户
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除C端用户")
    @CommonLog("删除C端用户")
    @PostMapping("/client/user/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<ClientUserIdParam> clientUserIdParamList) {
        clientUserService.delete(clientUserIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取C端用户详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取C端用户详情")
    @GetMapping("/client/user/detail")
    public CommonResult<ClientUser> detail(@Valid ClientUserIdParam clientUserIdParam) {
        return CommonResult.data(clientUserService.detail(clientUserIdParam));
    }
}
