package com.redheap.selenium.pages;

import com.redheap.selenium.component.uix.UixInput;
import com.redheap.selenium.page.Page;

import org.openqa.selenium.WebDriver;

/**
 * Java representation of the adf-richclient-demo/faces/components/inputText.jspx page.
 */
public class InputTextDemoPage extends Page {

    private final String INPUTTEXT_NUMBERCONVERTER = "dmoTpl:idInputText2";
    private final String INPUTTEXT_MESSAGEEXAMPLE = "dmoTpl:idInputText4";

    public InputTextDemoPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected String getExpectedTitle() {
        return "inputText Demo";
    }

    /**
     * @return
     */
    public UixInput findNumberInputText() {
        return findAdfComponent(INPUTTEXT_NUMBERCONVERTER);
    }

    public UixInput findMessageExampleInputText() {
        return findAdfComponent(INPUTTEXT_MESSAGEEXAMPLE);
    }
}
