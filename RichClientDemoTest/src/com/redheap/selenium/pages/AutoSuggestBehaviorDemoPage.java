package com.redheap.selenium.pages;

import com.redheap.selenium.component.AdfInputText;
import com.redheap.selenium.component.AdfListOfValues;
import com.redheap.selenium.component.AutoSuggestBehavior;
import com.redheap.selenium.page.Page;

import org.openqa.selenium.WebDriver;

public class AutoSuggestBehaviorDemoPage extends Page {

    private final String inputText = "dmoTpl:idInputText";
    private final String inputListOfValues = "dmoTpl:lov";
    private final String inputComboboxListOfValue = "dmoTpl:idInputComboboxListOfValues";

    public AutoSuggestBehaviorDemoPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected String getExpectedTitle() {
        return "autoSuggestBehavior Demo";
    }

    public AutoSuggestBehavior<AdfInputText> findInputText() {
        return new AutoSuggestBehavior<AdfInputText>(findAdfComponent(inputText, AdfInputText.class));
    }

    public AutoSuggestBehavior<AdfListOfValues> findListOfValues() {
        return new AutoSuggestBehavior<AdfListOfValues>(findAdfComponent(inputListOfValues, AdfListOfValues.class));
    }

}
