package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfInputComboboxListOfValues;
import com.redheap.selenium.component.AdfTable;
import com.redheap.selenium.pages.InputComboboxListOfValuesDemoPage;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;


public class InputComboboxListOfValuesTest extends PageTestBase<InputComboboxListOfValuesDemoPage> {

    @Test
    public void testDropdownTable () {
        //Here are typical tests for dropdown list
        
        AdfInputComboboxListOfValues enameCombo = pages.goHome().find_idInputComboboxListOfValues();
        
        //Disclose dropdown list
        enameCombo.clickDropdownIcon();
        AdfTable enameDropdownTable = enameCombo.findDropdownTable();
        assertNotNull("Dropdown List Of Values does not shown or empty", enameDropdownTable);

        // Getting rows in dropdown displayed on the client side. This means without any additional fetching from the server side.
        // Having list you can implement assert according to the way you store your test data
        // Also note that you can easily add step for scrolling the dropdownlist and assert the next fetched rows
        List<String> enameClientSideDropdownTableRows = new ArrayList<String>();
        enameClientSideDropdownTableRows = enameCombo.getClientSideDropdownTableRows();

        //Either you can just test that dropdown LOV is not empty or it is :)
        //Just mention which count do you want to test: client side -> getClientRowCount() or server side -> getRowCount()
 
        Long orgNameDisclosedRowsCount = enameDropdownTable.getClientRowCount();
        enameDropdownTable.selectRow(0);
        assertEquals("Selected value does not match to expected for row with index 0", "Bejond6", enameCombo.getContent());

        assertFalse("Ename combobox is not available for typing value", enameCombo.isDisabled());
    }


    public static void main(String[] args) {
        String[] args2 = { InputComboboxListOfValuesTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

    @Override
    protected Class<InputComboboxListOfValuesDemoPage> getPageClass() {
        return InputComboboxListOfValuesDemoPage.class;
    }

    @Override
    protected String getJspxName() {
        return "inputComboboxListOfValues.jspx";
    }
}
