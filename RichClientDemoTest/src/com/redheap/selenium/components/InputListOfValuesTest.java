package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfInputListOfValues;
import com.redheap.selenium.component.AdfInputText;
import com.redheap.selenium.component.AdfQuery;
import com.redheap.selenium.pages.InputListOfValuesDemoPage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


public class InputListOfValuesTest extends PageTestBase<InputListOfValuesDemoPage> {

    @Test
    public void testInputListofValuesDefaultSearchPopup() {
        AdfInputListOfValues enameLOV = pages.goHome().findEname();
        
        //Examples how to interract with default search popup of AdfInputListofValues component and general tests
        //Open seacrh popup and test that it is displayed
        enameLOV.clickSearchIcon();
        assertTrue("Serch Party Popup не открылся", enameLOV.isPopupVisible());
        //Then getting an internal component AdfQuery
        AdfQuery enamePopupQuery = enameLOV.findLovDialogQuery();
        //И далее получить поля для критериев поиска и вести в поля значения
        //And then getting search criteria fields and type values into them.
            //See also comment for findCriterionValueFieldByIndex() method
        AdfInputText enameEnameCriterion = enamePopupQuery.<AdfInputText>findCriterionValueFieldByIndex(0, 0);

        enameEnameCriterion.typeValue("");

        //Also the short variant works perfectly
        enamePopupQuery.<AdfInputText>findCriterionValueFieldByIndex(0, 0).typeValue("A");
        //Then init searching
        enameLOV.findLovDialogQuery().clickPopupQuerySearchButton();

        //Then we could test that result is not empty or it is 
        assertFalse("Search result is empty", enameLOV.findLovDialogTable().isEmpty());
        //As well the max number of resulted rows could be tested
        long searchResultRowNumber = enameLOV.findLovDialogTable().getRowCount();
        assertTrue("Search result contains more then 150 rows", searchResultRowNumber <= 150);

        //If tests for result set is passed then we can select row and finish with search popup
        enameLOV.findLovDialogTable().selectRow(0);
        enameLOV.findSearchDialog()
                .findOkButton()
                .click();

        //Then we can test assigning fields of selected row on popup to inputText components of the page
        assertEquals("Value assign incorrectly", "0", pages.goHome().findEmpno().getContent());
        assertEquals("Value assign incorrectly", "10", pages.goHome().findDeptno().getContent());
        assertEquals("Value assign incorrectly", "1/19/1998", pages.goHome().findHireDate().getContent());
        assertEquals("Value assign incorrectly", "1", pages.goHome().findManager().getContent());
        assertEquals("Value assign incorrectly", "23232", pages.goHome().findSalary().getContent());
        assertEquals("Value assign incorrectly", "66767", pages.goHome().findCommision().getContent());

    }


    public static void main(String[] args) {
        String[] args2 = { InputListOfValuesTest.class.getName() };
        org.junit
           .runner
           .JUnitCore
           .main(args2);
    }

    @Override
    protected Class<InputListOfValuesDemoPage> getPageClass() {
        return InputListOfValuesDemoPage.class;
    }

    @Override
    protected String getJspxName() {
        return "inputListOfValues.jspx";
    }
}
