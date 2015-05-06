package com.redheap.selenium.pages;

import com.redheap.selenium.component.AdfSelectManyChoice;
import com.redheap.selenium.page.Page;

import org.openqa.selenium.WebDriver;

public class SelectManyChoiceDemoPage extends Page {

    private final String drinks = "dmoTpl:targetChoice";

    public SelectManyChoiceDemoPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected String getExpectedTitle() {
        return "selectManyChoice Demo";
    }

    public AdfSelectManyChoice findDrinksSelectManyChoice() {
        return findAdfComponent(drinks);
    }

}
