package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfMessage;
import com.redheap.selenium.junit.PageProvider;
import com.redheap.selenium.junit.SavePageSourceOnFailure;
import com.redheap.selenium.junit.ScreenshotOnFailure;
import com.redheap.selenium.junit.WebDriverResource;
import com.redheap.selenium.pages.MessageDemoPage;

import java.io.File;

import static org.junit.Assert.*;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

public class MessageTest {

    @ClassRule
    public static WebDriverResource driver = new WebDriverResource();
    @Rule
    public PageProvider<MessageDemoPage> pages =
        new PageProvider(MessageDemoPage.class, HOME_PAGE, driver.getDriver());
    @Rule
    public TestWatcher screenshotOnFailure = new ScreenshotOnFailure(driver.getDriver(), new File("errors"));
    @Rule
    public TestWatcher saveSourceOnFailure = new SavePageSourceOnFailure(driver.getDriver(), new File("errors"));

    private static final String HOME_PAGE = "http://localhost:7101/adf-richclient-demo/faces/components/message.jspx";

    @Test
    public void testInitialEmptyMessage() {
        AdfMessage msg = pages.goHome().findMessage();
        assertEquals("none", msg.getMessageType());
        assertEquals(null, msg.getMessage());
    }

    @Test
    public void testMessageType() {
        MessageDemoPage msgPage = pages.goHome();
        msgPage.setMessageType("info");
        AdfMessage msg = msgPage.findMessage();
        assertEquals("info", msg.getMessageType());
        assertEquals(null, msg.getMessage());
    }

    @Test
    public void testMessageTypeAndText() {
        MessageDemoPage msgPage = pages.goHome();
        msgPage.setMessageType("warning");
        msgPage.setMessage("sample message");
        AdfMessage msg = msgPage.findMessage();
        assertEquals("warning", msg.getMessageType());
        assertEquals("sample message", msg.getMessage());
    }

    public static void main(String[] args) {
        String[] args2 = { MessageTest.class.getName() };
        org.junit.runner.JUnitCore.main(args2);
    }

}
