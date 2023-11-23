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
package com.kiteehub.mobile.api;

import java.util.List;

/**
 * 移动端按钮API
 *
 * @author xuyuxiang
 * @date 2023/2/1 9:52
 **/
public interface MobileButtonApi {

    /**
     * 根据按钮id集合获取按钮码列表
     *
     * @author 每天一点
     * @date 2023/2/5 13:26
     **/
    List<String> listByIds(List<String> idList);
}
