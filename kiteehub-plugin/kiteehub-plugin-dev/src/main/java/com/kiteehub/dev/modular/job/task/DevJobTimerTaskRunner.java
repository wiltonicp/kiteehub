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
package com.kiteehub.dev.modular.job.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.kiteehub.common.timer.CommonTimerTaskRunner;

/**
 * 定时器的一个示例
 *
 * @author xuyuxiang
 * @date 2022/8/5 15:52
 **/
@Slf4j
@Component
public class DevJobTimerTaskRunner implements CommonTimerTaskRunner {

    private int n = 1;

    @Override
    public void action() {
        log.info("我是一个定时任务，正在在被执行第" + n + "次");
        n = n + 1;
    }
}
