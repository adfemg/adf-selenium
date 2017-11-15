package com.redheap.selenium.component;

import com.redheap.selenium.component.uix.UixInput;

import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AdfInputDate extends UixInput {
    
    /**
     * Overriden the clear method in de AdfInputDate.
     * Getting the focus, does select all the chars allready, only need a delete/backspace here.
     */
    @Override
    public void clear() {
        if (isPlatform(Platform.MAC)) {
            findContentNode().sendKeys(Keys.DELETE);
        } else {
            findContentNode().sendKeys(Keys.BACK_SPACE);
        }

    }

    // subid's at http://jdevadf.oracle.com/adf-richclient-demo/docs/js-subids.html
    private static final String SUBID_content = "content"; //
    private static final String SUBID_timezoneId = "timezoneId"; // <input type=hidden>
    private static final String SUBID_timezoneName = "timezoneName"; // visible <span>

    public AdfInputDate(WebDriver webDriver, String string) {
        super(webDriver, string);
    }

    /**
     * Returns timezone as an ID
     * @return timezone id, for example {@code Europe/Amsterdam}
     */
    public String getTimezoneId() {
        WebElement element = findTimezoneId();
        return element == null ? null : element.getAttribute("value");
    }

    /**
     * Returns timezone name as displayed on the page
     * @return timezone name, for example {@code (UTC+01:00) Amsterdam - Central European Time (CET)}
     */
    public String getTimezoneName() {
        WebElement element = findTimezoneName();
        return element == null ? null : element.getText();
    }

    protected WebElement findTimezoneId() {
        return findSubIdElement(SUBID_timezoneId);
    }

    protected WebElement findTimezoneName() {
        return findSubIdElement(SUBID_timezoneName);
    }

}
