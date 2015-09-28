package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfInputFile;
import com.redheap.selenium.pages.InputFileDemoPage;

import java.io.IOException;

import java.net.URISyntaxException;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.Matchers.*;

import static org.junit.Assert.*;
import org.junit.Test;

public class InputFileTest extends PageTestBase<InputFileDemoPage> {

    @Test
    public void testPlainInputFile() throws URISyntaxException, IOException {
        InputFileDemoPage page = pages.goHome();
        AdfInputFile inputFile = page.findInputFile();
        Path tmp = Files.createTempFile(getClass().getName(), ".tmp");
        try {
            inputFile.typeFileName(tmp.toAbsolutePath().toFile());
            assertThat(inputFile.getSubmittedValue(), endsWith(tmp.getFileName().toString()));
            assertThat(inputFile.getValue(), nullValue());
            assertThat(inputFile.getUpdateValue(), nullValue());
            page.findPartialPostbackButton().click();
            assertThat(inputFile.getSubmittedValue(), hasToString(""));
            assertThat(inputFile.getValue(), nullValue());
            assertThat(inputFile.getUpdateValue(), hasToString(tmp.getFileName().toString()));
        } finally {
            Files.deleteIfExists(tmp);
        }
    }

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main(InputFileTest.class.getName());
    }

    @Override
    protected Class<InputFileDemoPage> getPageClass() {
        return InputFileDemoPage.class;
    }

    @Override
    protected String getJspxName() {
        return "inputFile.jspx";
    }
}
