package com.redheap.selenium.pages;

import com.redheap.selenium.component.AdfSelectBooleanRadio;
import com.redheap.selenium.page.Page;

import org.openqa.selenium.WebDriver;

public class SelectBooleanRadioDemoPage extends Page {

    private final String age10to18 = "dmoTpl:radio1";

    public SelectBooleanRadioDemoPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected String getExpectedTitle() {
        return "selectBooleanRadio Demo";
    }

    public AdfSelectBooleanRadio findAge10to18() {
        return findAdfComponent(age10to18);
    }

}
