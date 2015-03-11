package com.redheap.selenium.pages;

import com.redheap.selenium.component.AdfSelectOneChoice;
import com.redheap.selenium.page.Page;

import org.openqa.selenium.WebDriver;

public class SelectOneChoiceDemoPage extends Page {

    private final String drinks = "dmoTpl:targetChoice";
    private final String compact = "dmoTpl:targetCompactChoice";

    public SelectOneChoiceDemoPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected String getExpectedTitle() {
        return "selectOneChoice Demo";
    }

    public AdfSelectOneChoice findDrinksSelectOneChoice() {
        return findAdfComponent(drinks);
    }

    public AdfSelectOneChoice findCompactSelectOneChoice() {
        return findAdfComponent(compact);
    }

}
