package com.redheap.selenium.pages;

import com.redheap.selenium.component.AdfInputDate;
import com.redheap.selenium.page.Page;

import org.openqa.selenium.WebDriver;

public class InputDateDemoPage extends Page {

    private final String plain = "dmoTpl:iDate";
    private final String dateOnly = "dmoTpl:inputDate4";
    private final String dateAndTime = "dmoTpl:inputDate5";
    private final String withTimezone = "dmoTpl:inputDate9";

    public InputDateDemoPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected String getExpectedTitle() {
        return "inputDate Demo";
    }

    public AdfInputDate findPlainInputDate() {
        return findAdfComponent(plain);
    }

    public AdfInputDate findDateOnly() {
        return findAdfComponent(dateOnly);
    }

    public AdfInputDate findDateAndTime() {
        return findAdfComponent(dateAndTime);
    }

    public AdfInputDate findDateWithTimezone() {
        return findAdfComponent(withTimezone);
    }

}
