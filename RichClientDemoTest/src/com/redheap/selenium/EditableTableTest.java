package com.redheap.selenium;

import com.redheap.selenium.component.AdfInputComboboxListOfValues;
import com.redheap.selenium.component.AdfInputText;
import com.redheap.selenium.component.AdfOutputText;
import com.redheap.selenium.component.AdfSelectOneRadio;
import com.redheap.selenium.junit.PageProvider;
import com.redheap.selenium.junit.SavePageSourceOnFailure;
import com.redheap.selenium.junit.ScreenshotOnFailure;
import com.redheap.selenium.junit.WebDriverResource;
import com.redheap.selenium.pages.EditableTablePage;

import java.io.File;

import java.util.logging.Logger;

import static org.junit.Assert.*;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

public class EditableTableTest {

    @ClassRule
    public static WebDriverResource driver = new WebDriverResource();
    @Rule
    public PageProvider<EditableTablePage> pages =
        new PageProvider(EditableTablePage.class, HOME_PAGE, driver.getDriver());
    @Rule
    public ScreenshotOnFailure screenshotOnFailure = new ScreenshotOnFailure(driver.getDriver(), new File("errors"));
    @Rule
    public SavePageSourceOnFailure saveSourceOnFailure =
        new SavePageSourceOnFailure(driver.getDriver(), new File("errors"));


    //private static final String HOME_PAGE = "http://jdevadf.oracle.com/adf-richclient-demo/faces/components/table/editableTable.jspx";
    private static final String HOME_PAGE =
        "http://localhost:7101/adf-richclient-demo/faces/components/table/editableTable.jspx";
    private static final Logger logger = Logger.getLogger(EditableTableTest.class.getName());

    @Test
    public void testPopupButton() {
        EditableTablePage page = pages.goHome();
        int rowindex = 0;
        page.findPopupButton(rowindex).click();
        assertEquals("Text", page.findDialog(0).getTitle());
        page.findDialogOkButton(rowindex).click();
    }

    @Test
    public void testInputText() {
        EditableTablePage page = pages.goHome();
        int rowindex = 0;
        AdfInputText it = page.findInputText(rowindex);
        it.typeValue("foo");
        assertEquals("foo", it.getValue());
        assertEquals("foo", it.getSubmittedValue());
        it.typeValue("bar");
        assertEquals("bar", it.getValue());
        assertEquals("bar", it.getValue());
        it.sendKeys("baz");
        it.tabNext();
        assertEquals("barbaz", it.getValue());
        assertEquals("barbaz", it.getValue());
    }

    @Test
    public void testCommandLink() {
        EditableTablePage page = pages.goHome();
        int rowindex = 0;
        AdfOutputText clickText = page.findClickText(rowindex);
        assertNull(clickText.getValue());
        page.findCommandLink(rowindex).click();
        Object val = clickText.getValue();
        assertTrue(val instanceof String && ((String) val).startsWith("Clicked on"));
    }

    @Test
    public void testCommandLinkRow3() {
        EditableTablePage page = pages.goHome();
        int rowindex = 2;
        AdfOutputText clickText = page.findClickText(rowindex);
        assertNull(clickText.getValue());
        page.findCommandLink(rowindex).click();
        Object val = clickText.getValue();
        assertTrue(val instanceof String && ((String) val).startsWith("Clicked on"));
    }

    @Test
    public void testCommandLinkRow100() {
        EditableTablePage page = pages.goHome();
        int rowindex = 100;
        AdfOutputText clickText = page.findClickText(rowindex);
        assertNull(clickText.getValue());
        page.findCommandLink(rowindex).click();
        Object val = clickText.getValue();
        assertTrue(val instanceof String && ((String) val).startsWith("Clicked on"));
    }

    @Test
    public void testRequired() {
        EditableTablePage page = pages.goHome();
        int rowindex = 0;
        AdfInputText it = page.findRequiredInputText(rowindex);
        it.typeValue("foo");
        assertEquals("foo", it.getValue());
        assertEquals("foo", it.getSubmittedValue());
        assertEquals(true, it.isValid());
        it.typeValue("");
        assertEquals(null, it.getValue());
        assertEquals("", it.getSubmittedValue());
        assertEquals(false, it.isValid());
    }

    @Test
    public void testCombobxFullValue() {
        EditableTablePage page = pages.goHome();
        int rowindex = 0;
        AdfInputComboboxListOfValues box = page.findCombobox(rowindex);
        // type full value
        box.typeValue("Bob472");
        assertEquals("Bob472", box.getValue());
        assertEquals("Bob472", box.getSubmittedValue());
        assertEquals(true, box.isValid());
    }

    @Test
    public void testCombobxSingleSuggest() {
        EditableTablePage page = pages.goHome();
        int rowindex = 0;
        AdfInputComboboxListOfValues box = page.findCombobox(rowindex);
        // type part of value but enough to have a single match
        box.typeValue("Bob47");
        assertEquals("Bob472", box.getValue());
        assertEquals("Bob472", box.getSubmittedValue());
        assertEquals(true, box.isValid());
    }

    @Test
    public void testCombobxMultiSuggest() {
        EditableTablePage page = pages.goHome();
        int rowindex = 0;
        AdfInputComboboxListOfValues box = page.findCombobox(rowindex);
        // type part of value but enough to have a single match
        box.typeValue("Bob4");
        // TODO: thorough testing of InputComboboxListOfValues should be separate test and not part of EditableTable
        assertNotNull(box.findDropdownPopup());
        //assertNull(box.findDropdownTable()); // only exist when expanding combobox dropdown
        assertNotNull(box.findLovDialogQuery());
        assertNotNull(box.findLovDialogTable());
        assertNotNull(box.findLovDialogTableColumn(0));
        assertNotNull(box.findLovDialogTableCell(0, 0));
        assertNotNull(box.findSearchDialog());
        assertNotNull(box.findSearchDialogPopup());
        //assertNull(box.findSearchLink());

        assertEquals("Bob4", box.findLovDialogTableCell(0, 0).getValue());
        assertEquals("Bob43", box.findLovDialogTableCell(1, 0).getValue());
        assertEquals("Bob433", box.findLovDialogTableCell(2, 0).getValue());
        assertEquals("Bob472", box.findLovDialogTableCell(3, 0).getValue());

        assertEquals(Long.valueOf(4), box.findLovDialogTableCell(0, 1).getValue()); // empno
        assertEquals("Engineer", box.findLovDialogTableCell(0, 2).getValue()); // job
        assertEquals(Long.valueOf(1), box.findLovDialogTableCell(0, 3).getValue()); // mgr
        assertEquals("1998-01-19T00:00:00.000Z", box.findLovDialogTableCell(0, 4).getValue()); // hiredate
        assertEquals(Long.valueOf(23432), box.findLovDialogTableCell(0, 5).getValue()); // salary
        assertEquals(Long.valueOf(5454), box.findLovDialogTableCell(0, 6).getValue()); // comm
        assertEquals(Long.valueOf(40), box.findLovDialogTableCell(0, 7).getValue()); // deptno

        // select row 3 (Bob433)
        box.findLovDialogTableCell(2, 0).click();
        box.findSearchDialog().findOkButton().click();
        assertEquals("Bob433", box.getValue());
        assertEquals("Bob433", box.getSubmittedValue());
        assertEquals(true, box.isValid());
    }

    @Test
    public void testSelectOneRadio() {
        EditableTablePage page = pages.goHome();
        int rowindex = 0;
        AdfSelectOneRadio radio = page.findSelectOneRadio(rowindex);
        assertNull(radio.getValue());
        radio.clickItemByIndex(1);
        assertEquals("1", radio.getValue());
        radio.clickItemByLabel("zucchini");
        assertEquals("3", radio.getValue());
        assertEquals("zucchini", radio.getItemLabel(Integer.parseInt((String) radio.getValue())));
    }

    public static void main(String[] args) {
        String[] args2 = { EditableTableTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

}
