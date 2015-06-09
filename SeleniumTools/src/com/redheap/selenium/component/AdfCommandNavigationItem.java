package com.redheap.selenium.component;

import org.openqa.selenium.WebDriver;

public class AdfCommandNavigationItem extends AdfComponent {

    public static final String COMPONENT_TYPE = "oracle.adf.RichCommandNavigationItem";

    private static final String JS_GET_SELECTED = JS_FIND_COMPONENT + "return comp.getSelected();";

    public AdfCommandNavigationItem(WebDriver webDriver, String clientid) {
        super(webDriver, clientid);
    }

    public boolean isSelected() {
        return (Boolean) executeScript(JS_GET_SELECTED, getClientId());
    }

}
