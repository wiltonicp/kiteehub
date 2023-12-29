package com.kiteehub.knowledge.chain.split;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Primary
@Component
@AllArgsConstructor
public class CharacterTextSplitter implements TextSplitter{
    private final SplitterProperties splitterProperties;

    @Override
    public List<String> split(String content) {
        List<String> chunkList = new ArrayList<>();
        if (content.contains(splitterProperties.getEndSpliter())){
            // 按自定义分隔符切分
            String[] chunks = content.split(splitterProperties.getEndSpliter());
            chunkList.addAll(Arrays.asList(chunks));
        }else {
            int start = 0;
            int end = Math.min(splitterProperties.getSize(), content.length());
            while (start < content.length()) {
                // 在切分点后找到最近的标点符号、换行符或文本结尾
                while (end < content.length() && (!isPunctuation(content.charAt(end)) && content.charAt(end) != '\n')) {
                    end++;
                }
                // 切分文本
                chunkList.add(content.substring(start, end));
                // 更新切分点
                start = end;
                end = Math.min(start + splitterProperties.getSize(), content.length());
            }
        }
        return chunkList;
    }

    private static boolean isPunctuation(char c) {
        return c == '.' || c == ',' || c == '!' || c == '?' || c == ';' || c == ':' || c == '。' || c == '，' || c == '！' || c == '？' || c == '；' || c == '：';
    }
}