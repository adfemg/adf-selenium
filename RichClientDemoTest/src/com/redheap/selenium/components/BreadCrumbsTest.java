package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfBreadCrumbs;
import com.redheap.selenium.junit.PageProvider;
import com.redheap.selenium.junit.SavePageSourceOnFailure;
import com.redheap.selenium.junit.ScreenshotOnFailure;
import com.redheap.selenium.junit.WebDriverResource;
import com.redheap.selenium.pages.BreadCrumbsDemoPage;

import java.io.File;

import static org.junit.Assert.*;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

public class BreadCrumbsTest {

    @ClassRule
    public static WebDriverResource driver = new WebDriverResource();
    @Rule
    public PageProvider<BreadCrumbsDemoPage> pages =
        new PageProvider(BreadCrumbsDemoPage.class, HOME_PAGE, driver.getDriver());
    @Rule
    public TestWatcher screenshotOnFailure = new ScreenshotOnFailure(driver.getDriver(), new File("errors"));
    @Rule
    public TestWatcher saveSourceOnFailure = new SavePageSourceOnFailure(driver.getDriver(), new File("errors"));

    private static final String HOME_PAGE = "http://localhost:7101/adf-richclient-demo/faces/components/breadCrumbs.jspx";

    @Test
    public void testBreadCrumbs() {
        AdfBreadCrumbs breadCrumbs = pages.goHome().findBreadCrumbs();
        assertEquals(5, breadCrumbs.getCrumbs().size());
    }

    public static void main(String[] args) {
        String[] args2 = { BreadCrumbsTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

}
