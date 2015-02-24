package com.redheap.selenium.component;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class AdfButton extends AdfComponent {

    // see http://jdevadf.oracle.com/adf-richclient-demo/docs/js-subids.html
    private static final String SUBID_dropdown = "dropdown"; // <a> element
    private static final String SUBID_icon = "icon"; // <img> element
    private static final String SUBID_text = "text"; // <span> element

    private static final String JS_GET_DISABLED = JS_FIND_COMPONENT + "return comp.getDisabled();";
    private static final String JS_GET_SELECTED = JS_FIND_COMPONENT + "return comp.getSelected();";

    public AdfButton(WebDriver driver, String clientid) {
        super(driver, clientid);
    }

    @Override
    protected String getExpectedComponentType() {
        return "oracle.adf.RichButton";
    }

    @Override
    public void click() {
        // af:button with targetFrame="_blank" and external destination only works when clicking on nested <a>
        List<WebElement> elements = getElement().findElements(By.tagName("a"));
        if (!elements.isEmpty()) {
            elements.get(0).click();
            waitForPpr();
        } else {
            super.click();
        }
    }

    public boolean isDisabled() {
        return (Boolean) executeScript(JS_GET_DISABLED, getClientId());
    }

    public boolean isSelected() {
        return (Boolean) executeScript(JS_GET_SELECTED, getClientId());
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
