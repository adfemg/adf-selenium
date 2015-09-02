package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfCommandLink;
import com.redheap.selenium.junit.PageProvider;
import com.redheap.selenium.junit.SavePageSourceOnFailure;
import com.redheap.selenium.junit.ScreenshotOnFailure;
import com.redheap.selenium.junit.WebDriverResource;
import com.redheap.selenium.pages.NoteWindowDemoPage;

import java.io.File;

import static org.junit.Assert.*;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

public class NoteWindowTest {

    @ClassRule
    public static WebDriverResource driver = new WebDriverResource();
    @Rule
    public PageProvider<NoteWindowDemoPage> pages =
        new PageProvider(NoteWindowDemoPage.class, HOME_PAGE, driver.getDriver());
    @Rule
    public TestWatcher screenshotOnFailure = new ScreenshotOnFailure(driver.getDriver(), new File("errors"));
    @Rule
    public TestWatcher saveSourceOnFailure = new SavePageSourceOnFailure(driver.getDriver(), new File("errors"));

    private static final String HOME_PAGE = "http://localhost:7101/adf-richclient-demo/faces/components/noteWindow.jspx";

    @Test
    public void testHoverNoteWindow() {
        NoteWindowDemoPage demoPage = pages.goHome();
        AdfCommandLink link = demoPage.findScavengingLink();
        assertNotNull(demoPage.findScavengingPopup());
        assertFalse(demoPage.findScavengingPopup().isPopupVisible());

        link.hover();
        assertTrue(demoPage.findScavengingPopup().isPopupVisible());
    }

    public static void main(String[] args) {
        String[] args2 = { NoteWindowTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

}
