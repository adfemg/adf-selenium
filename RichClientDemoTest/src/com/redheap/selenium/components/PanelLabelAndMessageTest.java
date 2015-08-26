package com.redheap.selenium.components;

import com.redheap.selenium.junit.PageProvider;
import com.redheap.selenium.junit.SavePageSourceOnFailure;
import com.redheap.selenium.junit.ScreenshotOnFailure;
import com.redheap.selenium.junit.WebDriverResource;
import com.redheap.selenium.pages.PanelLabelAndMessageDemoPage;

import java.io.File;

import static org.junit.Assert.*;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

public class PanelLabelAndMessageTest {

    @ClassRule
    public static WebDriverResource driver = new WebDriverResource();
    @Rule
    public PageProvider<PanelLabelAndMessageDemoPage> pages =
        new PageProvider(PanelLabelAndMessageDemoPage.class, HOME_PAGE, driver.getDriver());
    @Rule
    public TestWatcher screenshotOnFailure = new ScreenshotOnFailure(driver.getDriver(), new File("errors"));
    @Rule
    public TestWatcher saveSourceOnFailure = new SavePageSourceOnFailure(driver.getDriver(), new File("errors"));

    private static final String HOME_PAGE = "http://localhost:7101/adf-richclient-demo/faces/components/panelLabelAndMessage.jspx";

    @Test
    public void testLabel() {
        PanelLabelAndMessageDemoPage demoPage = pages.goHome();
        assertEquals("Label", demoPage.findPanelLabelAndMessage().getLabel());
    }

    public static void main(String[] args) {
        String[] args2 = { PanelLabelAndMessageTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

}
