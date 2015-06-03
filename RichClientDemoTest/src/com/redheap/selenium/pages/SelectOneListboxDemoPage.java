package com.redheap.selenium.pages;

import com.redheap.selenium.component.AdfSelectOneListbox;
import com.redheap.selenium.page.Page;

import org.openqa.selenium.WebDriver;

public class SelectOneListboxDemoPage extends Page {
    
    private final String drinks = "dmoTpl:targetListbox";
    
    public SelectOneListboxDemoPage(WebDriver webDriver) {
        super(webDriver);
    }
    
    @Override
    protected String getExpectedTitle() {
        return "selectOneListbox Demo";
    }
    
    
    public AdfSelectOneListbox findDrinksSelectOneListbox() {
        return findAdfComponent(drinks);
    }

}
