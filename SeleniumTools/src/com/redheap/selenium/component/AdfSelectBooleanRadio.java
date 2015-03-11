package com.redheap.selenium.component;

import com.redheap.selenium.component.uix.UixValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AdfSelectBooleanRadio extends UixValue {

    // see http://jdevadf.oracle.com/adf-richclient-demo/docs/js-subids.html
    private static final String SUBID_content = "content"; // <input> element
    private static final String SUBID_label = "label"; // <label> element
    private static final String SUBID_text = "text"; // <label> element

    private static final String JS_GET_GROUP = JS_FIND_COMPONENT + "return comp.getGroup()";

    public AdfSelectBooleanRadio(WebDriver webDriver, String clientId) {
        super(webDriver, clientId);
    }

    @Override
    public void click() {
        findContent().click(); // click checkbox and not surrounding <div>
        waitForPpr();
    }

    public String getLabel() {
        WebElement label = findLabel();
        return label == null ? null : label.getText();
    }

    public String getText() {
        WebElement label = findText();
        return label == null ? null : label.getText();
    }

    public List<AdfSelectBooleanRadio> findGroupItems() {
        String groupName = getGroup();
        Collection<WebElement> items =
            (Collection<WebElement>) executeScript("return arguments[0].form.elements.namedItem(arguments[1])",
                                                   findContent(), groupName);
        List<AdfSelectBooleanRadio> retval = new ArrayList<AdfSelectBooleanRadio>(items.size());
        for (WebElement item : items) {
            AdfSelectBooleanRadio radio = AdfComponent.forElement(item);
            retval.add(radio);
        }
        return retval;
    }

    public String getGroup() {
        return (String) executeScript(JS_GET_GROUP, getClientId());
    }

    protected WebElement findContent() {
        return findSubIdElement(SUBID_content);
    }

    protected WebElement findLabel() {
        return findSubIdElement(SUBID_label);
    }

    protected WebElement findText() {
        return findSubIdElement(SUBID_text);
    }

}
