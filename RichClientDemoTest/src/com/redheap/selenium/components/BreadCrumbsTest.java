package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfBreadCrumbs;
import com.redheap.selenium.pages.BreadCrumbsDemoPage;

import static org.junit.Assert.*;
import org.junit.Test;

public class BreadCrumbsTest extends PageTestBase<BreadCrumbsDemoPage> {

    @Test
    public void testBreadCrumbs() {
        AdfBreadCrumbs breadCrumbs = pages.goHome().findBreadCrumbs();
        assertEquals(5, breadCrumbs.getCrumbs().size());
    }

    public static void main(String[] args) {
        String[] args2 = { BreadCrumbsTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

    @Override
    protected Class<BreadCrumbsDemoPage> getPageClass() {
        return BreadCrumbsDemoPage.class;
    }

    @Override
    protected String getJspxName() {
        return "breadCrumbs.jspx";
    }
}
