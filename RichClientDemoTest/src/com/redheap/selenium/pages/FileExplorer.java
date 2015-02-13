package com.redheap.selenium.pages;

import com.redheap.selenium.page.PageObject;

import org.junit.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FileExplorer extends PageObject {

    // TODO: AdfFinder for ADF component ID lookups with knowledge how NamingContainers and pseudo-elements work
    private By contentViewTabs = By.id("fe:contentViews:contentViewTab");
    private By treeTableTab = By.id("fe:contentViews:contentsTreeTable::ti");

    public FileExplorer(WebDriver driver) {
        super(driver);
    }

    @Override
    protected String getExpectedTitle() {
        return "File Explorer";
    }

    public FileExplorer clickTreeTableTab() {
        System.out.println("Clicking TreeTable tab in content view panelTabbed");
        Assert.fail();
        //findElement(treeTableTab).findElement(By.tagName("a")).click();
        waitForPpr();
        return this;
    }

}
