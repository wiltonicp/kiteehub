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
package com.kiteehub.sys.modular.resource.provider;

import org.springframework.stereotype.Service;
import com.kiteehub.sys.api.SysButtonApi;
import com.kiteehub.sys.modular.resource.service.SysButtonService;

import javax.annotation.Resource;

/**
 * 按钮API接口实现类
 *
 * @author xuyuxiang
 * @date 2022/11/1 13:50
 **/
@Service
public class SysButtonApiProvider implements SysButtonApi {

    @Resource
    private SysButtonService sysButtonService;

    @Override
    public void addForGenButton(String menuId, String className, String functionName) {
        sysButtonService.addForGenButton(menuId, className, functionName);
    }
}
