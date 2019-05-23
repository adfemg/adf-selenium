package com.redheap.selenium.component.uix;

import com.redheap.selenium.component.AdfColumn;
import com.redheap.selenium.component.AdfDialog;
import com.redheap.selenium.component.AdfOutputText;
import com.redheap.selenium.component.AdfPopup;
import com.redheap.selenium.component.AdfQuery;
import com.redheap.selenium.component.AdfTable;

import org.openqa.selenium.WebDriver;

public abstract class UixInputPopup extends UixInput {

    // see http://jdevadf.oracle.com/adf-richclient-demo/docs/js-subids.html
    private static final String SUBID_content = "content"; // <input> element
    private static final String SUBID_label = "label"; // <label> element
    // All list of inner SubIds of InputPopup's RichQuery section see in AdfQuery class
    private static final String SUBID_lovDialog_query = "lovDialog_query"; // RichQuery
    //SubIds for components of InputPopup's search result Table
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

    public AdfPopup findSearchDialogPopup() {
        return findSubIdComponent(SUBID_search_dialog_popup);
    }

    public boolean isPopupVisible() {
        AdfPopup popup = findSearchDialogPopup();
        return popup != null && popup.isPopupVisible();
    }

    public AdfDialog findSearchDialog() {
        return findSubIdComponent(SUBID_search_dialog);
    }

    /*
     * Methods for locating components of InputPopup's RichQuery section
    */

    public AdfQuery findLovDialogQuery() {
        return findSubIdComponent(SUBID_lovDialog_query);
    }

    /*
     * Methods for locating components of InputPopup's search result Table
    */

    public AdfTable findLovDialogTable() {
        return findSubIdComponent(SUBID_lovDialog_table);
    }

    public AdfColumn findLovDialogTableColumn(int colIndex) {
        return findSubIdComponent(SUBID_lovDialog_table_columnHeader_text + "[" + colIndex + "]");
    }

    public AdfOutputText findLovDialogTableCell(int rowIndex, int colIndex) {
        return findSubIdComponent(SUBID_lovDialog_table + "[" + rowIndex + "][" + colIndex + "]");
    }

}
