package com.kiteehub.knowledge.openai.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * Created By Ranger on 2023/4/4.
 */
@Getter
@AllArgsConstructor
public enum MsgType {

    TEXT("0"),
    IMAGES("1");

    /**
     * 标记数据库存的值是code
     */
    @EnumValue
    private final String code;

    public String getCode() {
        return this.code;
    }

    @JsonCreator
    public static MsgType getByCode(String code) {
        for (MsgType value : MsgType.values()) {
            if (Objects.equals(code, value.getCode())) {
                return value;
            }
        }
        return null;
    }
}
