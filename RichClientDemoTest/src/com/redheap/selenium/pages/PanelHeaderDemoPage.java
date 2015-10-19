package com.redheap.selenium.pages;

import com.redheap.selenium.component.AdfPanelHeader;
import com.redheap.selenium.page.Page;

import org.openqa.selenium.WebDriver;

public class PanelHeaderDemoPage extends Page {
    
    private final String parentPanelHeader = "dmoTpl:panelHeader";
    private final String subPanelHeader = "dmoTpl:panelHeader2";
    
    public PanelHeaderDemoPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected String getExpectedTitle() {
        return "panelHeader Demo";
    }
    
    public AdfPanelHeader findParentPanelHeader() {
        return findAdfComponent(parentPanelHeader);
    }
    
    public AdfPanelHeader findSubPanelHeader() {
        return findAdfComponent(subPanelHeader);
    }

}
