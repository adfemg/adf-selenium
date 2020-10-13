package com.redheap.selenium.component;

import org.openqa.selenium.WebDriver;

/**
 * ADF Selenium representation of a {@code panelBox} component.
 */
public class AdfPanelBox extends AdfComponent {
    // see javascript AdfDhtmlShowDetailItemPeer.prototype.GetSubIdDomElement
    // see http://jdevadf.oracle.com/adf-richclient-demo/docs/js-subids.html
    private static final String SUBID_DISCLOSE_LINK = "disclosure_icon";

    /**
     * Constructort, passthrough voor {@link AdfComponent} constructor.
     * @param webDriver
     * @param string
     */
    public AdfPanelBox(final WebDriver webDriver, final String string) {
        super(webDriver, string);
    }

    /**
     * Getter voor attribuut {@code text}.
     * @return value of attribute {@code text}, the title.
     */
    public String getText() {
        return (String) getProperty("text");
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
