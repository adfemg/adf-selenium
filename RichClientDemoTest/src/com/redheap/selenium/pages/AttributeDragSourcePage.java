package com.redheap.selenium.pages;

import com.redheap.selenium.component.AdfInputText;
import com.redheap.selenium.component.AdfOutputText;
import com.redheap.selenium.page.Page;

import org.openqa.selenium.WebDriver;

public class AttributeDragSourcePage extends Page {

    private final String outputTextDragSource = "dmoTpl:outputDragSource";
    private final String inputTextDragSource = "dmoTpl:inputDragSource";
    private final String outputTextDropTarget = "dmoTpl:attributeDropTarget";

    public AttributeDragSourcePage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected String getExpectedTitle() {
        return "attributeDragSource demo";
    }

    public AdfOutputText findOutputTextDragSource() {
        return findAdfComponent(outputTextDragSource);
    }

    public AdfInputText findInputTextDragSource() {
        return findAdfComponent(inputTextDragSource);
    }

    public AdfOutputText findOutputTextDropTarget() {
        return findAdfComponent(outputTextDropTarget);
    }

}
