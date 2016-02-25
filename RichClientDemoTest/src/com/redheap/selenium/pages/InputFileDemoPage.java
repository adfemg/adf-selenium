package com.redheap.selenium.pages;

import com.redheap.selenium.component.AdfCommandButton;
import com.redheap.selenium.component.AdfInputFile;
import com.redheap.selenium.page.Page;

import org.openqa.selenium.WebDriver;

public class InputFileDemoPage extends Page {

    private final String inputfile = "dmoTpl:testid";
    private final String partialPostback = "dmoTpl:richCommand";

    public InputFileDemoPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected String getExpectedTitle() {
        return "inputFile Demo";
    }

    public AdfInputFile findInputFile() {
        return findAdfComponent(inputfile);
    }

    public AdfCommandButton findPartialPostbackButton() {
        return findAdfComponent(partialPostback);
    }

}
