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

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.URLUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * 文件下载工具类，使用本类前，对参数校验的异常使用CommonResponseUtil.renderError()方法进行渲染
 *
 * @author xuyuxiang
 * @date 2020/8/5 21:45
 */
@Slf4j
public class CommonDownloadUtil {

    /**
     * 下载文件
     *
     * @param file     要下载的文件
     * @param response 响应
     * @author xuyuxiang
     * @date 2020/8/5 21:46
     */
    public static void download(File file, HttpServletResponse response) {
        download(file.getName(), FileUtil.readBytes(file), response);
    }

    /**
     * 下载文件
     *
     * @author xuyuxiang
     * @date 2022/7/31 10:57
     */
    public static void download(String fileName, byte[] fileBytes, HttpServletResponse response) {
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLUtil.encode(fileName));
            response.addHeader("Content-Length", "" + fileBytes.length);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setContentType("application/octet-stream;charset=UTF-8");
            IoUtil.write(response.getOutputStream(), true, fileBytes);
        } catch (IOException e) {
            log.error(">>> 文件下载异常：", e);
        }
    }
}
