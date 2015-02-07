package com.redheap.selenium.conditions;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class ClientSynchedWithServer implements ExpectedCondition<Boolean> {
    
    ClientSynchedWithServer() {
    }

    @Override
    public Boolean apply(WebDriver driver) {
        JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
        Object result = jsDriver.executeScript("return AdfPage.PAGE.isSynchronizedWithServer()");
        System.out.println("ppr complete: " + result);
        return (Boolean) result;
    }
}
