package com.redheap.selenium.dialogs;

import com.redheap.selenium.component.AdfCommandButton;
import com.redheap.selenium.page.WindowDialog;

import oracle.adf.view.rich.automation.selenium.Dialog;

import org.openqa.selenium.WebDriver;

public class NewFileWindowDialog extends WindowDialog {

    private final String saveButton = "saveNewFile";

    public NewFileWindowDialog(WebDriver driver, Dialog dialog) {
        super(driver, dialog);
    }

    @Override
    protected String getExpectedTitle() {
        return "New File";
    }

    public AdfCommandButton findSaveButton() {
        return findAdfComponent(saveButton);
    }

}
