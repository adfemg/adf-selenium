package com.redheap.selenium.component;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;


public class AdfDocument extends AdfComponent {

    public AdfDocument(WebDriver driver, String clientid) {
        super(driver, clientid);
    }

    @Override
    protected String getExpectedComponentType() {
        return "oracle.adf.RichDocument";
    }

    public static AdfDocument forDriver(WebDriver driver) {
        JavascriptExecutor jsdriver = (JavascriptExecutor) driver;
        String docid = (String) jsdriver.executeScript("return AdfPage.PAGE.getDocumentClientId()");
        return AdfComponent.forClientId(driver, docid, AdfDocument.class);
    }

    public void setAnimationEnabled(boolean enabled) {
        executeScript(String.format("AdfPage.PAGE.setAnimationEnabled(%b)", enabled));
    }

    public boolean isAutomationEnabled() {
        return Boolean.TRUE.equals(executeScript("AdfPage.PAGE.isAutomationEnabled()"));
    }

}
