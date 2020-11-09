package com.redheap.selenium.component;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AdfPanelCollection extends AdfComponent {

    private static final String SUBID_expandAll = "viewMenu_expandAll"; //
    private static final String SUBID_collapseAll = "viewMenu_collapseAll";
    private static final String SUBID_viewMenu = "viewMenu"; //


    public AdfPanelCollection(WebDriver webDriver, String string) {
        super(webDriver, string);
    }

    public void clickExpandAll() {
        WebElement viewmenu = findViewMenu();
        viewmenu.click();
        waitForPpr();

        WebElement expandElement = findExpandAllElement();
        expandElement.click();
        waitForPpr();
    }

    public void clickCollapseAll() {
        WebElement viewmenu = findViewMenu();
        viewmenu.click();
        waitForPpr();

        WebElement collapseElement = findCollapseAllElement();
        collapseElement.click();
        waitForPpr();
    }

    protected WebElement findExpandAllElement() {
        return findSubIdElement(SUBID_expandAll);
    }

    protected WebElement findViewMenu() {
        return findSubIdElement(SUBID_viewMenu);
    }

    protected WebElement findCollapseAllElement() {
        return findSubIdElement(SUBID_collapseAll);
    }
}
