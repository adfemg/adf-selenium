package com.redheap.selenium.pages;

import com.redheap.selenium.component.AdfButton;
import com.redheap.selenium.component.AdfCommandButton;
import com.redheap.selenium.component.AdfCommandLink;
import com.redheap.selenium.component.AdfDialog;
import com.redheap.selenium.component.AdfInputComboboxListOfValues;
import com.redheap.selenium.component.AdfInputText;
import com.redheap.selenium.component.AdfOutputText;
import com.redheap.selenium.component.AdfSelectOneRadio;
import com.redheap.selenium.component.AdfTable;
import com.redheap.selenium.page.Page;

import org.openqa.selenium.WebDriver;

public class EditableTablePage extends Page {

    private final String tableId = "dmoTpl:table1";
    private final String popupButtonId = "popupButton";
    //private final String popupId = "popupDialog";
    private final String dialogId = "d2";
    private final String dialogOkButton = "d2_ok";
    private final String commandLinkId = "cl1";
    private final String clickTextId = "clickText";
    private final String inputTextId = "it2";
    private final String requiredInputTextId = "it3";
    private final String comboboxId = "idInputComboboxListOfValues";
    private final String selectOneRadioId = "targetRadio";

    public EditableTablePage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected String getExpectedTitle() {
        return "Editable Table Demo";
    }

    public AdfTable findTable() {
        AdfTable table = findDocument().findAdfComponent(tableId, AdfTable.class);
        return table;
    }

    public AdfCommandButton findPopupButton(int rowIndex) {
        return findTable().findAdfComponent(popupButtonId, rowIndex, AdfCommandButton.class);
    }

    public AdfDialog findDialog(int rowIndex) {
        return findTable().findAdfComponent(dialogId, rowIndex, AdfDialog.class);
    }

    public AdfButton findDialogOkButton(int rowIndex) {
        return findDialog(rowIndex).findAdfComponent(dialogOkButton, AdfButton.class);
    }

    public AdfCommandLink findCommandLink(int rowIndex) {
        return findTable().findAdfComponent(commandLinkId, rowIndex, AdfCommandLink.class);
    }

    public AdfOutputText findClickText(int rowIndex) {
        return findTable().findAdfComponent(clickTextId, rowIndex, AdfOutputText.class);
    }

    public AdfInputText findInputText(int rowIndex) {
        return findTable().findAdfComponent(inputTextId, rowIndex, AdfInputText.class);
    }

    public AdfInputText findRequiredInputText(int rowIndex) {
        return findTable().findAdfComponent(requiredInputTextId, rowIndex, AdfInputText.class);
    }

    public AdfInputComboboxListOfValues findCombobox(int rowIndex) {
        return findTable().findAdfComponent(comboboxId, rowIndex, AdfInputComboboxListOfValues.class);
    }

    public AdfSelectOneRadio findSelectOneRadio(int rowIndex) {
        return findTable().findAdfComponent(selectOneRadioId, rowIndex, AdfSelectOneRadio.class);
    }

}
