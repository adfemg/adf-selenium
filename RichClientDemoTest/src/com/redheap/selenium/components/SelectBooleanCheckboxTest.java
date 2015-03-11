package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfSelectBooleanCheckbox;
import com.redheap.selenium.junit.PageProvider;
import com.redheap.selenium.junit.SavePageSourceOnFailure;
import com.redheap.selenium.junit.ScreenshotOnFailure;
import com.redheap.selenium.junit.WebDriverResource;
import com.redheap.selenium.pages.SelectBooleanCheckboxDemoPage;

import java.io.File;

import static org.junit.Assert.*;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;


public class SelectBooleanCheckboxTest {

    @ClassRule
    public static WebDriverResource driver = new WebDriverResource();
    @Rule
    public PageProvider<SelectBooleanCheckboxDemoPage> pages = new PageProvider(SelectBooleanCheckboxDemoPage.class, HOME_PAGE, driver.getDriver());
    @Rule
    public TestWatcher screenshotOnFailure = new ScreenshotOnFailure(driver.getDriver(), new File("errors"));
    @Rule
    public TestWatcher saveSourceOnFailure = new SavePageSourceOnFailure(driver.getDriver(), new File("errors"));

    private static final String HOME_PAGE = "http://localhost:7101/adf-richclient-demo/faces/components/selectBooleanCheckbox.jspx";

    @Test
    public void testText() {
        assertEquals("Extra keys required", pages.goHome().findExtraKeys().getText());
    }

    @Test
    public void testLabel() {
        assertEquals("Extra Keys", pages.goHome().findExtraKeys().getLabel());
    }

    @Test
    public void testValues() {
        AdfSelectBooleanCheckbox box = pages.goHome().findExtraKeys();
        assertTrue(Boolean.FALSE.equals(box.getValue()));
        box.click();
        assertTrue(Boolean.TRUE.equals(box.getValue()));
    }

    @Test
    public void testNonTriState() {
        AdfSelectBooleanCheckbox box = pages.goHome().findExtraKeys();
        assertFalse(box.isTriState());
    }

    @Test
    public void testTriState() {
        AdfSelectBooleanCheckbox box = pages.goHome().findTriStateCheckbox();
        assertTrue(box.isTriState());
        assertEquals("mixed", box.getNullValueMeans());
        assertTrue(box.isNull());
        assertTrue(Boolean.FALSE.equals(box.getValue())); // this one is a bit strange, would expect value==null
        box.click();
        assertFalse(box.isNull());
        assertTrue(Boolean.TRUE.equals(box.getValue()));
        box.click();
        assertFalse(box.isNull());
        assertTrue(Boolean.FALSE.equals(box.getValue()));
    }

    public static void main(String[] args) {
        String[] args2 = { SelectBooleanCheckboxTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

}
