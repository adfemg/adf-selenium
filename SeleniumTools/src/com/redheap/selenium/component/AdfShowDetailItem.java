package com.redheap.selenium.component;

import org.openqa.selenium.WebDriver;

public class AdfShowDetailItem extends AdfComponent {

    // see javascript AdfDhtmlShowDetailItemPeer.prototype.GetSubIdDomElement
    // see http://jdevadf.oracle.com/adf-richclient-demo/docs/js-subids.html
    private static final String SUBID_tab_link = "tab_link";

    public AdfShowDetailItem(WebDriver webDriver, String clientid) {
        super(webDriver, clientid);
    }

    @Override
    protected String getExpectedComponentType() {
        return "oracle.adf.RichShowDetailItem";
    }

    @Override
    public void click() {
        clickTabLink();
    }

    public void clickTabLink() {
        findSubIdElement(SUBID_tab_link).click();
        waitForPpr();
    }

}
