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
package com.kiteehub.dev.modular.log.provider;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import com.kiteehub.auth.core.util.StpLoginUserUtil;
import com.kiteehub.common.page.CommonPageRequest;
import com.kiteehub.dev.api.DevLogApi;
import com.kiteehub.dev.modular.log.entity.DevLog;
import com.kiteehub.dev.modular.log.enums.DevLogCategoryEnum;
import com.kiteehub.dev.modular.log.service.DevLogService;
import com.kiteehub.dev.modular.log.util.DevLogUtil;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 日志API接口实现类
 *
 * @author xuyuxiang
 * @date 2022/9/2 16:07
 */
@Service
public class DevLogApiProvider implements DevLogApi {

    @Resource
    private DevLogService devLogService;

    @Override
    public void executeLoginLog(String userName) {
        DevLogUtil.executeLoginLog(userName);
    }

    @Override
    public void executeLogoutLog(String userName) {
        DevLogUtil.executeLogoutLog(userName);
    }

    @Override
    public List<JSONObject> currentUserVisLogList() {
        return devLogService.page(CommonPageRequest.defaultPage(), new LambdaQueryWrapper<DevLog>()
                .eq(DevLog::getOpUser, StpLoginUserUtil.getLoginUser().getName())
                .in(DevLog::getCategory, DevLogCategoryEnum.LOGIN.getValue(), DevLogCategoryEnum.LOGOUT.getValue())
                .orderByDesc(DevLog::getCreateTime))
                .getRecords().stream().map(JSONUtil::parseObj).collect(Collectors.toList());
    }

    @Override
    public List<JSONObject> currentUserOpLogList() {
        return devLogService.page(CommonPageRequest.defaultPage(), new LambdaQueryWrapper<DevLog>()
                .eq(DevLog::getOpUser, StpLoginUserUtil.getLoginUser().getName())
                .in(DevLog::getCategory, DevLogCategoryEnum.OPERATE.getValue(), DevLogCategoryEnum.EXCEPTION.getValue())
                .orderByDesc(DevLog::getCreateTime))
                .getRecords().stream().map(JSONUtil::parseObj).collect(Collectors.toList());
    }
}
