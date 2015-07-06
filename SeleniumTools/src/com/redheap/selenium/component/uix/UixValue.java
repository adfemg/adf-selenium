package com.redheap.selenium.component.uix;

import com.redheap.selenium.component.AdfComponent;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class UixValue extends AdfComponent {

    private static final String JS_GET_VALUE = JS_FIND_COMPONENT + "return comp.getValue();";

    public UixValue(WebDriver webDriver, String clientid) {
        super(webDriver, clientid);
    }

    /**
     * Gets the current value of the component after any validation and client side converters.
     * @return current value of the component.
     */
    public Object getValue() {
        // returns null when component is not valid
        return executeScript(JS_GET_VALUE, getClientId());
    }

    /**
     * Returns the displayed text value of the content element of this component. This is the string that is
     * visible to the user.
     * @return null if component does not have a content element, or the {@code value} attribute if the content
     * element is an input component or the text content of any other type of content element.
     */
    public String getContent() {
        // try to find subid "content"
        WebElement content = findSubIdElement("content");
        if (content == null) {
            return null;
        } else if (content.getTagName().equalsIgnoreCase("input")) {
            return content.getAttribute("value");
        } else {
            return content.getText();
        }
    }

}
