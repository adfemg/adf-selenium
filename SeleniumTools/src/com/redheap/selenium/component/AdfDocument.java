package com.redheap.selenium.component;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;


public class AdfDocument extends AdfComponent {

    private static final String JS_SET_ANIMATION_ENABLED = "return AdfPage.PAGE.setAnimationEnabled(arguments[0])";
    private static final String JS_IS_AUTOMATION_ENABLED = "return AdfPage.PAGE.isAutomationEnabled()";

    public AdfDocument(WebDriver driver, String clientid) {
        super(driver, clientid);
    }

    public static AdfDocument forDriver(WebDriver driver) {
        JavascriptExecutor jsdriver = (JavascriptExecutor) driver;
        String docid = (String) jsdriver.executeScript("return AdfPage.PAGE.getDocumentClientId()");
        return AdfComponent.forClientId(driver, docid);
    }

    public void setAnimationEnabled(boolean enabled) {
        executeScript(JS_SET_ANIMATION_ENABLED, enabled);
    }

    public boolean isAutomationEnabled() {
        return (Boolean) executeScript(JS_IS_AUTOMATION_ENABLED);
    }

}
