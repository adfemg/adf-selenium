package com.redheap.selenium.pages;

import com.redheap.selenium.component.AdfCalendar;
import com.redheap.selenium.page.Page;

import org.openqa.selenium.WebDriver;

public class CalendarDemoPage extends Page {

    private final String calendar = "dmoTpl:cal";

    public CalendarDemoPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected String getExpectedTitle() {
        return "Calendar Demo";
    }

    public AdfCalendar findCalendar() {
        return findAdfComponent(calendar, AdfCalendar.class);
    }

}
