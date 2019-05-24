package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfSelectBooleanRadio;
import com.redheap.selenium.pages.SelectBooleanRadioDemoPage;

import static org.junit.Assert.*;
import org.junit.Test;


public class SelectBooleanRadioTest extends PageTestBase<SelectBooleanRadioDemoPage> {

    @Test
    public void testText() {
        assertEquals("10-18", pages.goHome().findAge10to18().getText());
    }

    @Test
    public void testLabel() {
        assertEquals("Age", pages.goHome().findAge10to18().getLabel());
    }

    @Test
    public void testValues() {
        AdfSelectBooleanRadio radio = pages.goHome().findAge10to18();
        assertTrue(Boolean.FALSE.equals(radio.getValue()));
        radio.click();
        assertTrue(Boolean.TRUE.equals(radio.getValue()));
    }

    @Test
    public void testGroupSize() {
        AdfSelectBooleanRadio radio = pages.goHome().findAge10to18();
        assertEquals(8, radio.findGroupItems().size());
    }

    @Test
    public void testGroup() {
        AdfSelectBooleanRadio radio = pages.goHome().findAge10to18();
        assertTrue(Boolean.FALSE.equals(radio.getValue()));
        radio.click();
        assertTrue(Boolean.TRUE.equals(radio.getValue()));
        radio.findGroupItems().get(radio.findGroupItems().size()-1).click();
        assertTrue(Boolean.FALSE.equals(radio.getValue()));
    }

    public static void main(String[] args) {
        String[] args2 = { SelectBooleanRadioTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

    @Override
    protected Class<SelectBooleanRadioDemoPage> getPageClass() {
        return SelectBooleanRadioDemoPage.class;
    }

    @Override
    protected String getJspxName() {
        return "selectBooleanRadio.jspx";
    }
}
