package com.redheap.selenium.component;

import org.openqa.selenium.WebDriver;


public class AdfDocument extends AdfComponent {

    public AdfDocument(WebDriver driver, String clientid) {
        super(driver, clientid);
    }

    @Override
    protected String getExpectedComponentType() {
        return "oracle.adf.RichDocument";
    }

    public void setAnimationEnabled(boolean enabled) {
        executeScript(String.format("AdfPage.PAGE.setAnimationEnabled(%b)",enabled));
    }

}
