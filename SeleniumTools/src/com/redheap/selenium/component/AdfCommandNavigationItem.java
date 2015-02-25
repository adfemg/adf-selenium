package com.redheap.selenium.component;

import org.openqa.selenium.WebDriver;

public class AdfCommandNavigationItem extends AdfComponent {

    public static final String COMPONENT_TYPE = "oracle.adf.RichCommandNavigationItem";

    public AdfCommandNavigationItem(WebDriver webDriver, String clientid) {
        super(webDriver, clientid);
    }

    @Override
    protected String getExpectedComponentType() {
        return COMPONENT_TYPE;
    }

}
