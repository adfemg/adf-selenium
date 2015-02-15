package com.redheap.selenium.errors;

import org.openqa.selenium.WebDriverException;

public class AutomationDisabledException extends WebDriverException {

    private static final String DFLT_MSG = "oracle.adf.view.rich.automation.ENABLED should be set to true in web.xml or as a system property and ADF Faces automation JAR should be included in application";

    public AutomationDisabledException() {
        this(DFLT_MSG);
    }

    public AutomationDisabledException(String msg) {
        super(msg);
    }

    public AutomationDisabledException(Throwable cause) {
        this(DFLT_MSG,cause);
    }

    public AutomationDisabledException(String msg, Throwable cause) {
        super(msg, cause);
    }


}
