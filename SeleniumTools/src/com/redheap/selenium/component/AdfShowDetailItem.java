package com.redheap.selenium.component;

import org.openqa.selenium.WebDriver;

public class AdfShowDetailItem extends AdfComponent {

    // see javascript AdfDhtmlShowDetailItemPeer.prototype.GetSubIdDomElement

    public enum SubId {
        panel_text,
        icon,
        panel_disclosure_icon,
        remove_icon_above,
        remove_icon_below,
        tab_link,
        tab_text_above,
        tab_text_below
    };

    public AdfShowDetailItem(WebDriver webDriver, String clientid) {
        super(webDriver, clientid);
    }

    @Override
    protected String getExpectedComponentType() {
        return "oracle.adf.RichShowDetailItem";
    }

    public void clickTabLink() {
        findSubIdElement(SubId.tab_link.toString()).click();
    }

}
