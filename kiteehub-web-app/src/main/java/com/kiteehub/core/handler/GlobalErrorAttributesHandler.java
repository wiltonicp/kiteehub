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
package com.kiteehub.core.handler;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpStatus;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;
import com.kiteehub.common.exception.CommonException;
import com.kiteehub.common.pojo.CommonResult;
import com.kiteehub.common.util.CommonServletUtil;

import java.util.Map;

/**
 * 将未知错误异常，输出格式重写为我们熟悉的响应格式
 *
 * @author xuyuxiang
 * @date 2021/10/9 15:24
 **/
@Component
public class GlobalErrorAttributesHandler extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions attributeOptions) {
        // 获取spring默认的返回内容
        Map<String, Object> defaultErrorAttributes = super.getErrorAttributes(webRequest, attributeOptions);

        // 获取其状态码
        Object status = defaultErrorAttributes.get("status");
        if (ObjectUtil.isNotEmpty(status)) {
            // 如果其为404，则处理
            if (HttpStatus.HTTP_NOT_FOUND == Convert.toInt(status)) {
                Object path = defaultErrorAttributes.get("path");
                if(ObjectUtil.isNotEmpty(path)) {
                    return BeanUtil.beanToMap(CommonResult.get(HttpStatus.HTTP_NOT_FOUND, "路径不存在，请求地址：" +
                            Convert.toStr(path), null));
                } else {
                    return BeanUtil.beanToMap(CommonResult.get(HttpStatus.HTTP_NOT_FOUND, "路径不存在", null));
                }
            } else {
                return BeanUtil.beanToMap(CommonResult.get(HttpStatus.HTTP_INTERNAL_ERROR, "服务器异常，请求地址：" +
                        CommonServletUtil.getRequest().getRequestURL(), null));
            }
        }

        // 如果返回的异常是CommonException，则按CommonException响应的内容进行返回
        Throwable throwable = this.getError(webRequest);
        if (ObjectUtil.isNotEmpty(throwable)) {
            if (throwable instanceof CommonException) {
                CommonException commonException = (CommonException) throwable;
                return BeanUtil.beanToMap(CommonResult.error(commonException.getMsg()));
            } else {
                return BeanUtil.beanToMap(CommonResult.get(HttpStatus.HTTP_INTERNAL_ERROR, "服务器异常，请求地址：" +
                        CommonServletUtil.getRequest().getRequestURL(), null));
            }
        } else {
            // throwable为空，则直接返回默认异常
            return BeanUtil.beanToMap(CommonResult.error());
        }
    }
}
