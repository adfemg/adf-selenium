package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfSelectManyShuttle;
import com.redheap.selenium.pages.SelectManyShuttleDemoPage;

import java.util.List;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SelectManyShuttleTest extends PageTestBase<SelectManyShuttleDemoPage> {

    @Test
    public void testItems() {
        AdfSelectManyShuttle shuttle = pages.goHome().findDrinksSelectManyShuttle();
        assertEquals("coffee", shuttle.getItemLabel(0));
        assertEquals("0", shuttle.getItemValue(0));
        assertEquals(0, shuttle.getItemIndexByLabel("coffee"));
        assertEquals("tea", shuttle.getItemLabel(1));
        assertEquals("1", shuttle.getItemValue(1));
        assertEquals(1, shuttle.getItemIndexByLabel("tea"));
        // milk (value "2") is rendered=false
        assertEquals("orange juice", shuttle.getItemLabel(2));
        assertEquals("2", shuttle.getItemValue(2));
        assertEquals(4, shuttle.getItemIndexByLabel("milk"));
        // item "4" is rendered=false
        assertEquals("wine", shuttle.getItemLabel(3));
        assertEquals("3", shuttle.getItemValue(3));
        assertEquals(5, shuttle.getItemIndexByLabel("fizz"));
        assertEquals("milk", shuttle.getItemLabel(4));
        assertEquals("4", shuttle.getItemValue(4));
        assertEquals(7, shuttle.getItemIndexByLabel("lemonade"));
    }

    @Test
    public void testSetValue() {
        AdfSelectManyShuttle shuttle = pages.goHome().findDrinksSelectManyShuttle();
        //Select items by index
        shuttle.clickItemsByIndices(1, 3, 4); // idx=3 is disabled so should not work
        shuttle.clickMove();
        // Check selected values
        List<Object> selectedValues = shuttle.getValue();
        assertEquals(2, selectedValues.size());
        assertEquals("1", selectedValues.get(0));
        assertEquals("4", selectedValues.get(1));
        // Check selected indices
        long[] selectedIndices = shuttle.getValueIndices();
        assertEquals(2, selectedIndices.length);
        assertEquals(6, selectedIndices[0]);
        assertEquals(7, selectedIndices[1]);
        //Check selected labels
        List<String> selectedLabels = shuttle.getValueLabels();
        assertEquals(2, selectedLabels.size());
        assertEquals("tea", selectedLabels.get(0));
        assertEquals("milk", selectedLabels.get(1));
        //Select items by label
        shuttle.clickItemsByLabels("fizz", "lemonade", "notExists");
        shuttle.clickMove();
        //Check selected labels
        selectedLabels = shuttle.getValueLabels();
        assertEquals(4, selectedLabels.size());
        assertEquals("tea", selectedLabels.get(0));
        assertEquals("milk", selectedLabels.get(1));
        assertEquals("fizz", selectedLabels.get(2));
        assertEquals("lemonade", selectedLabels.get(3));
        //Remove milk from selection
        shuttle.clickSelectedItemsByIndices(1);
        shuttle.clickRemove();
        selectedLabels = shuttle.getValueLabels();
        assertEquals(3, selectedLabels.size());
        assertEquals("tea", selectedLabels.get(0));
        assertEquals("fizz", selectedLabels.get(1));
        assertEquals("lemonade", selectedLabels.get(2));
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
