package com.redheap.selenium.component;

import org.openqa.selenium.WebDriver;

@Deprecated
public class AdfCommandLink extends AdfLink {

    public AdfCommandLink(WebDriver webDriver, String clientId) {
        super(webDriver, clientId);
    }

    @Override
    protected String getExpectedComponentType() {
        return "oracle.adf.RichCommandLink";
    }

}
