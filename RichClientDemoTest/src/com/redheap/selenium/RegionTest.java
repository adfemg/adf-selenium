package com.redheap.selenium;

import com.redheap.selenium.fragments.SampleFragment1;
import com.redheap.selenium.pages.RichClientDemoWithComponents;
import com.redheap.selenium.junit.SavePageSourceOnFailure;
import com.redheap.selenium.junit.ScreenshotOnFailure;

import java.io.File;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

/**
 * Test interaction with an af:region, especially navigation between page fragments.
 */
public class RegionTest extends TestCaseBase<RichClientDemoWithComponents> {

    private static final String HOME_PAGE = "http://jdevadf.oracle.com/adf-richclient-demo";

    @Rule
    public TestWatcher screenshotOnFailure = new ScreenshotOnFailure(getDriver(), new File("errors"));
    @Rule
    public TestWatcher saveSourceOnFailure = new SavePageSourceOnFailure(getDriver(), new File("errors"));

    public RegionTest() {
        super(HOME_PAGE, RichClientDemoWithComponents.class);
    }

    @Test
    public void expandTreeNode() {
        System.out.println("***** expandTreeNode");
        RichClientDemoWithComponents homePage = getPage();
        int expandedCount = homePage.getTagGuideTreeExpandedNodeCount();
        homePage.clickMiscellaneousTreeNode();
        Assert.assertEquals("number of expanded tree nodes should have increased", expandedCount + 1,
                            homePage.getTagGuideTreeExpandedNodeCount());
    }

    @Test
    public void expandNestedTreeNode() {
        System.out.println("***** expandNestedTreeNode");
        RichClientDemoWithComponents homePage = getPage();
        int expandedCount = homePage.getTagGuideTreeExpandedNodeCount();
        homePage.clickMiscellaneousTreeNode();
        Assert.assertEquals("number of expanded tree nodes should have increased", expandedCount + 1,
                            homePage.getTagGuideTreeExpandedNodeCount());
        homePage.clickRegionTreeNode(); // navigates to region page
    }

    @Test
    public void pageNavigation() {
        System.out.println("***** pageNavigation");
        getPage().clickMiscellaneousTreeNode().clickRegionTreeNode();
    }

    @Test
    public void regionNavigation() {
        System.out.println("***** regionNavigation");
        SampleFragment1 fragment1 = getPage().clickMiscellaneousTreeNode().clickRegionTreeNode().getRegionContent();
        fragment1.clickRegion2Button();
    }

    public static void main(String[] args) {
        String[] args2 = { RegionTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

}


