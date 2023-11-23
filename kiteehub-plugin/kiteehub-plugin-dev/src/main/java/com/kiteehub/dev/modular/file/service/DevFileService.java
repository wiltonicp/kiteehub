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
package com.kiteehub.dev.modular.file.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import com.kiteehub.dev.modular.file.entity.DevFile;
import com.kiteehub.dev.modular.file.param.DevFileIdParam;
import com.kiteehub.dev.modular.file.param.DevFileListParam;
import com.kiteehub.dev.modular.file.param.DevFilePageParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 文件Service接口
 *
 * @author xuyuxiang
 * @date 2022/2/23 18:27
 **/
public interface DevFileService extends IService<DevFile> {

    /**
     * MultipartFile文件上传，返回文件id
     *
     * @author xuyuxiang
     * @date 2022/4/22 15:53
     **/
    String uploadReturnId(String engine, MultipartFile file);

    /**
     * MultipartFile文件上传，返回文件Url
     *
     * @author xuyuxiang
     * @date 2022/4/22 15:53
     **/
    String uploadReturnUrl(String engine, MultipartFile file);

    /**
     * 文件分页列表接口
     *
     * @author xuyuxiang
     * @date 2022/6/21 15:44
     **/
    Page<DevFile> page(DevFilePageParam devFilePageParam);

    /**
     * 文件列表接口
     *
     * @author xuyuxiang
     * @date 2022/6/21 15:44
     **/
    List<DevFile> list(DevFileListParam devFileListParam);

    /**
     * 下载文件
     *
     * @author xuyuxiang
     * @date 2022/6/21 15:44
     **/
    void download(DevFileIdParam devFileIdParam, HttpServletResponse response) throws IOException;

    /**
     * 删除文件
     *
     * @author xuyuxiang
     * @date 2022/8/4 10:36
     **/
    void delete(List<DevFileIdParam> devFileIdParamList);

    /**
     * 获取文件详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:18
     */
    DevFile detail(DevFileIdParam devFileIdParam);

    /**
     * 获取文件详情
     *
     * @author xuyuxiang
     * @date 2022/4/24 21:18
     */
    DevFile queryEntity(String id);
}
