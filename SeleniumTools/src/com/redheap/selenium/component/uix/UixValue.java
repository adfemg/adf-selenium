package com.redheap.selenium.component.uix;

import com.redheap.selenium.component.AdfComponent;

import org.openqa.selenium.WebDriver;

public abstract class UixValue extends AdfComponent {

    private static final String JS_GET_VALID = JS_FIND_COMPONENT + "return comp.getValue();";

    public UixValue(WebDriver webDriver, String clientid) {
        super(webDriver, clientid);
    }

    public Object getValue() {
        // returns null when component is not valid
        return executeScript(JS_GET_VALID, getClientId());
    }

}
