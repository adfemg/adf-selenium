package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfInputListOfValues;
import com.redheap.selenium.component.AdfInputText;
import com.redheap.selenium.component.AutoSuggestBehavior;
import com.redheap.selenium.pages.AutoSuggestBehaviorDemoPage;

import static org.junit.Assert.*;
import org.junit.Test;

public class AutoSuggestBehaviorTest extends PageTestBase<AutoSuggestBehaviorDemoPage> {

    @Test
    public void testInputText() {
        AutoSuggestBehaviorDemoPage page = pages.goHome();
        AutoSuggestBehavior<AdfInputText> inputtext = page.findInputText();
        assertFalse(inputtext.isPopupVisible());
        inputtext.typeAndWait("Bla");
        assertTrue(inputtext.isPopupVisible());
        assertFalse(inputtext.isMoreLinkVisible());
        assertEquals(5, inputtext.getSuggestItems().size());
        assertEquals("Blake3         Technician", inputtext.getSuggestItems().get(0));
        inputtext.clickSuggestItem(2);
        assertEquals("Blake81", inputtext.getComponent().getValue());
    }

    @Test
    public void testListOfValues() {
        AutoSuggestBehaviorDemoPage page = pages.goHome();
        AutoSuggestBehavior<AdfInputListOfValues> lov = page.findListOfValues();
        assertFalse(lov.isPopupVisible());
        lov.typeAndWait("Bla");
        assertTrue(lov.isPopupVisible());
        assertTrue(lov.isMoreLinkVisible());
        assertEquals(5, lov.getSuggestItems().size());
        assertEquals("Blake3         Technician", lov.getSuggestItems().get(0));
        lov.clickSuggestItem(2);
        assertEquals("Blake81", lov.getComponent().getValue());
        // More... link
        lov.typeAndWait("Bla");
        assertTrue(lov.isPopupVisible());
        assertTrue(lov.isMoreLinkVisible());
        assertFalse(lov.getComponent().isPopupVisible());
        lov.clickMoreLink();
        assertTrue(lov.getComponent().isPopupVisible());
        // rest is upto ListOfValues component test and not part of autoSuggest
    }

    public static void main(String[] args) {
        String[] args2 = { AutoSuggestBehaviorTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

    @Override
    protected Class<AutoSuggestBehaviorDemoPage> getPageClass() {
        return AutoSuggestBehaviorDemoPage.class;
    }

    @Override
    protected String getJspxName() {
        return "autoSuggestBehavior.jspx";
    }
}
