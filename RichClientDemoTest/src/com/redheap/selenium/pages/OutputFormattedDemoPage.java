package com.redheap.selenium.pages;

import com.redheap.selenium.component.AdfOutputFormatted;
import com.redheap.selenium.page.Page;

import org.openqa.selenium.WebDriver;

public class OutputFormattedDemoPage extends Page {

    private final String output = "dmoTpl:of1";
    private final String red = "dmoTpl:of3";

    public OutputFormattedDemoPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected String getExpectedTitle() {
        return "outputFormatted Demo";
    }

    public AdfOutputFormatted findOutput() {
        return findAdfComponent(output);
    }

    public AdfOutputFormatted findRed() {
        return findAdfComponent(red);
    }

}
