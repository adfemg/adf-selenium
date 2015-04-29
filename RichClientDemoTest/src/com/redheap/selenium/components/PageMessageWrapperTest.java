package com.redheap.selenium.components;

import com.redheap.selenium.component.uix.UixInput;
import com.redheap.selenium.domain.PageMessageWrapper;
import com.redheap.selenium.junit.PageProvider;
import com.redheap.selenium.junit.SavePageSourceOnFailure;
import com.redheap.selenium.junit.ScreenshotOnFailure;
import com.redheap.selenium.junit.WebDriverResource;
import com.redheap.selenium.pages.InputTextDemoPage;

import java.io.File;

import java.text.MessageFormat;

import static org.junit.Assert.assertTrue;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

/**
 * Class containing tests related to {@link com.redheap.selenium.domain.PageMessageWrapper PageMessageWrapper}.
 * <p>
 * These tests are about handling facesmessages and the convenience methods.
 */
public class PageMessageWrapperTest {

    @ClassRule
    public static WebDriverResource driver = new WebDriverResource();
    @Rule
    public PageProvider<InputTextDemoPage> pages =
        new PageProvider(InputTextDemoPage.class, HOME_PAGE, driver.getDriver());
    @Rule
    public TestWatcher screenshotOnFailure = new ScreenshotOnFailure(driver.getDriver(), new File("errors"));
    @Rule
    public TestWatcher saveSourceOnFailure = new SavePageSourceOnFailure(driver.getDriver(), new File("errors"));

    private static final String HOME_PAGE = "http://localhost:7101/adf-richclient-demo/faces/components/inputText.jspx";

    /**
     * General test dealing with basic message handling.
     */
    @Test
    public void testGetMessages() {
        InputTextDemoPage page = pages.goHome();
        PageMessageWrapper pageMessageWrapper = page.getAllMessages();
        assertTrue("No messages when page loaded", !pageMessageWrapper.hasMessages());

        //Generate some errors
        UixInput numberInputText = page.findNumberInputText();
        numberInputText.typeValue("Text should give an error");
        UixInput messageExampleInputText = page.findMessageExampleInputText();
        messageExampleInputText.typeValue("fatal");
        // Get the messages again
        pageMessageWrapper = page.getAllMessages();
        assertTrue("Text should give an error", pageMessageWrapper.hasMessages());
        assertTrue("Error text should be 'The number is not a whole number.'",
                   numberInputText.hasMessage("The number is not a whole number."));
        final String ASSERT_HAS_ERROR_TEXT = "Error text should be {0}";
        final String FATAL_ERROR_SUMMARY = "Fatal message SUMMARY text.";
        assertTrue(MessageFormat.format(ASSERT_HAS_ERROR_TEXT, FATAL_ERROR_SUMMARY),
                   messageExampleInputText.hasMessage("oeps" + FATAL_ERROR_SUMMARY));
    }

    public static void main(String[] args) {
        String[] args2 = { PageMessageWrapperTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

}
