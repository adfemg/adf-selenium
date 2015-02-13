package com.redheap.selenium.component;

import org.openqa.selenium.WebDriver;

public class AdfPanelTabbed extends AdfComponent {

    public AdfPanelTabbed(WebDriver webDriver, String clientid) {
        super(webDriver, clientid);
    }

    @Override
    protected String getExpectedComponentType() {
        return "oracle.adf.RichPanelTabbed";
    }

}
