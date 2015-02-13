package com.redheap.selenium.pages;

import com.redheap.selenium.component.AdfPanelTabbed;
import com.redheap.selenium.component.AdfShowDetailItem;
import com.redheap.selenium.page.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FileExplorer extends PageObject {

    private final String contentViewPannelTabbedId = "fe:contentViews:contentViewTab";
    private final String contentViewTreeTableTabId = "contentsTreeTable";

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

    private AdfPanelTabbed findContentViewTabs() {
        return findDocument().findAdfComponent(contentViewPannelTabbedId, AdfPanelTabbed.class);
    }

    private AdfShowDetailItem findContentViewTreeTableTab() {
        return findContentViewTabs().findAdfComponent(contentViewTreeTableTabId, AdfShowDetailItem.class);
    }

    public FileExplorer clickTreeTableTab() {
        System.out.println("Clicking TreeTable tab in content view panelTabbed");
        findContentViewTreeTableTab().clickTabLink();
        waitForPpr();
        return this;
    }

}
