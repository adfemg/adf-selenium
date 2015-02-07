package com.redheap.selenium.pages;

import com.redheap.selenium.conditions.AdfConditions;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageObject implements TakesScreenshot {

    WebDriver driver;

    public static final long DFLT_WAIT_TIMEOUT_SECS = 10;
    public static final long DFLT_WAIT_INTERVAL_MSECS = 100;

    public PageObject(WebDriver driver) {
        this.driver = driver;
        waitForPpr();
    }

    protected void waitForPpr() {
        waitForPpr(DFLT_WAIT_TIMEOUT_SECS, DFLT_WAIT_INTERVAL_MSECS);
    }

    protected void waitForPpr(long timeOutInSeconds) {
        waitForPpr(timeOutInSeconds, DFLT_WAIT_INTERVAL_MSECS);
    }

    protected void waitForPpr(long timeOutInSeconds, long sleepInMillis) {
        new WebDriverWait(driver, timeOutInSeconds, sleepInMillis).until(AdfConditions.clientSynchedWithServer());
    }

    protected WebDriver getDriver() {
        return driver;
    }

    protected Object executeScript(String javascript) {
        JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
        System.out.println("Executing script " + javascript);
        Object result = jsDriver.executeScript(javascript);
        System.out.println("Executed script returned: " + result +
                           (result == null ? "" : String.format(" (%s)", result.getClass())));
        return result;
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        System.out.println("taking screenshot to " + target);
        return ((TakesScreenshot)driver).getScreenshotAs(target);
    }

}
