package com.redheap.selenium.component.uix;

import com.redheap.selenium.domain.FacesMessageDomain;

import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;

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

    public void clear() {
        if (isPlatform(Platform.MAC)) {
            findContentNode().sendKeys(Keys.chord(Keys.COMMAND, "a"), Keys.DELETE);
        } else {
            findContentNode().sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
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

    public Map getMessages() {
        return FacesMessageDomain.getComponentMessages((JavascriptExecutor)getDriver(), getClientId());
    }

    public Map getPageMessages() {
        return FacesMessageDomain.getAllMessages((JavascriptExecutor)getDriver());
    }
}
