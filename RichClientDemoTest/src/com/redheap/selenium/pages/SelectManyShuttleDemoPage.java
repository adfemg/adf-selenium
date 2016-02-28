package com.redheap.selenium.pages;

import com.redheap.selenium.component.AdfSelectManyShuttle;
import com.redheap.selenium.page.Page;

import org.openqa.selenium.WebDriver;

public class SelectManyShuttleDemoPage extends Page {

    private final String drinks = "dmoTpl:manyShuttle";

    public SelectManyShuttleDemoPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected String getExpectedTitle() {
        return "selectManyShuttle Demo";
    }

    public AdfSelectManyShuttle findDrinksSelectManyShuttle() {
        return findAdfComponent(drinks);
    }

}
