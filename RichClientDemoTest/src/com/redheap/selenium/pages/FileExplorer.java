package com.redheap.selenium.pages;

import com.redheap.selenium.component.AdfPanelTabbed;
import com.redheap.selenium.component.AdfShowDetailItem;
import com.redheap.selenium.page.Page;

import org.openqa.selenium.WebDriver;

public class FileExplorer extends Page {

    private final String contentViewPannelTabbedId = "fe:contentViews:contentViewTab";
    private final String contentViewTreeTableTabId = "contentsTreeTable";

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

    public AdfShowDetailItem findContentViewTreeTableTab() {
        return findContentViewTabs().findAdfComponent(contentViewTreeTableTabId, AdfShowDetailItem.class);
    }

}
