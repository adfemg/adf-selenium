package com.redheap.selenium.component.uix;

import com.redheap.selenium.domain.PageMessageWrapper;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;

public abstract class UixInput extends UixValue {

    private static final String JS_GET_VALID = JS_FIND_COMPONENT + "return comp.getValid();";
    private static final String JS_GET_SUBMITTED_VALUE = JS_FIND_ELEMENT + "return peer.GetSubmittedValue(comp,elem);";
    private static final String JS_GET_LABEL = JS_FIND_COMPONENT + "return comp.getLabel();";
    private static final String JS_SELECT_INPUT = "arguments[0].select();";

    public UixInput(WebDriver webDriver, String clientid) {
        super(webDriver, clientid);
    }

    public boolean isValid() {
        return (Boolean) executeScript(JS_GET_VALID, getClientId());
    }

    public String getSubmittedValue() {
        return (String) executeScript(JS_GET_SUBMITTED_VALUE, getClientId());
    }

    public String getLabel() {
        return (String) executeScript(JS_GET_LABEL, getClientId());
    }

    public void sendKeys(CharSequence... keys) {
        findContentNode().sendKeys(keys);
    }

    public void clear() {
        if (isBrowser(BrowserType.CHROME)) {
            executeScript(JS_SELECT_INPUT, findContentNode());
            findContentNode().sendKeys(Keys.DELETE);
        } else {
            if (isPlatform(Platform.MAC)) {
                findContentNode().sendKeys(Keys.chord(Keys.COMMAND, "a"), Keys.DELETE);
            } else {
                findContentNode().sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
            }
        }
    }

    public void tabNext() {
        findContentNode().sendKeys(Keys.TAB);
        waitForPpr(); // for when autosubmit=true
    }

    public void typeValue(String value) {
        clear();
        sendKeys(value);
        tabNext();
    }

    /**
     * Private method that will give back the {@link com.redheap.selenium.domain.PageMessageWrapper PageMessageWrapper}.
     * @return The {@link com.redheap.selenium.domain.PageMessageWrapper PageMessageWrapper} with all facesmessages
     * for the page.
     */
    private PageMessageWrapper getPageMessages() {
        return PageMessageWrapper.getAllMessages((JavascriptExecutor) getDriver());
    }

    /**
     * A convenience method that will check if this component has a specific message.
     * @param messageText The messagetext you want to check for.
     * @return  a boolean value with the result of the question if the supplied component has the supplied message.
     */
    public boolean hasMessage(String messageText) {
        return getPageMessages().hasMessage(getClientId(), messageText);
    }
}
