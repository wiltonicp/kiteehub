package com.kiteehub.knowledge.chain.loader;

import com.kiteehub.knowledge.chain.split.TextSplitter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class WordLoader implements ResourceLoader{
    private final TextSplitter textSplitter;
    @Override
    public String getContent(InputStream inputStream) {
        XWPFDocument document = null;
        try {
            document = new XWPFDocument(inputStream);
            XWPFWordExtractor extractor = new XWPFWordExtractor(document);
            String content = extractor.getText();
            return content;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getChunkList(String content) {
        return textSplitter.split(content);
    }

}
