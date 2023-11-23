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
package com.kiteehub.gen.modular.config.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kiteehub.gen.modular.config.entity.GenConfig;
import com.kiteehub.gen.modular.config.param.GenConfigEditParam;
import com.kiteehub.gen.modular.config.param.GenConfigIdParam;
import com.kiteehub.gen.modular.config.param.GenConfigListParam;

import java.util.List;

/**
 * 代码生成详细配置配置Service接口
 *
 * @author yubaoshan
 * @date 2022/10/25 22:33
 **/
public interface GenConfigService extends IService<GenConfig> {

    /**
     * 查询代码生成详细配置列表
     *
     * @author yubaoshan
     * @date 2022/10/25 22:33
     */
    List<GenConfig> list(GenConfigListParam genConfigListParam);

    /**
     * 编辑代码生成详细配置
     *
     * @author yubaoshan
     * @date 2022/10/25 22:33
     */
    void edit(GenConfigEditParam genConfigEditParam);

    /**
     * 删除代码生成详细配置
     *
     * @author yubaoshan
     * @date 2022/10/25 22:33
     */
    void delete(List<GenConfigIdParam> genConfigIdParamList);

    /**
     * 获取代码生成详细配置详情
     *
     * @author yubaoshan
     * @date 2022/10/25 22:33
     */
    GenConfig detail(GenConfigIdParam genConfigIdParam);

    /**
     * 获取代码生成详细配置详情
     *
     * @author yubaoshan
     * @date 2022/10/25 22:33
     **/
    GenConfig queryEntity(String id);

    /**
     * 批量编辑代码生成详细配置
     *
     * @author xuyuxiang
     * @date 2022/10/28 13:49
     **/
    void editBatch(List<GenConfigEditParam> genConfigEditParamList);
}
