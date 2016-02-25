package com.redheap.selenium.component;

import com.redheap.selenium.component.uix.UixInput;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AdfInputFile extends UixInput {

    // subid's at http://jdevadf.oracle.com/adf-richclient-demo/docs/js-subids.html
    // most are only relevant after partial-submit and update af:inputFile is available
    private static final String SUBID_content = "content"; // <input type=file>
    private static final String SUBID_dlg = "dlg"; // <div>
    private static final String SUBID_label = "label"; // <label>
    private static final String SUBID_upBtn = "upBtn"; // af:commandButton after partial submit
    private static final String SUBID_upVal = "upVal"; // <span> after partial submit or null

    private static final String JS_FIND_DIALOG_OK = JS_FIND_PEER+"var dlg=peer._getDialogSubId(); return dlg&&AdfDhtmlDialogPeer._getButtonDom(dlg, AdfDhtmlDialogPeer._OK_ID)";

    public AdfInputFile(WebDriver driver, String clientid) {
        super(driver, clientid);
    }

    public void typeFileName(File file) {
        if (!(file != null && file.isFile() && file.canRead())) {
            throw new IllegalArgumentException("cannot read file " + file);
        }
        boolean replacing = hasValue();
        if (replacing) {
            clickUpdateButton();
        }
        // sendKeys to input[type='file'] already triggers submit when autosubmit=true, no need for Tab-key
        sendKeys(file.getAbsolutePath());
        if (replacing) {
            findUpdateDialogOkButton().click();
        }
        waitForPpr();
    }

    public boolean hasValue() {
        return findUpdateValue() != null;
    }

    public String getUpdateValue() {
        WebElement span = findUpdateValue();
        return span == null ? null : span.getText();
    }

    protected void clickUpdateButton() {
        WebElement button = findUpdateButton();
        if (button == null) {
            throw new IllegalStateException("component does not have update button " + getClientId());
        }
        button.click();
        waitForPpr();
    }

    protected WebElement findUpdateValue() {
        return findSubIdElement(SUBID_upVal);
    }

    protected WebElement findUpdateDialogOkButton() {
        Object button = executeScript(JS_FIND_DIALOG_OK  , getClientId());
        return button instanceof WebElement ? (WebElement) button : null;
    }

    protected WebElement findUpdateButton() {
        return findSubIdElement(SUBID_upBtn);
    }

}
