package com.redheap.selenium.component.uix;

import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;

public abstract class UixInput extends UixValue {

    public UixInput(WebDriver webDriver, String clientid) {
        super(webDriver, clientid);
    }

    public boolean isValid() {
        String js = String.format("%s.getValid()", scriptFindComponent());
        return (Boolean) executeScript(js);
    }

    public String getSubmittedValue() {
        String js =
            String.format("%s.GetSubmittedValue(%s,%s)", scriptBoundPeer(), scriptFindComponent(), scriptDomElement());
        return (String) executeScript(js);
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
    }

    public void typeValue(String value) {
        clear();
        sendKeys(Keys.BACK_SPACE, value);
        tabNext();
    }

}
