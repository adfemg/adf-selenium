package com.redheap.selenium;

import com.redheap.selenium.fragments.SampleFragment1;
import com.redheap.selenium.pages.RichClientDemo;
import com.redheap.selenium.junit.SavePageSourceOnFailure;
import com.redheap.selenium.junit.ScreenshotOnFailure;

import java.io.File;

import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

/**
 * Test interaction with an af:region, especially navigation between page fragments.
 */
public class RegionTest extends TestCaseBase<RichClientDemo> {

    @Rule
    public TestWatcher screenshotOnFailure = new ScreenshotOnFailure(getDriver(), new File("errors"));
    @Rule
    public TestWatcher saveSourceOnFailure = new SavePageSourceOnFailure(getDriver(), new File("errors"));

    private static final String HOME_PAGE = "http://jdevadf.oracle.com/adf-richclient-demo";
    private static final Logger logger = Logger.getLogger(RegionTest.class.getName());

    public RegionTest() {
        super(HOME_PAGE, RichClientDemo.class);
    }

    @Test
    public void expandTreeNode() {
        logger.info("***** expandTreeNode");
        RichClientDemo homePage = getPage();
        int expandedCount = homePage.getTagGuideTreeExpandedNodeCount();
        homePage.clickMiscellaneousTreeNode();
        Assert.assertEquals("number of expanded tree nodes should have increased", expandedCount + 1,
                            homePage.getTagGuideTreeExpandedNodeCount());
    }

    @Test
    public void expandNestedTreeNode() {
        logger.info("***** expandNestedTreeNode");
        RichClientDemo homePage = getPage();
        int expandedCount = homePage.getTagGuideTreeExpandedNodeCount();
        homePage.clickMiscellaneousTreeNode();
        Assert.assertEquals("number of expanded tree nodes should have increased", expandedCount + 1,
                            homePage.getTagGuideTreeExpandedNodeCount());
        homePage.clickRegionTreeNode(); // navigates to region page
    }

    @Test
    public void pageNavigation() {
        logger.info("***** pageNavigation");
        getPage().clickMiscellaneousTreeNode().clickRegionTreeNode();
    }

    @Test
    public void regionNavigation() {
        logger.info("***** regionNavigation");
        SampleFragment1 fragment1 = getPage().clickMiscellaneousTreeNode().clickRegionTreeNode().getRegionContent();
        fragment1.clickRegion2Button();
    }

    public static void main(String[] args) {
        String[] args2 = { RegionTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

}


