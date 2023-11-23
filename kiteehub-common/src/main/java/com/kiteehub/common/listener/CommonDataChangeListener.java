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
package com.kiteehub.common.listener;

import cn.hutool.json.JSONArray;

import java.util.List;

/**
 * 通用数据变化侦听器，你可以实现该侦听器接口，在数据新增、更新、删除时进行一些AOP操作
 *
 * @author xuyuxiang
 * @date 2023/3/3 10:14
 **/
public interface CommonDataChangeListener {

    /**
     * 执行添加，ID集合
     *
     * @param dataType 数据类型，如USER、ORG，自行定义
     * @param dataIdList 被添加的数据ID集合
     * @author xuyuxiang
     * @date 2023/3/3 10:24
     **/
    void doAddWithDataIdList(String dataType, List<String> dataIdList);

    /**
     * 执行添加，数据集合
     *
     * @param dataType 数据类型，如USER、ORG，自行定义
     * @param jsonArray 被添加的数据集合
     * @author xuyuxiang
     * @date 2023/3/3 10:24
     **/
    void doAddWithDataList(String dataType, JSONArray jsonArray);

    /**
     * 执行更新，ID集合
     *
     * @param dataType 数据类型，如USER、ORG，自行定义
     * @param dataIdList 被更新的数据ID集合
     * @author xuyuxiang
     * @date 2023/3/3 10:24
     **/
    void doUpdateWithDataIdList(String dataType, List<String> dataIdList);

    /**
     * 执行更新，数据集合
     *
     * @param dataType 数据类型，如USER、ORG，自行定义
     * @param jsonArray 被更新的数据集合
     * @author xuyuxiang
     * @date 2023/3/3 10:24
     **/
    void doUpdateWithDataList(String dataType, JSONArray jsonArray);

    /**
     * 执行删除，ID集合
     *
     * @param dataType 数据类型，如USER、ORG，自行定义
     * @param dataIdList 被删除的数据ID集合
     * @author xuyuxiang
     * @date 2023/3/3 10:24
     **/
    void doDeleteWithDataIdList(String dataType, List<String> dataIdList);
}
