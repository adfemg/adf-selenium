package com.redheap.selenium.pages;

import com.redheap.selenium.component.AdfRegion;
import com.redheap.selenium.fragments.SampleFragment1;
import com.redheap.selenium.page.Page;

import org.openqa.selenium.WebDriver;

public class RegionDemoPage extends Page {

    private final String regionId = "dmoTpl:region1";

    public RegionDemoPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected String getExpectedTitle() {
        return "region Demo";
    }

    protected AdfRegion findDemoRegion() {
        return findAdfComponent(regionId);
    }

    public SampleFragment1 getRegionContent() {
        return new SampleFragment1(findDemoRegion());
    }

}
