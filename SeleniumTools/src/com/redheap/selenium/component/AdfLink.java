package com.redheap.selenium.component;

import org.openqa.selenium.WebDriver;

/**
 * Representation class for a {@code af|link} page component.
 */
public class AdfLink extends AdfComponent {

    private static final String JS_QUEUE_NEW_ACTION_EVENT =
        JS_FIND_COMPONENT + "AdfActionEvent.queue(comp, comp.getPartialSubmit());";

    /**
     * Constructor.
     * @param webDriver
     * @param clientid
     */
    public AdfLink(final WebDriver webDriver, final String clientid) {
        super(webDriver, clientid);
    }

    /**
     * Execute the action configured on the link component.
     */
    public void doAction() {
        executeScript(JS_QUEUE_NEW_ACTION_EVENT, getClientId());
    }

}
