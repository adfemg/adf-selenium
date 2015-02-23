package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfButton;
import com.redheap.selenium.dialogs.NewFileWindowDialog;
import com.redheap.selenium.junit.PageProvider;
import com.redheap.selenium.junit.SavePageSourceOnFailure;
import com.redheap.selenium.junit.ScreenshotOnFailure;
import com.redheap.selenium.junit.WebDriverResource;
import com.redheap.selenium.pages.ButtonDemoPage;

import java.io.File;

import java.util.HashSet;
import java.util.Set;

import oracle.adf.view.rich.automation.selenium.Dialog;
import oracle.adf.view.rich.automation.selenium.DialogManager;

import static org.junit.Assert.*;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import org.openqa.selenium.remote.RemoteWebDriver;


public class ButtonTest {

    @ClassRule
    public static WebDriverResource driver = new WebDriverResource();
    @Rule
    public PageProvider<ButtonDemoPage> pages = new PageProvider(ButtonDemoPage.class, HOME_PAGE, driver.getDriver());
    @Rule
    public TestWatcher screenshotOnFailure = new ScreenshotOnFailure(driver.getDriver(), new File("errors"));
    @Rule
    public TestWatcher saveSourceOnFailure = new SavePageSourceOnFailure(driver.getDriver(), new File("errors"));

    private static final String HOME_PAGE = "http://localhost:7101/adf-richclient-demo/faces/components/button.jspx";

    @Test
    public void testComponentGuideButton() {
        pages.goHome().clickComponentGuideButton();
    }

    @Test
    public void testDisabledButton() {
        AdfButton button = pages.goHome().findDisabledButton();
        assertTrue(button.isDisabled());
        assertTrue(button.hasIcon());
    }

    @Test
    public void testNoIconButton() {
        AdfButton button = pages.goHome().findNoIconButton();
        assertFalse(button.isDisabled());
        assertFalse(button.hasIcon());
        button.click();
    }

    @Test
    public void testBoldButton() {
        ButtonDemoPage page = pages.goHome();
        AdfButton bold = page.findBoldButton();
        assertFalse(bold.isSelected());
        bold.click();
        assertTrue(bold.isSelected());
        bold.click();
        assertFalse(bold.isSelected());
    }

    @Test
    public void testArrangeButton() {
        ButtonDemoPage page = pages.goHome();
        AdfButton button = page.findArrangeButton();
        // clicking the button should not open the popup
        button.click();
        assertFalse(page.findArrangeButtonPopupContent().isDisplayed());
        // clicking the dropdown button should open popup
        button.clickDropdownButton();
        assertTrue(page.findArrangeButtonPopupContent().isDisplayed());
        // clicking other button should close popup
        page.findNoIconButton().click();
        assertFalse(page.findArrangeButtonPopupContent().isDisplayed());
    }

    @Test
    public void testPopupButton() {
        ButtonDemoPage page = pages.goHome();
        AdfButton button = page.findShowPopupButton();
        // clicking the button should open the popup (dropdown menu style)
        button.click();
        assertTrue(page.findShowPopupButtonPopup().isPopupVisible());
        // clicking item in popup menu should close popup
        page.findShowPopupButtonCopyMenuItem().click();
        assertFalse(page.findShowPopupButtonPopup().isDisplayed());
    }

    @Test
    public void testWindowPopupButton() {
        RemoteWebDriver webdriver = driver.getDriver();
        ButtonDemoPage page = pages.goHome();

        DialogManager dialogManager = driver.getDialogManager();
        assertEquals(0, dialogManager.totalNumberOfDialogsOpen());
        assertNull(dialogManager.getCurrentDialog());

        // find an click af:button with useWindow='true'
        NewFileWindowDialog dialog = page.clickUseWindowButton(); // opens popup in new browser window

        // verify new dialog is opened and active
        assertEquals(1, dialogManager.totalNumberOfDialogsOpen());
        Dialog firstDialog = dialogManager.getCurrentDialog();
        assertNotNull(firstDialog);
        assertEquals("New File", firstDialog.getTitle(webdriver));

        // close dialog by clicking Save button in dialog
        dialog.findSaveButton().clickWithDialogDetect();
        assertEquals(0, dialogManager.totalNumberOfDialogsOpen());
    }

    @Test
    public void testInlinePopupButton() {
        RemoteWebDriver webdriver = driver.getDriver();
        ButtonDemoPage page = pages.goHome();

        DialogManager dialogManager = driver.getDialogManager();
        assertEquals(0, dialogManager.totalNumberOfDialogsOpen());
        assertNull(dialogManager.getCurrentDialog());

        // find an click af:button with useWindow='true'
        NewFileWindowDialog dialog = page.clickUseWindowInlineDocButton(); // opens popup in inline popup with iframe

        // verify new dialog is opened and active
        assertEquals(1, dialogManager.totalNumberOfDialogsOpen());
        Dialog firstDialog = dialogManager.getCurrentDialog();
        assertNotNull(firstDialog);
        assertEquals("New File", firstDialog.getTitle(webdriver));

        // close dialog programatically (Save & Cancel buttons will throw error)
        assertFalse(dialog.findSaveButton().isDisabled());
        dialog.close();
        assertEquals(0, dialogManager.totalNumberOfDialogsOpen());
    }

    @Test
    public void testTargetFrameBlankButton() {
        RemoteWebDriver webdriver = driver.getDriver();
        ButtonDemoPage page = pages.goHome();

        DialogManager dialogManager = driver.getDialogManager();
        assertEquals(0, dialogManager.totalNumberOfDialogsOpen());
        assertNull(dialogManager.getCurrentDialog());

        String mainwindow = webdriver.getWindowHandle();
        // find an click af:button with targetFrame='_blank'
        page.findTargetFrameBlankButton().click();
        // verify new browser window has opened
        assertEquals(2, webdriver.getWindowHandles().size());
        String newwindow = detectNewWindow(webdriver, mainwindow);
        webdriver.switchTo().window(newwindow);
        assertTrue(webdriver.getTitle().startsWith("Oracle"));
        webdriver.close(); // close current window
        webdriver.switchTo().window(mainwindow);
        assertEquals(1, webdriver.getWindowHandles().size());
        assertEquals(mainwindow, webdriver.getWindowHandle());
        assertEquals("button Demo", webdriver.getTitle());
    }

    private String detectNewWindow(RemoteWebDriver webdriver, String mainwindow) {
        Set<String> handles = new HashSet<String>(webdriver.getWindowHandles());
        handles.remove(mainwindow);
        return handles.iterator().next();
    }

    public static void main(String[] args) {
        String[] args2 = { ButtonTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

}
