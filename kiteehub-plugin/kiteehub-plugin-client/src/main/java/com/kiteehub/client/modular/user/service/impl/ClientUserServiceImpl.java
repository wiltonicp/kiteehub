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
package com.kiteehub.client.modular.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.PhoneUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fhs.trans.service.impl.TransService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kiteehub.client.modular.relation.service.ClientRelationService;
import com.kiteehub.client.modular.user.entity.ClientUser;
import com.kiteehub.client.modular.user.enums.ClientUserStatusEnum;
import com.kiteehub.client.modular.user.mapper.ClientUserMapper;
import com.kiteehub.client.modular.user.param.ClientUserAddParam;
import com.kiteehub.client.modular.user.param.ClientUserEditParam;
import com.kiteehub.client.modular.user.param.ClientUserIdParam;
import com.kiteehub.client.modular.user.param.ClientUserPageParam;
import com.kiteehub.client.modular.user.result.ClientLoginUser;
import com.kiteehub.client.modular.user.service.ClientUserService;
import com.kiteehub.common.enums.CommonSortOrderEnum;
import com.kiteehub.common.exception.CommonException;
import com.kiteehub.common.page.CommonPageRequest;
import com.kiteehub.common.util.*;
import com.kiteehub.dev.api.DevConfigApi;

import javax.annotation.Resource;
import java.util.List;

/**
 * C端用户Service接口实现类
 *
 * @author xuyuxiang
 * @date 2022/2/23 18:43
 **/
@Service
public class ClientUserServiceImpl extends ServiceImpl<ClientUserMapper, ClientUser> implements ClientUserService {

    private static final String KITEE_SYS_DEFAULT_PASSWORD_KEY = "KITEE_SYS_DEFAULT_PASSWORD";

    @Resource
    private DevConfigApi devConfigApi;

    @Resource
    private TransService transService;

    @Resource
    private ClientRelationService clientRelationService;

    @Override
    public ClientLoginUser getUserById(String id) {
        ClientUser clientUser = this.getById(id);
        if(ObjectUtil.isNotEmpty(clientUser)) {
            transService.transOne(clientUser);
            return BeanUtil.copyProperties(clientUser, ClientLoginUser.class);
        }
        return null;
    }

    @Override
    public ClientLoginUser getUserByAccount(String account) {
        ClientUser clientUser = this.getOne(new LambdaQueryWrapper<ClientUser>().eq(ClientUser::getAccount, account));
        if(ObjectUtil.isNotEmpty(clientUser)) {
            transService.transOne(clientUser);
            return BeanUtil.copyProperties(clientUser, ClientLoginUser.class);
        }
        return null;
    }

    @Override
    public ClientLoginUser getUserByPhone(String phone) {
        ClientUser clientUser = this.getOne(new LambdaQueryWrapper<ClientUser>().eq(ClientUser::getPhone, CommonCryptogramUtil.doSm4CbcEncrypt(phone)));
        if(ObjectUtil.isNotEmpty(clientUser)) {
            transService.transOne(clientUser);
            return BeanUtil.copyProperties(clientUser, ClientLoginUser.class);
        }
        return null;
    }

    @Override
    public ClientLoginUser getUserByEmail(String email) {
        ClientUser clientUser = this.getOne(new LambdaQueryWrapper<ClientUser>().eq(ClientUser::getEmail, email));
        if(ObjectUtil.isNotEmpty(clientUser)) {
            transService.transOne(clientUser);
            return BeanUtil.copyProperties(clientUser, ClientLoginUser.class);
        }
        return null;
    }

    @Override
    public Page<ClientUser> page(ClientUserPageParam clientUserPageParam) {
        QueryWrapper<ClientUser> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(clientUserPageParam.getSearchKey())) {
            queryWrapper.lambda().and(q -> q.like(ClientUser::getName, clientUserPageParam.getSearchKey())
                    .or().like(ClientUser::getAccount, clientUserPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(clientUserPageParam.getSortField(), clientUserPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(clientUserPageParam.getSortOrder());
            queryWrapper.orderBy(true, clientUserPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(clientUserPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(ClientUser::getSortCode);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ClientUserAddParam clientUserAddParam) {
        checkParam(clientUserAddParam);
        ClientUser clientUser = BeanUtil.toBean(clientUserAddParam, ClientUser.class);
        if(ObjectUtil.isEmpty(clientUser.getAvatar())) {
            // 设置默认头像
            clientUser.setAvatar(CommonAvatarUtil.generateImg(clientUser.getName()));
        }
        // 设置默认密码
        clientUser.setPassword(CommonCryptogramUtil.doHashValue(devConfigApi.getValueByKey(KITEE_SYS_DEFAULT_PASSWORD_KEY)));
        // 设置状态
        clientUser.setUserStatus(ClientUserStatusEnum.ENABLE.getValue());
        this.save(clientUser);
    }

    private void checkParam(ClientUserAddParam clientUserAddParam) {
        if (this.count(new LambdaQueryWrapper<ClientUser>()
                .eq(ClientUser::getAccount, clientUserAddParam.getAccount())) > 0) {
            throw new CommonException("存在重复的账号，账号为：{}", clientUserAddParam.getAccount());
        }
        if(ObjectUtil.isNotEmpty(clientUserAddParam.getPhone())) {
            if(!PhoneUtil.isMobile(clientUserAddParam.getPhone())) {
                throw new CommonException("手机号码：{}格式错误", clientUserAddParam.getPhone());
            }
            if (this.count(new LambdaQueryWrapper<ClientUser>()
                    .eq(ClientUser::getPhone, CommonCryptogramUtil.doSm4CbcEncrypt(clientUserAddParam.getPhone()))) > 0) {
                throw new CommonException("存在重复的手机号，手机号为：{}", clientUserAddParam.getPhone());
            }
        }
        if(ObjectUtil.isNotEmpty(clientUserAddParam.getEmail())) {
            if(!CommonEmailUtil.isEmail(clientUserAddParam.getEmail())) {
                throw new CommonException("邮箱：{}格式错误", clientUserAddParam.getEmail());
            }
            if (this.count(new LambdaQueryWrapper<ClientUser>()
                    .eq(ClientUser::getEmail, clientUserAddParam.getEmail())) > 0) {
                throw new CommonException("存在重复的邮箱，邮箱为：{}", clientUserAddParam.getEmail());
            }
        }
    }

    @Override
    public void edit(ClientUserEditParam clientUserEditParam) {
        ClientUser clientUser = this.queryEntity(clientUserEditParam.getId());
        checkParam(clientUserEditParam);
        BeanUtil.copyProperties(clientUserEditParam, clientUser);
        this.updateById(clientUser);
    }

    private void checkParam(ClientUserEditParam clientUserEditParam) {
        if (this.count(new LambdaQueryWrapper<ClientUser>()
                .eq(ClientUser::getAccount, clientUserEditParam.getAccount())
                .ne(ClientUser::getId, clientUserEditParam.getId())) > 0) {
            throw new CommonException("存在重复的账号，账号为：{}", clientUserEditParam.getAccount());
        }
        if(ObjectUtil.isNotEmpty(clientUserEditParam.getPhone())) {
            if(!PhoneUtil.isMobile(clientUserEditParam.getPhone())) {
                throw new CommonException("手机号码：{}格式错误", clientUserEditParam.getPhone());
            }
            if (this.count(new LambdaQueryWrapper<ClientUser>()
                    .eq(ClientUser::getPhone, CommonCryptogramUtil.doSm4CbcEncrypt(clientUserEditParam.getPhone()))
                    .ne(ClientUser::getId, clientUserEditParam.getId())) > 0) {
                throw new CommonException("存在重复的手机号，手机号为：{}", clientUserEditParam.getPhone());
            }
        }
        if(ObjectUtil.isNotEmpty(clientUserEditParam.getEmail())) {
            if(!CommonEmailUtil.isEmail(clientUserEditParam.getEmail())) {
                throw new CommonException("邮箱：{}格式错误", clientUserEditParam.getEmail());
            }
            if (this.count(new LambdaQueryWrapper<ClientUser>()
                    .eq(ClientUser::getEmail, clientUserEditParam.getEmail())
                    .ne(ClientUser::getId, clientUserEditParam.getId())) > 0) {
                throw new CommonException("存在重复的邮箱，邮箱为：{}", clientUserEditParam.getEmail());
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ClientUserIdParam> clientUserIdParamList) {
        this.removeByIds(CollStreamUtil.toList(clientUserIdParamList, ClientUserIdParam::getId));
    }

    @Override
    public ClientUser detail(ClientUserIdParam clientUserIdParam) {
        return this.queryEntity(clientUserIdParam.getId());
    }

    @Override
    public void updateUserLoginInfo(String userId, String device) {
        ClientUser clientUser = this.queryEntity(userId);
        clientUser.setLastLoginTime(clientUser.getLatestLoginTime());
        clientUser.setLastLoginIp(clientUser.getLatestLoginIp());
        clientUser.setLastLoginAddress(clientUser.getLatestLoginAddress());
        clientUser.setLastLoginDevice(clientUser.getLatestLoginDevice());
        clientUser.setLatestLoginTime(DateTime.now());
        String ip = CommonIpAddressUtil.getIp(CommonServletUtil.getRequest());
        clientUser.setLatestLoginIp(ip);
        clientUser.setLatestLoginAddress(CommonIpAddressUtil.getCityInfo(ip));
        clientUser.setLatestLoginDevice(device);
        this.updateById(clientUser);
    }

    @Override
    public ClientUser queryEntity(String id) {
        ClientUser clientUser = this.getById(id);
        if(ObjectUtil.isEmpty(clientUser)) {
            throw new CommonException("用户不存在，id值为：{}", id);
        }
        return clientUser;
    }
}
