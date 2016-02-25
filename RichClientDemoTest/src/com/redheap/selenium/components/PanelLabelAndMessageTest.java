package com.redheap.selenium.components;

import com.redheap.selenium.pages.PanelLabelAndMessageDemoPage;

import static org.junit.Assert.*;
import org.junit.Test;

public class PanelLabelAndMessageTest extends PageTestBase<PanelLabelAndMessageDemoPage> {

    @Test
    public void testLabel() {
        PanelLabelAndMessageDemoPage demoPage = pages.goHome();
        assertEquals("Label", demoPage.findPanelLabelAndMessage().getLabel());
    }

    public static void main(String[] args) {
        String[] args2 = { PanelLabelAndMessageTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

    @Override
    protected Class<PanelLabelAndMessageDemoPage> getPageClass() {
        return PanelLabelAndMessageDemoPage.class;
    }

    @Override
    protected String getJspxName() {
        return "panelLabelAndMessage.jspx";
    }
}
