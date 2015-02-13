package com.redheap.selenium.component;

import org.openqa.selenium.WebDriver;

public class AdfLink extends AdfComponent {

    public AdfLink(WebDriver webDriver, String clientid) {
        super(webDriver, clientid);
    }

    @Override
    protected String getExpectedComponentType() {
        return "oracle.adf.RichLink";
    }

}
