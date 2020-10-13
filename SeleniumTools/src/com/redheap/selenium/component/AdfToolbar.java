package com.redheap.selenium.component;

import com.redheap.selenium.errors.SubIdNotFoundException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AdfToolbar extends AdfComponent {
    private static final String JS_FIND_TOOLBAR_BUTTON_SUBID_CLIENTID =
        JS_FIND_PEER + "var subelem=peer.getSubIdDomElement(comp,arguments[1]).firstElementChild;" +
                       "if(!subelem){return null;}return AdfRichUIPeer.getFirstAncestorComponent(subelem).getClientId();";
    
    // see http://jdevadf.oracle.com/adf-richclient-demo/docs/js-subids.html
    private static final String SUBID_startPart_item = "item["; //item[#]	an item, corresponding to the #, on the toolbar
    private static final String SUBID_endtPart_item = "]";     //correspondes to <td> element, but not to <div> of CommandToolbarButton

    public AdfToolbar(WebDriver webDriver, String clientid) {
        super(webDriver, clientid);
    }

    protected <T extends AdfComponent> T findToolbarSubIdComponent(String subid) {
        final String subClientId = (String) executeScript(JS_FIND_TOOLBAR_BUTTON_SUBID_CLIENTID, getClientId(), subid);
        if (subClientId == null) {
            return null;
        }
        return AdfComponent.forClientId(getDriver(), subClientId);
    }

    public AdfCommandToolbarButton findCommandToolbarButton(int itemIndex) {
        return findToolbarSubIdComponent(SUBID_startPart_item + itemIndex + SUBID_endtPart_item);
        //findSubIdElement(SUBID_tab_link).click();
        //waitForPpr();
    }

}
