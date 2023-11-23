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
package com.kiteehub.common.exception;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * 通用异常
 *
 * @author xuyuxiang
 * @date 2020/4/8 15:54
 */
@Getter
@Setter
public class CommonException extends RuntimeException {

    private Integer code;

    private String msg;

    public CommonException() {
        super("服务器异常");
        this.code = 500;
        this.msg = "服务器异常";
    }

    public CommonException(String msg, Object... arguments) {
        super(StrUtil.format(msg, arguments));
        this.code = 500;
        this.msg = StrUtil.format(msg, arguments);
    }

    public CommonException(Integer code, String msg, Object... arguments) {
        super(StrUtil.format(msg, arguments));
        this.code = code;
        this.msg = StrUtil.format(msg, arguments);
    }
}
