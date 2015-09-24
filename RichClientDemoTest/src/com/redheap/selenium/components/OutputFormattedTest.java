package com.redheap.selenium.components;

import com.redheap.selenium.pages.OutputFormattedDemoPage;

import static org.junit.Assert.*;
import org.junit.Test;


public class OutputFormattedTest extends PageTestBase<OutputFormattedDemoPage> {

    @Test
    public void testOutput() {
        assertEquals("<b>Out</b>put", pages.goHome().findOutput().getValue());
    }

    @Test
    public void testRed() {
        assertEquals("<span style=\"color:red\">This should be red</span>", pages.goHome().findRed().getValue());
    }

    public static void main(String[] args) {
        String[] args2 = { OutputFormattedTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

    @Override
    protected Class<OutputFormattedDemoPage> getPageClass() {
        return OutputFormattedDemoPage.class;
    }

    @Override
    protected String getJspxName() {
        return "outputFormatted.jspx";
    }
}
