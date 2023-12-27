package com.kiteehub.knowledge.modular.attach.enums;

import com.kiteehub.common.exception.CommonException;
import lombok.Getter;

/**
 * 知识库枚举
 *
 * @author Ranger
 * @date  2023/12/27 10:20
 **/
@Getter
public enum KnowledgeGatherEnum {

    /** 采编中 */
    PROGRESSING("PROGRESSING"),
    /** 已就绪 */
    READY("READY");

    private final String value;

    KnowledgeGatherEnum(String value) {
        this.value = value;
    }

    public static void validate(String value) {
        boolean flag = PROGRESSING.getValue().equals(value) || READY.getValue().equals(value);
        if(!flag) {
            throw new CommonException("不支持的采编状态：{}", value);
        }
    }
}
