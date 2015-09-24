package com.redheap.selenium.components;

import com.redheap.selenium.junit.PageProvider;
import com.redheap.selenium.junit.PhantomJSDriverResource;
import com.redheap.selenium.junit.SavePageSourceOnFailure;
import com.redheap.selenium.junit.ScreenshotOnFailure;
import com.redheap.selenium.junit.WebDriverResource;
import com.redheap.selenium.page.Page;

import java.io.File;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.TestWatcher;

public abstract class PageTestBase<P extends Page> {

    @ClassRule
    public static WebDriverResource driver = new PhantomJSDriverResource();
    @Rule
    public PageProvider<P> pages = new PageProvider(getPageClass(), getHomePage(), driver.getDriver());
    @Rule
    public TestWatcher screenshotOnFailure = new ScreenshotOnFailure(driver.getDriver(), new File("errors"));
    @Rule
    public TestWatcher saveSourceOnFailure = new SavePageSourceOnFailure(driver.getDriver(), new File("errors"));

    protected abstract Class<P> getPageClass();

    protected abstract String getJspxName();

    private String getHomePage() {
        return "http://localhost:7101/adf-richclient-demo/faces/components/" + getJspxName();
    }
}
