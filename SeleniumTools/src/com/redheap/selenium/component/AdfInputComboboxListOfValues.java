package com.redheap.selenium.component;

import com.redheap.selenium.component.uix.UixInputPopup;

import org.openqa.selenium.WebDriver;

public class AdfInputComboboxListOfValues extends UixInputPopup {

    // see http://jdevadf.oracle.com/adf-richclient-demo/docs/js-subids.html
    private static final String SUBID_dropdownIcon = "dropdownIcon"; // <a> element to expand dropdown
    private static final String SUBID_dropdownPopup = "dropdownPopup"; // RichPopup
    private static final String SUBID_dropdownTable = "dropdownTable"; // Table
    private static final String SUBID_searchLink = "searchLink"; // CommandLink

    public AdfInputComboboxListOfValues(WebDriver webDriver, String clientid) {
        super(webDriver, clientid);
    }

    public AdfPopup findDropdownPopup() {
        // always exists
        return findSubIdComponent(SUBID_dropdownPopup);
    }

    public AdfTable findDropdownTable() {
        // only exists when combobox dropdown expanded
        return findSubIdComponent(SUBID_dropdownTable);
    }

    public AdfCommandLink findSearchLink() {
        return findSubIdComponent(SUBID_searchLink);
    }

}
