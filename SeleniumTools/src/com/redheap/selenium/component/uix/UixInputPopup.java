package com.redheap.selenium.component.uix;

import com.redheap.selenium.component.AdfColumn;
import com.redheap.selenium.component.AdfDialog;
import com.redheap.selenium.component.AdfOutputText;
import com.redheap.selenium.component.AdfPopup;
import com.redheap.selenium.component.AdfQuery;
import com.redheap.selenium.component.AdfTable;

import org.openqa.selenium.WebDriver;

public abstract class UixInputPopup extends UixInput {

    private static final String SUBID_content = "content"; // <input> element
    private static final String SUBID_label = "label"; // <label> element
    private static final String SUBID_lovDialog_query = "lovDialog_query"; // RichQuery
    private static final String SUBID_lovDialog_table = "lovDialog_table"; // Table
    private static final String SUBID_lovDialog_table_cellContainer = "lovDialog_table_cellContainer"; // <td> element
    private static final String SUBID_lovDialog_table_columnHeader_text =
        "lovDialog_table_columnHeader_text"; // RichColumn
    private static final String SUBID_lovDialog_toolbar = "lovDialog_toolbar"; // ??h
    private static final String SUBID_search_dialog = "search_dialog"; // Dialog
    private static final String SUBID_search_dialog_popup = "search_dialog_popup"; // Popup

    public UixInputPopup(WebDriver webDriver, String clientid) {
        super(webDriver, clientid);
    }

    public AdfQuery findLovDialogQuery() {
        return findSubIdComponent(SUBID_lovDialog_query, AdfQuery.class);
    }

    public AdfTable findLovDialogTable() {
        return findSubIdComponent(SUBID_lovDialog_table, AdfTable.class);
    }

    public AdfColumn findLovDialogTableColumn(int colIndex) {
        return findSubIdComponent(SUBID_lovDialog_table_columnHeader_text + "[" + colIndex + "]", AdfColumn.class);
    }

    public AdfOutputText findLovDialogTableCell(int rowIndex, int colIndex) {
        return findSubIdComponent(SUBID_lovDialog_table + "[" + rowIndex + "][" + colIndex + "]", AdfOutputText.class);
    }

    public AdfDialog findSearchDialog() {
        return findSubIdComponent(SUBID_search_dialog, AdfDialog.class);
    }

    public boolean isPopupVisible() {
        AdfPopup popup = findSearchDialogPopup();
        return popup != null && popup.isPopupVisible();
    }

    public AdfPopup findSearchDialogPopup() {
        return findSubIdComponent(SUBID_search_dialog_popup, AdfPopup.class);
    }

}
