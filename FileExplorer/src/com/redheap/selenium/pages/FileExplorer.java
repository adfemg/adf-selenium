package com.redheap.selenium.pages;

import com.redheap.selenium.conditions.AdfConditions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FileExplorer extends PageObject {

    public FileExplorer(WebDriver driver) {
        super(driver);
        String pageTitle = driver.getTitle();
        if (!"File Explorer".equals(pageTitle)) {
            throw new IllegalStateException("Not on FileExplorer page: " + pageTitle);
        }
    }

}
