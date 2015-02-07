package com.redheap.selenium.conditions;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class ClientSynchedWithServer implements ExpectedCondition<Boolean> {

    String js =
        "return typeof AdfPage !== 'undefined' && typeof AdfPage.PAGE !== 'undefined'" +
        " && typeof AdfPage.PAGE.isSynchronizedWithServer === 'function' && AdfPage.PAGE.isSynchronizedWithServer()";

    ClientSynchedWithServer() {
    }

    @Override
    public Boolean apply(WebDriver driver) {
        JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
        Object result = jsDriver.executeScript(js);
        System.out.println("client ready: " + result);
        return (Boolean) result;
    }
}
