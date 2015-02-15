package com.redheap.selenium;

import com.redheap.selenium.pages.FileExplorer;
import com.redheap.selenium.pages.RichClientDemo;

import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Test;

public class RichClientDemoTest extends TestCaseBase<RichClientDemo> {

    private static final String HOME_PAGE = "http://jdevadf.oracle.com/adf-richclient-demo/faces/index.jspx";
    private static final Logger logger = Logger.getLogger(RichClientDemoTest.class.getName());

    public RichClientDemoTest() {
        super(HOME_PAGE, RichClientDemo.class);
    }

    @Test
    public void testHomepageLoad() {
        logger.info("***** testHomepageLoad");
        getPage();
    }

    @Test
    public void testNavigationToFileExplorer() throws Exception {
        logger.info("***** testNavigationToFileExplorer");
        FileExplorer page = getPage().clickFileExplorerLink().clickTreeTableTab();
        //page.getScreenshotAs(new ScreenshotFile(new File("explorer-tree-table.png")));
    }

    @Test
    public void testExpandTagGuideNodeA() {
        logger.info("***** testExpandTagGuideNodeA");
        RichClientDemo page = getPage();
        int expandedNodesBefore = page.getTagGuideTreeExpandedNodeCount();
        page.clickLayoutTreeNode();
        Assert.assertEquals("number of expanded node should increase", expandedNodesBefore + 1,
                            page.getTagGuideTreeExpandedNodeCount());
    }

    @Test
    public void testExpandTagGuideNodeB() {
        // test the same as testExpandTagGuideNode to make sure cookies were cleared between tests so tree
        // should start with collapsed nodes again (not clearing cookies retains state and thus collapsed state
        // from testExpandTagGuideNodeA test)
        logger.info("***** testExpandTagGuideNodeB");
        RichClientDemo page = getPage();
        int expandedNodesBefore = page.getTagGuideTreeExpandedNodeCount();
        page.clickLayoutTreeNode();
        Assert.assertEquals("number of expanded node should increase", expandedNodesBefore + 1,
                            page.getTagGuideTreeExpandedNodeCount());
    }

    public static void main(String[] args) {
        String[] args2 = { RichClientDemoTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

}

