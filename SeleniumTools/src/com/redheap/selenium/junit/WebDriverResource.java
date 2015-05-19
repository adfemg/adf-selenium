package com.redheap.selenium.junit;

import java.util.logging.Level;
import java.util.logging.Logger;

import oracle.adf.view.rich.automation.selenium.DialogManager;

import org.junit.rules.ExternalResource;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WebDriverResource extends ExternalResource {

    private boolean quit;

    private RemoteWebDriver driver;

    private static final Logger logger = Logger.getLogger(WebDriverResource.class.getName());

    public WebDriverResource() {
        this(true);
    }

    public WebDriverResource(boolean quitAtEnd) {
        this.quit = quitAtEnd;
    }

    @Override
    protected void before() throws Throwable {
        logger.fine("starting Firefox...");
        driver = new FirefoxDriver(createProfile());
        if (!Long.valueOf(10).equals(driver.executeScript("return arguments[0]", 10))) {
            throw new WebDriverException("This browser version is not supported due to Selenium bug 8387. See https://code.google.com/p/selenium/issues/detail?id=8387");
        }
        if (logger.isLoggable(Level.FINE)) {
            Capabilities capabilities = driver.getCapabilities();
            logger.fine("running " + capabilities.getBrowserName() + " version " + capabilities.getVersion() + " on " +
                        capabilities.getPlatform());
        }
        DialogManager.init(driver, 10000); // timeout of 10 seconds
    }

    @Override
    protected void after() {
        if (quit) {
            logger.fine("quit firefox...");
            driver.quit();
        }
    }

    public RemoteWebDriver getDriver() {
        return driver;
    }

    public DialogManager getDialogManager() {
        return DialogManager.getInstance();
    }

    protected FirefoxProfile createProfile() {
        FirefoxProfile profile = new FirefoxProfile();
        // native events cause "Component returned failure code: 0x80004005 (NS_ERROR_FAILURE) [nsINativeMouse.click]"
        // on Windows with multiple calls to AdfSelectOneChoice.clickItemByIndex (and others)
        profile.setEnableNativeEvents(false);
        profile.setPreference("app.update.enabled", false); // don't bother updating Firefox (takes too much time)
        return profile;
    }

}
