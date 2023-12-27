package com.kiteehub.knowledge.chain.split;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "chain.split.chunk")
public class SplitterProperties {
    private int size;
    private int overlay;

    private String qaSpliter;
    /**
     * 自定义分隔符切分
     */
    private String endSpliter;
}