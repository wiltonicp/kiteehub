package com.kiteehub.knowledge.openai.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kiteehub.knowledge.openai.enums.MsgType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Created by Ranger on 2023/4/3.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MsgResult {

    private String robotId;

    private MsgType msgType;

    private String uid;

    private String content;

    private Long tokens;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private LocalDateTime createdTime = LocalDateTime.now();

    private boolean isEnd;
}
