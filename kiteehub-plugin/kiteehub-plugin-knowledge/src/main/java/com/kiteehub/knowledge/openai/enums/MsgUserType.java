package com.kiteehub.knowledge.openai.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum MsgUserType {

    AI("0"),
    USER("1");

    /**
     * 标记数据库存的值是code
     */
    @EnumValue
    private final String code;

    public String getCode() {
        return this.code;
    }

    @JsonCreator
    public static MsgUserType getByCode(String code) {
        for (MsgUserType value : MsgUserType.values()) {
            if (Objects.equals(code, value.getCode())) {
                return value;
            }
        }
        return null;
    }
}
