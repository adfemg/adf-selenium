package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfSelectManyShuttle;
import com.redheap.selenium.pages.SelectManyShuttleDemoPage;

import java.util.List;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SelectManyShuttleTest extends PageTestBase<SelectManyShuttleDemoPage> {

    @Test
    public void testItems() {
        AdfSelectManyShuttle choice = pages.goHome().findDrinksSelectManyShuttle();
        assertEquals("coffee", choice.getItemLabel(0));
        assertEquals("0", choice.getItemValue(0));
        assertEquals(0, choice.getItemIndexByLabel("coffee"));
        assertEquals("tea", choice.getItemLabel(1));
        assertEquals("1", choice.getItemValue(1));
        assertEquals(1, choice.getItemIndexByLabel("tea"));
        // milk (value "2") is rendered=false
        assertEquals("orange juice", choice.getItemLabel(2));
        assertEquals("2", choice.getItemValue(2));
        assertEquals(4, choice.getItemIndexByLabel("milk"));
        // item "4" is rendered=false
        assertEquals("wine", choice.getItemLabel(3));
        assertEquals("3", choice.getItemValue(3));
        assertEquals(5, choice.getItemIndexByLabel("fizz"));
        assertEquals("milk", choice.getItemLabel(4));
        assertEquals("4", choice.getItemValue(4));
        assertEquals(7, choice.getItemIndexByLabel("lemonade"));
    }

    @Test
    public void testSetValue() {
        AdfSelectManyShuttle choice = pages.goHome().findDrinksSelectManyShuttle();
        //Select items by index
        choice.clickItemsByIndices(1, 3, 4); // idx=3 is disabled so should not work
        choice.clickMove();
        // Check selected values
        List<Object> selectedValues = choice.getValue();
        assertEquals(2, selectedValues.size());
        assertEquals("1", selectedValues.get(0));
        assertEquals("4", selectedValues.get(1));
        // Check selected indices
        long[] selectedIndices = choice.getValueIndices();
        assertEquals(2, selectedIndices.length);
        assertEquals(6, selectedIndices[0]);
        assertEquals(7, selectedIndices[1]);
        //Check selected labels
        List<String> selectedLabels = choice.getValueLabels();
        assertEquals(2, selectedLabels.size());
        assertEquals("tea", selectedLabels.get(0));
        assertEquals("milk", selectedLabels.get(1));
        //Select items by label
        choice.clickItemsByLabels("fizz", "lemonade", "notExists");
        choice.clickMove();
        //Check selected labels
        selectedLabels = choice.getValueLabels();
        assertEquals(4, selectedLabels.size());
        assertEquals("tea", selectedLabels.get(0));
        assertEquals("milk", selectedLabels.get(1));
        assertEquals("fizz", selectedLabels.get(2));
        assertEquals("lemonade", selectedLabels.get(3));
    }

    public static void main(String[] args) {
        String[] args2 = { SelectManyShuttleTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

    @Override
    protected Class<SelectManyShuttleDemoPage> getPageClass() {
        return SelectManyShuttleDemoPage.class;
    }

    @Override
    protected String getJspxName() {
        return "selectManyShuttle.jspx";
    }
}
