package com.kiteehub.knowledge.chain.loader;

import com.kiteehub.knowledge.chain.split.TextSplitter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class MarkDownFileLoader implements ResourceLoader{
    private final TextSplitter textSplitter;
    @Override
    public String getContent(InputStream inputStream) {
        StringBuffer stringBuffer = new StringBuffer();
        try (InputStreamReader reader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(reader)){
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }
    @Override
    public List<String> getChunkList(String content){
        return textSplitter.split(content);
    }
}
