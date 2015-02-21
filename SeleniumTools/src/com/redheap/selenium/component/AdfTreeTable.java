package com.redheap.selenium.component;

import org.openqa.selenium.WebDriver;

public class AdfTreeTable extends AdfTree {

    public AdfTreeTable(WebDriver webDriver, String clientid) {
        super(webDriver, clientid);
    }

    @Override
    protected String getExpectedComponentType() {
        return "oracle.adf.RichTreeTable";
    }

}
