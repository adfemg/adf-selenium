package com.redheap.selenium.page;

import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public final class BaseObject implements TakesScreenshot /*implements SearchContext, JavascriptExecutor, TakesScreenshot*/ {

    RemoteWebDriver driver;

    private static final Logger logger = Logger.getLogger(BaseObject.class.getName());

    public BaseObject(WebDriver driver) {
        // we always run with WebDriver which always uses RemoteWebDriver subclasses which have a number of
        // convenience methods since they implement a number of additional interfaces.
        if (!(driver instanceof RemoteWebDriver)) {
            throw new IllegalArgumentException("only RemoteWebDriver are supported: " + driver);
        }
        this.driver = (RemoteWebDriver) driver;
    }

    public BaseObject(BaseObject oldObject) {
        this(oldObject.getDriver());
    }

    private RemoteWebDriver getDriver() {
        return driver;
    }

    //    protected <T extends AdfComponent> T findAdfComponent(By by, Class<? extends T> cls) {
    //        return AdfComponent.forElement(findElement(by), cls);
    //    }

    //@Override
    protected List<WebElement> findElements(By by) {
        return getDriver().findElements(by);
    }

    //@Override
    protected WebElement findElement(By by) {
        return getDriver().findElement(by);
    }

    //@Override
    protected Object executeScript(String javascript, Object... args) {
        JavascriptExecutor jsDriver = getDriver();
        logger.finer("Executing script " + javascript);
        Object result = jsDriver.executeScript(javascript);
        logger.finer("Executed script returned: " + result +
                     (result == null ? "" : String.format(" (%s)", result.getClass())));
        return result;
    }

    //@Override
    protected Object executeAsyncScript(String script, Object... args) {
        return getDriver().executeAsyncScript(script, args);
    }

    @Override
    public <X extends Object> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return getDriver().getScreenshotAs(target);
    }
}
