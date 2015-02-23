package com.redheap.selenium.pages;

import com.redheap.selenium.component.AdfButton;
import com.redheap.selenium.component.AdfCommandMenuItem;
import com.redheap.selenium.component.AdfComponent;
import com.redheap.selenium.component.AdfPanelGroupLayout;
import com.redheap.selenium.component.AdfPopup;
import com.redheap.selenium.dialogs.NewFileWindowDialog;
import com.redheap.selenium.page.Page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ButtonDemoPage extends Page {

    private final String compGuideButton = "dmoTpl:btn1";
    private final String disabledButton = "dmoTpl:ctb2";
    private final String noIconButton = "dmoTpl:ctb1";
    private final String boldButton = "dmoTpl:bold2";
    private final String arrangeButton = "dmoTpl:arrange2";
    private final String arrangeButtonPopupContent = "dmoTpl:pgl5";
    private final String showPopupButton = "dmoTpl:selectorBtn";
    private final String showPopupButtonPopup = "dmoTpl:popup";
    private final String showPopupButtonCopyMenuItem = "dmoTpl:cmi1";
    private final String useWindowButton = "dmoTpl:usewindowButton";
    private final String useWindowInlineDocButton = "dmoTpl:usewindowButton2";
    private final By targetFrameBlankButton = By.linkText("Oracle.com");

    public ButtonDemoPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected String getExpectedTitle() {
        return "button Demo";
    }

    public TagGuidePage clickComponentGuideButton() {
        findAdfComponent(compGuideButton, AdfButton.class).click();
        return navigatedTo(TagGuidePage.class);
    }

    public AdfButton findDisabledButton() {
        return findAdfComponent(disabledButton, AdfButton.class);
    }

    public AdfButton findNoIconButton() {
        return findAdfComponent(noIconButton, AdfButton.class);
    }

    public AdfButton findBoldButton() {
        return findAdfComponent(boldButton, AdfButton.class);
    }

    public AdfButton findArrangeButton() {
        return findAdfComponent(arrangeButton, AdfButton.class);
    }

    public AdfPanelGroupLayout findArrangeButtonPopupContent() {
        // popup facet of a button doesn't create real Popup component at client side so we look at component
        // within popup
        return findAdfComponent(arrangeButtonPopupContent, AdfPanelGroupLayout.class);
    }

    public AdfButton findShowPopupButton() {
        return findAdfComponent(showPopupButton, AdfButton.class);
    }

    public AdfPopup findShowPopupButtonPopup() {
        return findAdfComponent(showPopupButtonPopup, AdfPopup.class);
    }

    public AdfCommandMenuItem findShowPopupButtonCopyMenuItem() {
        return findAdfComponent(showPopupButtonCopyMenuItem, AdfCommandMenuItem.class);
    }

    public NewFileWindowDialog clickUseWindowButton() {
        findAdfComponent(useWindowButton, AdfButton.class).clickWithDialogDetect();
        return openedDialog(NewFileWindowDialog.class);
    }

    public AdfButton findUseWindowInlineDocButton() {
        return findAdfComponent(useWindowInlineDocButton, AdfButton.class);
    }

    public AdfButton findTargetFrameBlankButton() {
        WebElement link = findDocument().getElement().findElement(targetFrameBlankButton);
        return AdfComponent.forElement(link, AdfButton.class);
    }

}
