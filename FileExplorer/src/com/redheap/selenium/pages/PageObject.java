package com.redheap.selenium.pages;

import com.redheap.selenium.conditions.AdfConditions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageObject {

    WebDriver driver;

    public PageObject(WebDriver driver) {
        this.driver = driver;
        waitForPpr();
    }

    protected void waitForPpr() {
        waitForPpr(10);
    }

    protected void waitForPpr(int seconds) {
        new WebDriverWait(driver, seconds).until(AdfConditions.clientSynchedWithServer());
    }


}
