package com.redheap.selenium.components;

import com.redheap.selenium.junit.FirefoxDriverResource;
import com.redheap.selenium.junit.PageProvider;
import com.redheap.selenium.junit.SavePageSourceOnFailure;
import com.redheap.selenium.junit.ScreenshotOnFailure;
import com.redheap.selenium.junit.WebDriverResource;
import com.redheap.selenium.page.Page;

import java.io.File;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.TestWatcher;

public abstract class PageTestBase<P extends Page> {

    private static final String SYS_PROP = "adf.facesdemo.baseurl";

    @ClassRule
    public static WebDriverResource driver = new FirefoxDriverResource(); // or new PhantomJSDriverResource();
    @Rule
    public PageProvider<P> pages = new PageProvider(getPageClass(), getHomePage(), driver.getDriver());
    @Rule
    public TestWatcher screenshotOnFailure = new ScreenshotOnFailure(driver.getDriver(), new File("errors"));
    @Rule
    public TestWatcher saveSourceOnFailure = new SavePageSourceOnFailure(driver.getDriver(), new File("errors"));

    protected abstract Class<P> getPageClass();

    protected abstract String getJspxName();

    private String getHomePage() {
        String baseUrl = System.getProperty(SYS_PROP);
        if (baseUrl == null) {
            throw new IllegalStateException("system property " + SYS_PROP +
                                            " should contain base URL, for example http://localhost:7101/faces-12.2.1.0.0");
        }
        return baseUrl + "/faces/components/" + getJspxName();
    }
}
