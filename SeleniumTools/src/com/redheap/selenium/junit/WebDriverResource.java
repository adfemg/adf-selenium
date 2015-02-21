package com.redheap.selenium.junit;

import java.util.logging.Logger;

import org.junit.rules.ExternalResource;

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

    protected FirefoxProfile createProfile() {
        FirefoxProfile profile = new FirefoxProfile();
        profile.setEnableNativeEvents(true); // needed for Mac OSX (default is non-native which doesn't work with ADF)
        profile.setPreference("app.update.enabled", false); // don't bother updating Firefox (takes too much time)
        return profile;
    }

}
