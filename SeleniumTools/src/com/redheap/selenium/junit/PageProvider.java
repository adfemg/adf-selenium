package com.redheap.selenium.junit;

import com.redheap.selenium.page.Page;

import java.util.logging.Logger;

import org.junit.rules.ExternalResource;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

public class PageProvider<P extends Page> extends ExternalResource {

    private final Class<P> cls;
    private final String url;
    private final WebDriver driver;

    private static final Logger logger = Logger.getLogger(WebDriverResource.class.getName());

    public PageProvider(Class<P> cls, String url, WebDriver driver) {
        this.cls = cls;
        this.url = url;
        this.driver = driver;
    }

    @Override
    protected void after() {
        clearCookies();
    }

    public P goHome() {
        clearCookies();
        // navigate to homepage
        logger.fine("navigating to " + url + "...");
        driver.get(url);
        return createPage(cls);
    }

    protected void clearCookies() {
        // clear session cookie before each test so we start with a clean session
        logger.fine("delete all cookies");
        driver.manage().deleteAllCookies();
    }

    protected P createPage(Class<P> cls) {
        try {
            return cls.getConstructor(WebDriver.class).newInstance(driver);
        } catch (Exception e) {
            throw new WebDriverException(e.getCause() != null ? e.getCause() : e);
        }
    }

}
