package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfCalendar;
import com.redheap.selenium.junit.PageProvider;
import com.redheap.selenium.junit.SavePageSourceOnFailure;
import com.redheap.selenium.junit.ScreenshotOnFailure;
import com.redheap.selenium.junit.WebDriverResource;
import com.redheap.selenium.pages.CalendarDemoPage;

import java.io.File;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;

import static org.hamcrest.Matchers.*;

import static org.junit.Assert.*;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

public class CalendarTest {

    @ClassRule
    public static WebDriverResource driver = new WebDriverResource();
    @Rule
    public PageProvider<CalendarDemoPage> pages =
        new PageProvider(CalendarDemoPage.class, HOME_PAGE, driver.getDriver());
    @Rule
    public TestWatcher screenshotOnFailure = new ScreenshotOnFailure(driver.getDriver(), new File("errors"));
    @Rule
    public TestWatcher saveSourceOnFailure = new SavePageSourceOnFailure(driver.getDriver(), new File("errors"));

    private static final String HOME_PAGE = "http://localhost:7101/adf-richclient-demo/faces/components/calendar.jspx";

    private final DateFormat yyyymmdd = new SimpleDateFormat("yyyyMMdd");

    @Test
    public void testCalendarActiveDay() {
        AdfCalendar calendar = pages.goHome().findCalendar();
        String activeDay = calendar.getActiveDay();
        calendar.findPreviousButton().click();
        assertNotEquals(activeDay, calendar.getActiveDay());
    }

    @Test
    public void testCalendarViews() {
        AdfCalendar calendar = pages.goHome().findCalendar();
        assertEquals(AdfCalendar.View.MONTH, calendar.getView());
        calendar.findDayViewButton().click();
        assertEquals(AdfCalendar.View.DAY, calendar.getView());
        calendar.findWeekViewButton().click();
        assertEquals(AdfCalendar.View.WEEK, calendar.getView());
        calendar.findMonthViewButton().click();
        assertEquals(AdfCalendar.View.MONTH, calendar.getView());
        calendar.findListViewButton().click();
        assertEquals(AdfCalendar.View.LIST, calendar.getView());
    }

    @Test
    public void testToday() {
        AdfCalendar calendar = pages.goHome().findCalendar();
        String activeDay = calendar.getActiveDay();
        calendar.findPreviousButton().click();
        assertNotEquals(activeDay, calendar.getActiveDay());
        calendar.findTodayButton().click();
        assertEquals(activeDay, calendar.getActiveDay());
    }

    @Test
    public void testPreviousNext() {
        AdfCalendar calendar = pages.goHome().findCalendar();
        String activeDay = calendar.getActiveDay();
        calendar.findPreviousButton().click();
        assertNotEquals(activeDay, calendar.getActiveDay());
        calendar.findNextButton().click();
        assertEquals(activeDay, calendar.getActiveDay());
        calendar.findNextButton().click();
        assertNotEquals(activeDay, calendar.getActiveDay());
        calendar.findPreviousButton().click();
        assertEquals(activeDay, calendar.getActiveDay());
    }

    @Test
    public void testActiveDay() {
        AdfCalendar calendar = pages.goHome().findCalendar();
        Calendar midnightToday = Calendar.getInstance();
        midnightToday.set(Calendar.HOUR_OF_DAY, 0);
        midnightToday.set(Calendar.MINUTE, 0);
        midnightToday.set(Calendar.SECOND, 0);
        midnightToday.set(Calendar.MILLISECOND, 0);
        assertEquals(midnightToday.getTime(), calendar.getActiveDayFromDom());
        calendar.findPreviousButton().click();
        Calendar monthAgo = (Calendar) midnightToday.clone();
        monthAgo.add(Calendar.MONTH, -1);
        assertEquals(monthAgo.getTime(), calendar.getActiveDayFromDom());
    }

    @Test
    public void testActivitiesInView() {
        AdfCalendar calendar = pages.goHome().findCalendar();
        // exact number is unknown as sample app uses random data
        assertThat(calendar.getActivitiesInViewCount(), greaterThan(10));
        calendar.clickActivityInView(0);
        // TODO: how can we test if click succeeded?
    }

    @Test
    public void testMoreLinksInView() {
        AdfCalendar calendar = pages.goHome().findCalendar();
        // exact number is unknown as sample app uses random data
        assertThat(calendar.getMoreLinksInViewCount(), greaterThan(5));
        calendar.clickMoreLinkInView(0);
        assertEquals(AdfCalendar.View.DAY, calendar.getView());
    }

    @Test
    public void testDateLink() {
        AdfCalendar calendar = pages.goHome().findCalendar();
        assertEquals(42, calendar.getDateLinkCount());
        calendar.clickDateLink(0);
        assertEquals(AdfCalendar.View.DAY, calendar.getView());
    }

    @Test
    public void testCreatePopup() {
        CalendarDemoPage page = pages.goHome();
        AdfCalendar calendar = page.findCalendar();
        assertNull("popup should not be openend before click", page.findCreatePopupAllDay());
        assertNull("popup should not be openend before click", page.findCreatePopupFrom());
        assertNull("popup should not be openend before click", page.findCreatePopupTo());
        final int day = 15;
        calendar.createDayActivity(day);
        assertTrue("popup should open with all-day activity", (Boolean) page.findCreatePopupAllDay().getValue());
        assertThat(page.findCreatePopupTo().getSubmittedValue(), containsString("/" + (day + 1) + "/"));
    }

    @Test
    public void testHover() {
        CalendarDemoPage page = pages.goHome();
        AdfCalendar calendar = page.findCalendar();
        calendar.hoverActivityInView(0);
        assertEquals("NOTE: This popup is for demo purposes only; it is not part of the built in functionality of the calendar.",
                     page.findPopupNote().getValue());
    }

    @Test
    public void testActivitiesText() {
        CalendarDemoPage page = pages.goHome();
        AdfCalendar calendar = page.findCalendar();
        for (int i=0, n=calendar.getActivitiesInViewCount(); i<n; i++) {
            String label = calendar.getActivityInViewLabel(i);
            assertTrue("activity " + i + " should have a label, not " + label, StringUtils.isNotBlank(label));
        }
    }

    @Test
    public void testGoToDateMonth() throws ParseException {
        CalendarDemoPage page = pages.goHome();
        AdfCalendar calendar = page.findCalendar();
        calendar.findMonthViewButton().click();
        // browse back to first day of month
        calendar.goToDate(yyyymmdd.parse("20150301"));
        assertThat(calendar.getActiveDayFromDomCalendar().get(Calendar.MONTH), is(Calendar.MARCH));
        assertThat(calendar.getActiveDayFromDomCalendar().get(Calendar.YEAR), is(2015));
        // browse back to middle of month
        calendar.goToDate(yyyymmdd.parse("20150115"));
        assertThat(calendar.getActiveDayFromDomCalendar().get(Calendar.MONTH), is(Calendar.JANUARY));
        assertThat(calendar.getActiveDayFromDomCalendar().get(Calendar.YEAR), is(2015));
        // browse forward to last day of month
        calendar.goToDate(yyyymmdd.parse("20150430"));
        assertThat(calendar.getActiveDayFromDomCalendar().get(Calendar.MONTH), is(Calendar.APRIL));
        assertThat(calendar.getActiveDayFromDomCalendar().get(Calendar.YEAR), is(2015));
    }

    @Test
    public void testGoToDateWeek() throws ParseException {
        CalendarDemoPage page = pages.goHome();
        AdfCalendar calendar = page.findCalendar();
        calendar.findWeekViewButton().click();
        // browse back to first day of week
        calendar.goToDate(yyyymmdd.parse("20150504")); // monday
        assertThat(calendar.getActiveDayFromDomCalendar().get(Calendar.MONTH), is(Calendar.MAY));
        assertThat(calendar.getActiveDayFromDomCalendar().get(Calendar.YEAR), is(2015));
        assertThat(calendar.getActiveDayFromDomCalendar().get(Calendar.WEEK_OF_YEAR), is(19));
        // browse back to middle of week
        calendar.goToDate(yyyymmdd.parse("20150422")); // wednesday
        assertThat(calendar.getActiveDayFromDomCalendar().get(Calendar.MONTH), is(Calendar.APRIL));
        assertThat(calendar.getActiveDayFromDomCalendar().get(Calendar.YEAR), is(2015));
        assertThat(calendar.getActiveDayFromDomCalendar().get(Calendar.WEEK_OF_YEAR), is(17));
        // browse forward to last day of week
        calendar.goToDate(yyyymmdd.parse("20150516")); // saturday
        assertThat(calendar.getActiveDayFromDomCalendar().get(Calendar.MONTH), is(Calendar.MAY));
        assertThat(calendar.getActiveDayFromDomCalendar().get(Calendar.YEAR), is(2015));
        //assertThat(calendar.getActiveDayFromDomCalendar().get(Calendar.WEEK_OF_YEAR), is(20));
    }

    @Test
    public void testGoToDateDay() throws ParseException {
        CalendarDemoPage page = pages.goHome();
        AdfCalendar calendar = page.findCalendar();
        calendar.findMonthViewButton().click();
        // quickly browse by month to target month
        calendar.goToDate(yyyymmdd.parse("20150301"));
        assertThat(calendar.getActiveDayFromDomCalendar().get(Calendar.MONTH), is(Calendar.MARCH));
        assertThat(calendar.getActiveDayFromDomCalendar().get(Calendar.YEAR), is(2015));
        // switch to day and browse back to within february
        calendar.findDayViewButton().click();
        calendar.goToDate(yyyymmdd.parse("20150227"));
        assertThat(calendar.getActiveDayFromDomCalendar().get(Calendar.DAY_OF_MONTH), is(27));
        assertThat(calendar.getActiveDayFromDomCalendar().get(Calendar.MONTH), is(Calendar.FEBRUARY));
        assertThat(calendar.getActiveDayFromDomCalendar().get(Calendar.YEAR), is(2015));
        // browse forward
        calendar.goToDate(yyyymmdd.parse("20150302"));
        assertThat(calendar.getActiveDayFromDomCalendar().get(Calendar.DAY_OF_MONTH), is(2));
        assertThat(calendar.getActiveDayFromDomCalendar().get(Calendar.MONTH), is(Calendar.MARCH));
        assertThat(calendar.getActiveDayFromDomCalendar().get(Calendar.YEAR), is(2015));
    }

    public static void main(String[] args) {
        String[] args2 = { CalendarTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

}
