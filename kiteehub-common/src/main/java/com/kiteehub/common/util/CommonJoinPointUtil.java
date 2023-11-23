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
package com.kiteehub.common.util;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Spring切面工具类
 *
 * @author xuyuxiang
 * @date 2022/9/2 15:51
 */
public class CommonJoinPointUtil {

    /**
     * 获取切面的参数JSON
     *
     * @author xuyuxiang
     * @date 2022/9/2 15:51
     */
    public static String getArgsJsonString(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        // 参数名数组
        String[] parameterNames = ((MethodSignature) signature).getParameterNames();
        // 构造参数组集合
        Map<String, Object> map = MapUtil.newHashMap();
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if(ObjectUtil.isNotEmpty(args[i]) && isUsefulParam(args[i])) {
                if(JSONUtil.isTypeJSON(StrUtil.toString(args[i]))) {
                    map.put(parameterNames[i], JSONUtil.parseObj(args[i]));
                } else {
                    map.put(parameterNames[i], JSONUtil.toJsonStr(args[i]));
                }
            }
        }
        return JSONUtil.toJsonStr(map);
    }

    /**
     * 判断是否需要拼接的参数，过滤掉HttpServletRequest,MultipartFile,HttpServletResponse等类型参数
     *
     * @author xuyuxiang
     * @date 2022/9/2 15:51
     */
    private static boolean isUsefulParam(Object arg) {
        return !(arg instanceof MultipartFile) &&
                !(arg instanceof HttpServletRequest) &&
                !(arg instanceof HttpServletResponse);
    }
}
