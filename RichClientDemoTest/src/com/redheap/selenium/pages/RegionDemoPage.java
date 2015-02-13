package com.redheap.selenium.pages;

import com.redheap.selenium.component.AdfRegion;
import com.redheap.selenium.fragments.SampleFragment1;
import com.redheap.selenium.page.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegionDemoPage extends PageObject {

    private By demoRegion = By.id("dmoTpl:region1");

    public RegionDemoPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected String getExpectedTitle() {
        return "region Demo";
    }

    protected AdfRegion findDemoRegion() {
        return findAdfComponent("dmoTpl:region1", AdfRegion.class);
    }

    public SampleFragment1 getRegionContent() {
        return new SampleFragment1(findDemoRegion());
    }

}
