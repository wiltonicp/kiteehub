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
package com.kiteehub.dev.core.listener;

import cn.hutool.cron.CronUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import com.kiteehub.common.exception.CommonException;
import com.kiteehub.common.timer.CommonTimerTaskRunner;
import com.kiteehub.dev.modular.job.entity.DevJob;
import com.kiteehub.dev.modular.job.enums.DevJobStatusEnum;
import com.kiteehub.dev.modular.job.service.DevJobService;

/**
 * 定时任务监听器，系统启动时将定时任务启动
 *
 * @author xuyuxiang
 * @date 2022/8/5 16:07
 **/
@Slf4j
@Configuration
public class DevJobListener implements ApplicationListener<ApplicationStartedEvent>, Ordered {

    @SuppressWarnings("ALL")
    @Override
    public void onApplicationEvent( ApplicationStartedEvent applicationStartedEvent) {
        SpringUtil.getBean(DevJobService.class).list(new LambdaQueryWrapper<DevJob>()
                .eq(DevJob::getJobStatus, DevJobStatusEnum.RUNNING.getValue()).orderByAsc(DevJob::getSortCode))
                .forEach(devJob -> CronUtil.schedule(devJob.getId(), devJob.getCronExpression(), () -> {
                    try {
                        // 运行定时任务
                        ((CommonTimerTaskRunner) SpringUtil.getBean(Class.forName(devJob.getActionClass()))).action();
                    } catch (ClassNotFoundException e) {
                        throw new CommonException("定时任务找不到对应的类，名称为：{}", devJob.getActionClass());
                    }
                }));
        // 设置秒级别的启用
        CronUtil.setMatchSecond(true);
        // 启动定时器执行器
        CronUtil.restart();
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}
