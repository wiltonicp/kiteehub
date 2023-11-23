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
package com.kiteehub.sys.modular.org.provider;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import com.kiteehub.sys.api.SysOrgApi;
import com.kiteehub.sys.modular.org.entity.SysOrg;
import com.kiteehub.sys.modular.org.param.SysOrgSelectorOrgListParam;
import com.kiteehub.sys.modular.org.service.SysOrgService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 组织API接口提供者
 *
 * @author xuyuxiang
 * @date 2022/7/22 14:56
 **/
@Service
public class SysOrgApiProvider implements SysOrgApi {

    @Resource
    private SysOrgService sysOrgService;

    @Override
    public String getNameById(String orgId) {
        return sysOrgService.queryEntity(orgId).getName();
    }

    @Override
    public String getSupervisorIdByOrgId(String orgId) {
        SysOrg sysOrg = sysOrgService.getById(orgId);
        if(ObjectUtil.isNotEmpty(sysOrg)) {
            return sysOrg.getDirectorId();
        }
        return null;
    }

    @Override
    public List<Tree<String>> orgTreeSelector() {
        return sysOrgService.orgTreeSelector();
    }

    @SuppressWarnings("ALL")
    @Override
    public Page<JSONObject> orgListSelector(String parentId) {
        SysOrgSelectorOrgListParam sysOrgSelectorOrgListParam = new SysOrgSelectorOrgListParam();
        sysOrgSelectorOrgListParam.setParentId(parentId);
        return BeanUtil.toBean(sysOrgService.orgListSelector(sysOrgSelectorOrgListParam), Page.class);
    }
}
