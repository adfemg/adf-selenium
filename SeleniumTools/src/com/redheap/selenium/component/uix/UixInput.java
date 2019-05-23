package com.redheap.selenium.component.uix;

import com.redheap.selenium.domain.PageMessageWrapper;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public abstract class UixInput extends UixValue {

    private static final String JS_GET_VALID = JS_FIND_COMPONENT + "return comp.getValid();";
    private static final String JS_GET_SUBMITTED_VALUE = JS_FIND_ELEMENT + "return peer.GetSubmittedValue(comp,elem);";
    private static final String JS_GET_LABEL = JS_FIND_COMPONENT + "return comp.getLabel();";

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

    /**
     * Clear element value. Normally you would use {@code CTRL + A} to select all characters, but because this causes
     * issues in (some?) ADF tables, we use the below key combination for Windows. Afterwards the {@code DEL} key is
     * used to actually remove the value.
     * <ul>
     *  <li>{@code CTRL + SHIFT + HOME} to move cursor + select to the first character (multi-line is supported).
     *  <li>{@code HOME} to lose select, but keep the cursor at the first character.
     *  <li>{@code CTRL + SHIFT + END} to move cursor + select to the last character (multi-line is supported).
     * </ul>
     */
    public void clear() {
        Actions actions = new Actions(getDriver());
        if (isPlatform(Platform.MAC)) {
            actions.sendKeys(findContentNode(), Keys.chord(Keys.COMMAND, "a"), Keys.DELETE);
        } else {
            actions.sendKeys(findContentNode(),
                             new CharSequence[] { Keys.chord(Keys.CONTROL, Keys.SHIFT, Keys.HOME), Keys.HOME,
                                                  Keys.chord(Keys.CONTROL, Keys.SHIFT, Keys.END), Keys.DELETE });
        }
        actions.perform();
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

    public void typeValueWithoutTab(String value) {
        clear();
        sendKeys(value);
        
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
