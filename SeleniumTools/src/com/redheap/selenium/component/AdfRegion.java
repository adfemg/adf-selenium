package com.redheap.selenium.component;

import com.redheap.selenium.domain.PageMessageWrapper;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;


public class AdfRegion extends AdfComponent {

    public AdfRegion(WebDriver driver, String clientid) {
        super(driver, clientid);
    }

    /**
     * Convenience method to get all the messages on the page.
     * <p>
     * This method was put here mainly so the {@link com.redheap.selenium.page.PageFragment PageFragment} has access
     * to this method. This is because the method requires a JavascriptExecutor from a driver and PageFragment
     * doesn't have it while you do want to ask your fragment if there are any messages if you are writing test against
     * the taskflowtester.
     * @return A {@link com.redheap.selenium.domain.PageMessageWrapper PageMessageWrapper} object containing
     * all messages and some convenicence methods
     */
    public PageMessageWrapper getAllMessages() {
        return PageMessageWrapper.getAllMessages((JavascriptExecutor) getDriver());
    }

}
