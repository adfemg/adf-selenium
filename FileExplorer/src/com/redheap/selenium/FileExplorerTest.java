package com.redheap.selenium;

import com.redheap.selenium.conditions.AdfConditions;
import com.redheap.selenium.pages.FileExplorer;
import com.redheap.selenium.pages.RichClientDemo;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FileExplorerTest {

    private static WebDriver driver;

    @BeforeClass
    public static void setUp() throws Exception {
        System.out.println("Starting Firefox...");
        FirefoxProfile profile = new FirefoxProfile();
        profile.setEnableNativeEvents(true); // needed for Mac OSX (default is non-native which doesn't work with ADF)
        profile.setPreference("app.update.enabled", false);
        driver = new FirefoxDriver(profile);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        System.out.println("Quit firefox...");
        //driver.quit();
    }

    @Test
    public void testNothing() {
        driver.get("http://jdevadf.oracle.com/adf-richclient-demo/faces/index.jspx");
        new RichClientDemo(driver).clickLayoutTreeNode().clickFileExplorerLink();
    }

    public static void main(String[] args) {
        String[] args2 = { FileExplorerTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

}
