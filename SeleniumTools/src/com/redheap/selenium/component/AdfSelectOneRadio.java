package com.redheap.selenium.component;

import com.redheap.selenium.component.uix.UixValue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class AdfSelectOneRadio extends UixValue {

    // see http://jdevadf.oracle.com/adf-richclient-demo/docs/js-subids.html
    private static final String SUBID_content = "content"; // <div> element
    private static final String SUBID_item = "item"; // <input type='radio'> element
    private static final String SUBID_label = "label"; // <label> element

    private static final String JS_GET_LABEL_ELEMENT =
        JS_FIND_ELEMENT + "return AdfDomUtils.getDescendentElement(elem, 'label', arguments[1])";

    public AdfSelectOneRadio(WebDriver webDriver, String clientId) {
        super(webDriver, clientId);
    }

    @Override
    protected String getExpectedComponentType() {
        return "oracle.adf.RichSelectOneRadio";
    }

    public void clickItemByIndex(int index) {
        findItem(index).click();
        waitForPpr();
    }

    public void clickItemByLabel(String label) {
        int index = getIndexByLabel(label);
        if (index == -1) {
            throw new WebDriverException("item with label " + label + " not found");
        }
        clickItemByIndex(index);
    }

    public String getItemLabel(int index) {
        WebElement label = (WebElement) executeScript(JS_GET_LABEL_ELEMENT, getClientId(), index);
        return label.getText();
    }

    public int getIndexByLabel(String label) {
        List<WebElement> labels = getElement().findElements(By.tagName("label"));
        for (int i = 0, n = labels.size(); i < n; i++) {
            if (label.equals(labels.get(i).getText())) {
                return i;
            }
        }
        return -1; // not found
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
