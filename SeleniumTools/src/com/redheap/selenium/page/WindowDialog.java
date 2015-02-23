package com.redheap.selenium.page;

import oracle.adf.view.rich.automation.selenium.Dialog;

import static org.junit.Assert.*;

import org.openqa.selenium.WebDriver;


public abstract class WindowDialog extends Page {

    private final WebDriver driver;
    private final Dialog dialog;

    public WindowDialog(WebDriver driver, Dialog dialog) {
        super(driver, false);
        this.driver = driver;
        this.dialog = dialog;
        assertEquals(getExpectedTitle(), dialog.getTitle(driver));
    }

    protected abstract String getExpectedTitle();

    @Override
    public String getTitle() {
        return dialog.getTitle(driver);
    }

    public boolean isAlive() {
        return dialog.isAlive();
    }

    public void close() {
        dialog.close(driver);
    }

    public void focus() {
        dialog.focus(driver);
    }

}
