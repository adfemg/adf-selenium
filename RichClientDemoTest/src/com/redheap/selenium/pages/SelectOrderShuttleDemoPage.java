package com.redheap.selenium.pages;

import com.redheap.selenium.component.AdfSelectOrderShuttle;
import com.redheap.selenium.page.Page;

import org.openqa.selenium.WebDriver;

public class SelectOrderShuttleDemoPage extends Page {

    private final String drinks = "dmoTpl:manyShuttle";

    public SelectOrderShuttleDemoPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected String getExpectedTitle() {
        return "selectOrderShuttle Demo";
    }

    public AdfSelectOrderShuttle findDrinksSelectOrderShuttle() {
        return findAdfComponent(drinks);
    }

}
