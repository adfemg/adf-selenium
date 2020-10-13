package com.redheap.selenium.component;

import org.openqa.selenium.WebDriver;

/**
 * AdfShowDetailHeader component class.
 */
public class AdfShowDetailHeader extends AdfComponent {

    private static final String SUBID_DISCLOSE_LINK = "disclosure_icon";

    /**
     * Constructor.
     * @param webDriver
     * @param clientId
     */
    public AdfShowDetailHeader(WebDriver webDriver, String clientId) {
        super(webDriver, clientId);
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
