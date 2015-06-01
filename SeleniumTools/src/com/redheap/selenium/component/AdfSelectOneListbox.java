package com.redheap.selenium.component;
 
import com.redheap.selenium.component.uix.UixValue;
 
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
 
 
public class AdfSelectOneListbox extends UixValue {
 
    private static final String SUBID_content = "content"; // <div> element
    private static final String SUBID_item = "item"; // <input type='listbox'> element
    private static final String SUBID_label = "label"; // <label> element
 
    private static final String JS_VALUE_BY_LABEL =
        JS_FIND_COMPONENT +
        "var items=comp.getSelectItems(); for (var i=0;i<items.length;i++){if (items[i].getLabel()===arguments[1]) return items[i].getValue()}; return null;";
    private static final String JS_LABEL_BY_VALUE =
        JS_FIND_COMPONENT +
        "var items=comp.getSelectItems(); for (var i=0;i<items.length;i++){if (items[i].getValue()===arguments[1]) return items[i].getLabel()}; return null;";
 
 
    public AdfSelectOneListbox(WebDriver webDriver, String clientId) {
        super(webDriver, clientId);
    }
 
    public void clickItemByIndex(int index) {
        findItem(index).click(); // click item within list
        waitForPpr();
    }
 
    public void clickItemByLabel(String label) {
        clickItemByIndex(getValueByLabel(label));
    }
 
    public String getItemLabel(int index) {
        return (String) executeScript(JS_LABEL_BY_VALUE, getClientId(), Integer.toString(index));
    }
 
    public int getValueByLabel(String label) {
        String value = (String) executeScript(JS_VALUE_BY_LABEL, getClientId(), label);
        return value == null ? -1 : Integer.valueOf(value);
    }
 
    protected WebElement findContent() {
        return findSubIdElement(SUBID_content);
    }
 
    protected WebElement findItem(int index) {
        return findSubIdElement(SUBID_item + "[" + index + "]");
    }
 
    protected WebElement findLabel() {
        return findSubIdElement(SUBID_label);
    }
 
}
