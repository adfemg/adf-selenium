package com.redheap.selenium.junit;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import oracle.adf.view.rich.automation.selenium.DialogManager;

import org.junit.rules.ExternalResource;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public abstract class WebDriverResource extends ExternalResource {

    private RemoteWebDriver driver;
    private final int width;
    private final int height;
    private final Locale locale;

    private static final Logger logger = Logger.getLogger(WebDriverResource.class.getName());

    public WebDriverResource() {
        this(1920, 1200, Locale.US);
    }

    public WebDriverResource(int width, int height, Locale locale) {
        this.width = width;
        this.height = height;
        this.locale = locale;
    }

    @Override
    protected void before() throws Throwable {
        logger.fine("starting browser...");
        driver = createDriver(locale.toLanguageTag());
        if (logger.isLoggable(Level.FINE)) {
            Capabilities capabilities = driver.getCapabilities();
            logger.fine("running " + capabilities.getBrowserName() + " version " + capabilities.getVersion() + " on " +
                        capabilities.getPlatform());
        }
        DialogManager.init(driver, 10000); // timeout of 10 seconds
        driver.manage().window().maximize();
    }

    protected abstract RemoteWebDriver createDriver(String language);

    @Override
    protected void after() {
        logger.fine("quit browser...");
        driver.quit();
    }

    public RemoteWebDriver getDriver() {
        return driver;
    }

    public DialogManager getDialogManager() {
        return DialogManager.getInstance();
    }

}
