package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfPanelHeader;
import com.redheap.selenium.pages.PanelHeaderDemoPage;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class PanelHeaderTest extends PageTestBase<PanelHeaderDemoPage> {

    @Test
    public void testParentPanelHeader() {
        AdfPanelHeader parentPanelHeader = pages.goHome().findParentPanelHeader();
        assertEquals("Automatic Header", parentPanelHeader.getText());
        final long expectedHeaderLevel = -1;
        assertEquals(expectedHeaderLevel, parentPanelHeader.getHeaderLevel());
    }

    @Test
    public void testSubPanelHeader() {
        AdfPanelHeader subPanelHeader = pages.goHome().findSubPanelHeader();
        assertEquals("Automatic SubHeader", subPanelHeader.getText());
        //apparently even though we are dealing with a subpanel the headerLevel remains -1.
        final long expectedHeaderLevel = -1;
        assertEquals(expectedHeaderLevel, subPanelHeader.getHeaderLevel());
    }

    public static void main(String[] args) {
        String[] args2 = { PanelHeaderTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

    @Override
    protected Class<PanelHeaderDemoPage> getPageClass() {
        return PanelHeaderDemoPage.class;
    }

    @Override
    protected String getJspxName() {
        return "panelHeader.jspx";
    }
}
