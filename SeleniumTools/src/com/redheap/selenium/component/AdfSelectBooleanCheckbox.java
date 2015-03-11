package com.redheap.selenium.component;

import com.redheap.selenium.component.uix.UixValue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AdfSelectBooleanCheckbox extends UixValue {

    // see http://jdevadf.oracle.com/adf-richclient-demo/docs/js-subids.html
    private static final String SUBID_content = "content"; // <input> element
    private static final String SUBID_label = "label"; // <label> element
    private static final String SUBID_text = "text"; // <label> element

    private static final String JS_NULL_VALUE_MEANS = JS_FIND_COMPONENT + "return comp.getNullValueMeans()";

    public AdfSelectBooleanCheckbox(WebDriver webDriver, String clientId) {
        super(webDriver, clientId);
    }

    @Override
    public void click() {
        findContent().click(); // click checkbox and not surrounding <div>
        waitForPpr();
    }

    public String getNullValueMeans() {
        Object o = executeScript(JS_NULL_VALUE_MEANS, getClientId());
        String s = String.valueOf(o);
        return "null".equals(s) ? null : s;
    }

    public boolean isTriState() {
        return getNullValueMeans() != null;
    }

    public boolean isNull() {
        List<WebElement> elements = getElement().findElements(By.className("p_AFMixed"));
        return elements != null && !elements.isEmpty();
    }

    public String getLabel() {
        WebElement label = findLabel();
        return label == null ? null : label.getText();
    }

    public String getText() {
        WebElement label = findText();
        return label == null ? null : label.getText();
    }

    protected WebElement findContent() {
        // first try to find <a> link for tri-state checkboxes
        List<WebElement> elements = getElement().findElements(By.tagName("a"));
        if (elements != null && !elements.isEmpty()) {
            return elements.get(0);
        }
        return findSubIdElement(SUBID_content);
    }

    protected WebElement findLabel() {
        return findSubIdElement(SUBID_label);
    }

    protected WebElement findText() {
        return findSubIdElement(SUBID_text);
    }

}
