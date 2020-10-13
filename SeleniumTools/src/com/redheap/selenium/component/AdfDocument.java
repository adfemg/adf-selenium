package com.redheap.selenium.component;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;


public class AdfDocument extends AdfComponent {

    private static final String JS_SET_ANIMATION_ENABLED = "return AdfPage.PAGE.setAnimationEnabled(arguments[0])";
    private static final String JS_IS_AUTOMATION_ENABLED = "return AdfPage.PAGE.isAutomationEnabled()";
    private static final String SUBID_page_alertConfirmDialog = "page.alertConfirmDialog";// ${path to adffacesdemo jdev project}/adffacesdemo/public_html/docs/js-subids.html#document
    private static final String SUBID_page_alertConfirmPopup = "page.alertConfirmPopup";
    private static final String SUBID_page_dialogService = "page.dialogService";
    private static final String SUBID_page_messageDialog = "page.messageDialog"; 

        
    public AdfDocument(WebDriver driver, String clientid) {
        super(driver, clientid);
    }

    public static AdfDocument forDriver(WebDriver driver) {
        JavascriptExecutor jsdriver = (JavascriptExecutor) driver;
        String docid = (String) jsdriver.executeScript("return AdfPage.PAGE.getDocumentClientId()");
        return AdfComponent.forClientId(driver, docid);
    }

    public void setAnimationEnabled(boolean enabled) {
        executeScript(JS_SET_ANIMATION_ENABLED, enabled);
    }

    public boolean isAutomationEnabled() {
        return (Boolean) executeScript(JS_IS_AUTOMATION_ENABLED);
    }
    
    public AdfDialog findPageAlertConfirmDialog() {
        return findSubIdComponent(SUBID_page_alertConfirmDialog);
    }
    
    public AdfPopup findPageAlertConfirmPopup() {
        return findSubIdComponent(SUBID_page_alertConfirmPopup);
    }
    
    public AdfDialog findPageDialogService() {
        return findSubIdComponent(SUBID_page_dialogService);
    }
    
    public AdfDialog findPageMessageDialog() {
        return findSubIdComponent(SUBID_page_messageDialog);
    }

}
