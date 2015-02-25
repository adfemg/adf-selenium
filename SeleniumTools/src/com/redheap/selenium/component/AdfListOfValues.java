package com.redheap.selenium.component;

import com.redheap.selenium.component.uix.UixInputPopup;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AdfListOfValues extends UixInputPopup {

    // see http://jdevadf.oracle.com/adf-richclient-demo/docs/js-subids.html
    private static final String SUBID_search_icon = "search_icon"; // magnify glass icon after inputText field

    public AdfListOfValues(WebDriver webDriver, String string) {
        super(webDriver, string);
    }

    @Override
    protected String getExpectedComponentType() {
        return "oracle.adf.RichInputListOfValues";
    }

    public void clickSearchIcon() {
        findSearchIcon().click();
        waitForPpr();
    }

    protected WebElement findSearchIcon() {
        return findSubIdElement(SUBID_search_icon);
    }

}
