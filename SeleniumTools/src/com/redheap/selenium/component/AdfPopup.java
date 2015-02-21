package com.redheap.selenium.component;

import org.openqa.selenium.WebDriver;

public class AdfPopup extends AdfComponent {

    public AdfPopup(WebDriver webDriver, String clientid) {
        super(webDriver, clientid);
    }

    @Override
    protected String getExpectedComponentType() {
        return "oracle.adf.RichPopup";
    }
}
