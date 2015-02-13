package com.redheap.selenium.pages;

import com.redheap.selenium.finder.AdfFinder;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.pagefactory.ByChained;

public class RichClientDemo extends PageObject {

    private By tagGuideTree = By.id("tmplt:accMenu:tagGrouped:tree");
    private By layoutTreeNode = AdfFinder.treeNodeByLabel("Layout");
    private By tagGuideLayoutTreeNode = new ByChained(tagGuideTree, layoutTreeNode);

    private By fileExplorerLink = By.linkText("File Explorer");

    public RichClientDemo(WebDriver driver) {
        super(driver);
    }

    @Override
    protected String getExpectedTitle() {
        return "ADF Faces Rich Client Demos";
    }

    public int getTagGuideTreeExpandedNodeCount() {
        return 0;
//        WebElement tree = findElement(tagGuideTree);
//        String js =
//            String.format("return Object.keys(AdfPage.PAGE.findComponentByAbsoluteId('%s').getDisclosedRowKeys()).length",
//                          tree.getAttribute("id"));
//        return ((Number) executeScript(js)).intValue();
    }

    public RichClientDemo clickLayoutTreeNode() {
        return null;
//        System.out.println("Clicking Layout node in the Tag Guide component tree");
//        findElement(tagGuideLayoutTreeNode).findElement(By.tagName("a")).click();
//        waitForPpr();
//        return this;
    }

    public FileExplorer clickFileExplorerLink() {
        return null;
//        System.out.println("Clicking File Explorer link...");
//        findElement(fileExplorerLink).click();
//        return navigatedTo(FileExplorer.class); // navigation to new page
    }

}
