package com.redheap.selenium.component;

import com.redheap.selenium.component.uix.UixInput;

import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * Selenium component class for ADF inputNumberSpinbox component (RichInputNumberSpinbox.class)
 */
public class AdfInputNumberSpinbox extends UixInput {
    
    // elements from http://jdevadf.oracle.com/adf-richclient-demo/docs/js-subids.html#inputNumberSpinbox
    private static final String SUBID_content = "content";
    private static final String SUBID_label = "label";
    private static final String SUBID_increment = "increment";
    private static final String SUBID_decrement = "decrement";

    public AdfInputNumberSpinbox(WebDriver webDriver, String string) {
        super(webDriver, string);
    }

    /**
     * Returns the content ( HTML input tag )
     *
     * To get the inputNumberSpinbox value though, just use AdfInputNumberSpinbox.getValue()
     *
     * @return WebElement
     */
    protected WebElement findContent() {
        return findSubIdElement(SUBID_content);
    }

    /**
     * Returns the label ( HTML label tag )
     *
     * @return WebElement
     */
    protected WebElement findLabel() {
        return findSubIdElement(SUBID_label);
    }

    /**
     * Returns increment button ( HTML a tag )
     *
     * @return WebElement
     */
    protected WebElement findIncrement() {
        return findSubIdElement(SUBID_increment);
    }

    /**
     * Returns decrement button ( HTML a tag )
     *
     * @return WebElement
     */
    protected WebElement findDecrement() {
        return findSubIdElement(SUBID_decrement);
    }

    /**
     * Method to press the increment button of the inputNumberSpinbox
     */
    public void clickIncrement() {
        findIncrement().click();
        waitForPpr();
    }

    /**
     * Method to press the decrement button of the inputNumberSpinbox
     */
    public void clickDecrement() {
        findDecrement().click();
        waitForPpr();
    }
    
    /**
     * Custom clear function for the spinbox, since ctrl shift home & end select the min and max value.
     */
    public void clear() {
        Actions actions = new Actions(getDriver());
        if (isPlatform(Platform.MAC)) {
            actions.sendKeys(findContentNode(), Keys.chord(Keys.COMMAND, "a"), Keys.DELETE);
        } else {
            actions.sendKeys(findContentNode(), Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        }
        actions.perform();
    }

}
