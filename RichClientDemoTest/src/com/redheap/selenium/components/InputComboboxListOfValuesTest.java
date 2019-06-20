package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfInputComboboxListOfValues;
import com.redheap.selenium.component.AdfInputText;
import com.redheap.selenium.component.AdfQuery;
import com.redheap.selenium.component.AdfTable;
import com.redheap.selenium.component.AdfTreeTable;
import com.redheap.selenium.component.DvtSchedulingGantt;
import com.redheap.selenium.component.RowInfo;
import com.redheap.selenium.pages.DvtSchedulingGanttDemoPage;
import com.redheap.selenium.pages.InputComboboxListOfValuesDemoPage;
import com.redheap.selenium.pages.TableDetailStampDemoPage;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.*;
import static org.junit.Assert.*;
import static org.junit.Assert.*;
import org.junit.Test;

import org.openqa.selenium.interactions.Actions;


public class InputComboboxListOfValuesTest extends PageTestBase<InputComboboxListOfValuesDemoPage> {

    /**Test interractions with sub-components of Scheduling Gantt.
     *  - context menus
     *  - customer list (DataTreeTable)
     *  - tasks (ChartTreeTable, taskbar)
     */
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
        
        /*
        //Пример работы с элементами автоматически генерируемого popup'а поиска для компонента AdfInputListofValues
        //Открываем окно поиска и проверяем, что оно отобразилось
        p6FragmenteEnterData.findPartySiteNumber().clickSearchIcon();
        assertTrue("Serch Party Popup не открылся", p6FragmenteEnterData.findPartySiteNumber().isPopupVisible());
        //Далее получить внутренний компонент AdfQuery
        AdfQuery partySitePopupQuery = p6FragmenteEnterData.findPartySiteNumber().findLovDialogQuery();
        //И далее получить поля для критериев поиска и вести в поля значения
        AdfInputText partySiteNameCriterion = partySitePopupQuery.<AdfInputText>findCriterionValueFieldByIndex(0, 0);
        AdfInputText partySiteInnCriterion = partySitePopupQuery.<AdfInputText>findCriterionValueFieldByIndex(1, 0);
        AdfInputText partySiteNumberCriterion = partySitePopupQuery.<AdfInputText>findCriterionValueFieldByIndex(2, 0);

        partySiteNameCriterion.typeValue("");
        partySiteInnCriterion.typeValue("");
        partySiteNumberCriterion.typeValue("3");


        //Либо более краткий вариант
        partySitePopupQuery.<AdfInputText>findCriterionValueFieldByIndex(2, 0).typeValue("4");
        //Далее инициировать поиск
        p6FragmenteEnterData.findPartySiteNumber()
                            .findLovDialogQuery()
                            .clickPopupQuerySearchButton();

        //Далее проверить что результа поиска не пустой
        assertFalse("Поиск ничего не вернул", p6FragmenteEnterData.findPartySiteNumber()
                                                                  .findLovDialogTable()
                                                                  .isEmpty());
        //Тест требования на максимальный объем выборки в 50 записей
        long searchResultRowNumber = p6FragmenteEnterData.findPartySiteNumber()
                                                         .findLovDialogTable()
                                                         .getRowCount();
        assertTrue("Записей более 50", searchResultRowNumber <= 50);

        //Если две проверки выше успешны, то выбрать первую строку из результатов поиска
        p6FragmenteEnterData.findPartySiteNumber()
                            .findLovDialogTable()
                            .selectRow(0);
        p6FragmenteEnterData.findPartySiteNumber()
                            .findSearchDialog()
                            .findOkButton()
                            .click(); */

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
