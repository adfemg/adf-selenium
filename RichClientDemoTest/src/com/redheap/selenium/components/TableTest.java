package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfTable;
import com.redheap.selenium.junit.PageProvider;
import com.redheap.selenium.junit.SavePageSourceOnFailure;
import com.redheap.selenium.junit.ScreenshotOnFailure;
import com.redheap.selenium.junit.WebDriverResource;
import com.redheap.selenium.pages.TableDetailStampDemoPage;

import java.io.File;

import java.util.Arrays;

import static org.junit.Assert.*;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;


public class TableTest {

    @ClassRule
    public static WebDriverResource driver = new WebDriverResource();
    @Rule
    public PageProvider<TableDetailStampDemoPage> detailStampPage =
        new PageProvider(TableDetailStampDemoPage.class, DETAIL_STAMP_PAGE, driver.getDriver());
    @Rule
    public TestWatcher screenshotOnFailure = new ScreenshotOnFailure(driver.getDriver(), new File("errors"));
    @Rule
    public TestWatcher saveSourceOnFailure = new SavePageSourceOnFailure(driver.getDriver(), new File("errors"));

    private static final String DETAIL_STAMP_PAGE =
        "http://localhost:7101/adf-richclient-demo/faces/components/table/detailStampTable.jspx";

    @Test
    public void testDetailStamp() {
        AdfTable table = detailStampPage.goHome().findTable();
        assertFalse(table.isRowDisclosed(0));
        table.discloseRowDetail(0);
        assertTrue(table.isRowDisclosed(0));
        assertFalse(table.isRowDisclosed(30));
        table.discloseRowDetail(30);
        assertTrue(table.isRowDisclosed(30));
        table.discloseRowDetail(60);
        assertTrue(table.isRowDisclosed(60));
        table.discloseRowDetail(60);
        assertFalse(table.isRowDisclosed(60));
    }

    @Test
    public void testRowSelection() {
        AdfTable table = detailStampPage.goHome().findTable();
        table.selectRow(3);
        assertEquals(Arrays.asList(3), table.getSelectedRows());
        table.selectAdditionalRow(5);
        assertEquals(Arrays.asList(3,5), table.getSelectedRows());
        table.selectToRow(8); // starts new selection from current row (5), so deselects 3
        assertEquals(Arrays.asList(5,6,7,8), table.getSelectedRows());
    }

    @Test
    public void testChildComponent() {
        TableDetailStampDemoPage page = detailStampPage.goHome();
        assertEquals("admin.jar", page.findName(2).getValue());
        assertEquals("database", page.findName(7).getValue());
        page.findTable().discloseRowDetail(9);
        assertEquals("1,290 KB", page.findSizeDetailFacet(9).getValue());
    }

    public static void main(String[] args) {
        String[] args2 = { TableTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

}
