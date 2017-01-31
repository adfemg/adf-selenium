package com.redheap.selenium.component;

import org.openqa.selenium.WebDriver;

/**
 * AdfShowDetail component class.
 * TODO Deze class mag weg zodra deze is opgenomen in adf-selenium
 */
public class AdfShowDetail extends AdfComponent {

    // see javascript AdfDhtmlShowDetailItemPeer.prototype.GetSubIdDomElement
    // see http://jdevadf.oracle.com/adf-richclient-demo/docs/js-subids.html
    private static final String SUBID_DISCLOSE_LINK = "disclosure_icon";

    /**
     * Default constructor.
     * @param webDriver
     * @param clientid
     */
    public AdfShowDetail(WebDriver webDriver, String clientid) {
        super(webDriver, clientid);
    }

    /**
     * Override the standard click method.
     */
    @Override
    public void click() {
        clickDiscloseLink();
    }

    /**
     * Click open the showdetail component using the component sub id disclosure_icon.
     */
    public void clickDiscloseLink() {
        findSubIdElement(SUBID_DISCLOSE_LINK).click();
        waitForPpr();
    }

}
