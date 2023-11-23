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

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.ContentType;
import cn.hutool.json.JSONUtil;
import com.kiteehub.common.pojo.CommonResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 通用响应工具类
 *
 * @author xuyuxiang
 * @date 2022/8/4 9:40
 **/
public class CommonResponseUtil {

    /**
     * 以流的方式响应错误信息，默认错误消息
     *
     * @author xuyuxiang
     * @date 2022/8/4 9:41
     **/
    public static void renderError(HttpServletResponse response) throws IOException {
        renderError(response, null);
    }

    /**
     * 以流的方式响应错误信息，指定错误消息
     *
     * @author xuyuxiang
     * @date 2022/8/4 9:41
     **/
    public static void renderError(HttpServletResponse response, String msg) throws IOException {
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        response.setContentType(ContentType.JSON.toString());
        response.getWriter().write(JSONUtil.toJsonStr(ObjectUtil.isNotEmpty(msg)?CommonResult.error(msg):CommonResult.error()));
    }

    /**
     * 以流的方式响应错误信息，指定错误码和错误消息
     *
     * @author xuyuxiang
     * @date 2022/8/4 9:41
     **/
    public static void renderError(HttpServletResponse response, Integer code, String msg) throws IOException {
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        response.setContentType(ContentType.JSON.toString());
        response.getWriter().write(JSONUtil.toJsonStr(CommonResult.get(code, msg, null)));
    }
}
