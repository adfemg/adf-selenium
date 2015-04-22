package com.redheap.selenium.component;

import com.redheap.selenium.component.uix.UixInput;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AdfTextEditor extends UixInput {

    // subid's at http://jdevadf.oracle.com/adf-richclient-demo/docs/js-subids.html
    private static final String SUBID_cont = "cont"; // Gets the IFrame
    private static final String SUBID_content =
        "content"; // Gets the Head Div with all other content like toolbar,IFrame etc.
    private static final String SUBID_label = "label"; // Gets the content_Label

    public AdfTextEditor(WebDriver webDriver, String clientId) {
        super(webDriver, clientId);
    }

    /**
     * Gets the component label.
     * @return the component label
     */
    public String getLabel() {
        WebElement element = findlabel();
        return element.getText();
    }

    /**
     * Method to get the IFrame WebElement.
     * @return the IFrame WebElement
     */
    protected WebElement findCont() {
        return findSubIdElement(SUBID_cont);
    }

    /**
     * Method to get the Content WebElement.
     * <p>
     * This element has both the Toolbar as well as the IFrame with the content.
     * @return the Content WebElement
     */
    protected WebElement findContent() {
        return findSubIdElement(SUBID_content);
    }

    /**
     * Method to get the Content Label WebElement.
     * @return the Content Label WebElement
     */
    protected WebElement findlabel() {
        return findSubIdElement(SUBID_label);
    }
}