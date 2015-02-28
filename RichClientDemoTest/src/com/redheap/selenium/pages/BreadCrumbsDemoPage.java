package com.redheap.selenium.pages;

import com.redheap.selenium.component.AdfBreadCrumbs;
import com.redheap.selenium.page.Page;

import org.openqa.selenium.WebDriver;

public class BreadCrumbsDemoPage extends Page {

    private final String breadCrumbs = "dmoTpl:breadCrumbs1";

    public BreadCrumbsDemoPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected String getExpectedTitle() {
        return "breadCrumbs Demo";
    }

    public AdfBreadCrumbs findBreadCrumbs() {
        return findAdfComponent(breadCrumbs);
    }

}
