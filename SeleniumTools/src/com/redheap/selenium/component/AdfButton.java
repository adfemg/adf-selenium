package com.redheap.selenium.component;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class AdfButton extends AdfComponent {

    // see http://jdevadf.oracle.com/adf-richclient-demo/docs/js-subids.html
    private static final String SUBID_dropdown = "dropdown"; // <a> element
    private static final String SUBID_icon = "icon"; // <img> element
    private static final String SUBID_text = "text"; // <span> element

    public AdfButton(WebDriver driver, String clientid) {
        super(driver, clientid);
    }

    @Override
    protected String getExpectedComponentType() {
        return "oracle.adf.RichButton";
    }

    public boolean isDisabled() {
        return (Boolean) executeScript(String.format("%s.getDisabled()", scriptFindComponent()));
    }

    public boolean isSelected() {
        return (Boolean) executeScript(String.format("%s.getSelected()", scriptFindComponent()));
    }

    public void clickDropdownButton() {
        WebElement dropdown = findDropdownButtonElement();
        dropdown.click();
        waitForPpr();
        // opens dmoTpl:arrange2::popup which contains dmoTpl:pgl5 as button is no naming container
    }

    public boolean hasIcon() {
        return findIconElement() != null;
    }

    protected WebElement findDropdownButtonElement() {
        return findSubIdElement(SUBID_dropdown);
    }

    protected WebElement findIconElement() {
        return findSubIdElement(SUBID_icon);
    }

    protected WebElement findTextElement() {
        return findSubIdElement(SUBID_text);
    }

}
