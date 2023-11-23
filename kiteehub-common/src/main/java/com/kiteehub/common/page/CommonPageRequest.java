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
package com.kiteehub.common.page;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import com.kiteehub.common.util.CommonServletUtil;

import java.util.List;

/**
 * 通用分页请求
 *
 * @author xuyuxiang
 * @date 2021/12/18 14:43
 */
@Slf4j
public class CommonPageRequest {

    private static final String PAGE_SIZE_PARAM_NAME = "size";

    private static final String PAGE_PARAM_NAME = "current";

    private static final Integer PAGE_SIZE_MAX_VALUE = 100;

    public static <T> Page<T> defaultPage() {
        return defaultPage(null);
    }

    public static <T> Page<T> defaultPage(List<OrderItem> orderItemList) {

        int size = 20;

        int page = 1;

        //每页条数
        String pageSizeString = CommonServletUtil.getParamFromRequest(PAGE_SIZE_PARAM_NAME);
        if (ObjectUtil.isNotEmpty(pageSizeString)) {
            try {
                size = Convert.toInt(pageSizeString);
                if(size > PAGE_SIZE_MAX_VALUE) {
                    size = PAGE_SIZE_MAX_VALUE;
                }
            } catch (Exception e) {
                log.error(">>> 分页条数转换异常：", e);
                size = 20;
            }
        }

        //第几页
        String pageString = CommonServletUtil.getParamFromRequest(PAGE_PARAM_NAME);
        if (ObjectUtil.isNotEmpty(pageString)) {
            try {
                page = Convert.toInt(pageString);
            } catch (Exception e) {
                log.error(">>> 分页页数转换异常：", e);
                page = 1;
            }
        }
        Page<T> objectPage = new Page<>(page, size);
        if (ObjectUtil.isNotEmpty(orderItemList)) {
            objectPage.setOrders(orderItemList);
        }
        return objectPage;
    }
}
