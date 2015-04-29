package com.redheap.selenium.pages;

import com.redheap.selenium.component.AdfCalendar;
import com.redheap.selenium.component.AdfInputDate;
import com.redheap.selenium.component.AdfSelectBooleanCheckbox;
import com.redheap.selenium.page.Page;

import org.openqa.selenium.WebDriver;

public class CalendarDemoPage extends Page {

    private final String calendar = "dmoTpl:cal";

    // components in create-activity popup
    private final String createAllDay = "allDayNewFlagId";
    private final String createFrom = "id7";
    private final String createTo = "id8";

    public CalendarDemoPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected String getExpectedTitle() {
        return "Calendar Demo";
    }

    public AdfCalendar findCalendar() {
        return findAdfComponent(calendar);
    }

    public AdfSelectBooleanCheckbox findCreatePopupAllDay() {
        return findCalendar().findAdfComponent(createAllDay);
    }

    public AdfInputDate findCreatePopupFrom() {
        return findCalendar().findAdfComponent(createFrom);
    }

    public AdfInputDate findCreatePopupTo() {
        return findCalendar().findAdfComponent(createTo);
    }

}
