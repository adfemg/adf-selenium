package com.redheap.selenium.pages;

import com.redheap.selenium.component.AdfInputText;
import com.redheap.selenium.component.AdfOutputText;
import com.redheap.selenium.component.AdfTable;
import com.redheap.selenium.page.Page;

import org.openqa.selenium.WebDriver;

public class TableDetailStampDemoPage extends Page {

    private final String table = "dmoTpl:table1";
    private final String nameColumn = "ot4";
    private final String sizeDetail = "it2";

    public TableDetailStampDemoPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected String getExpectedTitle() {
        return "Table with a detailStamp Facet";
    }

    public AdfTable findTable() {
        return findAdfComponent(table);
    }

    public AdfOutputText findName(int rowIndex) {
        return findTable().findAdfComponent(nameColumn, rowIndex);
    }

    public AdfInputText findSizeDetailFacet(int rowIndex) {
        return findTable().findAdfComponent(sizeDetail, rowIndex);
    }

}
