package com.redheap.selenium.component;

import org.openqa.selenium.WebDriver;

public class AdfPopup extends AdfComponent {

    private static final String JS_IS_POPUP_VISIBLE = JS_FIND_COMPONENT + "return comp.isPopupVisible();";
    private static final String JS_CANCEL = JS_FIND_COMPONENT + "comp.cancel();";

    public AdfPopup(WebDriver webDriver, String clientid) {
        super(webDriver, clientid);
    }

    public boolean isPopupVisible() {
        return (Boolean) executeScript(JS_IS_POPUP_VISIBLE, getClientId());
    }

    public void cancel() {
        executeScript(JS_CANCEL, getClientId());
        waitForPpr();
    }

}
