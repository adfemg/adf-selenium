package com.redheap.selenium.component;

import org.openqa.selenium.WebDriver;

public class AdfPopup extends AdfComponent {

    private static final String JS_IS_POPUP_VISIBLE = JS_FIND_COMPONENT + "return comp.isPopupVisible();";

    public AdfPopup(WebDriver webDriver, String clientid) {
        super(webDriver, clientid);
    }

    @Override
    protected String getExpectedComponentType() {
        return "oracle.adf.RichPopup";
    }

    public boolean isPopupVisible() {
        return (Boolean) executeScript(JS_IS_POPUP_VISIBLE, getClientId());
    }
}
