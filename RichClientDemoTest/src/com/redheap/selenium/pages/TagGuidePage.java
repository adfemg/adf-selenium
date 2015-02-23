package com.redheap.selenium.pages;

import com.redheap.selenium.page.Page;

import org.openqa.selenium.WebDriver;

public class TagGuidePage extends Page {

    public TagGuidePage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected String getExpectedTitle() {
        return "Tag Guide";
    }

}
