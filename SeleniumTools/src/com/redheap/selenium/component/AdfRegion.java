package com.redheap.selenium.component;

import org.openqa.selenium.WebDriver;


public class AdfRegion extends AdfComponent {

    public AdfRegion(WebDriver driver, String clientid) {
        super(driver, clientid);
    }

    @Override
    protected String getExpectedComponentType() {
        return "oracle.adf.RichRegion";
    }

}
