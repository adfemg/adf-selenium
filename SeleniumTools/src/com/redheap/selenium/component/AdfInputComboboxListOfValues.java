package com.redheap.selenium.component;

import com.redheap.selenium.component.uix.UixInputPopup;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
    
    public List<String> getDropdownTableRows() {
        AdfTable dropdownTable = findDropdownTable();
        long ddRC = dropdownTable.getRowCount();
        List<String> dropdownTableRows = new ArrayList<String>();
        for (int i = 0; i < ddRC; i++) {
            dropdownTableRows.add(dropdownTable.findRow(i).getText());     
        }
        return dropdownTableRows;
    }

    public AdfCommandLink findSearchLink() {
        return findSubIdComponent(SUBID_searchLink);
    }

    protected WebElement findDropdownIcon() {
        return findSubIdElement(SUBID_dropdownIcon);
    }
    
    public void clickDropdownIcon() {
        findDropdownIcon().click();
        waitForPpr();
    }


}
