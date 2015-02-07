package com.redheap.selenium.pages;

import com.redheap.selenium.finder.AdfFinder;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RichClientDemo extends PageObject {

    //private By layoutTreeNode = By.id("tmplt:accMenu:tagGrouped:tree:4::di");
    //private By layoutTreeNode = By.xpath("//tr[@_afrrk and .//span[@title='Layout']]//a");
    private By layoutTreeNode = AdfFinder.treeNodeByLabel("Lay'out");
    private By fileExplorerLink = By.linkText("File Explorer");

    public RichClientDemo(WebDriver driver) {
        super(driver);
        String pageTitle = driver.getTitle();
        if (!"ADF Faces Rich Client Demos".equals(pageTitle)) {
            throw new IllegalStateException("Not on RichClientDemo page: " + pageTitle);
        }
    }

    public RichClientDemo clickLayoutTreeNode() {
        System.out.println("Clicking Layout node in the Tag Guide component tree");
        //WebElement treeNode = driver.findElement(layoutTreeNode);
        //WebElement treeNodeLink = treeNode.findElement(AdfFinder.treeNodeByLabel("dummy"));
        //treeNodeLink.click();
        driver.findElement(layoutTreeNode).findElement(By.tagName("a")).click();
        waitForPpr();
        System.out.println("done");
        return this;
    }

    public FileExplorer clickFileExplorerLink() {
        System.out.println("Clicking File Explorer link...");
        WebElement link = driver.findElement(fileExplorerLink);
        System.out.println(link);
        System.out.println(link.getClass());
        link.click();
        System.out.println("clicked link");
        return new FileExplorer(driver);
    }

}
