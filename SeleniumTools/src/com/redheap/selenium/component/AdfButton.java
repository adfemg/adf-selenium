package com.redheap.selenium.component;

import org.openqa.selenium.WebDriver;


public class AdfButton extends AdfComponent {

    public AdfButton(WebDriver driver, String clientid) {
        super(driver, clientid);
    }

    @Override
    protected String getExpectedComponentType() {
        return "oracle.adf.RichButton";
    }

}
