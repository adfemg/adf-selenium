package com.redheap.selenium.pages;

import com.redheap.selenium.component.AdfTree;
import com.redheap.selenium.finder.AdfFinder;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RichClientDemoWithComponents extends PageObject {

    private final String treeId = "tmplt:accMenu:tagGrouped:tree";
    private final By miscellaneousTreeNode = AdfFinder.treeNodeByLabel("Miscellaneous");
    private final By regionTreeNode = AdfFinder.treeNodeByLabel("Region");

    public RichClientDemoWithComponents(WebDriver driver) {
        super(driver);
    }

    @Override
    protected String getExpectedTitle() {
        return "ADF Faces Rich Client Demos";
    }

    private AdfTree findTagGuideTree() {
        return findDocument().findAdfComponent(treeId, AdfTree.class);
    }

    public int getTagGuideTreeExpandedNodeCount() {
        return findTagGuideTree().getExpandedNodeCount();
    }

    public RichClientDemoWithComponents clickMiscellaneousTreeNode() {
        System.out.println("Clicking Miscellaneous node in the Tag Guide component tree");
        findTagGuideTree().clickNode(miscellaneousTreeNode);
        waitForPpr();
        return this;
    }

    public RegionDemoPage clickRegionTreeNode() {
        System.out.println("Clicking Region node in the Tag Guide component tree");
        findTagGuideTree().clickNode(regionTreeNode);
        waitForPpr();
        return navigatedTo(RegionDemoPage.class);
    }

}
