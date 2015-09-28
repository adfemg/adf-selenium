package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfInputDate;
import com.redheap.selenium.pages.InputDateDemoPage;

import java.text.SimpleDateFormat;

import java.util.Date;

import static org.hamcrest.Matchers.*;

import static org.junit.Assert.*;
import org.junit.Test;

public class InputDateTest extends PageTestBase<InputDateDemoPage> {

    private String today(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    @Test
    public void testPlainDate() {
        AdfInputDate id = pages.goHome().findPlainInputDate();
        // getvalue() returns Date (which is converted to string due to JSON communication)
        assertThat(id.getValue().toString(), startsWith(today("yyyy-MM-dd")));
        // getSubmittedValue() returns value as shown in the <input> element
        assertEquals(today("M/d/yyyy"), id.getSubmittedValue());
        assertNull(id.getTimezoneId());
        assertNull(id.getTimezoneName());
    }

    @Test
    public void testDateOnly() {
        AdfInputDate id = pages.goHome().findDateOnly();
        assertThat(id.getValue().toString(), startsWith(today("yyyy-MM-dd")));
        assertEquals(today("M/d/yyyy"), id.getSubmittedValue());
        assertNull(id.getTimezoneId());
        assertNull(id.getTimezoneName());
    }

    @Test
    public void testDateAndTime() {
        AdfInputDate id = pages.goHome().findDateAndTime();
        assertThat(id.getValue().toString(), startsWith(today("yyyy-MM-dd")));
        assertEquals(today("EEEE, MMMM d, yyyy h:mm aa"), id.getSubmittedValue());
        assertNull(id.getTimezoneId());
        assertNull(id.getTimezoneName());
    }

    @Test
    public void testTimezone() {
        AdfInputDate id = pages.goHome().findDateWithTimezone();
        assertThat(id.getValue().toString(), startsWith(today("yyyy-MM-dd")));
        assertThat(id.getSubmittedValue(), containsString(today("yyyy/MM/dd hh:mm")));
        assertEquals("Europe/Amsterdam", id.getTimezoneId());
        assertThat(id.getTimezoneName(), containsString("(CET)"));
    }

    public static void main(String[] args) {
        String[] args2 = { InputDateTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

    @Override
    protected Class<InputDateDemoPage> getPageClass() {
        return InputDateDemoPage.class;
    }

    @Override
    protected String getJspxName() {
        return "inputDate.jspx";
    }
}
