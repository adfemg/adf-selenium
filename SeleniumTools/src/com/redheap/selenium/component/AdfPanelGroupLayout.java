package com.redheap.selenium.component;

import org.openqa.selenium.WebDriver;

public class AdfPanelGroupLayout extends AdfComponent {

    public AdfPanelGroupLayout(WebDriver webDriver, String clientid) {
        super(webDriver, clientid);
    }

    @Override
    protected String getExpectedComponentType() {
        return "oracle.adf.RichPanelGroupLayout";
    }

}
