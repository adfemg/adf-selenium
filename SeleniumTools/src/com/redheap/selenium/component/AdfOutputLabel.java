package com.redheap.selenium.component;

import com.redheap.selenium.component.uix.UixValue;

import org.openqa.selenium.WebDriver;

/**
 * AdfOutputLabel component class.
 */
public class AdfOutputLabel extends UixValue {

    /**
     * Constructor.
     * @param webDriver
     * @param clientid
     */
    public AdfOutputLabel(final WebDriver webDriver, final String clientid) {
        super(webDriver, clientid);
    }

}
