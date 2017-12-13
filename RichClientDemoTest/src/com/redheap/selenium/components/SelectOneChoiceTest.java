package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfSelectOneChoice;
import com.redheap.selenium.pages.SelectOneChoiceDemoPage;

import static org.junit.Assert.*;
import org.junit.Test;


public class SelectOneChoiceTest extends PageTestBase<SelectOneChoiceDemoPage> {

    @Test
    public void testItemLabels() {
        AdfSelectOneChoice choice = pages.goHome().findDrinksSelectOneChoice();
        assertEquals("coffee", choice.getItemLabel(0));
        assertEquals("tea", choice.getItemLabel(1));
        // item 2 is rendered=false
        assertEquals("fizz", choice.getItemLabel(3));
        assertEquals("beer", choice.getItemLabel(4));
        assertEquals("lemonade", choice.getItemLabel(5));
    }

    @Test
    public void testCompactItemLabels() {
        AdfSelectOneChoice choice = pages.goHome().findCompactSelectOneChoice();
        assertEquals("Mouse", choice.getItemLabel(0));
        assertEquals("Keyboard", choice.getItemLabel(1));
    }

    @Test
    public void testLabelLookup() {
        AdfSelectOneChoice choice = pages.goHome().findDrinksSelectOneChoice();
        assertEquals(0, choice.getValueByLabel("coffee"));
        assertEquals(1, choice.getValueByLabel("tea"));
        assertEquals(3, choice.getValueByLabel("fizz"));
        assertEquals(4, choice.getValueByLabel("beer"));
        assertEquals(5, choice.getValueByLabel("lemonade"));
        assertEquals(-1, choice.getValueByLabel("foo"));
    }

    @Test
    public void testCompactLabelLookup() {
        AdfSelectOneChoice choice = pages.goHome().findCompactSelectOneChoice();
        assertEquals(0, choice.getValueByLabel("Mouse"));
        assertEquals(1, choice.getValueByLabel("Keyboard"));
    }

    @Test
    public void testSetValue() {
        AdfSelectOneChoice choice = pages.goHome().findDrinksSelectOneChoice();
        choice.clickItemByIndex(1);
        assertEquals("tea", choice.getValueLabel());
        assertEquals("1", choice.getValue());
        choice.clickItemByLabel("fizz");
        assertEquals("fizz", choice.getValueLabel());
        assertEquals("3", choice.getValue());
        choice.clickItemByIndex(0);
        assertEquals("coffee", choice.getValueLabel());
        assertEquals("0", choice.getValue());
    }

    @Test
    public void testCompactness() {
        SelectOneChoiceDemoPage page = pages.goHome();
        assertFalse(page.findDrinksSelectOneChoice().isCompact());
        assertTrue(page.findCompactSelectOneChoice().isCompact());
    }

    public static void main(String[] args) {
        String[] args2 = { SelectOneChoiceTest.class.getName() };        
        org.junit.runner.JUnitCore.main(args2);
    }

    @Override
    protected Class<SelectOneChoiceDemoPage> getPageClass() {
        return SelectOneChoiceDemoPage.class;
    }

    @Override
    protected String getJspxName() {
        return "selectOneChoice.jspx";
    }
}
