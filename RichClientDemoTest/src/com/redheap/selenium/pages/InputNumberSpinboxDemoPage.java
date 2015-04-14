package com.redheap.selenium.pages;

import com.redheap.selenium.component.AdfInputNumberSpinbox;
import com.redheap.selenium.page.Page;

import org.openqa.selenium.WebDriver;

public class InputNumberSpinboxDemoPage extends Page {

    private final String spinbox = "dmoTpl:idInputNumberSpinbox";

    public InputNumberSpinboxDemoPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected String getExpectedTitle() {
        return "InputNumberSpinbox Demo";
    }

    public AdfInputNumberSpinbox findSpinbox() {
        return findAdfComponent(spinbox);
    }

}
