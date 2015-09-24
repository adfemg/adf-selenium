package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfInputFile;
import com.redheap.selenium.pages.InputFileDemoPage;

import java.io.File;

import java.net.URISyntaxException;
import java.net.URL;

import static org.hamcrest.Matchers.*;

import static org.junit.Assert.*;
import org.junit.Test;

public class InputFileTest extends PageTestBase<InputFileDemoPage> {

    @Test
    public void testPlainInputFile() throws URISyntaxException {
        InputFileDemoPage page = pages.goHome();
        AdfInputFile inputFile = page.findInputFile();
        URL url = Thread.currentThread().getContextClassLoader().getResource("build.xml");
        File f = new File(url.toURI());
        System.out.println(f.getAbsolutePath());
        inputFile.typeFileName(f);
        assertThat(inputFile.getSubmittedValue(), hasToString("C:\\fakepath\\build.xml"));
        assertThat(inputFile.getValue(), nullValue());
        assertThat(inputFile.getUpdateValue(), nullValue());
        page.findPartialPostbackButton().click();
        assertThat(inputFile.getSubmittedValue(), hasToString(""));
        assertThat(inputFile.getValue(), nullValue());
        assertThat(inputFile.getUpdateValue(), hasToString("build.xml"));
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
