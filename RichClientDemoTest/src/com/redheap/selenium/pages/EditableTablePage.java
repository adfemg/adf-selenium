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
        AdfTable table = findDocument().findAdfComponent(tableId);
        return table;
    }

    public AdfCommandButton findPopupButton(int rowIndex) {
        return findTable().findAdfComponent(popupButtonId, rowIndex);
    }

    public AdfDialog findDialog(int rowIndex) {
        return findTable().findAdfComponent(dialogId, rowIndex);
    }

    public AdfButton findDialogOkButton(int rowIndex) {
        return findDialog(rowIndex).findAdfComponent(dialogOkButton);
    }

    public AdfCommandLink findCommandLink(int rowIndex) {
        return findTable().findAdfComponent(commandLinkId, rowIndex);
    }

    public AdfOutputText findClickText(int rowIndex) {
        return findTable().findAdfComponent(clickTextId, rowIndex);
    }

    public AdfInputText findInputText(int rowIndex) {
        return findTable().findAdfComponent(inputTextId, rowIndex);
    }

    public AdfInputText findRequiredInputText(int rowIndex) {
        return findTable().findAdfComponent(requiredInputTextId, rowIndex);
    }

    public AdfInputComboboxListOfValues findCombobox(int rowIndex) {
        return findTable().findAdfComponent(comboboxId, rowIndex);
    }

    public AdfSelectOneRadio findSelectOneRadio(int rowIndex) {
        return findTable().findAdfComponent(selectOneRadioId, rowIndex);
    }

}
