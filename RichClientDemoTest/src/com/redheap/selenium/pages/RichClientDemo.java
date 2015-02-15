package com.redheap.selenium.pages;

import com.redheap.selenium.AdfFinder;
import com.redheap.selenium.component.AdfCommandLink;
import com.redheap.selenium.component.AdfTree;
import com.redheap.selenium.page.PageObject;

import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RichClientDemo extends PageObject {

    private final String treeId = "tmplt:accMenu:tagGrouped:tree";
    private final String fileExplorerLink = "tmplt:fileExplorer";

    private final By miscellaneousTreeNode = AdfFinder.treeNodeByLabel("Miscellaneous");
    private final By layoutTreeNode = AdfFinder.treeNodeByLabel("Layout");
    private final By regionTreeNode = AdfFinder.treeNodeByLabel("Region");

    private static final Logger logger = Logger.getLogger(RichClientDemo.class.getName());

    public RichClientDemo(WebDriver driver) {
        super(driver);
    }

    @Override
    protected String getExpectedTitle() {
        return "ADF Faces Rich Client Demos";
    }

    private AdfTree findTagGuideTree() {
        return findDocument().findAdfComponent(treeId, AdfTree.class);
    }

    private AdfCommandLink findFileExplorerLink() {
        return findDocument().findAdfComponent(fileExplorerLink, AdfCommandLink.class);
    }

    public int getTagGuideTreeExpandedNodeCount() {
        return findTagGuideTree().getExpandedNodeCount();
    }

    public RichClientDemo clickMiscellaneousTreeNode() {
        logger.fine("Clicking Miscellaneous node in the Tag Guide component tree");
        findTagGuideTree().clickNode(miscellaneousTreeNode);
        return this;
    }

    public RichClientDemo clickLayoutTreeNode() {
        logger.fine("Clicking Layout node in the Tag Guide component tree");
        findTagGuideTree().clickNode(layoutTreeNode);
        return this;
    }

    public RegionDemoPage clickRegionTreeNode() {
        logger.fine("Clicking Region node in the Tag Guide component tree");
        findTagGuideTree().clickNode(regionTreeNode);
        return navigatedTo(RegionDemoPage.class);
    }

    public FileExplorer clickFileExplorerLink() {
        logger.fine("Clicking File Explorer link");
        findFileExplorerLink().click();
        return navigatedTo(FileExplorer.class);
    }

}
