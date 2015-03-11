package com.redheap.selenium.components;

import com.redheap.selenium.junit.PageProvider;
import com.redheap.selenium.junit.SavePageSourceOnFailure;
import com.redheap.selenium.junit.ScreenshotOnFailure;
import com.redheap.selenium.junit.WebDriverResource;
import com.redheap.selenium.pages.OutputFormattedDemoPage;

import java.io.File;

import static org.junit.Assert.*;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;


public class OutputFormattedTest {

    @ClassRule
    public static WebDriverResource driver = new WebDriverResource();
    @Rule
    public PageProvider<OutputFormattedDemoPage> pages =
        new PageProvider(OutputFormattedDemoPage.class, HOME_PAGE, driver.getDriver());
    @Rule
    public TestWatcher screenshotOnFailure = new ScreenshotOnFailure(driver.getDriver(), new File("errors"));
    @Rule
    public TestWatcher saveSourceOnFailure = new SavePageSourceOnFailure(driver.getDriver(), new File("errors"));

    private static final String HOME_PAGE =
        "http://localhost:7101/adf-richclient-demo/faces/components/outputFormatted.jspx";

    @Test
    public void testOutput() {
        assertEquals("<b>Out</b>put", pages.goHome().findOutput().getValue());
    }

    @Test
    public void testRed() {
        assertEquals("<span style=\"color:red\">This should be red</span>", pages.goHome().findRed().getValue());
    }

    public static void main(String[] args) {
        String[] args2 = { OutputFormattedTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

}
