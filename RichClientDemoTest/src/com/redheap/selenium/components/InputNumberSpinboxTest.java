package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfInputNumberSpinbox;
import com.redheap.selenium.pages.InputNumberSpinboxDemoPage;

import static org.junit.Assert.*;
import org.junit.Test;

public class InputNumberSpinboxTest extends PageTestBase<InputNumberSpinboxDemoPage> {

    @Test
    public void testPlainSpinbox() {
        AdfInputNumberSpinbox spinbox = pages.goHome().findSpinbox();
        assertEquals(1979L, spinbox.getValue());
        long initialValue = (Long)spinbox.getValue();
        spinbox.clickDecrement();
        assertEquals(initialValue - 1, spinbox.getValue());
        spinbox.clickIncrement();
        assertEquals(initialValue, spinbox.getValue());
    }

    public static void main(String[] args) {
        String[] args2 = { InputNumberSpinboxTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

    @Override
    protected Class<InputNumberSpinboxDemoPage> getPageClass() {
        return InputNumberSpinboxDemoPage.class;
    }

    @Override
    protected String getJspxName() {
        return "inputNumberSpinbox.jspx";
    }
}
