package com.redheap.selenium;

import java.util.logging.Logger;

import oracle.adf.view.rich.automation.selenium.ByRich;
import oracle.adf.view.rich.automation.selenium.Dialog;
import oracle.adf.view.rich.automation.selenium.DialogManager;
import oracle.adf.view.rich.automation.selenium.RichWebDrivers;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Example of a Bot Style test where there is no separation between test classes and page object classes.
 * @see https://code.google.com/p/selenium/wiki/BotStyleTests
 */
public class BotStyleTest {

    private static final Logger logger = Logger.getLogger(BotStyleTest.class.getName());
    private static final long TIMEOUT_MSECS = 10000;
    private static final String BASE_URL = "http://localhost:7101/adf-richclient-demo";

    private RemoteWebDriver driver;
    private DialogManager dialogManager;

    @Before
    public void setup() {
        final FirefoxProfile profile = new FirefoxProfile();
        profile.setEnableNativeEvents(true);
        profile.setPreference("app.update.enabled", false);
        driver = new FirefoxDriver(profile);

        DialogManager.init(driver, TIMEOUT_MSECS,true);
        dialogManager = DialogManager.getInstance();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testWaitForPpr() throws Exception {
        logger.info("load demo page...");
        driver.get(BASE_URL);
        logger.info("wait for completion...");
        RichWebDrivers.waitForServer(driver, TIMEOUT_MSECS);
        logger.info("click search button...");
        driver.findElement(By.id("tmplt:gTools:glryFind:doFind")).click();
        logger.info("wait for completion...");
        RichWebDrivers.waitForServer(driver, TIMEOUT_MSECS);
    }

    @Test
    public void testWaitForPprRichWebDrivers() throws Exception {
        logger.info("load demo page...");
        driver.get(BASE_URL);
        logger.info("wait for completion...");
        RichWebDrivers.waitForRichPageToLoad(driver, TIMEOUT_MSECS);
        logger.info("click search button...");
        driver.findElement(By.id("tmplt:gTools:glryFind:doFind")).click();
        logger.info("wait for completion...");
        RichWebDrivers.waitForServer(driver, TIMEOUT_MSECS);
    }

    @Test
    public void testDialogFramework() throws Exception {

        driver.get(BASE_URL + "/faces/components/button.jspx");
        RichWebDrivers.waitForRichPageToLoad(driver, TIMEOUT_MSECS);
        assertTrue("oracle.adf.view.rich.automation.ENABLED must be true and adf-richclient-automation-11.jar must be on classpath",
                   (Boolean) driver.executeScript("return AdfPage.PAGE.isAutomationEnabled()"));

        // assert browser and dialog state before we get started
        assertEquals("initially ADF dialogManager should find zero dialogs", 0,
                     dialogManager.totalNumberOfDialogsOpen(driver));
        assertNull("initially ADF dialogManager should report no current dialog", dialogManager.getCurrentDialog());

        // click button with useWindow="true" and action="dialog:createNewFile"
        final WebElement launchDialogButton = driver.findElement(ByRich.locator("rich=dmoTpl:usewindowButton"));
        launchDialogButton.click();
        RichWebDrivers.waitForServer(driver, TIMEOUT_MSECS, /*useAutomaticDialogDetection*/true);

        // verify new dialog is opened and active
        assertEquals("after launching first dialog, ADF dialogManager should find single dialog", 1,
                     dialogManager.totalNumberOfDialogsOpen(driver));
        final Dialog firstDialog = dialogManager.getCurrentDialog();
        assertNotNull("after launching first dialog, ADF dialogManager should detect dialog having focus", firstDialog);
        assertEquals("title of dialog should be 'New File'", "New File", firstDialog.getTitle(driver));
        assertTrue("firstDialog starts out being alive", firstDialog.isAlive(driver));

        // demonstrate we can also locate new dialog by title and launching component id
        assertEquals("locating dialog by its title should find it", firstDialog,
                     dialogManager.getDialogBy(driver, DialogManager.SelectDialogBy.TITLE, "New File"));
        assertEquals("locating dialog by its launching component id should find it", firstDialog,
                     dialogManager.getDialogBy(driver, DialogManager.SelectDialogBy.LAUNCH_SOURCE_ID,
                                               "dmoTpl:usewindowButton"));

        // switch back to main window (but keep popup window open)
        dialogManager.selectMainWindow(driver);
        assertEquals("dialog remains running after switching back to main window", 1,
                     dialogManager.totalNumberOfDialogsOpen(driver));
        assertNull("dialog no longer reported as current after switching back to main window",
                   dialogManager.getCurrentDialog());

        // click button again to open a second popup
        launchDialogButton.click();
        RichWebDrivers.waitForServer(driver, TIMEOUT_MSECS, /*useAutomaticDialogDetection*/true);

        // verify new dialog is opened and active
        assertEquals("two dialogs after clicking button for second time", 2, dialogManager.totalNumberOfDialogsOpen(driver));
        Dialog secondDialog = dialogManager.getCurrentDialog();
        assertEquals("second dialog also has 'New File' title", "New File", secondDialog.getTitle(driver));
        assertNotEquals("second dialog has different handle than first dialog", firstDialog, secondDialog);
        // note: dialogManager.getDialogBy results are now uncertain as both dialogs have same title
        // and launching component

        // close first dialog (which is not the current dialog).
        // DialogManager also resets active window to main window when detecting closed dialogs
        firstDialog.close(driver); // includes RichWebDrivers.waitForServer(..,..,true)
        assertEquals("one dialog remaining after closing first dialog", 1, dialogManager.totalNumberOfDialogsOpen(driver));
        assertEquals("main browser window active after closing first dialog", null, dialogManager.getCurrentDialog());
        assertFalse("first dialog handle should be marked dead", firstDialog.isAlive(driver));

        // make second dialog current and show we can close current dialog
        secondDialog.focus(driver);
        assertEquals("second dialog window active after switching to it", secondDialog,
                     dialogManager.getCurrentDialog());
        secondDialog.close(driver); // includes RichWebDrivers.waitForServer(..,..,true)
        assertEquals("no dialogs open after closing second dialog", 0, dialogManager.totalNumberOfDialogsOpen(driver));
        assertNull("main browser window active after closing second dialog", dialogManager.getCurrentDialog());
        assertFalse("first dialog handle still marked dead after closing second dialog", firstDialog.isAlive(driver));
        assertFalse("second dialog handle marked dead after closing", secondDialog.isAlive(driver));

        // click button once more to open new popup, but this time close with save button in popup
        launchDialogButton.click();
        RichWebDrivers.waitForServer(driver, TIMEOUT_MSECS, /*useAutomaticDialogDetection*/true);
        assertEquals("one dialog running for final test", 1, dialogManager.totalNumberOfDialogsOpen(driver));
        assertNotNull("dialog active after launching for final test", dialogManager.getCurrentDialog());
        driver.findElement(ByRich.locator("rich=saveNewFile")).click();
        RichWebDrivers.waitForServer(driver, TIMEOUT_MSECS, /*useAutomaticDialogDetection*/true);
        assertEquals("no dialogs running after pressing Save button in dialog", 0,
                     dialogManager.totalNumberOfDialogsOpen(driver));
        assertNull("main browser window active afer pressing Save button in dialog", dialogManager.getCurrentDialog());
    }

    public static void main(String[] args) {
        String[] args2 = { BotStyleTest.class.getName() };
        JUnitCore.main(args2);
    }

}
