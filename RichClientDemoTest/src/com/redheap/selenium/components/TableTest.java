package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfTable;
import com.redheap.selenium.pages.TableDetailStampDemoPage;

import java.util.Arrays;

import static org.junit.Assert.*;
import org.junit.Test;


public class TableTest extends PageTestBase<TableDetailStampDemoPage> {

    @Test
    public void testDetailStamp() {
        AdfTable table = pages.goHome().findTable();
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
        AdfTable table = pages.goHome().findTable();
        table.selectRow(3);
        assertEquals(Arrays.asList(3), table.getSelectedRows());
        table.selectAdditionalRow(5);
        assertEquals(Arrays.asList(3,5), table.getSelectedRows());
        table.selectToRow(8); // starts new selection from current row (5), so deselects 3
        assertEquals(Arrays.asList(5,6,7,8), table.getSelectedRows());
    }

    @Test
    public void testChildComponent() {
        TableDetailStampDemoPage page = pages.goHome();
        assertEquals("admin.jar", page.findName(2).getValue());
        assertEquals("database", page.findName(7).getValue());
        page.findTable().discloseRowDetail(9);
        assertEquals("1,290 KB", page.findSizeDetailFacet(9).getValue());
    }

    @Test
    public void testNonExistingChildComponent() {
        TableDetailStampDemoPage page = pages.goHome();
        AdfTable table = page.findTable();
        assertNotNull("existing component by row-index", table.findAdfComponent("ot4", 0));
        assertNull("non-existing component by row-index", table.findAdfComponent("foo", 0));
        assertNull("existing component by non-existing row-index", table.findAdfComponent("ot4", 9999999));
        assertNull("non-existing component by non-existing row-index", table.findAdfComponent("foo", 9999999));
        String rowkey = table.getRowKey(0);
        assertNotNull("existing component by row-key", table.findAdfComponent("ot4", rowkey));
        assertNull("non-existing component by row-key", table.findAdfComponent("foo", rowkey));
        assertNull("existing component by non-existing row-key", table.findAdfComponent("ot4", "9999999"));
        assertNull("non-existing component by non-existing row-key", table.findAdfComponent("foo", "9999999"));
    }

    @Test
    public void testCount() {
        TableDetailStampDemoPage page = pages.goHome();
        assertEquals(5400, page.findTable().getRowCount());
    }
    
    @Test
    public void testSelectRowByColumnCellValue() {
        TableDetailStampDemoPage page = pages.goHome();
        AdfTable table = page.findTable();
        table.selectRow("dmoTpl:table1:c7", "admin.jar");
        table.selectRow("dmoTpl:table1:c7", "connectors");
    }

    public static void main(String[] args) {
        String[] args2 = { TableTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

    @Override
    protected Class<TableDetailStampDemoPage> getPageClass() {
        return TableDetailStampDemoPage.class;
    }

    @Override
    protected String getJspxName() {
        return "table/detailStampTable.jspx";
    }
}
