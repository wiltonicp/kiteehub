package com.kiteehub.knowledge.chain.loader;

import com.kiteehub.knowledge.chain.split.TextSplitter;
import lombok.AllArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@AllArgsConstructor
public class PdfFileLoader implements ResourceLoader{
    private final TextSplitter characterTextSplitter;
    @Override
    public String getContent(InputStream inputStream) {
        PDDocument document = null;
        try {
            document = PDDocument.load(inputStream);
            PDFTextStripper textStripper = new PDFTextStripper();
            String content = textStripper.getText(document);
            return content;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getChunkList(String content) {
        return characterTextSplitter.split(content);
    }
}
