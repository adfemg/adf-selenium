package com.redheap.selenium.pages;

import com.redheap.selenium.AdfFinder;
import com.redheap.selenium.component.AdfCommandLink;
import com.redheap.selenium.component.AdfTree;
import com.redheap.selenium.page.Page;

import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RichClientDemo extends Page {

    private final String treeId = "tmplt:accMenu:tagGrouped:tree";
    private final String fileExplorerLink = "tmplt:fileExplorer";

    private final By regionTreeNode = AdfFinder.treeNodeByLabel("Region");

    private static final Logger logger = Logger.getLogger(RichClientDemo.class.getName());

    public RichClientDemo(WebDriver driver) {
        super(driver);
    }

    @Override
    protected String getExpectedTitle() {
        return "ADF Faces Rich Client Demos";
    }

    public AdfTree findTagGuideTree() {
        return findDocument().findAdfComponent(treeId, AdfTree.class);
    }

    private AdfCommandLink findFileExplorerLink() {
        return findDocument().findAdfComponent(fileExplorerLink, AdfCommandLink.class);
    }

    public void clickMiscellaneousTreeNode() {
        logger.fine("Clicking Miscellaneous node in the Tag Guide component tree");
        findTagGuideTree().discloseNode(0, 6);
    }

    public void clickLayoutTreeNode() {
        logger.fine("Clicking Layout node in the Tag Guide component tree");
        findTagGuideTree().discloseNode(0, 3);
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
