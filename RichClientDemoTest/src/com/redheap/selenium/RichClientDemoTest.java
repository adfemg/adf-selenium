package com.redheap.selenium;

import com.redheap.selenium.junit.PageProvider;
import com.redheap.selenium.junit.SavePageSourceOnFailure;
import com.redheap.selenium.junit.ScreenshotOnFailure;
import com.redheap.selenium.junit.WebDriverResource;
import com.redheap.selenium.pages.FileExplorer;
import com.redheap.selenium.pages.RichClientDemo;

import java.io.File;

import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

public class RichClientDemoTest {

    @ClassRule
    public static WebDriverResource driver = new WebDriverResource();
    @Rule
    public PageProvider<RichClientDemo> pages = new PageProvider(RichClientDemo.class, HOME_PAGE, driver.getDriver());
    @Rule
    public ScreenshotOnFailure screenshotOnFailure = new ScreenshotOnFailure(driver.getDriver(), new File("errors"));
    @Rule
    public SavePageSourceOnFailure saveSourceOnFailure =
        new SavePageSourceOnFailure(driver.getDriver(), new File("errors"));


    //private static final String HOME_PAGE = "http://jdevadf.oracle.com/adf-richclient-demo";
    private static final String HOME_PAGE = "http://localhost:7101/adf-richclient-demo";
    private static final Logger logger = Logger.getLogger(RichClientDemoTest.class.getName());

    @Test
    public void testHomepageLoad() {
        logger.info("***** testHomepageLoad");
        pages.goHome();
    }

    @Test
    public void testNavigationToFileExplorer() throws Exception {
        logger.info("***** testNavigationToFileExplorer");
        FileExplorer explorerPage = pages.goHome().clickFileExplorerLink();
        explorerPage.findContentViewTreeTableTab().click();
    }

    @Test
    public void testExpandTagGuideNodeA() {
        logger.info("***** testExpandTagGuideNodeA");
        RichClientDemo page = pages.goHome();
        int expandedNodesBefore = page.findTagGuideTree().getExpandedNodeCount();
        page.clickLayoutTreeNode();
        Assert.assertEquals("number of expanded node should increase", expandedNodesBefore + 1,
                            page.findTagGuideTree().getExpandedNodeCount());
    }

    @Test
    public void testExpandTagGuideNodeB() {
        // test the same as testExpandTagGuideNode to make sure cookies were cleared between tests so tree
        // should start with collapsed nodes again (not clearing cookies retains state and thus collapsed state
        // from testExpandTagGuideNodeA test)
        logger.info("***** testExpandTagGuideNodeB");
        RichClientDemo page = pages.goHome();
        int expandedNodesBefore = page.findTagGuideTree().getExpandedNodeCount();
        page.clickLayoutTreeNode();
        Assert.assertEquals("number of expanded node should increase", expandedNodesBefore + 1,
                            page.findTagGuideTree().getExpandedNodeCount());
    }

    public static void main(String[] args) {
        String[] args2 = { RichClientDemoTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

}

