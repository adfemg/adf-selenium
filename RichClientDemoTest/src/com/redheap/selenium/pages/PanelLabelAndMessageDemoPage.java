package com.redheap.selenium.pages;

import com.redheap.selenium.component.AdfPanelLabelAndMessage;
import com.redheap.selenium.page.Page;

import org.openqa.selenium.WebDriver;

public class PanelLabelAndMessageDemoPage extends Page {

    private final String panelLabelAndMessage = "dmoTpl:panelLabelAndMessage";

    public PanelLabelAndMessageDemoPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected String getExpectedTitle() {
        return "panelLabelAndMessage Demo";
    }

    public AdfPanelLabelAndMessage findPanelLabelAndMessage() {
        return findAdfComponent(panelLabelAndMessage);
    }

}
