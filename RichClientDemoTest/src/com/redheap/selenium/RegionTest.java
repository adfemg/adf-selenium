package com.redheap.selenium;

import com.redheap.selenium.fragments.SampleFragment1;
import com.redheap.selenium.junit.PageProvider;
import com.redheap.selenium.junit.SavePageSourceOnFailure;
import com.redheap.selenium.junit.ScreenshotOnFailure;
import com.redheap.selenium.junit.WebDriverResource;
import com.redheap.selenium.pages.RegionDemoPage;
import com.redheap.selenium.pages.RichClientDemo;

import java.io.File;

import java.util.logging.Logger;

import static org.junit.Assert.*;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

/**
 * Test interaction with an af:region, especially navigation between page fragments.
 */
public class RegionTest {

    @ClassRule
    public static WebDriverResource driver = new WebDriverResource();
    @Rule
    public PageProvider<RichClientDemo> pages = new PageProvider(RichClientDemo.class, HOME_PAGE, driver.getDriver());
    @Rule
    public TestWatcher screenshotOnFailure = new ScreenshotOnFailure(driver.getDriver(), new File("errors"));
    @Rule
    public TestWatcher saveSourceOnFailure = new SavePageSourceOnFailure(driver.getDriver(), new File("errors"));

    //private static final String HOME_PAGE = "http://jdevadf.oracle.com/adf-richclient-demo";
    private static final String HOME_PAGE = "http://localhost:7101/adf-richclient-demo";
    private static final Logger logger = Logger.getLogger(RegionTest.class.getName());

    @Test
    public void expandTreeNode() {
        logger.info("***** expandTreeNode");
        RichClientDemo homePage = pages.goHome();
        int expandedCount = homePage.findTagGuideTree().getExpandedNodeCount();
        homePage.clickMiscellaneousTreeNode();
        assertEquals("number of expanded tree nodes should have increased", expandedCount + 1,
                     homePage.findTagGuideTree().getExpandedNodeCount());
    }

    @Test
    public void expandNestedTreeNode() {
        logger.info("***** expandNestedTreeNode");
        RichClientDemo homePage = pages.goHome();
        int expandedCount = homePage.findTagGuideTree().getExpandedNodeCount();
        homePage.clickMiscellaneousTreeNode();
        assertEquals("number of expanded tree nodes should have increased", expandedCount + 1,
                     homePage.findTagGuideTree().getExpandedNodeCount());
        homePage.clickRegionTreeNode(); // navigates to region page
    }

    @Test
    public void pageNavigation() {
        logger.info("***** pageNavigation");
        RichClientDemo homepage = pages.goHome();
        homepage.clickMiscellaneousTreeNode();
        homepage.clickRegionTreeNode();
    }

    @Test
    public void regionNavigation() {
        logger.info("***** regionNavigation");
        RichClientDemo homepage = pages.goHome();
        homepage.clickMiscellaneousTreeNode();
        RegionDemoPage regionPage = homepage.clickRegionTreeNode();
        SampleFragment1 fragment1 = regionPage.getRegionContent();
        fragment1.clickRegion2Button();
    }

    public static void main(String[] args) {
        String[] args2 = { RegionTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

}


