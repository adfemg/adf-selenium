package com.redheap.selenium.component;

import org.openqa.selenium.WebDriver;

public class AdfPanelWindow extends AdfComponent {

    // see http://jdevadf.oracle.com/adf-richclient-demo/docs/js-subids.html
    // some subids return only html tags, some return tags that are full adf components
    private static final String SUBID_close_icon = "close_icon"; // <a> tag
    private static final String SUBID_content = "content"; // <td> tag
    private static final String SUBID_title = "title"; // <div> element

    private static final String JS_GET_TITLE = JS_FIND_COMPONENT + "return comp.getTitle();";

    public AdfPanelWindow(WebDriver webDriver, String clientid) {
        super(webDriver, clientid);
    }

    public String getTitle() {
        return (String) executeScript(JS_GET_TITLE, getClientId());
    }
    
    public void clickCloseIcon() {
        findCloseIcon().click();
    }

    private AdfButton findCloseIcon() {
        return findSubIdComponent(SUBID_close_icon);
    }

}
