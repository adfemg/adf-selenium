package com.redheap.selenium.pages;

import com.redheap.selenium.AdfFinder;
import com.redheap.selenium.component.AdfCommandLink;
import com.redheap.selenium.component.AdfTree;
import com.redheap.selenium.page.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RichClientDemo extends PageObject {

    private final String treeId = "tmplt:accMenu:tagGrouped:tree";
    private final String fileExplorerLink = "tmplt:fileExplorer";

    private final By miscellaneousTreeNode = AdfFinder.treeNodeByLabel("Miscellaneous");
    private final By layoutTreeNode = AdfFinder.treeNodeByLabel("Layout");
    private final By regionTreeNode = AdfFinder.treeNodeByLabel("Region");

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
        System.out.println("Clicking Miscellaneous node in the Tag Guide component tree");
        findTagGuideTree().clickNode(miscellaneousTreeNode);
        waitForPpr();
        return this;
    }

    public RichClientDemo clickLayoutTreeNode() {
        System.out.println("Clicking Layout node in the Tag Guide component tree");
        findTagGuideTree().clickNode(layoutTreeNode);
        waitForPpr();
        return this;
    }

    public RegionDemoPage clickRegionTreeNode() {
        System.out.println("Clicking Region node in the Tag Guide component tree");
        findTagGuideTree().clickNode(regionTreeNode);
        waitForPpr();
        return navigatedTo(RegionDemoPage.class);
    }

    public FileExplorer clickFileExplorerLink() {
        System.out.println("Clicking File Explorer link");
        findFileExplorerLink().click();
        waitForPpr();
        return navigatedTo(FileExplorer.class);
    }

}
