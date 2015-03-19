package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfInputDate;
import com.redheap.selenium.junit.PageProvider;
import com.redheap.selenium.junit.SavePageSourceOnFailure;
import com.redheap.selenium.junit.ScreenshotOnFailure;
import com.redheap.selenium.junit.WebDriverResource;
import com.redheap.selenium.pages.InputDateDemoPage;

import java.io.File;

import java.text.SimpleDateFormat;

import java.util.Date;

import static org.hamcrest.Matchers.*;

import static org.junit.Assert.*;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

public class InputDateTest {

    @ClassRule
    public static WebDriverResource driver = new WebDriverResource();
    @Rule
    public PageProvider<InputDateDemoPage> pages =
        new PageProvider(InputDateDemoPage.class, HOME_PAGE, driver.getDriver());
    @Rule
    public TestWatcher screenshotOnFailure = new ScreenshotOnFailure(driver.getDriver(), new File("errors"));
    @Rule
    public TestWatcher saveSourceOnFailure = new SavePageSourceOnFailure(driver.getDriver(), new File("errors"));

    private static final String HOME_PAGE = "http://localhost:7101/adf-richclient-demo/faces/components/inputDate.jspx";

    private String today(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    @Test
    public void testPlainDate() {
        AdfInputDate id = pages.goHome().findPlainInputDate();
        // getvalue() returns Date (which is converted to string due to JSON communication)
        assertTrue(id.getValue().toString().startsWith(today("yyyy-MM-dd")));
        // getSubmittedValue() returns value as shown in the <input> element
        assertEquals(today("M/d/yyyy"), id.getSubmittedValue());
        assertNull(id.getTimezoneId());
        assertNull(id.getTimezoneName());
    }

    @Test
    public void testDateOnly() {
        AdfInputDate id = pages.goHome().findDateOnly();
        assertTrue(id.getValue().toString().startsWith(today("yyyy-MM-dd")));
        assertEquals(today("M/d/yyyy"), id.getSubmittedValue());
        assertNull(id.getTimezoneId());
        assertNull(id.getTimezoneName());
    }

    @Test
    public void testDateAndTime() {
        AdfInputDate id = pages.goHome().findDateAndTime();
        assertTrue(id.getValue().toString().startsWith(today("yyyy-MM-dd")));
        assertEquals(today("EEEE, MMMM d, yyyy h:mm aa"), id.getSubmittedValue());
        assertNull(id.getTimezoneId());
        assertNull(id.getTimezoneName());
    }

    @Test
    public void testTimezone() {
        AdfInputDate id = pages.goHome().findDateWithTimezone();
        assertTrue(id.getValue().toString().startsWith(today("yyyy-MM-dd")));
        assertThat(id.getSubmittedValue(), containsString(today("yyyy/MM/dd hh:mm")));
        assertEquals("Europe/Amsterdam", id.getTimezoneId());
        assertThat(id.getTimezoneName(), containsString("(CET)"));
    }

    public static void main(String[] args) {
        String[] args2 = { InputDateTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

}
