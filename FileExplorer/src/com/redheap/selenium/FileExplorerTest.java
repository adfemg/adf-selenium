package com.redheap.selenium;

import com.redheap.selenium.output.ScreenshotFile;
import com.redheap.selenium.pages.RichClientDemo;

import java.io.File;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class FileExplorerTest {

    private static final String HOME_PAGE = "http://jdevadf.oracle.com/adf-richclient-demo/faces/index.jspx";

    private static WebDriver driver;

    @BeforeClass
    public static void setUpBrowser() throws Exception {
        System.out.println("Starting Firefox...");
        FirefoxProfile profile = new FirefoxProfile();
        profile.setEnableNativeEvents(true); // needed for Mac OSX (default is non-native which doesn't work with ADF)
        profile.setPreference("app.update.enabled", false); // don't bother updating Firefox (takes too much time)
        driver = new FirefoxDriver(profile);
    }

    @AfterClass
    public static void tearDownBrowser() throws Exception {
        System.out.println("Quit firefox...");
        driver.quit();
    }

    @Before
    public void setupSession() {
        // clear session cookie before each test so we start with a clean session
        System.out.println("Clearing session cookie...");
        driver.manage().deleteCookieNamed("JSESSIONID");
        driver.get(HOME_PAGE);
    }

    @Test
    @Ignore
    public void testNavigationToFileExplorer() throws Exception {
        new RichClientDemo(driver).clickLayoutTreeNode().clickFileExplorerLink();
        File file = ((TakesScreenshot) driver).getScreenshotAs(new ScreenshotFile(new File("final-screen.png")));
        System.out.println("took screenshot " + file.getCanonicalPath());
    }

    @Test
    public void testExpandTagGuideNode() {
        RichClientDemo page = new RichClientDemo(driver);
        int expandedNodes = page.getTagGuideTreeExpandedNodeCount();
        page.clickLayoutTreeNode();
        Assert.assertEquals("number of expanded node should increase", expandedNodes + 1,
                            page.getTagGuideTreeExpandedNodeCount());
    }

    @Test
    public void testExpandTagGuideNodeAgain() {
        // test the same as testExpandTagGuideNode to make sure cookies were cleared between tests so tree
        // should start with collapsed nodes again (not clearing cookies retains state and thus collapsed state
        // from testExpandTagGuideNode test)
        RichClientDemo page = new RichClientDemo(driver);
        int expandedNodes = page.getTagGuideTreeExpandedNodeCount();
        page.clickLayoutTreeNode();
        Assert.assertEquals("number of expanded node should increase", expandedNodes + 1,
                            page.getTagGuideTreeExpandedNodeCount());
    }

    public static void main(String[] args) {
        String[] args2 = { FileExplorerTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

}
