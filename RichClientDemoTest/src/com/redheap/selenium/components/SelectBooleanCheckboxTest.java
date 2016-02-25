package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfSelectBooleanCheckbox;
import com.redheap.selenium.pages.SelectBooleanCheckboxDemoPage;

import static org.junit.Assert.*;
import org.junit.Test;


public class SelectBooleanCheckboxTest extends PageTestBase<SelectBooleanCheckboxDemoPage> {

    @Test
    public void testText() {
        assertEquals("Extra keys required", pages.goHome().findExtraKeys().getText());
    }

    @Test
    public void testLabel() {
        assertEquals("Extra Keys", pages.goHome().findExtraKeys().getLabel());
    }

    @Test
    public void testValues() {
        AdfSelectBooleanCheckbox box = pages.goHome().findExtraKeys();
        assertTrue(Boolean.FALSE.equals(box.getValue()));
        box.click();
        assertTrue(Boolean.TRUE.equals(box.getValue()));
    }

    @Test
    public void testNonTriState() {
        AdfSelectBooleanCheckbox box = pages.goHome().findExtraKeys();
        assertFalse(box.isTriState());
    }

    @Test
    public void testTriState() {
        AdfSelectBooleanCheckbox box = pages.goHome().findTriStateCheckbox();
        assertTrue(box.isTriState());
        assertEquals("mixed", box.getNullValueMeans());
        assertTrue(box.isNull());
        assertTrue(Boolean.FALSE.equals(box.getValue())); // this one is a bit strange, would expect value==null
        box.click();
        assertFalse(box.isNull());
        assertTrue(Boolean.TRUE.equals(box.getValue()));
        box.click();
        assertFalse(box.isNull());
        assertTrue(Boolean.FALSE.equals(box.getValue()));
    }

    public static void main(String[] args) {
        String[] args2 = { SelectBooleanCheckboxTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

    @Override
    protected Class<SelectBooleanCheckboxDemoPage> getPageClass() {
        return SelectBooleanCheckboxDemoPage.class;
    }

    @Override
    protected String getJspxName() {
        return "selectBooleanCheckbox.jspx";
    }
}
