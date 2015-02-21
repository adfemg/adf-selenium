package com.redheap.selenium.component;

import com.redheap.selenium.component.uix.UixInput;

import org.openqa.selenium.WebDriver;

public class AdfInputComboboxListOfValues extends UixInput {

    // see http://jdevadf.oracle.com/adf-richclient-demo/docs/js-subids.html
    private static final String SUBID_content = "content"; // <input> element
    private static final String SUBID_dropdownIcon = "dropdownIcon"; // <a> element to expand dropdown
    private static final String SUBID_dropdownPopup = "dropdownPopup"; // RichPopup
    private static final String SUBID_dropdownTable = "dropdownTable"; // Table
    private static final String SUBID_label = "label"; // <label> element
    private static final String SUBID_lovDialog_query = "lovDialog_query"; // RichQuery
    private static final String SUBID_lovDialog_table = "lovDialog_table"; // Table
    private static final String SUBID_lovDialog_table_columnHeader_text =
        "lovDialog_table_columnHeader_text"; // RichColumn
    private static final String SUBID_searchLink = "searchLink"; // CommandLink
    private static final String SUBID_search_dialog = "search_dialog"; // Dialog
    private static final String SUBID_search_dialog_popup = "search_dialog_popup"; // Popup

    public AdfInputComboboxListOfValues(WebDriver webDriver, String clientid) {
        super(webDriver, clientid);
    }

    @Override
    protected String getExpectedComponentType() {
        return "oracle.adf.RichInputComboboxListOfValues";
    }

    public AdfPopup findDropdownPopup() {
        // always exists
        return findSubIdComponent(SUBID_dropdownPopup, AdfPopup.class);
    }

    public AdfTable findDropdownTable() {
        // only exists when combobox dropdown expanded
        return findSubIdComponent(SUBID_dropdownTable, AdfTable.class);
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

    public AdfCommandLink findSearchLink() {
        return findSubIdComponent(SUBID_searchLink, AdfCommandLink.class);
    }

    public AdfDialog findSearchDialog() {
        return findSubIdComponent(SUBID_search_dialog, AdfDialog.class);
    }

    public AdfPopup findSearchDialogPopup() {
        return findSubIdComponent(SUBID_search_dialog_popup, AdfPopup.class);
    }

}
