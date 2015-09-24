package com.redheap.selenium;

import com.redheap.selenium.components.PageTestBase;
import com.redheap.selenium.fragments.SampleFragment1;
import com.redheap.selenium.pages.RegionDemoPage;

import java.util.logging.Logger;

import org.junit.Test;

/**
 * Test interaction with an af:region, especially navigation between page fragments.
 */
public class RegionTest extends PageTestBase<RegionDemoPage> {

    private static final Logger logger = Logger.getLogger(RegionTest.class.getName());

    @Test
    public void regionNavigation() {
        RegionDemoPage regionPage = pages.goHome();
        SampleFragment1 fragment1 = regionPage.getRegionContent();
        fragment1.clickRegion2Button();
    }

    public static void main(String[] args) {
        String[] args2 = { RegionTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

    @Override
    protected Class<RegionDemoPage> getPageClass() {
        return RegionDemoPage.class;
    }

    @Override
    protected String getJspxName() {
        return "region.jspx";
    }

}


