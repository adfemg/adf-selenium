package com.redheap.selenium.component.uix;

import com.redheap.selenium.component.AdfComponent;

import org.openqa.selenium.WebDriver;

public abstract class UixValue extends AdfComponent {

    public UixValue(WebDriver webDriver, String clientid) {
        super(webDriver, clientid);
    }

    public Object getValue() {
        // returns null when component is not valid
        String js = String.format("%s.getValue()", scriptFindComponent());
        return executeScript(js);
    }

}
