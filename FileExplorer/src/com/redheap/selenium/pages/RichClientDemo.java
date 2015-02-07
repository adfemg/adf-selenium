package com.redheap.selenium.pages;

import com.redheap.selenium.finder.AdfFinder;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ByChained;

public class RichClientDemo extends PageObject {

    private By tagGuideTree = By.id("tmplt:accMenu:tagGrouped:tree");
    private By layoutTreeNode = AdfFinder.treeNodeByLabel("Layout");
    private By tagGuideLayoutTreeNode = new ByChained(tagGuideTree, layoutTreeNode);

    private By fileExplorerLink = By.linkText("File Explorer");

    private static final String PAGE_TITLE = "ADF Faces Rich Client Demos";

    public RichClientDemo(WebDriver driver) {
        super(driver);
        String pageTitle = driver.getTitle();
        if (!PAGE_TITLE.equals(pageTitle)) {
            throw new IllegalStateException("Not on RichClientDemo page: " + pageTitle);
        }
    }

    public int getTagGuideTreeExpandedNodeCount() {
        WebElement tree = driver.findElement(tagGuideTree);
        String js =
            String.format("return Object.keys(AdfPage.PAGE.findComponentByAbsoluteId('%s').getDisclosedRowKeys()).length",
                          tree.getAttribute("id"));
        return ((Number) executeScript(js)).intValue();
    }

    public RichClientDemo clickLayoutTreeNode() {
        System.out.println("Clicking Layout node in the Tag Guide component tree");
        driver.findElement(tagGuideLayoutTreeNode).findElement(By.tagName("a")).click();
        waitForPpr();
        return this;
    }

    public FileExplorer clickFileExplorerLink() {
        System.out.println("Clicking File Explorer link...");
        driver.findElement(fileExplorerLink).click();
        return new FileExplorer(driver); // navigation to new page
    }

}
