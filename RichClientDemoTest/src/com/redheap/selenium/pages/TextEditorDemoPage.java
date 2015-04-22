package com.redheap.selenium.pages;

import com.redheap.selenium.component.AdfInputNumberSpinbox;
import com.redheap.selenium.component.AdfTextEditor;
import com.redheap.selenium.page.Page;

import org.openqa.selenium.WebDriver;

public class TextEditorDemoPage extends Page {

    private static final String TEXTEDITOR = "dmoTpl:idRichTextEditor";

    public TextEditorDemoPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected String getExpectedTitle() {
        return "richTextEditor Demo";
    }

    public AdfTextEditor findTextEditor() {
        return findAdfComponent(TEXTEDITOR);
    }

}
