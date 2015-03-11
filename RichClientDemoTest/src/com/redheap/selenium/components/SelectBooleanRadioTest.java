package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfSelectBooleanRadio;
import com.redheap.selenium.junit.PageProvider;
import com.redheap.selenium.junit.SavePageSourceOnFailure;
import com.redheap.selenium.junit.ScreenshotOnFailure;
import com.redheap.selenium.junit.WebDriverResource;
import com.redheap.selenium.pages.SelectBooleanRadioDemoPage;

import java.io.File;

import static org.junit.Assert.*;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;


public class SelectBooleanRadioTest {

    @ClassRule
    public static WebDriverResource driver = new WebDriverResource();
    @Rule
    public PageProvider<SelectBooleanRadioDemoPage> pages = new PageProvider(SelectBooleanRadioDemoPage.class, HOME_PAGE, driver.getDriver());
    @Rule
    public TestWatcher screenshotOnFailure = new ScreenshotOnFailure(driver.getDriver(), new File("errors"));
    @Rule
    public TestWatcher saveSourceOnFailure = new SavePageSourceOnFailure(driver.getDriver(), new File("errors"));

    private static final String HOME_PAGE = "http://localhost:7101/adf-richclient-demo/faces/components/selectBooleanRadio.jspx";

    @Test
    public void testText() {
        assertEquals("10-18", pages.goHome().findAge10to18().getText());
    }

    @Test
    public void testLabel() {
        assertEquals("Age", pages.goHome().findAge10to18().getLabel());
    }

    @Test
    public void testValues() {
        AdfSelectBooleanRadio radio = pages.goHome().findAge10to18();
        assertTrue(Boolean.FALSE.equals(radio.getValue()));
        radio.click();
        assertTrue(Boolean.TRUE.equals(radio.getValue()));
    }

    @Test
    public void testGroupSize() {
        AdfSelectBooleanRadio radio = pages.goHome().findAge10to18();
        assertEquals(8, radio.findGroupItems().size());
    }

    @Test
    public void testGroup() {
        AdfSelectBooleanRadio radio = pages.goHome().findAge10to18();
        assertTrue(Boolean.FALSE.equals(radio.getValue()));
        radio.click();
        assertTrue(Boolean.TRUE.equals(radio.getValue()));
        radio.findGroupItems().get(3).click();
        assertTrue(Boolean.FALSE.equals(radio.getValue()));
    }

    public static void main(String[] args) {
        String[] args2 = { SelectBooleanRadioTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

}
