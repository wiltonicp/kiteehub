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
package com.kiteehub.dev.core.aop;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.kiteehub.auth.core.pojo.SaBaseLoginUser;
import com.kiteehub.auth.core.util.StpLoginUserUtil;
import com.kiteehub.common.annotation.CommonLog;
import com.kiteehub.dev.modular.log.util.DevLogUtil;

import java.lang.reflect.Method;

/**
 * 业务日志aop切面
 *
 * @author xuyuxiang
 * @date 2020/3/20 11:47
 */
@Aspect
@Order
@Component
public class DevLogAop {

    /**
     * 日志切入点
     *
     * @author xuyuxiang
     * @date 2020/3/23 17:10
     */
    @Pointcut("@annotation(com.kiteehub.common.annotation.CommonLog)")
    private void getLogPointCut() {
    }

    /**
     * 操作成功返回结果记录日志
     *
     * @author xuyuxiang
     * @date 2022/9/2 15:24
     */
    @AfterReturning(pointcut = "getLogPointCut()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        CommonLog commonLog = method.getAnnotation(CommonLog.class);
        String userName = "未知";
        try {
            SaBaseLoginUser loginUser = StpLoginUserUtil.getLoginUser();
            if(ObjectUtil.isNotNull(loginUser)) {
                userName = loginUser.getName();
            }
        } catch (Exception ignored) {
        }
        // 异步记录日志
        DevLogUtil.executeOperationLog(commonLog, userName, joinPoint, JSONUtil.toJsonStr(result));
    }

    /**
     * 操作发生异常记录日志
     *
     * @author xuyuxiang
     * @date 2022/9/2 15:24
     */
    @AfterThrowing(pointcut = "getLogPointCut()", throwing = "exception")
    public void doAfterThrowing(JoinPoint joinPoint, Exception exception) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        CommonLog commonLog = method.getAnnotation(CommonLog.class);
        String userName = "未知";
        try {
            SaBaseLoginUser loginUser = StpLoginUserUtil.getLoginUser();
            if(ObjectUtil.isNotNull(loginUser)) {
                userName = loginUser.getName();
            }
        } catch (Exception ignored) {
        }
        //异步记录日志
        DevLogUtil.executeExceptionLog(commonLog, userName, joinPoint, exception);
    }
}
