package com.redheap.selenium;

import org.junit.Test;
import org.junit.runner.JUnitCore;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Example of a Bot Style test where there is no separation between test classes and page object classes.
 * @see https://code.google.com/p/selenium/wiki/BotStyleTests
 */
public class BotStyleTest {

    @Test
    public void test() throws Exception {
        FirefoxProfile profile = new FirefoxProfile();
        profile.setEnableNativeEvents(true);
        profile.setPreference("app.update.enabled", false);
        WebDriver driver = new FirefoxDriver(profile);
        System.out.println("load demo page...");
        driver.get("http://jdevadf.oracle.com/adf-richclient-demo");
        System.out.println("wait for completion...");
        new WebDriverWait(driver, 10).until(AdfConditions.clientSynchedWithServer());
        System.out.println("click search button...");
        driver.findElement(By.id("tmplt:gTools:glryFind:doFind")).click();
        System.out.println("wait for completion...");
        new WebDriverWait(driver, 10).until(AdfConditions.clientSynchedWithServer());
        driver.quit();
    }

    public static void main(String[] args) {
        String[] args2 = { BotStyleTest.class.getName() };
        JUnitCore.main(args2);
    }

}