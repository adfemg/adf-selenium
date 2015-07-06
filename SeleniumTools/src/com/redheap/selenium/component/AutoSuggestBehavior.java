package com.redheap.selenium.component;

import com.google.common.base.Predicate;

import com.redheap.selenium.component.uix.UixInput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;


public class AutoSuggestBehavior<C extends UixInput> {

    private final C component;

    // constants from AdfAutoSuggestBehavior.js
    private static final String _POPUP_PANEL_ID = "_afrAutoSuggest";
    private static final String _SELECTED_STYLE_CLASS = "p_AFSelected";
    private static final String _NON_INTERACTIVE_ITEM = "ni";
    private static final String _ITEM_VALUE_ATTR = "_afrValue";
    private static final String ITEM_LABEL_ATTR = "_afrLabel";
    private static final String SELECTED_ITEM_INDEX = "_afrSelected";
    private static final String SUGGEST_ITEMS_ID = "_afrautosuggestpopup";
    private static final String POPUP_CONTAINER_DIV_ID = "_afrautosuggestcontainer";
    private static final String MORE_LINK_ID = "_afrautosuggestmorelink";
    private static final String BUSY_DIV_ID = "_afrautosuggestbusydiv";
    private static final String POPUP_CONTAINER_STYLE = "AFAutoSuggestItemsContainer";
    private static final String SUGGESTED_ITEM_STYLE = "AFAutoSuggestItem";
    private static final String MORE_LINK_STYLE = "AFAutoSuggestMoreLink";
    private static final String SUGGEST_BUSY_STYLE = "AFAutoSuggestBusyStyle";

    private static final String JS_IS_POPUP_VISIBLE =
        AdfComponent.JS_FIND_PEER + "return peer.isPopupVisible(comp,AdfAutoSuggestBehavior._POPUP_PANEL_ID);";
    private static final String JS_GET_POPUP_ELEMENT =
        AdfComponent.JS_FIND_PEER +
        "var pw=peer.getPopupWindow(comp,AdfAutoSuggestBehavior._POPUP_PANEL_ID);if (!pw){return null;}return pw.getElement();";

    public AutoSuggestBehavior(C component) {
        super();
        this.component = component;
        //  autosuggest: if(peer.isPopupVisible(component, AdfAutoSuggestBehavior._POPUP_PANEL_ID))
    }

    public C getComponent() {
        return component;
    }

    public boolean isPopupVisible() {
        return (Boolean) component.executeScript(JS_IS_POPUP_VISIBLE, component.getClientId());
    }

    protected WebElement findPopupElement() {
        return (WebElement) component.executeScript(JS_GET_POPUP_ELEMENT, component.getClientId());
    }

    public boolean isMoreLinkVisible() {
        return findMoreLinkElement() != null;
    }

    public void clickMoreLink() {
        findMoreLinkElement().click();
        component.waitForPpr();
    }

    protected WebElement findMoreLinkElement() {
        // search from driver scope as link is not a DOM child of component itself
        final List<WebElement> links =
            component.getDriver().findElements(By.id(component.getClientId() + "::" + MORE_LINK_ID));
        return links.isEmpty() ? null : links.get(0);
    }

    public void typeAndWait(String value) {
        component.clear();
        component.sendKeys(Keys.BACK_SPACE, value);
        final AdfComponent comp = component;
        new WebDriverWait(component.getDriver(), 10, 100).until(new Predicate<WebDriver>() {
            @Override
            public boolean apply(WebDriver webDriver) {
                if (isPopupVisible()) {
                    comp.waitForPpr();
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    public List<String> getSuggestItems() {
        WebElement table = findPopupElement();
        if (table == null) {
            return Collections.emptyList();
        }
        List<WebElement> items = table.findElements(By.xpath(".//li[@_afrvalue]"));
        List<String> retval = new ArrayList<String>(items.size());
        for (WebElement li : items) {
            retval.add(li.getText());
        }
        return retval;
    }

    public void clickSuggestItem(int index) {
        WebElement table = findPopupElement();
        List<WebElement> items = table.findElements(By.xpath(".//li[@_afrvalue]"));
        items.get(index).click();
        component.waitForPpr();
    }

}
