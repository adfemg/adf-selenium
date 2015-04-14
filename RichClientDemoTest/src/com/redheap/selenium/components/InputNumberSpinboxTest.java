package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfInputNumberSpinbox;
import com.redheap.selenium.junit.PageProvider;
import com.redheap.selenium.junit.SavePageSourceOnFailure;
import com.redheap.selenium.junit.ScreenshotOnFailure;
import com.redheap.selenium.junit.WebDriverResource;
import com.redheap.selenium.pages.InputNumberSpinboxDemoPage;

import java.io.File;

import java.text.SimpleDateFormat;

import java.util.Date;

import static org.junit.Assert.*;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

public class InputNumberSpinboxTest {

    @ClassRule
    public static WebDriverResource driver = new WebDriverResource();
    @Rule
    public PageProvider<InputNumberSpinboxDemoPage> pages =
        new PageProvider(InputNumberSpinboxDemoPage.class, HOME_PAGE, driver.getDriver());
    @Rule
    public TestWatcher screenshotOnFailure = new ScreenshotOnFailure(driver.getDriver(), new File("errors"));
    @Rule
    public TestWatcher saveSourceOnFailure = new SavePageSourceOnFailure(driver.getDriver(), new File("errors"));

    private static final String HOME_PAGE = "http://localhost:7101/adf-richclient-demo/faces/components/inputNumberSpinbox.jspx";

    private String today(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    @Test
    public void testPlainSpinbox() {
        AdfInputNumberSpinbox spinbox = pages.goHome().findSpinbox();
        assertEquals(1979L, spinbox.getValue());
        long initialValue = (Long)spinbox.getValue();
        spinbox.clickDecrement();
        assertEquals(initialValue - 1, spinbox.getValue());
        spinbox.clickIncrement();
        assertEquals(initialValue, spinbox.getValue());
    }

    public static void main(String[] args) {
        String[] args2 = { InputNumberSpinboxTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

}
