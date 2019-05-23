package com.redheap.selenium.component;

import com.redheap.selenium.component.uix.UixInput;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AdfQuery extends AdfComponent {

    // SubIds for components of InputPopup's RichQuery section
    private static final String SUBID_criterion_label = "criterion_label";// element for search criteria field X. Here X is a digit refers to criterion index. Interanal Client SubId crtnLab
    private static final String SUBID_criterion_operator = "criterion_operator";// element for search criteria operator (as "Between"). For example value=1. Here the digit refers to criterion index.
    private static final String SUBID_criterion_value = "criterion_value";// element for search criteria field XY. 
                                                                          // Here the first digit X refers to criterion index and second digit Y is the index of the value field of that criterion.
                                                                          // As in case "Between" operator there are two value fields having "value" such as 20 and 21.
    private static final String SUBID_search_button = "search_button";// element for search button.
    private static final String SUBID_reset_search_button =  "reset_button";
    
    
    public AdfQuery(WebDriver webDriver, String clientid) {
        super(webDriver, clientid);
    }

    public <T extends UixInput> T findCriterionValueFieldByIndex(int criterionIndex, int valueFieldIndex) {
        // Here the first digit of index refers to criterion index and second digit is the index of the value field of that criterion.
        // As in case "Between" operator there are two value fields having "value" such as 20 and 21.
        return findSubIdComponent(SUBID_criterion_value + "["+ criterionIndex + valueFieldIndex + "]");
    }
    
    protected WebElement findPopupQuerySearchButton() {
        return findSubIdElement(SUBID_search_button);
    }
    
    protected WebElement findPopupQueryResetSearchButton() {
        return findSubIdElement(SUBID_reset_search_button); 
    }
        
    public void clickPopupQuerySearchButton() {
        findPopupQuerySearchButton().click();
        waitForPpr();
    }
    
    public void clickPopupQueryResetSearchButton() {
        findPopupQueryResetSearchButton().click();
        waitForPpr();
    }

    
}
