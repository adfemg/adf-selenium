package com.redheap.selenium.component;

import org.openqa.selenium.WebDriver;


public class AdfRegion extends AdfComponent {

    //private WebElement element;

    public AdfRegion(WebDriver driver, String clientid) {
        super(driver, clientid);
        //this.element = webElement;
    }

    @Override
    protected String getExpectedComponentType() {
        return "oracle.adf.RichRegion";
    }

    /**
     * **for internal use only** Normally AdfComponents don't expose there WebElement as all interaction with the
     * element has to be through the AdfComponent. This is an exception as PageFragment needs access to the
     * WebElement for the af:region.
     * @return RemoteWebElement for the af:region DOM element
     */
    //public RemoteWebElement getElement() {
    //    return (RemoteWebElement) element;
    //}

}
