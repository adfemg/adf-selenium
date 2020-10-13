package com.redheap.selenium.component;

import org.openqa.selenium.WebDriver;

public class AdfShowDetailItem extends AdfComponent {

    // see javascript AdfDhtmlShowDetailItemPeer.prototype.GetSubIdDomElement
    // see http://jdevadf.oracle.com/adf-richclient-demo/docs/js-subids.html
    private static final String SUBID_tab_link = "tab_link";
    
    private static final String JS_IS_SHOW_ITEM_DISCLOSED =
        JS_FIND_COMPONENT + "return comp.getDisclosed();";
    
    private static final String JS_GET_HEADER_TEXT =
        JS_FIND_COMPONENT + "return comp.getText();";

    public AdfShowDetailItem(WebDriver webDriver, String clientid) {
        super(webDriver, clientid);
    }

    @Override
    public void click() {
        clickTabLink();
    }

    public void clickTabLink() {
        findSubIdElement(SUBID_tab_link).click();
        waitForPpr();
    }
    
    public boolean isDisclosed() {
        return (Boolean) executeScript(JS_IS_SHOW_ITEM_DISCLOSED, getClientId());
    }
    
    public String getHeaderText() {
        return (String) executeScript(JS_GET_HEADER_TEXT, getClientId());
    }

}
