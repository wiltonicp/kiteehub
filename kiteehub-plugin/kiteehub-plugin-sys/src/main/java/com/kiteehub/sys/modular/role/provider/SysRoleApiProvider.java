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
package com.kiteehub.sys.modular.role.provider;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kiteehub.sys.api.SysRoleApi;
import com.kiteehub.sys.core.enums.SysBuildInEnum;
import com.kiteehub.sys.modular.relation.entity.SysRelation;
import com.kiteehub.sys.modular.relation.enums.SysRelationCategoryEnum;
import com.kiteehub.sys.modular.relation.service.SysRelationService;
import com.kiteehub.sys.modular.resource.entity.SysButton;
import com.kiteehub.sys.modular.resource.entity.SysMenu;
import com.kiteehub.sys.modular.resource.enums.SysResourceCategoryEnum;
import com.kiteehub.sys.modular.resource.service.SysButtonService;
import com.kiteehub.sys.modular.resource.service.SysMenuService;
import com.kiteehub.sys.modular.role.entity.SysRole;
import com.kiteehub.sys.modular.role.enums.SysRoleCategoryEnum;
import com.kiteehub.sys.modular.role.param.SysRoleGrantResourceParam;
import com.kiteehub.sys.modular.role.param.SysRoleSelectorRoleParam;
import com.kiteehub.sys.modular.role.service.SysRoleService;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色API接口提供者
 *
 * @author xuyuxiang
 * @date 2022/7/22 14:57
 **/
@Service
public class SysRoleApiProvider implements SysRoleApi {

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysMenuService sysMenuService;

    @Resource
    private SysButtonService sysButtonService;

    @Resource
    private SysRelationService sysRelationService;

    @Override
    public boolean orgHasRole(List<String> orgIdList) {
        return sysRoleService.count(new LambdaQueryWrapper<SysRole>().in(SysRole::getOrgId, orgIdList)) > 0;
    }

    @SuppressWarnings("ALL")
    @Override
    public Page<JSONObject> roleSelector(String orgId, String category, String searchKey, List<String> dataScopeList) {
        SysRoleSelectorRoleParam sysRoleSelectorRoleParam = new SysRoleSelectorRoleParam();
        sysRoleSelectorRoleParam.setOrgId(orgId);
        sysRoleSelectorRoleParam.setCategory(category);
        sysRoleSelectorRoleParam.setSearchKey(searchKey);
        sysRoleSelectorRoleParam.setDataScopeList(dataScopeList);
        return BeanUtil.toBean(sysRoleService.roleSelector(sysRoleSelectorRoleParam), Page.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void grantForGenMenuAndButton(String menuId) {
        String superAdminRoleId = sysRoleService.getOne(new LambdaQueryWrapper<SysRole>().eq(SysRole::getCode, SysBuildInEnum.BUILD_IN_ROLE_CODE.getValue())
                .eq(SysRole::getCategory, SysRoleCategoryEnum.GLOBAL.getValue())).getId();
        SysRoleGrantResourceParam sysRoleGrantResourceParam = new SysRoleGrantResourceParam();
        sysRoleGrantResourceParam.setId(superAdminRoleId);
        SysMenu sysMenu = sysMenuService.queryEntity(menuId);
        SysRoleGrantResourceParam.SysRoleGrantResource sysRoleGrantResource = new SysRoleGrantResourceParam.SysRoleGrantResource();
        sysRoleGrantResource.setMenuId(sysMenu.getId());
        List<String> buttonIdList = sysButtonService.list(new LambdaQueryWrapper<SysButton>().eq(SysButton::getParentId,
                sysMenu.getId()).eq(SysButton::getCategory, SysResourceCategoryEnum.BUTTON.getValue())).stream()
                .map(SysButton::getId).collect(Collectors.toList());
        sysRoleGrantResource.setButtonInfo(buttonIdList);
        sysRoleGrantResourceParam.setGrantInfoList(CollectionUtil.newArrayList(sysRoleGrantResource));

        List<String> menuIdList = sysRoleGrantResourceParam.getGrantInfoList().stream()
                .map(SysRoleGrantResourceParam.SysRoleGrantResource::getMenuId).collect(Collectors.toList());
        List<String> extJsonList = sysRoleGrantResourceParam.getGrantInfoList().stream()
                .map(JSONUtil::toJsonStr).collect(Collectors.toList());

        List<String> existMenuIdList = sysMenuService.list(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getCategory,
                SysResourceCategoryEnum.MENU.getValue())).stream().map(SysMenu::getId).collect(Collectors.toList());
        if(ObjectUtil.isNotEmpty(existMenuIdList)) {
            sysRelationService.remove(new LambdaQueryWrapper<SysRelation>().eq(SysRelation::getObjectId, superAdminRoleId)
                    .eq(SysRelation::getCategory, SysRelationCategoryEnum.SYS_ROLE_HAS_RESOURCE.getValue()).notIn(SysRelation::getTargetId, existMenuIdList));
        }
        sysRelationService.saveRelationBatchWithAppend(superAdminRoleId, menuIdList, SysRelationCategoryEnum.SYS_ROLE_HAS_RESOURCE.getValue(), extJsonList);
    }
}
