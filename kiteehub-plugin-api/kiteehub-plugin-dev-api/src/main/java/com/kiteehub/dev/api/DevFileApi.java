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
package com.kiteehub.dev.api;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件API接口，可参考com.kiteehub.dev.core.util.file包下的工具类扩展更多需要的方法
 *
 * @author xuyuxiang
 * @date 2022/6/22 15:21
 **/
public interface DevFileApi {

    /* =========本地文件========= */

    /**
     * 上传文件返回Url
     *
     * @param file 文件
     * @author xuyuxiang
     * @date 2022/6/22 17:44
     **/
    String storageFileWithReturnUrlLocal(MultipartFile file);

    /**
     * 上传文件返回Id
     *
     * @param file 文件
     * @author xuyuxiang
     * @date 2022/6/22 17:44
     **/
    String storageFileWithReturnIdLocal(MultipartFile file);

    /**
     * 根据文件ID获取文件真实路径
     * @param localId
     * @return
     */
    String getStoragePathWithReturnIdLocal(String localId);

    /* =========阿里云文件========= */

    /**
     * 上传文件返回Url
     *
     * @param file 文件
     * @author xuyuxiang
     * @date 2022/6/22 17:44
     **/
    String storageFileWithReturnUrlAliyun(MultipartFile file);

    /**
     * 上传文件返回Id
     *
     * @param file 文件
     * @author xuyuxiang
     * @date 2022/6/22 17:44
     **/
    String storageFileWithReturnIdAliyun(MultipartFile file);

    /* =========腾讯云件========= */

    /**
     * 上传文件返回Url
     *
     * @param file 文件
     * @author xuyuxiang
     * @date 2022/6/22 17:44
     **/
    String storageFileWithReturnUrlTencent(MultipartFile file);

    /**
     * 上传文件返回Id
     *
     * @param file 文件
     * @author xuyuxiang
     * @date 2022/6/22 17:44
     **/
    String storageFileWithReturnIdTencent(MultipartFile file);

    /* =========MINIO件========= */

    /**
     * 上传文件返回Url
     *
     * @param file 文件
     * @author xuyuxiang
     * @date 2022/6/22 17:44
     **/
    String storageFileWithReturnUrlMinio(MultipartFile file);

    /**
     * 上传文件返回Id
     *
     * @param file 文件
     * @author xuyuxiang
     * @date 2022/6/22 17:44
     **/
    String storageFileWithReturnIdMinio(MultipartFile file);
}
