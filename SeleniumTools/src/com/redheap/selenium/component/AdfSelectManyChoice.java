package com.redheap.selenium.component;

import com.redheap.selenium.component.uix.UixValue;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AdfSelectManyChoice extends UixValue {

    // see http://jdevadf.oracle.com/adf-richclient-demo/docs/js-subids.html
    private static final String SUBID_content = "content"; // <select> element
    private static final String SUBID_dropdown = "dropdown"; // only when mode=compact <a> element
    private static final String SUBID_item = "item"; // item[99] <option> element (or <li> when mode=compact)
    private static final String SUBID_label = "label"; // <label> element

    private static final String JS_VALUE_BY_LABEL = JS_FIND_COMPONENT + "var items=comp.getSelectItems(); for (var i=0;i<items.length;i++){if (items[i].getLabel()===arguments[1]) return items[i].getValue()}; return null;";
    private static final String JS_LABEL_BY_VALUE = JS_FIND_COMPONENT + "var items=comp.getSelectItems(); for (var i=0;i<items.length;i++){if (items[i].getValue()===arguments[1]) return items[i].getLabel()}; return null;";

    public AdfSelectManyChoice(WebDriver webDriver, String clientId) {
        super(webDriver, clientId);
    }

    public void clickItemsByIndices(int[] indices) {
        // expand list
        WebElement dropdown = findDropdown();
        if (dropdown != null) {
            dropdown.click(); // mode=compact
        } else {
            click(); // click element itself when not in compact mode
        }
        // click items within list
        for (int i =0; i < indices.length; i++) {
            findItem(indices[i]).click();
        }
        findContentNode().sendKeys(Keys.TAB);
        waitForPpr();
    }

    public void clickItemsByLabels(String[] label) {
        int[] indices = new int[label.length];
        for (int i = 0; i < label.length; i++) {
            indices[i] = getValueByLabel(label[i]);
        }
        clickItemsByIndices(indices);
    }

    public String getItemLabel(int index) {
        return (String)executeScript(JS_LABEL_BY_VALUE , getClientId(), Integer.toString(index));
    }

    public int getValueByLabel(String label) {
        String value = (String)executeScript(JS_VALUE_BY_LABEL , getClientId(), label);
        return value == null ? -1 : Integer.valueOf(value);
    }

    public String getValueLabel() {
        String value = (String) getValue();
        return value == null || value.isEmpty() ? null : getItemLabel(Integer.valueOf(value));
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
