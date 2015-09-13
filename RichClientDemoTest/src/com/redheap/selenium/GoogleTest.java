package com.redheap.selenium;

import org.junit.Test;

import static org.openqa.selenium.By.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class GoogleTest {

    /**
     * Most basic Selenium example that searches for a term on google.
     * Does not close the browser on test completion to keep the test as simple as possible.
     */
    @Test
    public void simpleTest() {
        WebDriver driver = new FirefoxDriver();
        driver.get("http://google.com/?hl=en");
        WebElement searchBox = driver.findElement(name("q"));
        searchBox.sendKeys("adf selenium");
        searchBox.submit();
    }

}
