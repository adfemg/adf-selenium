package com.redheap.selenium.component;

import com.redheap.selenium.component.uix.UixInput;

import java.io.File;

import org.openqa.selenium.WebDriver;

public class AdfInputFile extends UixInput {

    // subid's at http://jdevadf.oracle.com/adf-richclient-demo/docs/js-subids.html
    // most are only relevant after partial-submit and update af:inputFile is available
    private static final String SUBID_content = "content"; // <input type=file>
    private static final String SUBID_dlg = "dlg"; // <div>
    private static final String SUBID_label = "label"; // <label>
    private static final String SUBID_upBtn = "upBtn"; // af:commandButton after partial submit
    private static final String SUBID_upVal = "upVal"; // <span> after partial submit or null

    public AdfInputFile(WebDriver driver, String clientid) {
        super(driver, clientid);
    }

    public void typeFileName(File file) {
        if (!(file != null && file.isFile() && file.canRead())) {
            throw new IllegalArgumentException("cannot read file " + file);
        }
        // sendKeys to input[type='file'] already triggers submit when autosubmit=true, no need for Tab-key
        sendKeys(file.getAbsolutePath());
        waitForPpr();
    }

}
