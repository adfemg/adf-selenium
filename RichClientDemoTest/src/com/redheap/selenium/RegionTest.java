package com.redheap.selenium;

import com.redheap.selenium.fragments.SampleFragment1;
import com.redheap.selenium.junit.PageProvider;
import com.redheap.selenium.junit.SavePageSourceOnFailure;
import com.redheap.selenium.junit.ScreenshotOnFailure;
import com.redheap.selenium.junit.WebDriverResource;
import com.redheap.selenium.pages.RichClientDemo;

import java.io.File;

import java.util.logging.Logger;

import org.junit.Assert;
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
    private static final String HOME_PAGE = "http://localhost:7101/RichClientDemo-adf-richclient-demo-context-root";
    private static final Logger logger = Logger.getLogger(RegionTest.class.getName());

    @Test
    public void expandTreeNode() {
        logger.info("***** expandTreeNode");
        RichClientDemo homePage = pages.goHome();
        int expandedCount = homePage.getTagGuideTreeExpandedNodeCount();
        homePage.clickMiscellaneousTreeNode();
        Assert.assertEquals("number of expanded tree nodes should have increased", expandedCount + 1,
                            homePage.getTagGuideTreeExpandedNodeCount());
    }

    @Test
    public void expandNestedTreeNode() {
        logger.info("***** expandNestedTreeNode");
        RichClientDemo homePage = pages.goHome();
        int expandedCount = homePage.getTagGuideTreeExpandedNodeCount();
        homePage.clickMiscellaneousTreeNode();
        Assert.assertEquals("number of expanded tree nodes should have increased", expandedCount + 1,
                            homePage.getTagGuideTreeExpandedNodeCount());
        homePage.clickRegionTreeNode(); // navigates to region page
    }

    @Test
    public void pageNavigation() {
        logger.info("***** pageNavigation");
        pages.goHome().clickMiscellaneousTreeNode().clickRegionTreeNode();
    }

    @Test
    public void regionNavigation() {
        logger.info("***** regionNavigation");
        SampleFragment1 fragment1 =
            pages.goHome().clickMiscellaneousTreeNode().clickRegionTreeNode().getRegionContent();
        fragment1.clickRegion2Button();
    }

    public static void main(String[] args) {
        String[] args2 = { RegionTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

}


