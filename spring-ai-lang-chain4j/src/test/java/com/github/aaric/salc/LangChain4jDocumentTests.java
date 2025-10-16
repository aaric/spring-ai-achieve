package com.github.aaric.salc;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.nio.file.Paths;

/**
 * LangChain4jDocumentTests
 *
 * @author Aaric
 * @version 0.23.0-SNAPSHOT
 */
@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class LangChain4jDocumentTests {

    @Test
    public void testLoadText() {
        String textFilePath = "build.gradle";
        Document doc = FileSystemDocumentLoader.loadDocument(Paths.get(textFilePath), new TextDocumentParser());
        System.err.println(doc.text());
    }

    @Disabled
    @Test
    public void testLoadPdf() {
        String pdfFilePath = "/path/to/test.pdf";
        Document doc = FileSystemDocumentLoader.loadDocument(Paths.get(pdfFilePath), new ApachePdfBoxDocumentParser());
        System.err.println(doc.text());
    }
}