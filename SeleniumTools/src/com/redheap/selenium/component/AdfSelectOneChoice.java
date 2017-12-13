package com.redheap.selenium.component;

import com.redheap.selenium.component.uix.UixValue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AdfSelectOneChoice extends UixValue {

    // see http://jdevadf.oracle.com/adf-richclient-demo/docs/js-subids.html
    private static final String SUBID_content = "content"; // <select> element
    private static final String SUBID_dropdown = "dropdown"; // only when mode=compact <a> element
    private static final String SUBID_item = "item"; // item[99] <option> element (or <li> when mode=compact)
    private static final String SUBID_label = "label"; // <label> element

    private static final String JS_VALUE_BY_LABEL = JS_FIND_COMPONENT + "var items=comp.getSelectItems(); for (var i=0;i<items.length;i++){if (items[i].getLabel()===arguments[1]) return items[i].getValue()}; return null;";
    private static final String JS_LABEL_BY_VALUE = JS_FIND_COMPONENT + "var items=comp.getSelectItems(); for (var i=0;i<items.length;i++){if (items[i].getValue()===arguments[1]) return items[i].getLabel()}; return null;";

    public AdfSelectOneChoice(WebDriver webDriver, String clientId) {
        super(webDriver, clientId);
    }

    public void clickItemByIndex(int index) {
        // expand list
        if(isCompact()){
            findDropdown().click();
        }        
        findItem(index).click(); // click item within list
        waitForPpr();
    }

    public void clickItemByLabel(String label) {
        clickItemByIndex(getValueByLabel(label));
    }

    public String getItemLabel(int index) {
        return (String)executeScript(JS_LABEL_BY_VALUE , getClientId(), Integer.toString(index));
    }

    public int getValueByLabel(String label) {
        String value = (String)executeScript(JS_VALUE_BY_LABEL , getClientId(), label);
        return value == null ? -1 : Integer.valueOf(value);
    }

    public String getValueLabel() {
        Object value = getValue();
        if (value instanceof Number) {
            return getItemLabel(((Number) value).intValue());
        } else if (value instanceof String) {
            return getItemLabel(Integer.valueOf((String) value));
        } else {
            return null;
        }
    }

    public boolean isCompact() {
        return findDropdown() != null;
    }

    protected WebElement findContent() {
        return findSubIdElement(SUBID_content);
    }

    protected WebElement findDropdown() {
        return findSubIdElement(SUBID_dropdown);
    }

    protected WebElement findItem(int index) {
        // warning: does not work for mode=compact when not yet expanded
        return findSubIdElement(SUBID_item + "[" + index + "]");
    }

    protected WebElement findLabel() {
        return findSubIdElement(SUBID_label);
    }

}
