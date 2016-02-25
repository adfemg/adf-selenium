package com.redheap.selenium.components;

import com.redheap.selenium.component.AdfMessage;
import com.redheap.selenium.pages.MessageDemoPage;

import static org.junit.Assert.*;
import org.junit.Test;

public class MessageTest extends PageTestBase<MessageDemoPage> {

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

    @Override
    protected Class<MessageDemoPage> getPageClass() {
        return MessageDemoPage.class;
    }

    @Override
    protected String getJspxName() {
        return "message.jspx";
    }
}
