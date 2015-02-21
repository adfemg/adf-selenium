package com.redheap.selenium.component;

import org.openqa.selenium.WebDriver;

public class AdfColumn extends AdfComponent {

    public AdfColumn(WebDriver webDriver, String clientid) {
        super(webDriver, clientid);
    }

    @Override
    protected String getExpectedComponentType() {
        return "oracle.adf.RichColumn";
    }

}
