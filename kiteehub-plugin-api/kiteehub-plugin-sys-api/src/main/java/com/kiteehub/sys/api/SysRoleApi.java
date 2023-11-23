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
package com.kiteehub.sys.api;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * 角色API
 *
 * @author xuyuxiang
 * @date 2022/6/6 11:36
 **/
public interface SysRoleApi {

    /**
     * 判断组织下是否存在角色
     *
     * @author xuyuxiang
     * @date 2022/8/2 11:16
     */
    boolean orgHasRole(List<String> orgIdList);

    /**
     * 获取角色选择器
     *
     * @author xuyuxiang
     * @date 2022/7/22 14:49
     **/
    Page<JSONObject> roleSelector(String orgId, String category, String searchKey, List<String> dataScopeList);

    /**
     * 代码生成菜单按钮授权
     *
     * @author xuyuxiang
     * @date 2022/11/1 15:58
     **/
    void grantForGenMenuAndButton(String menuId);
}
