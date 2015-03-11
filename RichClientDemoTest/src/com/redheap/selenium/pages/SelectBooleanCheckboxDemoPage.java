package com.redheap.selenium.pages;

import com.redheap.selenium.component.AdfSelectBooleanCheckbox;
import com.redheap.selenium.page.Page;

import org.openqa.selenium.WebDriver;

public class SelectBooleanCheckboxDemoPage extends Page {

    private final String extraKeys = "dmoTpl:idSBC2";
    private final String triState = "dmoTpl:trip1";

    public SelectBooleanCheckboxDemoPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected String getExpectedTitle() {
        return "selectBooleanCheckbox Demo";
    }

    public AdfSelectBooleanCheckbox findExtraKeys() {
        return findAdfComponent(extraKeys);
    }

    public AdfSelectBooleanCheckbox findTriStateCheckbox() {
        return findAdfComponent(triState);
    }

}
