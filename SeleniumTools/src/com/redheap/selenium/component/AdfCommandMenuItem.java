package com.redheap.selenium.component;

import org.openqa.selenium.WebDriver;

public class AdfCommandMenuItem extends AdfComponent {

    public AdfCommandMenuItem(WebDriver webDriver, String clientid) {
        super(webDriver, clientid);
    }

    @Override
    protected String getExpectedComponentType() {
        return "oracle.adf.RichCommandMenuItem";
    }

}
