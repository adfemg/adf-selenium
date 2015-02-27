package com.redheap.selenium.component;

import org.openqa.selenium.WebDriver;

public class AdfCarousel extends AdfComponent {

    // see http://jdevadf.oracle.com/adf-richclient-demo/docs/js-subids.html
    private static final String SUBID_itemText   = "itemText"; // af:inputText
    private static final String SUBID_spinNext   = "spinNext"; // <a> element
    private static final String SUBID_spinPrevious= "spinPrevious"; // <a> element

    private static final String JS_FIND_RELATIVE_COMPONENT_CLIENTID_ITEMKEY =
        JS_FIND_COMPONENT + "return comp.findComponent(arguments[1],arguments[2]).getClientId()";
    private static final String JS_FIND_RELATIVE_COMPONENT_CLIENTID_CURRENT_ITEM =
        JS_FIND_COMPONENT + "return comp.findComponent(arguments[1],comp.getCurrentItemKey()).getClientId()";
    private static final String JS_GET_CURRENT_ITEM_KEY =
        JS_FIND_COMPONENT + "return comp.getCurrentItemKey()";
    private static final String JS_SET_CURRENT_ITEM_KEY =
        JS_FIND_COMPONENT + "return comp.setCurrentItemKey(arguments[1])";
    private static final String JS_GET_ITEM_COUNT =
        JS_FIND_COMPONENT + "return comp.getRows()";

    public AdfCarousel(WebDriver webDriver, String clientid) {
        super(webDriver, clientid);
    }

    @Override
    protected String getExpectedComponentType() {
        return "oracle.adf.RichCarousel";
    }

    public <T extends AdfComponent> T findAdfComponent(String relativeClientId, String itemKey, Class<? extends T> cls) {
        String clientid =
            (String) executeScript(JS_FIND_RELATIVE_COMPONENT_CLIENTID_ITEMKEY, getClientId(), relativeClientId, itemKey);
        return AdfComponent.forClientId(getDriver(), clientid, cls);
    }

    @Override
    public <T extends AdfComponent> T findAdfComponent(String relativeClientId, Class<? extends T> cls) {
        String clientid =
            (String) executeScript(JS_FIND_RELATIVE_COMPONENT_CLIENTID_CURRENT_ITEM, getClientId(), relativeClientId);
        return AdfComponent.forClientId(getDriver(), clientid, cls);
    }

    public String getItemText() {
        return findSubIdElement(SUBID_itemText).getText();
    }

    public void clickPrevious() {
        findSubIdElement(SUBID_spinPrevious).click();
        waitForPpr();
    }

    public void clickNext() {
        findSubIdElement(SUBID_spinNext).click();
        waitForPpr();
    }

    public String getCurrentItemKey() {
        // warning: this is not the row-index (server side) but client side key
        // eg. when showing items 100-124 on screen the keys are still "0" to "24"
        return (String) executeScript(JS_GET_CURRENT_ITEM_KEY, getClientId());
    }

    public String setCurrentItemKey(String key) {
        String oldKey = (String) executeScript(JS_SET_CURRENT_ITEM_KEY, getClientId(), key);
        return oldKey;
    }

    public int getItemDisplayCount() {
        return ((Number)executeScript(JS_GET_ITEM_COUNT, getClientId())).intValue();
    }

}
