package com.redheap.selenium;

import com.redheap.selenium.output.ScreenshotFile;
import com.redheap.selenium.pages.FileExplorer;
import com.redheap.selenium.pages.RichClientDemo;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

public class RichClientDemoTest extends TestCaseBase<RichClientDemo> {

    private static final String HOME_PAGE = "http://jdevadf.oracle.com/adf-richclient-demo/faces/index.jspx";

    public RichClientDemoTest() {
        super(HOME_PAGE, RichClientDemo.class);
    }

    @Test
    public void testHomepageLoad() {
        RichClientDemo page = getPage();
    }

    @Test
    public void testNavigationToFileExplorer() throws Exception {
        FileExplorer page = getPage().clickFileExplorerLink().clickTreeTableTab();
        File file = page.getScreenshotAs(new ScreenshotFile(new File("explorer-tree-table.png")));
        System.out.println("took screenshot " + file.getCanonicalPath());
    }

    @Test
    public void testExpandTagGuideNode() {
        RichClientDemo page = getPage();
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
        RichClientDemo page = getPage();
        int expandedNodes = page.getTagGuideTreeExpandedNodeCount();
        page.clickLayoutTreeNode();
        Assert.assertEquals("number of expanded node should increase", expandedNodes + 1,
                            page.getTagGuideTreeExpandedNodeCount());
    }

    public static void main(String[] args) {
        String[] args2 = { RichClientDemoTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

}
