package com.redheap.selenium.component;

import org.openqa.selenium.WebDriver;

public class AdfPanelLabelAndMessage extends AdfComponent {

    private static final String JS_GET_LABEL = JS_FIND_COMPONENT + "return comp.getLabel();";

    public AdfPanelLabelAndMessage(WebDriver webDriver, String clientid) {
        super(webDriver, clientid);
    }

    public String getLabel() {
        return (String) executeScript(JS_GET_LABEL, getClientId());
    }

}
