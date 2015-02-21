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

import org.junit.Assert;
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
        Assert.assertEquals("Text", page.findDialog(0).getTitle());
        page.findDialogOkButton(rowindex).click();
    }

    @Test
    public void testInputText() {
        EditableTablePage page = pages.goHome();
        int rowindex = 0;
        AdfInputText it = page.findInputText(rowindex);
        it.typeValue("foo");
        Assert.assertEquals("foo", it.getValue());
        Assert.assertEquals("foo", it.getSubmittedValue());
        it.typeValue("bar");
        Assert.assertEquals("bar", it.getValue());
        Assert.assertEquals("bar", it.getValue());
        it.sendKeys("baz");
        it.tabNext();
        Assert.assertEquals("barbaz", it.getValue());
        Assert.assertEquals("barbaz", it.getValue());
    }

    @Test
    public void testCommandLink() {
        EditableTablePage page = pages.goHome();
        int rowindex = 0;
        AdfOutputText clickText = page.findClickText(rowindex);
        Assert.assertNull(clickText.getValue());
        page.findCommandLink(rowindex).click();
        Object val = clickText.getValue();
        Assert.assertTrue(val instanceof String && ((String) val).startsWith("Clicked on"));
    }

    @Test
    public void testCommandLinkRow3() {
        EditableTablePage page = pages.goHome();
        int rowindex = 2;
        AdfOutputText clickText = page.findClickText(rowindex);
        Assert.assertNull(clickText.getValue());
        page.findCommandLink(rowindex).click();
        Object val = clickText.getValue();
        Assert.assertTrue(val instanceof String && ((String) val).startsWith("Clicked on"));
    }

    @Test
    public void testCommandLinkRow100() {
        EditableTablePage page = pages.goHome();
        int rowindex = 100;
        AdfOutputText clickText = page.findClickText(rowindex);
        Assert.assertNull(clickText.getValue());
        page.findCommandLink(rowindex).click();
        Object val = clickText.getValue();
        Assert.assertTrue(val instanceof String && ((String) val).startsWith("Clicked on"));
    }

    @Test
    public void testRequired() {
        EditableTablePage page = pages.goHome();
        int rowindex = 0;
        AdfInputText it = page.findRequiredInputText(rowindex);
        it.typeValue("foo");
        Assert.assertEquals("foo", it.getValue());
        Assert.assertEquals("foo", it.getSubmittedValue());
        Assert.assertEquals(true, it.isValid());
        it.typeValue("");
        Assert.assertEquals(null, it.getValue());
        Assert.assertEquals("", it.getSubmittedValue());
        Assert.assertEquals(false, it.isValid());
    }

    @Test
    public void testCombobxFullValue() {
        EditableTablePage page = pages.goHome();
        int rowindex = 0;
        AdfInputComboboxListOfValues box = page.findCombobox(rowindex);
        // type full value
        box.typeValue("Bob472");
        Assert.assertEquals("Bob472", box.getValue());
        Assert.assertEquals("Bob472", box.getSubmittedValue());
        Assert.assertEquals(true, box.isValid());
    }

    @Test
    public void testCombobxSingleSuggest() {
        EditableTablePage page = pages.goHome();
        int rowindex = 0;
        AdfInputComboboxListOfValues box = page.findCombobox(rowindex);
        // type part of value but enough to have a single match
        box.typeValue("Bob47");
        Assert.assertEquals("Bob472", box.getValue());
        Assert.assertEquals("Bob472", box.getSubmittedValue());
        Assert.assertEquals(true, box.isValid());
    }

    @Test
    public void testCombobxMultiSuggest() {
        EditableTablePage page = pages.goHome();
        int rowindex = 0;
        AdfInputComboboxListOfValues box = page.findCombobox(rowindex);
        // type part of value but enough to have a single match
        box.typeValue("Bob4");
        // TODO: thorough testing of InputComboboxListOfValues should be separate test and not part of EditableTable
        Assert.assertNotNull(box.findDropdownPopup());
        //Assert.assertNull(box.findDropdownTable()); // only exist when expanding combobox dropdown
        Assert.assertNotNull(box.findLovDialogQuery());
        Assert.assertNotNull(box.findLovDialogTable());
        Assert.assertNotNull(box.findLovDialogTableColumn(0));
        Assert.assertNotNull(box.findLovDialogTableCell(0, 0));
        Assert.assertNotNull(box.findSearchDialog());
        Assert.assertNotNull(box.findSearchDialogPopup());
        //Assert.assertNull(box.findSearchLink());

        Assert.assertEquals("Bob4", box.findLovDialogTableCell(0, 0).getValue());
        Assert.assertEquals("Bob43", box.findLovDialogTableCell(1, 0).getValue());
        Assert.assertEquals("Bob433", box.findLovDialogTableCell(2, 0).getValue());
        Assert.assertEquals("Bob472", box.findLovDialogTableCell(3, 0).getValue());

        Assert.assertEquals(Long.valueOf(4), box.findLovDialogTableCell(0, 1).getValue()); // empno
        Assert.assertEquals("Engineer", box.findLovDialogTableCell(0, 2).getValue()); // job
        Assert.assertEquals(Long.valueOf(1), box.findLovDialogTableCell(0, 3).getValue()); // mgr
        Assert.assertEquals("1998-01-19T00:00:00.000Z", box.findLovDialogTableCell(0, 4).getValue()); // hiredate
        Assert.assertEquals(Long.valueOf(23432), box.findLovDialogTableCell(0, 5).getValue()); // salary
        Assert.assertEquals(Long.valueOf(5454), box.findLovDialogTableCell(0, 6).getValue()); // comm
        Assert.assertEquals(Long.valueOf(40), box.findLovDialogTableCell(0, 7).getValue()); // deptno

        // select row 3 (Bob433)
        box.findLovDialogTableCell(2, 0).click();
        box.findSearchDialog().findOkButton().click();
        Assert.assertEquals("Bob433", box.getValue());
        Assert.assertEquals("Bob433", box.getSubmittedValue());
        Assert.assertEquals(true, box.isValid());
    }

    @Test
    public void testSelectOneRadio() {
        EditableTablePage page = pages.goHome();
        int rowindex = 0;
        AdfSelectOneRadio radio = page.findSelectOneRadio(rowindex);
        Assert.assertNull(radio.getValue());
        radio.clickItemByIndex(1);
        Assert.assertEquals("1", radio.getValue());
        radio.clickItemByLabel("zucchini");
        Assert.assertEquals("3", radio.getValue());
        Assert.assertEquals("zucchini", radio.getItemLabel(Integer.parseInt((String) radio.getValue())));
    }

    public static void main(String[] args) {
        String[] args2 = { EditableTableTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

}
