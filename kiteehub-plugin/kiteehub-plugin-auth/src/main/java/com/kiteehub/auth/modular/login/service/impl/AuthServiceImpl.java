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
package com.kiteehub.auth.modular.login.service.impl;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.PhoneUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.springframework.stereotype.Service;
import com.kiteehub.auth.api.SaBaseLoginUserApi;
import com.kiteehub.auth.core.enums.SaClientTypeEnum;
import com.kiteehub.auth.core.pojo.SaBaseClientLoginUser;
import com.kiteehub.auth.core.pojo.SaBaseLoginUser;
import com.kiteehub.auth.core.util.StpClientLoginUserUtil;
import com.kiteehub.auth.core.util.StpClientUtil;
import com.kiteehub.auth.core.util.StpLoginUserUtil;
import com.kiteehub.auth.modular.login.enums.AuthDeviceTypeEnum;
import com.kiteehub.auth.modular.login.enums.AuthExceptionEnum;
import com.kiteehub.auth.modular.login.param.AuthAccountPasswordLoginParam;
import com.kiteehub.auth.modular.login.param.AuthGetPhoneValidCodeParam;
import com.kiteehub.auth.modular.login.param.AuthPhoneValidCodeLoginParam;
import com.kiteehub.auth.modular.login.result.AuthPicValidCodeResult;
import com.kiteehub.auth.modular.login.service.AuthService;
import com.kiteehub.common.cache.CommonCacheOperator;
import com.kiteehub.common.exception.CommonException;
import com.kiteehub.common.util.CommonCryptogramUtil;
import com.kiteehub.common.util.CommonEmailUtil;
import com.kiteehub.dev.api.DevConfigApi;
import com.kiteehub.dev.api.DevSmsApi;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 登录Service接口实现类
 *
 * @author xuyuxiang
 * @date 2021/12/23 21:52
 */
@Service
public class AuthServiceImpl implements AuthService {

    private static final String KITEE_SYS_DEFAULT_CAPTCHA_OPEN_KEY = "KITEE_SYS_DEFAULT_CAPTCHA_OPEN";

    private static final String AUTH_VALID_CODE_CACHE_KEY = "auth-validCode:";

    @Resource(name = "loginUserApi")
    private SaBaseLoginUserApi loginUserApi;

    @Resource(name = "clientLoginUserApi")
    private SaBaseLoginUserApi clientLoginUserApi;

    @Resource
    private DevConfigApi devConfigApi;

    @Resource
    private DevSmsApi devSmsApi;

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Override
    public AuthPicValidCodeResult getPicCaptcha(String type) {
        // 生成验证码，随机4位字符
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(100, 38, 4, 10);
        // 定义返回结果
        AuthPicValidCodeResult authPicValidCodeResult = new AuthPicValidCodeResult();
        // 获取验证码的值
        String validCode = circleCaptcha.getCode();
        // 获取验证码的base64
        String validCodeBase64 = circleCaptcha.getImageBase64Data();
        // 生成请求号
        String validCodeReqNo = IdWorker.getIdStr();
        // 将base64返回前端
        authPicValidCodeResult.setValidCodeBase64(validCodeBase64);
        // 将请求号返回前端
        authPicValidCodeResult.setValidCodeReqNo(validCodeReqNo);
        // 将请求号作为key，验证码的值作为value放到redis，用于校验，5分钟有效
        commonCacheOperator.put(AUTH_VALID_CODE_CACHE_KEY + validCodeReqNo, validCode, 5 * 60);
        return authPicValidCodeResult;
    }

    @Override
    public String getPhoneValidCode(AuthGetPhoneValidCodeParam authGetPhoneValidCodeParam, String type) {
        // 手机号
        String phone = authGetPhoneValidCodeParam.getPhone();
        // 验证码
        String validCode = authGetPhoneValidCodeParam.getValidCode();
        // 验证码请求号
        String validCodeReqNo = authGetPhoneValidCodeParam.getValidCodeReqNo();
        // 校验参数
        validPhoneValidCodeParam(null, validCode, validCodeReqNo, type);
        // 生成手机验证码的值，随机6为数字
        String phoneValidCode = RandomUtil.randomNumbers(6);
        // 生成手机验证码的请求号
        String phoneValidCodeReqNo = IdWorker.getIdStr();

        // TODO 使用阿里云执行发送验证码，将验证码作为短信内容的参数变量放入，
        // TODO 签名不传则使用系统默认配置的签名，支持传入多个参数，示例：{"name":"张三","number":"15038****76"}
        //devSmsApi.sendSmsAliyun(phone, null, "验证码模板号", JSONUtil.toJsonStr(JSONUtil.createObj().set("validCode", phoneValidCode)));

        // TODO 使用腾讯云执行发送验证码，将验证码作为短信内容的参数变量放入，
        // TODO sdkAppId和签名不传则使用系统默认配置的sdkAppId和签名，支持传入多个参数，逗号拼接，示例："张三,15038****76,进行中"
        devSmsApi.sendSmsTencent("1400825573", phone, "风筝科技", "1814888", phoneValidCode);

        // 将请求号作为key，验证码的值作为value放到redis，用于校验，5分钟有效
        commonCacheOperator.put(AUTH_VALID_CODE_CACHE_KEY + phone + StrUtil.UNDERLINE + phoneValidCodeReqNo, phoneValidCode, 5 * 60);
        // 返回请求号
        return phoneValidCodeReqNo;
    }

    /**
     * 校验验证码方法
     *
     * @author xuyuxiang
     * @date 2022/8/25 15:26
     **/
    private void validValidCode(String phoneOrEmail, String validCode, String validCodeReqNo) {
        // 依据请求号，取出缓存中的验证码进行校验
        Object existValidCode;
        if(ObjectUtil.isEmpty(phoneOrEmail)) {
            existValidCode = commonCacheOperator.get(AUTH_VALID_CODE_CACHE_KEY + validCodeReqNo);
        } else {
            existValidCode = commonCacheOperator.get(AUTH_VALID_CODE_CACHE_KEY + phoneOrEmail + StrUtil.UNDERLINE + validCodeReqNo);
        }
        // 为空则直接验证码错误
        if(ObjectUtil.isEmpty(existValidCode)) {
            throw new CommonException(AuthExceptionEnum.VALID_CODE_ERROR.getValue());
        }
        // 移除该验证码
        if(ObjectUtil.isEmpty(phoneOrEmail)) {
            commonCacheOperator.remove(AUTH_VALID_CODE_CACHE_KEY + validCodeReqNo);
        } else {
            commonCacheOperator.remove(AUTH_VALID_CODE_CACHE_KEY + phoneOrEmail + StrUtil.UNDERLINE + validCodeReqNo);
        }
        // 不一致则直接验证码错误
        if (!validCode.equals(Convert.toStr(existValidCode).toLowerCase())) {
            throw new CommonException("验证码错误");
        }
    }

    /**
     * 校验手机号与验证码等参数
     *
     * @author xuyuxiang
     * @date 2022/8/25 14:29
     **/
    private void validPhoneValidCodeParam(String phoneOrEmail, String validCode, String validCodeReqNo, String type) {
        // 验证码正确则校验手机号格式
        if(ObjectUtil.isEmpty(phoneOrEmail)) {
            // 执行校验验证码
            validValidCode(null, validCode, validCodeReqNo);
        } else {
            if(!PhoneUtil.isMobile(phoneOrEmail) && !CommonEmailUtil.isEmail(phoneOrEmail)) {
                throw new CommonException(AuthExceptionEnum.PHONE_FORMAT_ERROR.getValue());
            }
            // 执行校验验证码
            validValidCode(phoneOrEmail, validCode, validCodeReqNo);

            // 根据手机号获取用户信息，判断用户是否存在，根据B端或C端判断
            if(SaClientTypeEnum.B.getValue().equals(type)) {
                if(ObjectUtil.isEmpty(loginUserApi.getUserByPhone(phoneOrEmail))) {
                    throw new CommonException(AuthExceptionEnum.PHONE_ERROR.getValue());
                }
            } else {
                if(ObjectUtil.isEmpty(clientLoginUserApi.getClientUserByPhone(phoneOrEmail))) {
                    throw new CommonException(AuthExceptionEnum.PHONE_ERROR.getValue());
                }
            }
        }
    }

    @Override
    public String doLogin(AuthAccountPasswordLoginParam authAccountPasswordLoginParam, String type) {
        // 获取账号
        String account = authAccountPasswordLoginParam.getAccount();
        // 获取密码
        String password = authAccountPasswordLoginParam.getPassword();
        // 获取设备
        String device = authAccountPasswordLoginParam.getDevice();
        // 默认指定为PC，如在小程序跟移动端的情况下，自行指定即可
        if(ObjectUtil.isEmpty(device)) {
            device = AuthDeviceTypeEnum.PC.getValue();
        } else {
            AuthDeviceTypeEnum.validate(device);
        }
        // 校验验证码
        String defaultCaptchaOpen = devConfigApi.getValueByKey(KITEE_SYS_DEFAULT_CAPTCHA_OPEN_KEY);
        if(ObjectUtil.isNotEmpty(defaultCaptchaOpen)) {
            if(Convert.toBool(defaultCaptchaOpen)) {
                // 获取验证码
                String validCode = authAccountPasswordLoginParam.getValidCode();
                // 获取验证码请求号
                String validCodeReqNo = authAccountPasswordLoginParam.getValidCodeReqNo();
                // 开启验证码则必须传入验证码
                if(ObjectUtil.isEmpty(validCode)) {
                    throw new CommonException(AuthExceptionEnum.VALID_CODE_EMPTY.getValue());
                }
                // 开启验证码则必须传入验证码请求号
                if(ObjectUtil.isEmpty(validCodeReqNo)) {
                    throw new CommonException(AuthExceptionEnum.VALID_CODE_REQ_NO_EMPTY.getValue());
                }
                // 执行校验验证码
                validValidCode(null, validCode, validCodeReqNo);
            }
        }
        // SM2解密并获得前端传来的密码哈希值
        String passwordHash;
        try {
            // 解密，并做哈希值
            passwordHash = CommonCryptogramUtil.doHashValue(CommonCryptogramUtil.doSm2Decrypt(password));
        } catch (Exception e) {
            throw new CommonException(AuthExceptionEnum.PWD_DECRYPT_ERROR.getValue());
        }
        // 根据账号获取用户信息，根据B端或C端判断
        if(SaClientTypeEnum.B.getValue().equals(type)) {
            SaBaseLoginUser saBaseLoginUser = loginUserApi.getUserByAccount(account);
            if(ObjectUtil.isEmpty(saBaseLoginUser)) {
                throw new CommonException(AuthExceptionEnum.ACCOUNT_ERROR.getValue());
            }
            if (!saBaseLoginUser.getPassword().equals(passwordHash)) {
                throw new CommonException(AuthExceptionEnum.PWD_ERROR.getValue());
            }
            // 执行B端登录
            return execLoginB(saBaseLoginUser, device);
        } else {
            SaBaseClientLoginUser saBaseClientLoginUser = clientLoginUserApi.getClientUserByAccount(account);
            if(ObjectUtil.isEmpty(saBaseClientLoginUser)) {
                throw new CommonException(AuthExceptionEnum.ACCOUNT_ERROR.getValue());
            }
            if (!saBaseClientLoginUser.getPassword().equals(passwordHash)) {
                throw new CommonException(AuthExceptionEnum.PWD_ERROR.getValue());
            }
            // 执行C端登录
            return execLoginC(saBaseClientLoginUser, device);
        }
    }

    @Override
    public String doLoginByPhone(AuthPhoneValidCodeLoginParam authPhoneValidCodeLoginParam, String type) {
        // 手机号
        String phone = authPhoneValidCodeLoginParam.getPhone();
        // 校验参数
        validPhoneValidCodeParam(phone, authPhoneValidCodeLoginParam.getValidCode(), authPhoneValidCodeLoginParam.getValidCodeReqNo(), type);
        // 设备
        String device = authPhoneValidCodeLoginParam.getDevice();
        // 默认指定为PC，如在小程序跟移动端的情况下，自行指定即可
        if(ObjectUtil.isEmpty(device)) {
            device = AuthDeviceTypeEnum.PC.getValue();
        } else {
            AuthDeviceTypeEnum.validate(device);
        }
        // 根据手机号获取用户信息，根据B端或C端判断
        if(SaClientTypeEnum.B.getValue().equals(type)) {
            SaBaseLoginUser saBaseLoginUser = loginUserApi.getUserByPhone(phone);
            if(ObjectUtil.isEmpty(saBaseLoginUser)) {
                throw new CommonException(AuthExceptionEnum.ACCOUNT_ERROR.getValue());
            }
            // 执行B端登录
            return execLoginB(saBaseLoginUser, device);
        } else {
            SaBaseClientLoginUser saBaseClientLoginUser = clientLoginUserApi.getClientUserByPhone(phone);
            if(ObjectUtil.isEmpty(saBaseClientLoginUser)) {
                throw new CommonException(AuthExceptionEnum.ACCOUNT_ERROR.getValue());
            }
            // 执行C端登录
            return execLoginC(saBaseClientLoginUser, device);
        }
    }

    /**
     * 执行B端登录
     *
     * @author xuyuxiang
     * @date 2022/8/25 14:36
     **/
    private String execLoginB(SaBaseLoginUser saBaseLoginUser, String device) {
        // 校验状态
        if(!saBaseLoginUser.getEnabled()) {
            throw new CommonException(AuthExceptionEnum.ACCOUNT_DISABLED.getValue());
        }
        // 执行登录
        StpUtil.login(saBaseLoginUser.getId(), new SaLoginModel().setDevice(device).setExtra("name", saBaseLoginUser.getName()));
        // 角色集合
        List<JSONObject> roleList = loginUserApi.getRoleListByUserId(saBaseLoginUser.getId());
        // 角色id集合
        List<String> roleIdList = roleList.stream().map(jsonObject -> jsonObject.getStr("id")).collect(Collectors.toList());
        // 角色码集合
        List<String> roleCodeList = roleList.stream().map(jsonObject -> jsonObject.getStr("code")).collect(Collectors.toList());
        // 角色id和用户id集合
        List<String> userAndRoleIdList = CollectionUtil.unionAll(roleIdList, CollectionUtil.newArrayList(saBaseLoginUser.getId()));
        // 获取按钮码
        saBaseLoginUser.setButtonCodeList(loginUserApi.getButtonCodeListListByUserAndRoleIdList(userAndRoleIdList));
        // 获取移动端按钮码
        saBaseLoginUser.setMobileButtonCodeList(loginUserApi.getMobileButtonCodeListListByUserIdAndRoleIdList(userAndRoleIdList));
        // 获取数据范围
        saBaseLoginUser.setDataScopeList(Convert.toList(SaBaseLoginUser.DataScope.class,
                loginUserApi.getPermissionListByUserIdAndRoleIdList(userAndRoleIdList, saBaseLoginUser.getOrgId())));
        // 获取权限码
        saBaseLoginUser.setPermissionCodeList(saBaseLoginUser.getDataScopeList().stream()
                .map(SaBaseLoginUser.DataScope::getApiUrl).collect(Collectors.toList()));
        // 获取角色码
        saBaseLoginUser.setRoleCodeList(roleCodeList);
        // 缓存用户信息，此处使用TokenSession为了指定时间内无操作则自动下线
        StpUtil.getTokenSession().set("loginUser", saBaseLoginUser);
        // 返回token
        return StpUtil.getTokenInfo().tokenValue;
    }

    /**
     * 执行C端登录
     *
     * @author xuyuxiang
     * @date 2022/8/25 14:37
     **/
    private String execLoginC(SaBaseClientLoginUser saBaseClientLoginUser, String device) {
        // 校验状态
        if(!saBaseClientLoginUser.getEnabled()) {
            throw new CommonException(AuthExceptionEnum.ACCOUNT_DISABLED.getValue());
        }
        // 执行登录
        StpClientUtil.login(saBaseClientLoginUser.getId(), new SaLoginModel().setDevice(device).setExtra("name", saBaseClientLoginUser.getName()));
        // 角色集合
        List<JSONObject> roleList = loginUserApi.getRoleListByUserId(saBaseClientLoginUser.getId());
        // 角色id集合
        List<String> roleIdList = roleList.stream().map(jsonObject -> jsonObject.getStr("id")).collect(Collectors.toList());
        // 角色码集合
        List<String> roleCodeList = roleList.stream().map(jsonObject -> jsonObject.getStr("code")).collect(Collectors.toList());
        // 角色id和用户id集合
        List<String> userAndRoleIdList = CollectionUtil.unionAll(roleIdList, CollectionUtil.newArrayList(saBaseClientLoginUser.getId()));
        // 获取按钮码
        saBaseClientLoginUser.setButtonCodeList(clientLoginUserApi.getButtonCodeListListByUserAndRoleIdList(userAndRoleIdList));
        // 获取移动端按钮码
        saBaseClientLoginUser.setMobileButtonCodeList(clientLoginUserApi.getMobileButtonCodeListListByUserIdAndRoleIdList(userAndRoleIdList));
        // 获取数据范围
        saBaseClientLoginUser.setDataScopeList(Convert.toList(SaBaseClientLoginUser.DataScope.class,
                clientLoginUserApi.getPermissionListByUserIdAndRoleIdList(userAndRoleIdList, null)));
        // 获取权限码
        saBaseClientLoginUser.setPermissionCodeList(saBaseClientLoginUser.getDataScopeList().stream()
                .map(SaBaseClientLoginUser.DataScope::getApiUrl).collect(Collectors.toList()));
        // 获取角色码
        saBaseClientLoginUser.setRoleCodeList(roleCodeList);
        // 缓存用户信息，此处使用TokenSession为了指定时间内无操作则自动下线
        StpClientUtil.getTokenSession().set("loginUser", saBaseClientLoginUser);
        // 返回token
        return StpClientUtil.getTokenInfo().tokenValue;
    }

    /**
     * 获取B端登录用户信息
     *
     * @author xuyuxiang
     * @date 2021/10/12 15:59
     **/
    @Override
    public SaBaseLoginUser getLoginUser() {
        SaBaseLoginUser saBaseLoginUser = StpLoginUserUtil.getLoginUser();
        saBaseLoginUser.setPassword(null);
        saBaseLoginUser.setPermissionCodeList(null);
        saBaseLoginUser.setDataScopeList(null);
        return saBaseLoginUser;
    }

    /**
     * 获取C端登录用户信息
     *
     * @author xuyuxiang
     * @date 2021/10/12 15:59
     **/
    @Override
    public SaBaseClientLoginUser getClientLoginUser() {
        SaBaseClientLoginUser saBaseClientLoginUser = StpClientLoginUserUtil.getClientLoginUser();
        saBaseClientLoginUser.setPassword(null);
        saBaseClientLoginUser.setPermissionCodeList(null);
        saBaseClientLoginUser.setDataScopeList(null);
        return saBaseClientLoginUser;
    }

    @Override
    public String doLoginById(String userId, String device, String type) {
        // 根据id获取用户信息，根据B端或C端判断
        if(SaClientTypeEnum.B.getValue().equals(type)) {
            SaBaseLoginUser saBaseLoginUser = loginUserApi.getUserById(userId);
            if (ObjectUtil.isEmpty(saBaseLoginUser)) {
                throw new CommonException(AuthExceptionEnum.ACCOUNT_ERROR.getValue());
            }
            // 执行B端登录
            return execLoginB(saBaseLoginUser, device);
        } else {
            SaBaseClientLoginUser saBaseClientLoginUser = clientLoginUserApi.getClientUserById(userId);
            if (ObjectUtil.isEmpty(saBaseClientLoginUser)) {
                throw new CommonException(AuthExceptionEnum.ACCOUNT_ERROR.getValue());
            }
            // 执行C端登录
            return execLoginC(saBaseClientLoginUser, device);
        }
    }
}
