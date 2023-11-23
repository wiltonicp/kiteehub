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
package com.kiteehub.common.timer;

/**
 * 定时器执行者，定时器都要实现本接口，并需要把实现类加入到spring容器中
 *
 * @author xuyuxiang
 * @date 2022/8/15 16:09
 **/
public interface CommonTimerTaskRunner {

    /**
     * 任务执行的具体内容
     *
     * @author xuyuxiang
     * @date 2022/8/15 16:09
     **/
    void action();
}
