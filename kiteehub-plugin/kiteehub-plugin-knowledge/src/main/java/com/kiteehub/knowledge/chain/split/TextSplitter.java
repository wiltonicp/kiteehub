package com.kiteehub.knowledge.chain.split;

import java.util.List;

/**
 * 文本切分
 * Created by Ranger on 2023/11/21.
 */
public interface TextSplitter {

    List<String> split(String content);
}
