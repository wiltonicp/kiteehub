package com.kiteehub.knowledge.chain.loader;

import com.kiteehub.knowledge.chain.split.CharacterTextSplitter;
import com.kiteehub.knowledge.chain.split.CodeTextSplitter;
import com.kiteehub.knowledge.chain.split.MarkdownTextSplitter;

import com.kiteehub.knowledge.constant.FileType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ResourceLoaderFactory {

    private final CharacterTextSplitter characterTextSplitter;
    private final CodeTextSplitter codeTextSplitter;
    private final MarkdownTextSplitter markdownTextSplitter;

    public ResourceLoader getLoaderByFileType(String fileType){
        if (FileType.isTextFile(fileType)){
            return new TextFileLoader(characterTextSplitter);
        } else if (FileType.isWord(fileType)) {
            return new WordLoader(characterTextSplitter);
        } else if (FileType.isPdf(fileType)) {
            return new PdfFileLoader(characterTextSplitter);
        } else if (FileType.isMdFile(fileType)) {
            return new MarkDownFileLoader(markdownTextSplitter);
        }else if (FileType.isCodeFile(fileType)) {
            return new CodeFileLoader(codeTextSplitter);
        }else {
            return new TextFileLoader(characterTextSplitter);
        }
    }
}
