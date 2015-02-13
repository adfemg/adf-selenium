package com.redheap.selenium;

import com.redheap.selenium.fragments.SampleFragment1;
import com.redheap.selenium.fragments.SampleFragment2;
import com.redheap.selenium.output.ScreenshotFile;
import com.redheap.selenium.pages.RegionDemoPage;
import com.redheap.selenium.pages.RichClientDemoWithComponents;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

public class RegionTest extends TestCaseBase<RichClientDemoWithComponents> {

    private static final String HOME_PAGE = "http://jdevadf.oracle.com/adf-richclient-demo/faces/index.jspx";

    public RegionTest() {
        super(HOME_PAGE, RichClientDemoWithComponents.class);
    }

    @Test
    public void testRegion() {
        try {
            System.out.println("***** testRegion");
            RegionDemoPage regionPage = getPage().clickMiscellaneousTreeNode().clickRegionTreeNode();
            SampleFragment1 fragment1 = regionPage.getRegionContent();
            SampleFragment2 fragment2 = fragment1.clickRegion2Button();
            regionPage.getScreenshotAs(new ScreenshotFile(new File("region-test.png")));
        } catch (Throwable t) {
            dumpError("test-region");
            throw t;
        }
    }

    @Test
    public void expandTreeNode() {
        try {
            System.out.println("***** expandTreeNode");
            RichClientDemoWithComponents homePage = getPage();
            int expandedCount = homePage.getTagGuideTreeExpandedNodeCount();
            homePage.clickMiscellaneousTreeNode();
            Assert.assertEquals("number of expanded tree nodes should have increased", expandedCount + 1,
                                homePage.getTagGuideTreeExpandedNodeCount());
        } catch (Throwable t) {
            dumpError("expand-tree-region");
            throw t;
        }
    }

    @Test
    public void expandNestedTreeNode() {
        try {
            System.out.println("***** expandNestedTreeNode");
            RichClientDemoWithComponents homePage = getPage();
            int expandedCount = homePage.getTagGuideTreeExpandedNodeCount();
            homePage.clickMiscellaneousTreeNode();
            Assert.assertEquals("number of expanded tree nodes should have increased", expandedCount + 1,
                                homePage.getTagGuideTreeExpandedNodeCount());
            homePage.clickRegionTreeNode(); // navigates to region page
        } catch (Throwable t) {
            dumpError("expand-nested-tree-node");
            throw t;
        }

    }

    @Test
    public void pageNavigation() {
        try {
            System.out.println("***** pageNavigation");
            getPage().clickMiscellaneousTreeNode().clickRegionTreeNode();
        } catch (Throwable t) {
            dumpError("page-navigation");
            throw t;
        }

    }

    @Test
    public void regionNavigation() {
        try {
            System.out.println("***** regionNavigation");
            SampleFragment1 fragment1 = getPage().clickMiscellaneousTreeNode().clickRegionTreeNode().getRegionContent();
            fragment1.clickRegion2Button();
        } catch (Throwable t) {
            dumpError("region-navigation");
            throw t;
        }

    }

    public static void main(String[] args) {
        String[] args2 = { RegionTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

}


