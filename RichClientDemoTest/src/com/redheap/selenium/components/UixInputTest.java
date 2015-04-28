package com.redheap.selenium.components;

import com.redheap.selenium.component.uix.UixInput;
import com.redheap.selenium.junit.PageProvider;
import com.redheap.selenium.junit.SavePageSourceOnFailure;
import com.redheap.selenium.junit.ScreenshotOnFailure;
import com.redheap.selenium.junit.WebDriverResource;
import com.redheap.selenium.pages.InputTextDemoPage;

import java.io.File;

import java.util.Map;

import static org.junit.Assert.assertTrue;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

public class UixInputTest {

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

    @Test
    public void testGetMessages() {
        InputTextDemoPage page = pages.goHome();
        UixInput numberInputText = page.findNumberInputText();
        Map messages = numberInputText.getMessages();
        Map pageMessages = numberInputText.getPageMessages();
        assertTrue("No messages when page loaded", pageMessages.isEmpty());
        assertTrue("No messages when page loaded", messages.isEmpty());

        //Generate some errors
        numberInputText.typeValue("Text should give an error");
        UixInput messageExampleInputText = page.findMessageExampleInputText();
        messageExampleInputText.typeValue("fatal");
        // Get the messages again
        pageMessages = numberInputText.getPageMessages();
        assertTrue("Text should give an error", !pageMessages.isEmpty());
        messages = numberInputText.getMessages();
        assertTrue("Text should give an error", !messages.isEmpty());
    }

    public static void main(String[] args) {
        String[] args2 = { UixInputTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

}
